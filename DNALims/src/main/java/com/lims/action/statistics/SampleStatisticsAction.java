/**
 * SampleStatisticsAction.java
 *
 * 2013-9-24
 */
package com.lims.action.statistics;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.domain.query.SampleInfoQuery;
import com.lims.service.sample.SampleInfoService;

/**
 * @author lizhihua
 *
 */
public class SampleStatisticsAction extends BaseAction {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private SampleInfoQuery sampleInfoQuery;

	private List<Map<String, Object>> resultList;

	@Resource
	private SampleInfoService sampleInfoService;

	public String query() throws Exception {

		if(sampleInfoQuery == null){
			sampleInfoQuery = new SampleInfoQuery();

			sampleInfoQuery.setSortColumn("organizationCode");
			sampleInfoQuery.setSortOrder("asc");
		}

		resultList = sampleInfoService.sampleStatistics(sampleInfoQuery);

		return SUCCESS;
	}

	public List<Map<String, Object>> getResultList() {
		return resultList;
	}

	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}

	public SampleInfoService getSampleInfoService() {
		return sampleInfoService;
	}

	public void setSampleInfoService(SampleInfoService sampleInfoService) {
		this.sampleInfoService = sampleInfoService;
	}
}
