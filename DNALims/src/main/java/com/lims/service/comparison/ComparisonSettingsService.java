/**
 * ComparisonSettingsService.java
 *
 * 2013-7-13
 */
package com.lims.service.comparison;

import com.lims.domain.po.ComparisonSettings;
import com.lims.exception.ServiceException;

/**
 * @author lizhihua
 *
 */
public interface ComparisonSettingsService {

	public ComparisonSettings findComparisonSettings() throws ServiceException;

	public void updateComparisonSettings(ComparisonSettings settings) throws ServiceException;
}
