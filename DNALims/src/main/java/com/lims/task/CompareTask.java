/**
 * CompareTask.java
 *
 * 2013-7-13
 */
package com.lims.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lims.domain.po.CompareQueue;
import com.lims.domain.po.CompareResult;
import com.lims.domain.po.ComparisonSettings;
import com.lims.domain.po.GeneInfo;
import com.lims.exception.ServiceException;
import com.lims.service.comparison.CompareQueueService;
import com.lims.service.comparison.CompareResultService;
import com.lims.service.comparison.ComparisonSettingsService;
import com.lims.service.gene.GeneInfoService;
import com.lims.service.sample.SampleInfoService;
import com.lims.util.DateUtil;
import com.lims.util.ListUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 */
public class CompareTask {
	/**
	 * 是否自动启动任务标识
	 */
	private boolean autoStartup;

	static final int TASK_SIZE_100 = 100;

	@Resource
	private CompareQueueService compareQueueService;

	@Resource
	private GeneInfoService geneInfoService;

	@Resource
	private SampleInfoService sampleInfoService;

	@Resource
	private CompareResultService compareResultService;

	@Resource
	private ComparisonSettingsService comparisonSettingsService;

//	public static boolean flag = false;

	Log log = LogFactory.getLog(CompareTask.class);

	/**
	 * spring + quartz-scheduler实现任务调度
	 *
	 */
	public void work() {
		//autoStartup is False, return;
		if(!autoStartup){
			return;
		}

		//FOR CONVERT DATA
//		List<GeneInfo> geneInfoList = null;
//		geneInfoList = geneInfoService.findAllGeneInfoList();
//		StringBuffer newGenotypeStr = null;
//		for(GeneInfo gi : geneInfoList){
//			newGenotypeStr = new StringBuffer();
//			if(StringUtils.isEmpty(gi.getGenotypeInfo())){
//				continue;
//			}
//
//			String[] genotypeStrArr = gi.getGenotypeInfo().split(";");
//			for(String genotypeStr : genotypeStrArr){
//				if(StringUtil.findCharCountInString(genotypeStr, '/') == 2){
//					newGenotypeStr.append(genotypeStr).append(";");
//					continue;
//				}
//
//				if(genotypeStr.equals("/")){
//					newGenotypeStr.append(genotypeStr).append(genotypeStr).append(";");
//				}else{
//					String[] alleleArr = genotypeStr.split("/");
//					if(!StringUtils.isEmpty(alleleArr[0])
//							&& !StringUtils.isEmpty(alleleArr[1])
//							&& !"X".equalsIgnoreCase(alleleArr[0])){
//						if(alleleArr[0].equals(alleleArr[1])){
//							newGenotypeStr.append(alleleArr[0]).append("//;");
//						}else{
//							newGenotypeStr.append(alleleArr[0]).append("/").append(alleleArr[1]).append("/;");
//						}
//					}
//				}
//			}
//
//			String tempStr = newGenotypeStr.toString();
//			if(!"".equals(tempStr)){
//				gi.setGenotypeInfo(tempStr.substring(0, tempStr.length()-1));
//
//				geneInfoService.updateGeneInfo(gi);
//			}
//		}
		//end convert
		//第一次执行完后删除此段代码


		log.info("********************** 比对任务开始工作! **********************");

//			flag = true;

		ComparisonSettings settings = null;
		List<CompareQueue> compareQueueList = null;
		List<GeneInfo> geneInfoList = null;
		try {
			log.info("********************** 获取待比对任务列表! **********************");
			compareQueueList = compareQueueService.findCompareQueueList(CompareQueue.TASK_STATUS_WAITING, TASK_SIZE_100);

			if(ListUtil.isEmptyList(compareQueueList)){
				compareQueueList = compareQueueService.findCompareQueueList(CompareQueue.TASK_STATUS_ERROR, -1);
			}

			if(ListUtil.isEmptyList(compareQueueList)){
				log.info("********************** 待比对任务列表为空，比对结束! **********************");
				return;
			}

			log.info("********************** 待比对任务个数：" + compareQueueList.size() + " **********************");

			settings = comparisonSettingsService.findComparisonSettings();
			//TODO 分批次获取
			geneInfoList = geneInfoService.findAllGeneInfoList();
		} catch(Exception e){
			log.error("获取比对数据时出错! Cause: " + e.getMessage(), e);
			return;
		}

		log.info("********************** 开始进行比对！ **********************");
		for(CompareQueue compareQueue : compareQueueList){
			List<CompareResult> compareResultList = new ArrayList<CompareResult>();
			CompareResult compareResult = null;

			for(GeneInfo geneInfo : geneInfoList){
				//同一个样本不做比对
				if(geneInfo.getSampleId().equals(compareQueue.getSampleId())){
					continue;
				}

				compareResult = compare(compareQueue, geneInfo, settings);
				if(compareResult != null){
					compareResultList.add(compareResult);
				}
			}


			compareQueue.setTaskStatus(CompareQueue.TASK_STATUS_SUCCESS);
			compareQueue.setUpdateDate(DateUtil.currentDate());
			compareQueue.setUpdateUser("CompareTask");

			try {
				compareResultService.insertCompareResult(compareResultList, compareQueue);
			} catch(ServiceException se) {
				log.error("保存比对结果时出错！ Cause: " + se.getMessage(), se);
			}
		}

		log.info("********************** 比对完成！ **********************");

//			flag = false;
	}


	private CompareResult compare(CompareQueue queue, GeneInfo geneInfo, ComparisonSettings settings) {
		String genotypeInfoOfQueue = queue.getGenetypeInfo();
		String targetGenotypeInfo = geneInfo.getGenotypeInfo();

		if(StringUtil.isNullOrEmpty(genotypeInfoOfQueue)
				|| StringUtil.isNullOrEmpty(targetGenotypeInfo)){
			return null;
		}

		String[] genotypeInfoArrOfQueue = genotypeInfoOfQueue.split(";");
		String[] targetGenotypeInfoArr = targetGenotypeInfo.split(";");
		int sameCount = 0;
		int diffCount = 0;
		for(int i = 0; i < genotypeInfoArrOfQueue.length && i < targetGenotypeInfoArr.length;  i++){
			if("//".equals(genotypeInfoArrOfQueue[i])
					|| "//".equals(targetGenotypeInfoArr[i])){
				continue;
			}

			if(genotypeInfoArrOfQueue[i].equalsIgnoreCase(targetGenotypeInfoArr[i])){
				sameCount++;
			}else{
				diffCount++;
			}
		}

		if(sameCount >= Integer.parseInt(settings.getSameLowerLimitNum())
				&& diffCount <= Integer.parseInt(settings.getDiffUpperLimitNum())) {
			Date currentDate = DateUtil.currentDate();
			CompareResult compareResult = new CompareResult();
			compareResult.setSourceGeneId(queue.getGeneId());
			compareResult.setMatchGeneId(geneInfo.getId());
			compareResult.setSameNum(sameCount);
			compareResult.setDiffNum(diffCount);
			compareResult.setMatchDate(currentDate);
			compareResult.setUpdateDate(currentDate);
			compareResult.setUpdateUser("CompareTask");

			return compareResult;
		} else {
			return null;
		}
	}


	/**
	 * @return the autoStartup
	 */
	public boolean isAutoStartup() {
		return autoStartup;
	}

	/**
	 * @param autoStartup the autoStartup to set
	 */
	public void setAutoStartup(boolean autoStartup) {
		this.autoStartup = autoStartup;
	}


	public CompareQueueService getCompareQueueService() {
		return compareQueueService;
	}


	public void setCompareQueueService(CompareQueueService compareQueueService) {
		this.compareQueueService = compareQueueService;
	}


	public GeneInfoService getGeneInfoService() {
		return geneInfoService;
	}


	public void setGeneInfoService(GeneInfoService geneInfoService) {
		this.geneInfoService = geneInfoService;
	}


	public SampleInfoService getSampleInfoService() {
		return sampleInfoService;
	}


	public void setSampleInfoService(SampleInfoService sampleInfoService) {
		this.sampleInfoService = sampleInfoService;
	}


	public ComparisonSettingsService getComparisonSettingsService() {
		return comparisonSettingsService;
	}


	public void setComparisonSettingsService(
			ComparisonSettingsService comparisonSettingsService) {
		this.comparisonSettingsService = comparisonSettingsService;
	}


	public CompareResultService getCompareResultService() {
		return compareResultService;
	}


	public void setCompareResultService(CompareResultService compareResultService) {
		this.compareResultService = compareResultService;
	}
}
