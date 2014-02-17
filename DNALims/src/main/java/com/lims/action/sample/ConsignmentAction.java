/**
 * ConsignmentAction.java
 *
 * 2013-8-3
 */
package com.lims.action.sample;

import java.util.List;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.domain.po.ConsignOrganization;
import com.lims.domain.po.Consignment;
import com.lims.domain.po.DictInfo;
import com.lims.domain.po.DictItem;
import com.lims.domain.po.SysUser;
import com.lims.domain.query.ConsignmentQuery;
import com.lims.domain.vo.ConsignmentView;
import com.lims.service.consignment.ConsignmentService;
import com.lims.service.dict.SysDictService;
import com.lims.service.organization.ConsignOrganizationService;
import com.lims.service.sysuser.SysUserService;
import com.lims.util.DateUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 */
public class ConsignmentAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private ConsignmentQuery consignmentQuery;

	private Consignment consignment;

	private List<ConsignmentView> consignmentList;

	//委托单位列表
	private List<ConsignOrganization> consignOrganizationList;

	private List<DictItem> consignModeList;			/** 送检方式 */
	private List<DictItem> sampleTypeList;			/** 样本类型 */
	private List<DictItem> checkContentList;		/** 检验内容 */
	private List<DictItem> sampleCarrierList;		/** 样本载体 */


	@Resource
	private ConsignmentService consignmentService;

	@Resource
	private SysUserService sysUserService;

	@Resource
	private ConsignOrganizationService consignOrganizationService;

	@Resource
	private SysDictService sysDictService;

	private List<SysUser> createUserList;
	private List<ConsignOrganization> organizationList;


	/**
	 * 查询委托列表
	 *
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		if(consignmentQuery == null){
			consignmentQuery = new ConsignmentQuery();
			consignmentQuery.setSortColumn("createDate");
			consignmentQuery.setSortOrder("DESC");
		}else{
			trimQueryOptions();
		}

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		consignmentQuery.setStartRowNum((pageIndex-1)*pageSize + 1);
		consignmentQuery.setEndRowNum(pageIndex*pageSize);

		recordCount = consignmentService.findConsignmentListCount(consignmentQuery);
		consignmentList = consignmentService.findConsignmentList(consignmentQuery);

		createUserList = sysUserService.findSysUserList(new SysUser());
		organizationList = consignOrganizationService.findAllConsignOrganizationList();

		return SUCCESS;
	}


	public String editConsignment() throws Exception {
		initDictInfo();

		consignment = consignmentService.findConsignmentById(consignment.getId());

		return SUCCESS;
	}

	public String deleteConsignment() throws Exception {
		consignmentService.deleteConsignmentById(consignment.getId());

		return SUCCESS;
	}

	public String submitConsignment() throws Exception {
		SysUser sysUser = getLoginSysUser();
		consignment.setUpdateDate(DateUtil.currentDate());
		consignment.setUpdateUser(sysUser.getId());
		consignmentService.updateConsignment(consignment);

		return SUCCESS;
	}


	private void initDictInfo() throws Exception {
		consignModeList = sysDictService.findDictItemListByDictInfoType(DictInfo.CONSIGN_MODE);
		sampleTypeList = sysDictService.findDictItemListByDictInfoType(DictInfo.SAMPLE_TYPE);
		checkContentList = sysDictService.findDictItemListByDictInfoType(DictInfo.CHECK_CONTENT);
		sampleCarrierList = sysDictService.findDictItemListByDictInfoType(DictInfo.SAMPLE_CARRIER);

		consignOrganizationList = consignOrganizationService.findAllConsignOrganizationList();
	}


	private void trimQueryOptions() {
		if(StringUtil.isNullOrEmpty(consignmentQuery.getOrganizationId())){
			consignmentQuery.setOrganizationId(null);
		}else{
			consignmentQuery.setOrganizationId(consignmentQuery.getOrganizationId().trim());
		}

		if(StringUtil.isNullOrEmpty(consignmentQuery.getConsignmentNo())){
			consignmentQuery.setConsignmentNo(null);
		}else{
			consignmentQuery.setConsignmentNo(consignmentQuery.getConsignmentNo().trim());
		}

		if(StringUtil.isNullOrEmpty(consignmentQuery.getCreateUser())){
			consignmentQuery.setCreateUser(null);
		}else{
			consignmentQuery.setCreateUser(consignmentQuery.getCreateUser().trim());
		}

	}

	public ConsignmentQuery getConsignmentQuery() {
		return consignmentQuery;
	}

	public void setConsignmentQuery(ConsignmentQuery consignmentQuery) {
		this.consignmentQuery = consignmentQuery;
	}

	public List<ConsignmentView> getConsignmentList() {
		return consignmentList;
	}

	public void setConsignmentList(List<ConsignmentView> consignmentList) {
		this.consignmentList = consignmentList;
	}

	public ConsignmentService getConsignmentService() {
		return consignmentService;
	}

	public void setConsignmentService(ConsignmentService consignmentService) {
		this.consignmentService = consignmentService;
	}


	public SysUserService getSysUserService() {
		return sysUserService;
	}


	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}


	public List<SysUser> getCreateUserList() {
		return createUserList;
	}


	public void setCreateUserList(List<SysUser> createUserList) {
		this.createUserList = createUserList;
	}


	public ConsignOrganizationService getConsignOrganizationService() {
		return consignOrganizationService;
	}


	public void setConsignOrganizationService(
			ConsignOrganizationService consignOrganizationService) {
		this.consignOrganizationService = consignOrganizationService;
	}


	public List<ConsignOrganization> getOrganizationList() {
		return organizationList;
	}


	public void setOrganizationList(List<ConsignOrganization> organizationList) {
		this.organizationList = organizationList;
	}


	public Consignment getConsignment() {
		return consignment;
	}


	public void setConsignment(Consignment consignment) {
		this.consignment = consignment;
	}


	public List<ConsignOrganization> getConsignOrganizationList() {
		return consignOrganizationList;
	}


	public void setConsignOrganizationList(
			List<ConsignOrganization> consignOrganizationList) {
		this.consignOrganizationList = consignOrganizationList;
	}


	public List<DictItem> getConsignModeList() {
		return consignModeList;
	}


	public void setConsignModeList(List<DictItem> consignModeList) {
		this.consignModeList = consignModeList;
	}


	public List<DictItem> getSampleTypeList() {
		return sampleTypeList;
	}


	public void setSampleTypeList(List<DictItem> sampleTypeList) {
		this.sampleTypeList = sampleTypeList;
	}


	public List<DictItem> getCheckContentList() {
		return checkContentList;
	}


	public void setCheckContentList(List<DictItem> checkContentList) {
		this.checkContentList = checkContentList;
	}


	public List<DictItem> getSampleCarrierList() {
		return sampleCarrierList;
	}


	public void setSampleCarrierList(List<DictItem> sampleCarrierList) {
		this.sampleCarrierList = sampleCarrierList;
	}


	public SysDictService getSysDictService() {
		return sysDictService;
	}


	public void setSysDictService(SysDictService sysDictService) {
		this.sysDictService = sysDictService;
	}

}
