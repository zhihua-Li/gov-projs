/**
 * ComparisonSettingsServiceImpl.java
 *
 * 2013-7-13
 */
package com.lims.service.comparison;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.ComparisonSettings;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;
import com.lims.util.DateUtil;

/**
 * @author lizhihua
 *
 */
@Service("comparisonSettingsService")
@Transactional
public class ComparisonSettingsServiceImpl extends LimsBaseServiceImpl
		implements ComparisonSettingsService {


	public ComparisonSettings findComparisonSettings() throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("ComparisonSettingsMapper.findComparisonSettings");
		} catch(Exception e) {
			log.error("invoke ComparisonSettingsService.findComparisonSettings error!", e);
			throw new ServiceException(e);
		}
	}


	public void updateComparisonSettings(ComparisonSettings settings) throws ServiceException {
		try {
			settings.setUpdateDate(DateUtil.currentDate());

			dao.getSqlSession().update("ComparisonSettingsMapper.updateComparisonSettings", settings);
		} catch(Exception e) {
			log.error("invoke ComparisonSettingsService.updateComparisonSettings error!", e);
			throw new ServiceException(e);
		}
	}
}
