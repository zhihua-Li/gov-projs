/**
 * SysOrganizationAction.java
 *
 * 2013-6-2
 */
package com.lims.action.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.domain.po.ConsignOrganization;
import com.lims.domain.po.SysUser;
import com.lims.service.organization.ConsignOrganizationService;
import com.lims.util.DateUtil;
import com.lims.util.ListUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 * 委托单位管理Action
 *
 */
public class SysOrganizationAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private ConsignOrganization consignOrganization;

	private List<ConsignOrganization> consignOrganizationList;

	@Resource
	private ConsignOrganizationService consignOrganizationService;
	
	private Map<String, Object> jsonData;

	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		if(consignOrganization == null){
			consignOrganization = new ConsignOrganization();
		}else{
			trimQueryConditions();
		}

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		consignOrganization.setStartRowNum((pageIndex-1)*pageSize + 1);
		consignOrganization.setEndRowNum(pageIndex*pageSize);

		recordCount = consignOrganizationService.findConsignOrganizationListCount(consignOrganization);
		consignOrganizationList = consignOrganizationService.findConsignOrganizationList(consignOrganization);

		return SUCCESS;
	}


	public String edit() throws Exception {

		if(BaseAction.OPERATE_TYPE_ADD.equals(operateType)){
			consignOrganization = new ConsignOrganization();
		}else if(BaseAction.OPERATE_TYPE_UPDATE.equals(operateType)){
			consignOrganization = consignOrganizationService.findConsignOrganizationById(consignOrganization.getId().trim());
		}

		return SUCCESS;
	}
	
	public String checkOrganizationRepeat() throws Exception {
		jsonData = new HashMap<String, Object>();
		
		List<ConsignOrganization> consignOrganizationList = consignOrganizationService.findConsignOrganizationList(consignOrganization);
		if(ListUtil.isEmptyList(consignOrganizationList)){
			jsonData.put("hasRepeat", false);
		}else{
			jsonData.put("hasRepeat", true);
		}
				
		return SUCCESS;
	}


	public String submit() throws Exception {
		SysUser sysUser = getLoginSysUser();

		if(BaseAction.OPERATE_TYPE_ADD.equals(operateType)){
			consignOrganization.setCreateUser(sysUser.getId());
			consignOrganization.setCreateDate(DateUtil.currentDate());

			consignOrganizationService.addConsignOrganization(consignOrganization);
		}else if(BaseAction.OPERATE_TYPE_UPDATE.equals(operateType)){
			consignOrganization.setUpdateUser(sysUser.getId());
			consignOrganization.setUpdateDate(DateUtil.currentDate());

			consignOrganizationService.updateConsignOrganization(consignOrganization);
		}else if(BaseAction.OPERATE_TYPE_DELETE.equals(operateType)){
			consignOrganizationService.deleteConsignOrganizationById(consignOrganization.getId().trim());
		}

		return SUCCESS;
	}


	private void trimQueryConditions(){
		if(StringUtil.isNullOrEmpty(consignOrganization.getOrganizationCode())){
			consignOrganization.setOrganizationCode(null);
		}else{
			consignOrganization.setOrganizationCode(consignOrganization.getOrganizationCode().trim());
		}

		if(StringUtil.isNullOrEmpty(consignOrganization.getOrganizationName())){
			consignOrganization.setOrganizationName(null);
		}else{
			consignOrganization.setOrganizationName(consignOrganization.getOrganizationName().trim());
		}
	}

	public ConsignOrganization getConsignOrganization() {
		return consignOrganization;
	}

	public void setConsignOrganization(ConsignOrganization consignOrganization) {
		this.consignOrganization = consignOrganization;
	}

	public List<ConsignOrganization> getConsignOrganizationList() {
		return consignOrganizationList;
	}

	public void setConsignOrganizationList(
			List<ConsignOrganization> consignOrganizationList) {
		this.consignOrganizationList = consignOrganizationList;
	}

	public ConsignOrganizationService getConsignOrganizationService() {
		return consignOrganizationService;
	}

	public void setConsignOrganizationService(
			ConsignOrganizationService consignOrganizationService) {
		this.consignOrganizationService = consignOrganizationService;
	}


	public Map<String, Object> getJsonData() {
		return jsonData;
	}


	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}

}
