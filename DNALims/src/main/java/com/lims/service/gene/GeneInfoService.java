/**
 * GeneInfoService.java
 *
 * 2013-7-13
 */
package com.lims.service.gene;

import java.util.List;

import com.lims.domain.po.GeneInfo;
import com.lims.domain.vo.GeneInfoView;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 */
public interface GeneInfoService {

	public List<GeneInfo> findAllGeneInfoList() throws ServiceException;


	public GeneInfoView findGeneInfoViewBySampleId(String sampleId) throws ServiceException;


	public void updateGeneInfo(GeneInfo geneInfo) throws ServiceException;

}
