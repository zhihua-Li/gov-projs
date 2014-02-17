/**
 * ReagentKitManageAction.java
 *
 * 2013-7-9
 */
package com.lims.action.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.lims.action.BaseAction;
import com.lims.domain.po.LocusInfo;
import com.lims.domain.po.ReagentKit;
import com.lims.domain.po.SysUser;
import com.lims.service.locus.LocusInfoService;
import com.lims.service.locus.ReagentKitService;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 * 试剂盒管理Action
 */
public class ReagentKitManageAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private ReagentKitService reagentKitService;
	@Resource
	private LocusInfoService locusInfoService;

	private List<ReagentKit> reagentKitList;

	private ReagentKit reagentKitQuery;

	private ReagentKit reagentKit;

	private Map<String, Object> jsonData;

	private List<LocusInfo> allLocusInfoList;

	private int allLocusCount;

	private String[] genoNo;

	private List<LocusInfo> reagentLocusList;

	private File panelFile;


	public String query() throws Exception {
		if(reagentKitQuery == null){
			reagentKitQuery = new ReagentKit();
		}else{
			trimQueryConditions();
		}

		reagentKitList = reagentKitService.findReagentKitList(reagentKitQuery);

		return SUCCESS;
	}

	public String into() throws Exception {
		allLocusInfoList = locusInfoService.findAllLocusInfoList();
		allLocusCount = allLocusInfoList.size();

		if(operateType.equals(BaseAction.OPERATE_TYPE_ADD)) {
			return "add";
		} else if(operateType.equals(BaseAction.OPERATE_TYPE_UPDATE)) {
			reagentKit = reagentKitService.findReagentKitById(reagentKit.getId());
			genoNo = StringUtil.stringToArray(reagentKit.getLocusOrder(), ReagentKit.LOCUS_ORDER_SEPARATOR);
			reagentLocusList = convertReagentLocusList(genoNo, allLocusInfoList);

			return "edit";
		} else {
			reagentKitService.deleteReagentKitById(reagentKit.getId());
			return "delete";
		}
	}


	@SuppressWarnings("unchecked")
	public String importPanelFile() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(panelFile));
		String str = null;

		for(int i = 0; i < 4; i++){
			br.readLine();
		}

		List<String> locusList = new ArrayList<String>();
		while ((str = br.readLine()) != null) {
			String[] locusLine =str.split("\t");
			locusList.add(locusLine[0]);
		}

		Map<String, Object> locusMap = null;
		List<LocusInfo> allLocusList = locusInfoService.findAllLocusInfoList();
		JSONArray ja = new JSONArray();
		for(String locus : locusList){
			boolean hasLocus = false;
			locusMap = new HashMap<String, Object>();
			for(LocusInfo li : allLocusList){
				if(li.getGenoName().equals(locus)
						|| li.getGenoName().toUpperCase().indexOf(locus.toUpperCase()) != -1){
					hasLocus = true;
					locusMap.put("genoName", li.getGenoName());
					locusMap.put("genoNo", li.getGenoNo());
					break;
				}
			}

			if(!hasLocus){
				LocusInfo newLocusInfo = new LocusInfo();
				newLocusInfo.setGenoName(locus);
				newLocusInfo.setCreateDate(new Date());
				newLocusInfo.setCreateUser(getLoginSysUser().getId());

				newLocusInfo = locusInfoService.insertLocusInfo(newLocusInfo);
				locusMap.put("genoName", newLocusInfo.getGenoName());
				locusMap.put("genoNo", newLocusInfo.getGenoNo());
			}

			ja.add(locusMap);
		}

		HttpServletResponse response = this.getServletResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;

		try{
			out = response.getWriter();
			out.write(ja.toJSONString());
			out.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(out != null){
				out.close();
			}
		}

		return NONE;
	}

	public String submit() throws Exception {
		SysUser sysUser = getLoginSysUser();

		if(operateType.equals(BaseAction.OPERATE_TYPE_ADD)) {
			String locusOrderStr = StringUtil.arrayToString(genoNo, ReagentKit.LOCUS_ORDER_SEPARATOR);
			reagentKit.setLocusOrder(locusOrderStr);
			reagentKit.setCreateUser(sysUser.getId());

			reagentKitService.insertReagentKit(reagentKit);
		} else {
			String locusOrderStr = StringUtil.arrayToString(genoNo, ReagentKit.LOCUS_ORDER_SEPARATOR);
			reagentKit.setLocusOrder(locusOrderStr);
			reagentKit.setUpdateUser(sysUser.getId());

			reagentKitService.updateReagentKit(reagentKit);
		}

		return SUCCESS;
	}


	private List<LocusInfo> convertReagentLocusList(String[] genoNoArr, List<LocusInfo> locusInfoList){
		List<LocusInfo> resultList = new ArrayList<LocusInfo>();
		for(String genoNoStr : genoNoArr){
			for(LocusInfo li : locusInfoList){
				if(li.getGenoNo().equals(genoNoStr)){
					resultList.add(li);
					break;
				}
			}
		}

		return resultList;
	}


	private void trimQueryConditions() {
		if(StringUtil.isNotEmpty(reagentKitQuery.getReagentName())){
			reagentKitQuery.setReagentName(reagentKitQuery.getReagentName().trim());
		}else{
			reagentKitQuery.setReagentName(null);
		}
	}

	public ReagentKitService getReagentKitService() {
		return reagentKitService;
	}

	public void setReagentKitService(ReagentKitService reagentKitService) {
		this.reagentKitService = reagentKitService;
	}

	public List<ReagentKit> getReagentKitList() {
		return reagentKitList;
	}

	public void setReagentKitList(List<ReagentKit> reagentKitList) {
		this.reagentKitList = reagentKitList;
	}

	public ReagentKit getReagentKitQuery() {
		return reagentKitQuery;
	}

	public void setReagentKitQuery(ReagentKit reagentKitQuery) {
		this.reagentKitQuery = reagentKitQuery;
	}

	public ReagentKit getReagentKit() {
		return reagentKit;
	}

	public void setReagentKit(ReagentKit reagentKit) {
		this.reagentKit = reagentKit;
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}

	public List<LocusInfo> getAllLocusInfoList() {
		return allLocusInfoList;
	}

	public void setAllLocusInfoList(List<LocusInfo> allLocusInfoList) {
		this.allLocusInfoList = allLocusInfoList;
	}

	public LocusInfoService getLocusInfoService() {
		return locusInfoService;
	}

	public void setLocusInfoService(LocusInfoService locusInfoService) {
		this.locusInfoService = locusInfoService;
	}

	public String[] getGenoNo() {
		return genoNo;
	}

	public void setGenoNo(String[] genoNo) {
		this.genoNo = genoNo;
	}

	public List<LocusInfo> getReagentLocusList() {
		return reagentLocusList;
	}

	public void setReagentLocusList(List<LocusInfo> reagentLocusList) {
		this.reagentLocusList = reagentLocusList;
	}

	public int getAllLocusCount() {
		return allLocusCount;
	}

	public void setAllLocusCount(int allLocusCount) {
		this.allLocusCount = allLocusCount;
	}

	public File getPanelFile() {
		return panelFile;
	}

	public void setPanelFile(File panelFile) {
		this.panelFile = panelFile;
	}
}
