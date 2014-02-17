/**
 * BoardInfoService.java
 *
 * 2013-6-18
 */
package com.lims.service.sample;

import java.util.List;
import java.util.Map;

import com.lims.domain.po.BoardInfo;
import com.lims.domain.po.BoardSampleMap;
import com.lims.domain.po.SampleInfo;
import com.lims.domain.po.SysUser;
import com.lims.domain.query.BoardInfoQuery;
import com.lims.domain.vo.BoardInfoView;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 */
public interface BoardInfoService {

	/**
	 * 添加boardInfo
	 * @param boardInfo
	 * @return
	 * @throws ServiceException
	 */
	public String addBoardInfo(BoardInfo boardInfo, String[] sampleNoArr, String[] positionNoArr, SysUser sysUser) throws ServiceException;

	/**
	 * 新增上样板
	 * @param boardInfo
	 * @param sysUser
	 * @return
	 * @throws ServiceException
	 */
	public String addBoardInfo(BoardInfo boardInfo, SysUser sysUser) throws ServiceException;

	public void updateBoardInfo(BoardInfo boardInfo, String[] sampleNoArr, String[] positionNoArr, SysUser sysUser) throws ServiceException;

	/**
	 * 更新上样板信息
	 * @param boardInfo
	 * @param sysUser
	 * @throws ServiceException
	 */
	public void updateBoardInfo(BoardInfo boardInfo, SysUser sysUser) throws ServiceException;

	/**
	 * 保存上样板与样本关系
	 * @param boardSampleMap
	 * @throws ServiceException
	 */
	public void addBoardSampleMap(BoardSampleMap boardSampleMap) throws ServiceException;

	/**
	 * 在上样板上新增样本（打孔取样）
	 * @param boardSampleMap
	 * @throws ServiceException
	 */
	public void addSampleInBoard(SampleInfo sample, BoardSampleMap boardSampleMap) throws ServiceException;

	/**
	 * 根据id获取上样板信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public BoardInfo findBoardInfoById(String id) throws ServiceException;

	public void deleteCKSampleByBoardId(String boardId) throws ServiceException;


	/*
	 * 删除上样板及相关数据
	 */
	public void deleteBoardInfoById(String id) throws ServiceException;
	
	/**
	 * 清除指定板孔上的样本（置空该位置，不删除样本）
	 * params:
	 * 		boardId,
	 * 		samplePosition
	 */
	public void deleteSampleOfBoardPosition(Map<String, String> params) throws ServiceException;

	/**
	 * 更新下载次数
	 */
	public void updateBoardDownloadCount(Map<String, Object> params) throws ServiceException;

	/**
	 * 根据上样板id获取对应样本列表
	 * @return
	 * @throws ServiceException
	 */
	public List<BoardSampleMap> findBoardSampleListByBoardId(String boardId) throws ServiceException;


	/**
	 * 根据boardInfo实体查询对应的上样板信息列表
	 * @param boardInfo
	 * @return
	 * @throws ServiceException
	 */
	public List<BoardInfoView> findBoardInfoList(BoardInfoQuery boardInfoQuery) throws ServiceException;

	/**
	 * 根据boardInfo实体查询对应的上样板信息个数
	 * @param boardInfo
	 * @return
	 * @throws ServiceException
	 */
	public int findBoardInfoListCount(BoardInfoQuery boardInfoQuery) throws ServiceException;

}
