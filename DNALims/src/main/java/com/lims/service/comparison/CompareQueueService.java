/**
 * CompareQueueService.java
 *
 * 2013-7-13
 */
package com.lims.service.comparison;

import java.util.List;

import com.lims.domain.po.CompareQueue;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 */
public interface CompareQueueService {

	/**
	 * 获取比对队列列表
	 * @return
	 * @throws ServiceException
	 */
	public List<CompareQueue> findCompareQueueList(String taskStatus, int taskSize) throws ServiceException;

}
