/**
 * LocusInfoManageAction.java
 *
 * 2013-7-9
 */
package com.lims.action.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.domain.po.LocusInfo;
import com.lims.domain.po.SysUser;
import com.lims.service.locus.LocusInfoService;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 * 基因座管理Action
 */
public class LocusInfoManageAction extends BaseAction {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private LocusInfoService locusInfoService;

	private List<LocusInfo> locusInfoList;

	private LocusInfo locusInfoQuery;

	private LocusInfo locusInfo;

	private Map<String, Object> jsonData;

	public String query() throws Exception {
		if(locusInfoQuery == null){
			locusInfoQuery = new LocusInfo();
		}else{
			trimQueryConditions();
		}

		locusInfoList = locusInfoService.findLocusInfoList(locusInfoQuery);

		return SUCCESS;
	}


	public String submit() throws Exception {
		SysUser sysUser = getLoginSysUser();
		jsonData = new HashMap<String, Object>();
		try {
			if(BaseAction.OPERATE_TYPE_ADD.equals(operateType)){
				locusInfo.setCreateUser(sysUser.getId());

				locusInfoService.insertLocusInfo(locusInfo);
			}else if(BaseAction.OPERATE_TYPE_UPDATE.equals(operateType)){
				LocusInfo originLocusInfo = locusInfoService.findLocusInfoById(locusInfo.getId());
				originLocusInfo.setGenoAlias(locusInfo.getGenoAlias());
				originLocusInfo.setGenoName(locusInfo.getGenoName());
				originLocusInfo.setGenoDesc(locusInfo.getGenoDesc());
				originLocusInfo.setUpdateUser(sysUser.getId());

				locusInfoService.updateLocusInfo(originLocusInfo);
			}

			jsonData.put("success", true);
		} catch(Exception e) {
			jsonData.put("success", false);
			jsonData.put("errorMsg", "保存失败，数据库异常！");
		}

		return SUCCESS;
	}


	private void trimQueryConditions() {
		if(StringUtil.isNotEmpty(locusInfoQuery.getGenoName())){
			locusInfoQuery.setGenoName(locusInfoQuery.getGenoName().trim());
		}else{
			locusInfoQuery.setGenoName(null);
		}

		if(StringUtil.isNotEmpty(locusInfoQuery.getGenoAlias())){
			locusInfoQuery.setGenoAlias(locusInfoQuery.getGenoAlias().trim());
		}else{
			locusInfoQuery.setGenoAlias(null);
		}
	}

	public LocusInfoService getLocusInfoService() {
		return locusInfoService;
	}

	public void setLocusInfoService(LocusInfoService locusInfoService) {
		this.locusInfoService = locusInfoService;
	}


	public List<LocusInfo> getLocusInfoList() {
		return locusInfoList;
	}


	public void setLocusInfoList(List<LocusInfo> locusInfoList) {
		this.locusInfoList = locusInfoList;
	}


	public LocusInfo getLocusInfoQuery() {
		return locusInfoQuery;
	}


	public void setLocusInfoQuery(LocusInfo locusInfoQuery) {
		this.locusInfoQuery = locusInfoQuery;
	}


	public Map<String, Object> getJsonData() {
		return jsonData;
	}


	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}


	public LocusInfo getLocusInfo() {
		return locusInfo;
	}


	public void setLocusInfo(LocusInfo locusInfo) {
		this.locusInfo = locusInfo;
	}
}
