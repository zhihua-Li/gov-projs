/**
 * GeneInfoServiceImpl.java
 *
 * 2013-7-13
 */
package com.lims.service.gene;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.GeneInfo;
import com.lims.domain.vo.GeneInfoView;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;

/**
 * @author lizhihua
 *
 */
@Service("geneInfoService")
@Transactional
public class GeneInfoServiceImpl extends LimsBaseServiceImpl implements
		GeneInfoService {

	public List<GeneInfo> findAllGeneInfoList() throws ServiceException {
		try {
			return dao.getSqlSession().selectList("GeneInfoMapper.findAllGeneInfoList");
		} catch(Exception e) {
			log.error("invoke GeneInfoService.findAllGeneInfoList error!", e);
			throw new ServiceException(e);
		}
	}

	public GeneInfoView findGeneInfoViewBySampleId(String sampleId) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("GeneInfoMapper.findGeneInfoViewBySampleId", sampleId);
		} catch(Exception e) {
			log.error("invoke GeneInfoService.findGeneInfoViewBySampleId error!", e);
			throw new ServiceException(e);
		}
	}

	public void updateGeneInfo(GeneInfo geneInfo) throws ServiceException {
		try {
			dao.getSqlSession().selectOne("GeneInfoMapper.updateGeneInfo", geneInfo);
		} catch(Exception e) {
			log.error("invoke GeneInfoService.updateGeneInfo error!", e);
			throw new ServiceException(e);
		}
	}

}
