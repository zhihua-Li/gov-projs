/**
 * QuickComparisonServiceImpl.java
 *
 * 2014-1-11
 */
package com.lims.service.comparison;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.bo.ComparisonGene;
import com.lims.domain.vo.CompareResultView;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;
import com.lims.util.ListUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 */
@Service("quickComparisonService")
@Transactional
public class QuickComparisonServiceImpl extends LimsBaseServiceImpl implements
		QuickComparisonService {

	/**
	 * 单个样本快速比对
	 * @param
	 * 		genInfo	待比对基因型信息
	 * @param
	 * 		compareParams 比对条件
	 * @return
	 * 		比中结果列表
	 * @throws
	 * 		ServiceException
	 */
	public List<Map<String, Object>> singleSampleCompare(String genInfo, Map<String, Object> compareParams) throws ServiceException {
		try {
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			int offset = 0;
			int count = 1000;
			List<ComparisonGene> geneInfoList = null;

			Map<String, Integer> params = new HashMap<String, Integer>();
			params.put("offset", offset);
			params.put("count", count);

			while(true){
				geneInfoList = dao.getSqlSession().selectList("GeneInfoMapper.findComparisonGeneList", params);

				if(ListUtil.isEmptyList(geneInfoList)){
					break;
				}

				for(ComparisonGene cg : geneInfoList){
					Map<String, Object> resultMap = compare(genInfo, cg, compareParams);
					if(resultMap == null){
						continue;
					}

					resultList.add(resultMap);
				}

				if(geneInfoList.size() < count){
					break;
				}else{
					params.put("offset", offset + count);
				}
			}

			return resultList;
		} catch(Exception e) {
			log.error("单个样本快速比对时出现异常！", e);
			throw new ServiceException(e);
		}
	}


	private Map<String, Object> compare(String srcGeneInfo, ComparisonGene targetGene, Map<String, Object> params) {
		String targetGenotypeInfo = targetGene.getGenotypeInfo();

		if(StringUtil.isNullOrEmpty(srcGeneInfo)
				|| StringUtil.isNullOrEmpty(targetGenotypeInfo)){
			return null;
		}

		String[] srcGenotypeInfoArr = srcGeneInfo.split(";");
		String[] targetGenotypeInfoArr = targetGenotypeInfo.split(";");
		int sameCount = 0;
		int diffCount = 0;
		for(int i = 0; i < srcGenotypeInfoArr.length && i < targetGenotypeInfoArr.length; i++){
			if("//".equals(srcGenotypeInfoArr[i])
					|| "//".equals(targetGenotypeInfoArr[i])){
				continue;
			}

			if(srcGenotypeInfoArr[i].equalsIgnoreCase(targetGenotypeInfoArr[i])){
				sameCount++;
			}else{
				diffCount++;
			}
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(sameCount >= Integer.parseInt(params.get("sameLimit").toString())
				&& diffCount <= Integer.parseInt(params.get("diffLimit").toString())) {
			resultMap.put("sameCount", sameCount);
			resultMap.put("diffCount", diffCount);
			resultMap.put("matchedGeneId", targetGene.getId());

			return resultMap;
		} else {
			return null;
		}
	}

	public CompareResultView findQuickCompareMatchedSample(String matchedGeneId) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("CompareResultMapper.findQuickCompareMatchedSample", matchedGeneId);
		} catch(Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

}
