/**
 * InstoreComparisonServiceImpl.java
 *
 * 2013-7-13
 */
package com.lims.service.comparison;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.constants.LimsConstants;
import com.lims.domain.po.CompareQueue;
import com.lims.domain.po.GeneInfo;
import com.lims.domain.po.SysUser;
import com.lims.domain.query.SampleSourceGeneQuery;
import com.lims.domain.vo.SampleSourceGeneView;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;
import com.lims.util.DateUtil;

/**
 * @author lizhihua
 *
 */
@Service("instoreComparisonService")
@Transactional
public class InstoreComparisonServiceImpl extends LimsBaseServiceImpl implements
		InstoreComparisonService {

	/**
	 * 查询未入库样本列表
	 * @param sampleSourceGeneQuery
	 * @return
	 * @throws ServiceException
	 */
	public List<SampleSourceGeneView> findWaitingInstoreSampleList(SampleSourceGeneQuery sampleSourceGeneQuery)
			throws ServiceException {
		try {
			return dao.getSqlSession().selectList("InstoreAndCompareMapper.findWaitingInstoreSampleList", sampleSourceGeneQuery);
		} catch(Exception e) {
			log.error("invoke InstoreComparisonService.findWaitingInstoreSampleList error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 查询未入库样本个数
	 * @param sampleSourceGeneQuery
	 * @return
	 * @throws ServiceException
	 */
	public int findWaitingInstoreSampleListCount(SampleSourceGeneQuery sampleSourceGeneQuery)
			throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("InstoreAndCompareMapper.findWaitingInstoreSampleListCount", sampleSourceGeneQuery);
		} catch(Exception e) {
			log.error("invoke InstoreComparisonService.findWaitingInstoreSampleListCount error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 *
	 * 入库
	 * @param sourceGeneList
	 * @throws ServiceException
	 */
	public void instoreSampleSourceGeneList(List<SampleSourceGeneView> sourceGeneList, SysUser sysUser) throws ServiceException {
		try {
			Date currentDate = DateUtil.currentDate();
			List<String> sourceGeneIdList = new ArrayList<String>();
			List<GeneInfo> geneInfoList = new ArrayList<GeneInfo>();
			List<CompareQueue> queueList = new ArrayList<CompareQueue>();
			GeneInfo gi = null;
			for(SampleSourceGeneView ssg : sourceGeneList){

				sourceGeneIdList.add(ssg.getId());
				gi = convertSourceGene2GeneInfo(ssg, sysUser, currentDate);
				geneInfoList.add(gi);
				queueList.add(getCompareQueueByGeneInfo(gi, sysUser, currentDate));
			}

			Map<String, Object> sourceGeneParams = new HashMap<String, Object>();
			sourceGeneParams.put("alignDbFlag", "1");
			sourceGeneParams.put("updateUser", sysUser.getId());
			sourceGeneParams.put("updateDate", currentDate);
			sourceGeneParams.put("sourceGeneIdList", sourceGeneIdList);
			dao.getSqlSession().update("SampleSourceGeneMapper.updateGeneInstoreFlagBatch", sourceGeneParams);

			dao.getSqlSession().insert("GeneInfoMapper.insertGeneInfoBatch", geneInfoList);

			dao.getSqlSession().insert("CompareQueueMapper.insertCompareQueueBatch", queueList);
		} catch(Exception e) {
			log.error("invoke InstoreComparisonService.findWaitingInstoreSampleListCount error!", e);
			throw new ServiceException(e);
		}
	}

	private GeneInfo convertSourceGene2GeneInfo(SampleSourceGeneView ssg, SysUser sysUser, Date currentDate) throws ServiceException {
		GeneInfo gi = new GeneInfo();
		gi.setId(keyGeneratorService.getNextKey());
		gi.setSampleId(ssg.getSampleId());
		gi.setSampleType(GeneInfo.SAMPLE_TYPE_NORMAL);
		gi.setGeneType(LimsConstants.GENE_TYPE_STR);
		gi.setReagentKit(ssg.getReagentKitId());
		gi.setGenotypeInfo(ssg.getGenotypeInfo());
		gi.setStoreDate(currentDate);
		gi.setStoreUser(sysUser.getId());

		return gi;
	}

	private CompareQueue getCompareQueueByGeneInfo(GeneInfo gi, SysUser sysUser, Date currentDate) throws ServiceException {
		CompareQueue cq = new CompareQueue();
		cq.setId(keyGeneratorService.getNextKey());
		cq.setGeneId(gi.getId());
		cq.setTaskStatus(CompareQueue.TASK_STATUS_WAITING);
		cq.setCreateUser(sysUser.getId());
		cq.setCreateDate(currentDate);

		return cq;
	}

}
