/**
 * SysDictService.java
 *
 * 2013-6-4
 */
package com.lims.service.dict;

import java.util.List;

import com.lims.domain.po.DictItem;

/**
 * @author lizhihua
 *
 * 字典Service
 *
 */
public interface SysDictService {

	/**
	 * 根据字典类型获取字典项列表
	 * @return
	 * @throws ServiceException
	 */
	public List<DictItem> findDictItemListByDictInfoType(String dictInfoType) throws Exception;


	public void addDictItem(DictItem dictItem) throws Exception;

	public void updateDictItem(DictItem dictItem) throws Exception;

	public void deleteDictItemById(String id) throws Exception;

}
