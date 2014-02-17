/**
 * ConsignOrganizationServiceImpl.java
 *
 * 2013-6-2
 */
package com.lims.service.organization;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.ConsignOrganization;
import com.lims.service.LimsBaseServiceImpl;

/**
 * @author lizhihua
 *
 */
@Service("consignOrganizationService")
@Transactional
public class ConsignOrganizationServiceImpl extends LimsBaseServiceImpl implements
		ConsignOrganizationService {


	/**
	 * 获取委托单位列表
	 * @return
	 * @throws Exception
	 */
	public List<ConsignOrganization> findConsignOrganizationList(ConsignOrganization consignOrganization) throws Exception {
		try {
			return dao.getSqlSession().selectList("ConsignOrganizationMapper.findConsignOrganizationList", consignOrganization);
		} catch(Exception e) {
			log.error("invoke ConsignOrganizationService.findConsignOrganizationList error!", e);
			throw e;
		}
	}


	/**
	 * 获取委托单位个数
	 * @return
	 * @throws Exception
	 */
	public int findConsignOrganizationListCount(ConsignOrganization consignOrganization) throws Exception {
		try {
			return dao.getSqlSession().selectOne("ConsignOrganizationMapper.findConsignOrganizationListCount", consignOrganization);
		} catch(Exception e) {
			log.error("invoke ConsignOrganizationService.findConsignOrganizationListCount error!", e);
			throw e;
		}
	}

	/**
	 * 获取所有委托单位列表
	 * @return
	 * @throws Exception
	 */
	public List<ConsignOrganization> findAllConsignOrganizationList() throws Exception {
		try {
			return dao.getSqlSession().selectList("ConsignOrganizationMapper.findAllConsignOrganizationList");
		} catch(Exception e) {
			log.error("invoke ConsignOrganizationService.findAllConsignOrganizationList error!", e);
			throw e;
		}
	}


	public ConsignOrganization findConsignOrganizationById(String id) throws Exception {
		try {
			return dao.getSqlSession().selectOne("ConsignOrganizationMapper.findConsignOrganizationById", id);
		} catch(Exception e) {
			log.error("invoke ConsignOrganizationService.findConsignOrganizationById error!", e);
			throw e;
		}
	}


	public void addConsignOrganization(ConsignOrganization consignOrganization) throws Exception {
		try {
			consignOrganization.setId(keyGeneratorService.getNextKey());
			dao.getSqlSession().insert("ConsignOrganizationMapper.insertConsignOrganization", consignOrganization);
		} catch(Exception e) {
			log.error("invoke ConsignOrganizationService.addConsignOrganization error!", e);
			throw e;
		}
	}

	public void updateConsignOrganization(ConsignOrganization consignOrganization) throws Exception {
		try {
			dao.getSqlSession().update("ConsignOrganizationMapper.updateConsignOrganization", consignOrganization);
		} catch(Exception e) {
			log.error("invoke ConsignOrganizationService.updateConsignOrganization error!", e);
			throw e;
		}
	}


	public void deleteConsignOrganizationById(String id) throws Exception {
		try {
			dao.getSqlSession().delete("ConsignOrganizationMapper.deleteConsignOrganizationById", id);
		} catch(Exception e) {
			log.error("invoke ConsignOrganizationService.deleteConsignOrganizationById error!", e);
			throw e;
		}
	}

}
