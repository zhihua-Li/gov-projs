/**
 * CompareQueueServiceImpl.java
 *
 * 2013-7-13
 */
package com.lims.service.comparison;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.CompareQueue;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;

/**
 * @author lizhihua
 *
 */
@Service("compareQueueService")
@Transactional
public class CompareQueueServiceImpl extends LimsBaseServiceImpl implements
		CompareQueueService {

	/**
	 * 获取比对队列列表
	 * @return
	 * @throws ServiceException
	 */
	public List<CompareQueue> findCompareQueueList(String taskStatus, int taskSize) throws ServiceException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("taskStatus", taskStatus);
			if(taskSize > 0){
				params.put("taskSize", new Integer(taskSize));
			}

			return dao.getSqlSession().selectList("CompareQueueMapper.findCompareQueueList", params);
		} catch(Exception e) {
			log.error("invoke CompareQueueService.findCompareQueueList error!", e);
			throw new ServiceException(e);
		}
	}
}
