/**
 * ReagentKitServiceImpl.java
 *
 * 2013-7-9
 */
package com.lims.service.locus;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lims.domain.po.ReagentKit;
import com.lims.service.LimsBaseServiceImpl;

/**
 * @author lizhihua
 *
 * 试剂盒Service
 */
@Service("reagentKitService")
public class ReagentKitServiceImpl extends LimsBaseServiceImpl implements
		ReagentKitService {

	/**
	 * 根据id获取试剂盒信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ReagentKit findReagentKitById(String id) throws Exception {
		try {
			return dao.getSqlSession().selectOne("ReagentKitMapper.findReagentKitById", id);
		} catch(Exception e) {
			log.error("invoke ReagentKitService.findReagentKitById error!", e);
			throw e;
		}
	}

	/**
	 * 查询试剂盒列表
	 * @param reagentKit
	 * @return
	 * @throws Exception
	 */
	public List<ReagentKit> findReagentKitList(ReagentKit reagentKit) throws Exception {
		try {
			return dao.getSqlSession().selectList("ReagentKitMapper.findReagentKitList", reagentKit);
		} catch(Exception e) {
			log.error("invoke ReagentKitService.findReagentKitList error!", e);
			throw e;
		}
	}

	/**
	 * 获取所有试剂盒列表
	 * @return
	 * @throws Exception
	 */
	public List<ReagentKit> finalAllReagentKitList() throws Exception {
		try {
			return dao.getSqlSession().selectList("ReagentKitMapper.finalAllReagentKitList");
		} catch(Exception e) {
			log.error("invoke ReagentKitService.finalAllReagentKitList error!", e);
			throw e;
		}
	}


	/**
	 * 新增试剂盒
	 * @param reagentKit
	 * @throws Exception
	 */
	public void insertReagentKit(ReagentKit reagentKit) throws Exception {
		try {
			reagentKit.setId(keyGeneratorService.getNextKey());
			reagentKit.setCreateDate(new Date());

			dao.getSqlSession().insert("ReagentKitMapper.insertReagentKit", reagentKit);
		} catch(Exception e) {
			log.error("invoke ReagentKitService.insertReagentKit error!", e);
			throw e;
		}
	}

	/**
	 * 更新试剂盒列表
	 * @param reagentKit
	 * @throws Exception
	 */
	public void updateReagentKit(ReagentKit reagentKit) throws Exception {
		try {
			reagentKit.setUpdateDate(new Date());

			dao.getSqlSession().update("ReagentKitMapper.updateReagentKit", reagentKit);
		} catch(Exception e) {
			log.error("invoke ReagentKitService.updateReagentKit error!", e);
			throw e;
		}
	}


	public void deleteReagentKitById(String id) throws Exception {
		try {
			dao.getSqlSession().delete("ReagentKitMapper.deleteReagentKitById", id);
		} catch(Exception e) {
			log.error("invoke ReagentKitService.deleteReagentKitById error!", e);
			throw e;
		}
	}
}
