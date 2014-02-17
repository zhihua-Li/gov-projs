/**
 * CodisSampleServiceImpl.java
 *
 * 2013-10-8
 */
package com.lims.service.codissample;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.CodisSample;
import com.lims.domain.po.GeneInfo;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;
import com.lims.util.ListUtil;

/**
 * @author lizhihua
 *
 */
@Service("codisSampleService")
@Transactional
public class CodisSampleServiceImpl extends LimsBaseServiceImpl implements
		CodisSampleService {

	/**
	 * 根据样本编号获取CODIS样本信息
	 * @param sampleNo
	 * @return
	 * @throws ServiceException
	 */
	public CodisSample findCodisSampleBySampleNo(String sampleNo) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("CodisSampleMapper.findCodisSampleBySampleNo", sampleNo);
		} catch(Exception e) {
			log.error("invoke CodisSampleService.findCodisSampleBySampleNo error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 保存导入的codis中的样本及基因信息
	 * @throws ServiceException
	 */
	public void insertImportCodisSample(List<CodisSample> codisSampleList, List<GeneInfo> geneInfoList) throws ServiceException {
		try {
			dao.getSqlSession().insert("CodisSampleMapper.insertCodisSampleBatch", codisSampleList);

			dao.getSqlSession().insert("GeneInfoMapper.insertGeneInfoBatch", geneInfoList);
		} catch(Exception e) {
			log.error("invoke CodisSampleService.insertImportCodisSample error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 删除codis样本
	 * @param sampleNo
	 * @throws ServiceException
	 */
	public void deleteSampleBySampleNo(String sampleNo) throws ServiceException {
		try {
			dao.getSqlSession().delete("CodisSampleMapper.deleteCodisSampleBySampleNo", sampleNo);

			dao.getSqlSession().delete("CodisSampleMapper.deleteCodisSampleGeneBySampleNo", sampleNo);
		} catch(Exception e) {
			log.error("invoke CodisSampleService.deleteSampleBySampleNo error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 删除codis样本
	 * @param sampleNo
	 * @throws ServiceException
	 */
	public void deleteSamplesBySampleNoArr(String[] sampleNoArr) throws ServiceException {
		try {
			dao.getSqlSession().delete("CodisSampleMapper.deleteCodisSampleBySampleNoList", ListUtil.stringArrayToList(sampleNoArr));

			dao.getSqlSession().delete("CodisSampleMapper.deleteCodisSampleGeneBySampleNoList", ListUtil.stringArrayToList(sampleNoArr));
		} catch(Exception e) {
			log.error("invoke CodisSampleService.deleteSamplesBySampleNoArr error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 修改codis样本
	 * @param codisSample
	 * @throws ServiceException
	 */
	public void updateCodisSample(CodisSample codisSample) throws ServiceException {
		try {
			dao.getSqlSession().delete("CodisSampleMapper.updateCodisSample", codisSample);
		} catch(Exception e) {
			log.error("invoke CodisSampleService.updateCodisSample error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取codis样本总数
	 * @param codisSample
	 * @return
	 * @throws ServiceException
	 */
	public int findCodisSampleListCount(CodisSample codisSample) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("CodisSampleMapper.findCodisSampleListCount", codisSample);
		} catch(Exception e) {
			log.error("invoke CodisSampleService.findCodisSampleListCount error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取codis样本列表
	 * @param codisSample
	 * @return
	 * @throws ServiceException
	 */
	public List<CodisSample> findCodisSampleList(CodisSample codisSample) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("CodisSampleMapper.findCodisSampleList", codisSample);
		} catch(Exception e) {
			log.error("invoke CodisSampleService.findCodisSampleList error!", e);
			throw new ServiceException(e);
		}
	}

	public GeneInfo findCodisSampleGeneInfoBySampleId(String sampleId) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("CodisSampleMapper.findCodisSampleGeneInfoBySampleId", sampleId);
		} catch(Exception e) {
			log.error("invoke CodisSampleService.findCodisSampleGeneInfoBySampleId error!", e);
			throw new ServiceException(e);
		}
	}

	public GeneInfo findCodisSampleGeneInfoById(String sampleId) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("CodisSampleMapper.findCodisSampleGeneInfoById", sampleId);
		} catch(Exception e) {
			log.error("invoke CodisSampleService.findCodisSampleGeneInfoById error!", e);
			throw new ServiceException(e);
		}
	}

}
