/**
 * LocusInfoServiceImpl.java
 *
 * 2013-7-9
 */
package com.lims.service.locus;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.LocusInfo;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 * 基因座管理Service
 *
 */
@Service("locusInfoService")
@Transactional
public class LocusInfoServiceImpl extends LimsBaseServiceImpl implements
		LocusInfoService {

	/**
	 * 根据id获取基因座信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public LocusInfo findLocusInfoById(String id) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("LocusInfoMapper.findLocusInfoById", id);
		} catch(Exception e) {
			log.error("invoke LocusInfoService.findLocusInfoById error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 查询基因座列表
	 * @param locusInfo
	 * @return
	 * @throws ServiceException
	 */
	public List<LocusInfo> findLocusInfoList(LocusInfo locusInfo) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("LocusInfoMapper.findLocusInfoList", locusInfo);
		} catch(Exception e) {
			log.error("invoke LocusInfoService.findLocusInfoList error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取所有基因座列表
	 * @return
	 * @throws ServiceException
	 */
	public List<LocusInfo> findAllLocusInfoList() throws ServiceException {
		try {
			return dao.getSqlSession().selectList("LocusInfoMapper.findAllLocusInfoList");
		} catch(Exception e) {
			log.error("invoke LocusInfoService.finalAllLocusInfoList error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 新增基因座
	 * @param locusInfo
	 * @throws ServiceException
	 */
	public LocusInfo insertLocusInfo(LocusInfo locusInfo) throws ServiceException {
		try {
			locusInfo.setId(keyGeneratorService.getNextKey());
			locusInfo.setCreateDate(new Date());

			int maxGenoNo = dao.getSqlSession().selectOne("LocusInfoMapper.findMaxGenoNo");
			locusInfo.setGenoNo(StringUtil.getFullChar(String.valueOf(maxGenoNo + 1), '0', 2));

			dao.getSqlSession().insert("LocusInfoMapper.insertLocusInfo", locusInfo);

			return locusInfo;
		} catch(Exception e) {
			log.error("invoke LocusInfoService.insertLocusInfo error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 更新基因座列表
	 * @param locusInfo
	 * @throws ServiceException
	 */
	public void updateLocusInfo(LocusInfo locusInfo) throws ServiceException {
		try {
			locusInfo.setUpdateDate(new Date());

			dao.getSqlSession().update("LocusInfoMapper.updateLocusInfo", locusInfo);
		} catch(Exception e) {
			log.error("invoke LocusInfoService.updateLocusInfo error!", e);
			throw new ServiceException(e);
		}
	}
}
