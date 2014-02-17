/**
 *
 */
package com.lims.service;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lims.dao.LimsBaseDao;

/**
 * @author lizhihua
 *
 */
public class LimsBaseServiceImpl implements LimsBaseService {

	@Resource
	protected LimsBaseDao dao;

	@Autowired
	protected KeyGeneratorService keyGeneratorService;

	protected final Log log = LogFactory.getLog(getClass());

	public LimsBaseDao getDao() {
		return dao;
	}

	public void setDao(LimsBaseDao dao) {
		this.dao = dao;
	}

	public KeyGeneratorService getKeyGeneratorService() {
		return keyGeneratorService;
	}

	public void setKeyGeneratorService(KeyGeneratorService keyGeneratorService) {
		this.keyGeneratorService = keyGeneratorService;
	}
}
