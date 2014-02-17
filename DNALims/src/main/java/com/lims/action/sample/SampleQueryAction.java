/**
 * SampleQueryAction.java
 *
 * 2013-6-3
 */
package com.lims.action.sample;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.constants.LimsConstants;
import com.lims.domain.po.BoardInfo;
import com.lims.domain.po.BoardSampleMap;
import com.lims.domain.po.DictInfo;
import com.lims.domain.po.DictItem;
import com.lims.domain.query.ConsignmentQuery;
import com.lims.domain.query.SampleInfoQuery;
import com.lims.domain.vo.SampleInfoView;
import com.lims.service.dict.SysDictService;
import com.lims.service.sample.BoardInfoService;
import com.lims.service.sample.SampleInfoService;
import com.lims.util.ListUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 * 样本查询Action
 *
 */
public class SampleQueryAction extends BaseAction {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private SampleInfoQuery sampleInfoQuery;
	private ConsignmentQuery consignmentQuery;

	private List<SampleInfoView> sampleInfoViewList;

	@Resource
	private BoardInfoService boardInfoService;
	@Resource
	private SampleInfoService sampleInfoService;
	@Resource
	private SysDictService sysDictService;

	private BoardInfo boardInfo;

	private File workstationFile;

	private List<DictItem> holeDiameterList;
	private List<DictItem> examineTypeList;

	private String jsonString;

	private String checkedId;

	private Map<String, Object> jsonData;

	private String consignmentNo;

	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		if(sampleInfoQuery == null){
			sampleInfoQuery = new SampleInfoQuery();

			sampleInfoQuery.setSortColumn("acceptDate");
			sampleInfoQuery.setSortOrder("DESC");
		}else{
			if(StringUtil.isNullOrEmpty(sampleInfoQuery.getSampleNo())){
				sampleInfoQuery.setSampleNo(null);
			}else{
				sampleInfoQuery.setSampleNo(sampleInfoQuery.getSampleNo().trim());
			}

			if(StringUtil.isNullOrEmpty(sampleInfoQuery.getConsignmentNo())){
				sampleInfoQuery.setConsignmentNo(null);
			}else{
				sampleInfoQuery.setConsignmentNo(sampleInfoQuery.getConsignmentNo().trim());
			}

			if(StringUtil.isNullOrEmpty(sampleInfoQuery.getFuzzyFlag())){
				sampleInfoQuery.setFuzzyFlag(null);
			}else{
				if("false".equals(sampleInfoQuery.getFuzzyFlag())){
					sampleInfoQuery.setFuzzyFlag(null);
				}else{
					sampleInfoQuery.setFuzzyFlag(sampleInfoQuery.getFuzzyFlag().trim());
				}
			}
		}

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		sampleInfoQuery.setStartRowNum((pageIndex-1)*pageSize + 1);
		sampleInfoQuery.setEndRowNum(pageIndex*pageSize);

		recordCount = sampleInfoService.findSampleInfoQueryListCount(sampleInfoQuery);
		sampleInfoViewList = sampleInfoService.findSampleInfoQueryList(sampleInfoQuery);

		return SUCCESS;
	}


	public String view() throws Exception {
		initDictInfo();

		boardInfo = boardInfoService.findBoardInfoById(boardInfo.getId());
		if(StringUtil.isNotEmpty(boardInfo.getHoleDiameter())){
			boardInfo.setHoleDiameterArr(StringUtil.stringToArray(boardInfo.getHoleDiameter(), ","));
		}
		List<BoardSampleMap> boardSampleList = boardInfoService.findBoardSampleListByBoardId(boardInfo.getId());

		List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
		for(int i = 0; i < LimsConstants.horizontalPositionArr.length; i++) {
			String temp = LimsConstants.horizontalPositionArr[i];
			boolean isExists = false;
			Map<String, String> tempMap = new HashMap<String, String>();
			for(BoardSampleMap bsm : boardSampleList){
				if(temp.equals(bsm.getSamplePosition())){
					tempMap.put("positionNo", bsm.getSamplePosition());
					tempMap.put("sampleNo", bsm.getSampleNo());
					tempList.add(tempMap);
					isExists = true;
					break;
				}
			}

			if(!isExists){
				Map<String, String> map = new HashMap<String, String>();
				map.put("positionNo", temp);
				map.put("sampleNo", "");
				tempList.add(map);
			}
		}

		jsonString = "{\"sampleList\":[";

		for(int i = 0; i < 8; i++){
			jsonString += "{\"prefix\":\"" + LimsConstants.horizontalPositionPrefixArr[i] + "\",\"samples\":[";
			int startIdx = i*12;
			for(int j = startIdx; j < (i+1) * 12; j++){
				Map<String, String> tempMap = tempList.get(j);
				jsonString += "{\"positionNo\":\"" + tempMap.get("positionNo") + "\",\"sampleNo\":\"" + tempMap.get("sampleNo") + "\"},";
			}

			jsonString = jsonString.substring(0, jsonString.length() - 1);
			jsonString += "]},";
		}

		jsonString = jsonString.substring(0, jsonString.length() - 1);
		jsonString += "]}";


		return SUCCESS;
	}

	public String queryConsignmentSamples() throws Exception {
		if(sampleInfoQuery == null){
			sampleInfoQuery = new SampleInfoQuery();
			sampleInfoQuery.setSortColumn("sampleNo");
			sampleInfoQuery.setSortOrder("ASC");
		}else{
			if(StringUtil.isNullOrEmpty(sampleInfoQuery.getSortColumn())){
				sampleInfoQuery.setSortColumn("sampleNo");
				sampleInfoQuery.setSortOrder("ASC");
			}
		}

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		sampleInfoQuery.setStartRowNum((pageIndex-1)*pageSize + 1);
		sampleInfoQuery.setEndRowNum(pageIndex*pageSize);

		recordCount = sampleInfoService.findSampleInfoQueryListCount(sampleInfoQuery);
		sampleInfoViewList = sampleInfoService.findSampleInfoQueryList(sampleInfoQuery);

		consignmentNo = sampleInfoViewList.get(0).getConsignmentNo();

		return SUCCESS;
	}

	public String deleteSample() throws Exception {
		jsonData = new HashMap<String, Object>();

		try {
			String[] checkedSampleArr = checkedId.split(",");

			sampleInfoService.deleteSampleInfoByIdList(ListUtil.stringArrayToList(checkedSampleArr));

			jsonData.put("success", true);
		} catch (Exception e) {
			jsonData.put("success", false);
			jsonData.put("errorMsg", e.getMessage());
		}

		return SUCCESS;
	}

	private void initDictInfo() throws Exception {
		holeDiameterList = sysDictService.findDictItemListByDictInfoType(DictInfo.HOLE_DIAMETER);
		examineTypeList = sysDictService.findDictItemListByDictInfoType(DictInfo.EXAMINE_TYPE);
	}



	public SampleInfoQuery getSampleQueryDomain() {
		return sampleInfoQuery;
	}


	public void setSampleQueryDomain(SampleInfoQuery sampleInfoQuery) {
		this.sampleInfoQuery = sampleInfoQuery;
	}



	public BoardInfoService getBoardInfoService() {
		return boardInfoService;
	}


	public void setBoardInfoService(BoardInfoService boardInfoService) {
		this.boardInfoService = boardInfoService;
	}


	public BoardInfo getBoardInfo() {
		return boardInfo;
	}


	public void setBoardInfo(BoardInfo boardInfo) {
		this.boardInfo = boardInfo;
	}


	public File getWorkstationFile() {
		return workstationFile;
	}


	public void setWorkstationFile(File workstationFile) {
		this.workstationFile = workstationFile;
	}


	public List<DictItem> getHoleDiameterList() {
		return holeDiameterList;
	}


	public void setHoleDiameterList(List<DictItem> holeDiameterList) {
		this.holeDiameterList = holeDiameterList;
	}


	public List<DictItem> getExamineTypeList() {
		return examineTypeList;
	}


	public void setExamineTypeList(List<DictItem> examineTypeList) {
		this.examineTypeList = examineTypeList;
	}


	public String getJsonString() {
		return jsonString;
	}


	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}


	public SysDictService getSysDictService() {
		return sysDictService;
	}


	public void setSysDictService(SysDictService sysDictService) {
		this.sysDictService = sysDictService;
	}


	public SampleInfoQuery getSampleInfoQuery() {
		return sampleInfoQuery;
	}


	public void setSampleInfoQuery(SampleInfoQuery sampleInfoQuery) {
		this.sampleInfoQuery = sampleInfoQuery;
	}


	public List<SampleInfoView> getSampleInfoViewList() {
		return sampleInfoViewList;
	}


	public void setSampleInfoViewList(List<SampleInfoView> sampleInfoViewList) {
		this.sampleInfoViewList = sampleInfoViewList;
	}


	public SampleInfoService getSampleInfoService() {
		return sampleInfoService;
	}


	public void setSampleInfoService(SampleInfoService sampleInfoService) {
		this.sampleInfoService = sampleInfoService;
	}


	public String getCheckedId() {
		return checkedId;
	}


	public void setCheckedId(String checkedId) {
		this.checkedId = checkedId;
	}


	public Map<String, Object> getJsonData() {
		return jsonData;
	}


	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}


	public String getConsignmentNo() {
		return consignmentNo;
	}


	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}


	public ConsignmentQuery getConsignmentQuery() {
		return consignmentQuery;
	}


	public void setConsignmentQuery(ConsignmentQuery consignmentQuery) {
		this.consignmentQuery = consignmentQuery;
	}

}
