/**
 * SysDictServiceImpl.java
 *
 * 2013-6-4
 */
package com.lims.service.dict;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.DictItem;
import com.lims.service.LimsBaseServiceImpl;

/**
 * @author lizhihua
 *
 */
@Service("sysDictService")
@Transactional
public class SysDictServiceImpl extends LimsBaseServiceImpl implements SysDictService {

	/**
	 * 根据字典类型获取字典项列表
	 * @return
	 * @throws Exception
	 */
	public List<DictItem> findDictItemListByDictInfoType(String dictInfoType) throws Exception {
		try {
			return dao.getSqlSession().selectList("DictItemMapper.findDictItemsByDictInfoType", dictInfoType);
		} catch(Exception e) {
			log.error("invoke SysDictService.findDictItemListByDictInfoType error!", e);
			throw e;
		}
	}

	public void addDictItem(DictItem dictItem) throws Exception {
		try {
			dao.getSqlSession().insert("DictItemMapper.addDictItem", dictItem);
			} catch(Exception e) {
				log.error("invoke SysDictService.addDictItem error!", e);
				throw e;
			}
	}

	public void updateDictItem(DictItem dictItem) throws Exception {
		try {
			dao.getSqlSession().update("DictItemMapper.updateDictItem", dictItem);
			} catch(Exception e) {
				log.error("invoke SysDictService.updateDictItem error!", e);
				throw e;
			}
	}


	public void deleteDictItemById(String id) throws Exception {
		try {
			dao.getSqlSession().update("DictItemMapper.deleteDictItemById", id);
			} catch(Exception e) {
				log.error("invoke SysDictService.deleteDictItemById error!", e);
				throw e;
			}
	}

}
