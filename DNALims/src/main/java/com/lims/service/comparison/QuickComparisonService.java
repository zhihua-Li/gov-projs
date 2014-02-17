/**
 * QuickComparisonService.java
 *
 * 2014-1-11
 */
package com.lims.service.comparison;

import java.util.List;
import java.util.Map;

import com.lims.domain.vo.CompareResultView;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 */
public interface QuickComparisonService {


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
	public List<Map<String, Object>> singleSampleCompare(String genInfo, Map<String, Object> compareParams) throws ServiceException;


	public CompareResultView findQuickCompareMatchedSample(String matchedGeneId) throws ServiceException;

}
