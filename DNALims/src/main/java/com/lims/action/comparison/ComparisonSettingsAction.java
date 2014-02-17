/**
 * ComparisonSettingsAction.java
 *
 * 2013-7-13
 */
package com.lims.action.comparison;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.domain.po.ComparisonSettings;
import com.lims.domain.po.SysUser;
import com.lims.service.comparison.ComparisonSettingsService;

/**
 * @author lizhihua
 *
 */
public class ComparisonSettingsAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private ComparisonSettings comparisonSettings;

	@Resource
	private ComparisonSettingsService comparisonSettingsService;

	public String into() throws Exception {
		comparisonSettings = comparisonSettingsService.findComparisonSettings();

		return SUCCESS;
	}


	public String submit() throws Exception {
		SysUser sysUser = getLoginSysUser();
		comparisonSettings.setUpdateUser(sysUser.getId());

		comparisonSettingsService.updateComparisonSettings(comparisonSettings);

		return SUCCESS;
	}


	public ComparisonSettings getComparisonSettings() {
		return comparisonSettings;
	}


	public void setComparisonSettings(ComparisonSettings comparisonSettings) {
		this.comparisonSettings = comparisonSettings;
	}


	public ComparisonSettingsService getComparisonSettingsService() {
		return comparisonSettingsService;
	}


	public void setComparisonSettingsService(
			ComparisonSettingsService comparisonSettingsService) {
		this.comparisonSettingsService = comparisonSettingsService;
	}

}
