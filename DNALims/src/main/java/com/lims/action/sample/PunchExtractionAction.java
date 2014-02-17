/**
 * PunchExtractionAction.java
 *
 * 2013-6-3
 */
package com.lims.action.sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.lims.action.BaseAction;
import com.lims.constants.LimsConstants;
import com.lims.constants.StaticSample;
import com.lims.domain.po.BoardInfo;
import com.lims.domain.po.BoardSampleMap;
import com.lims.domain.po.Consignment;
import com.lims.domain.po.DictInfo;
import com.lims.domain.po.DictItem;
import com.lims.domain.po.SampleInfo;
import com.lims.domain.po.SysUser;
import com.lims.domain.query.BoardInfoQuery;
import com.lims.domain.vo.BoardInfoView;
import com.lims.domain.vo.SampleInfoView;
import com.lims.exception.ServiceException;
import com.lims.service.consignment.ConsignmentService;
import com.lims.service.dict.SysDictService;
import com.lims.service.sample.BoardInfoService;
import com.lims.service.sample.SampleInfoService;
import com.lims.service.sysuser.SysUserService;
import com.lims.util.ConfigUtil;
import com.lims.util.DateUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 * 打孔取样Action
 */
public class PunchExtractionAction extends BaseAction {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private BoardInfo boardInfo;

	private BoardInfoQuery boardInfoQuery;

	private File workstationFile;

	private File boardSampleFile;

	private List<DictItem> holeDiameterList;
	private List<DictItem> examineTypeList;

	@Resource
	private SysDictService sysDictService;
	@Resource
	private ConsignmentService consignmentService;
	@Resource
	private BoardInfoService boardInfoService;
	@Resource
	private SampleInfoService sampleInfoService;
	@Resource
	private SysUserService sysUserService;

	private String[] positionNo;

	private String[] sampleNo;

	private String[] holeDiameter;

	private Map<String, Object> jsonData;

	private String jsonString;

	private String analysisFileName;
	private InputStream inputStream;

	private List<BoardInfoView> boardInfoList;

	private List<SysUser> importUserList;


	private String inputSampleNo;
	private String inputSamplePosition;

	private String checkedBoardId;

	private String zipedFilePath;

	/**
	 * 进入打孔取样页面
	 * @return
	 * @throws Exception
	 */
	public String into() throws Exception {
		initDictInfo();

		boardInfo = new BoardInfo();
		boardInfo.setExamineStatus(LimsConstants.EXAMINE_STATUS_EXTRACTED);

		operateType = BaseAction.OPERATE_TYPE_ADD;

		return SUCCESS;
	}


	/**
	 * 提交
	 * @return
	 * @throws Exception
	 */
	public String submit() throws Exception {
		SysUser sysUser = getLoginSysUser();
		jsonData = new HashMap<String, Object>();
		boolean isSucceed = true;
		String errorMsg = "";

		try {
			Consignment consignment = consignmentService.findConsignmentByNo(boardInfo.getConsignmentNo().trim());
			boardInfo.setBoardNo(boardInfo.getBoardNo().trim());
			boardInfo.setConsignmentNo(boardInfo.getConsignmentNo().trim());
			boardInfo.setHoleDiameter(StringUtil.arrayToString(boardInfo.getHoleDiameterArr(), ","));
			if(BaseAction.OPERATE_TYPE_ADD.equals(operateType) || StringUtil.isNullOrEmpty(boardInfo.getId())){
//				String boardInfoId = boardInfoService.addBoardInfo(boardInfo, sampleNo, positionNo, sysUser);
				String boardInfoId = boardInfoService.addBoardInfo(boardInfo, sysUser);
				jsonData.put("boardInfoId", boardInfoId);
			}else{
//				boardInfoService.updateBoardInfo(boardInfo, sampleNo, positionNo, sysUser);
				boardInfo.setReviewStatus(LimsConstants.REVIEW_STATUS_WAITING);
				boardInfo.setExamineStatus(LimsConstants.EXAMINE_STATUS_EXTRACTED);
				boardInfo.setPcrFlag(LimsConstants.FLAG_FALSE);
				boardInfo.setAnalysisFlag(LimsConstants.FLAG_FALSE);
				boardInfoService.updateBoardInfo(boardInfo, sysUser);
				jsonData.put("boardInfoId", boardInfo.getId());
			}

			jsonData.put("consignmentId", consignment.getId());
			jsonData.put("operateType", BaseAction.OPERATE_TYPE_UPDATE);
		} catch(ServiceException se) {
			isSucceed = false;
			errorMsg = "保存取样信息失败， 原因： 数据库异常！";

			jsonData.put("errorMsg", errorMsg);
		}


		jsonData.put("success", isSucceed);

		return SUCCESS;
	}

	public String deleteCKSample() throws Exception {
		jsonData = new HashMap<String, Object>();

		boardInfoService.deleteCKSampleByBoardId(boardInfo.getId());
		jsonData.put("success", true);

		return SUCCESS;
	}

	//导入上样表
	public String intoImportBoardSample() throws Exception {

		return SUCCESS;
	}

	//生成上样表
	public String generate() throws Exception {
		boardInfo = boardInfoService.findBoardInfoById(boardInfo.getId());
		Date currentDate = new Date();

		this.generateSyTable(boardInfo, currentDate);

		jsonData = new HashMap<String, Object>();
		jsonData.put("success", true);


		return SUCCESS;
	}

	public String zipDownSyTable() throws Exception {
		String[] checkedBoardIdArr = checkedBoardId.split(",");
		BoardInfo tempBoardInfo = null;

		File downloadTmpPathFile = new File(DOWNLOAD_TMP_PATH);
		if(!downloadTmpPathFile.exists()){
			downloadTmpPathFile.mkdir();
		}
		
		String zipFileName = "上样表_" + DateUtil.currentDateStr("yyyyMMddHHmmss") + ".zip";
		zipedFilePath = DOWNLOAD_TMP_PATH + System.getProperty("file.separator") + zipFileName;
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipedFilePath));
		byte[] buffer = new byte[1024];
		String generatedFilePath = null;
		File generatedFile = null;

		for(String checkedId : checkedBoardIdArr){

			tempBoardInfo = boardInfoService.findBoardInfoById(checkedId);
//			if(tempBoardInfo.getGenerateSyTableFlag() == null
//					|| LimsConstants.FLAG_FALSE.equals(tempBoardInfo.getGenerateSyTableFlag())){
				Date currentDate = new Date();
				generatedFilePath = this.generateSyTable(tempBoardInfo, currentDate);
				generatedFile = new File(generatedFilePath);
//			}else{
//			    String newFilename = tempBoardInfo.getBoardNo() + ".txt";
//				//目标完整路径
//			    generatedFilePath = new StringBuffer().append(ConfigUtil.getProperty(ConfigUtil.DOCUMENT_GENERATE_DIR_KEY))
//											.append(System.getProperty("file.separator")).append("上样表")
//											.append(System.getProperty("file.separator"))
//											.append(DateUtil.getYearStr(tempBoardInfo.getGenerateSyTableDate()))
//											.append(System.getProperty("file.separator"))
//											.append(DateUtil.getMonthStr(tempBoardInfo.getGenerateSyTableDate()))
//											.append(System.getProperty("file.separator"))
//											.append(DateUtil.getDayStr(tempBoardInfo.getGenerateSyTableDate()))
//											.append(System.getProperty("file.separator")).append(newFilename)
//											.toString();
//			    generatedFile = new File(generatedFilePath);
//			    if(!generatedFile.exists()){
//			    	Date currentDate = new Date();
//					generatedFilePath = this.generateSyTable(tempBoardInfo, currentDate);
//					generatedFile = new File(generatedFilePath);
//			    }
//			}

			FileInputStream fis = new FileInputStream(generatedFile);
			out.putNextEntry(new ZipEntry(generatedFile.getName()));
			out.setEncoding("GBK");

			int len;
            // 读入需要下载的文件的内容，打包到zip文件
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }

            out.closeEntry();
            fis.close();
		}

		out.close();

		jsonData = new HashMap<String, Object>();
		jsonData.put("success", true);
		jsonData.put("zipedFilePath", zipedFilePath);
		jsonData.put("checkedBoardId", checkedBoardId);

		return SUCCESS;
	}


	public String downSyTable() throws Exception {
		File zipFile = new File(zipedFilePath);
		inputStream = new FileInputStream(zipFile);
		analysisFileName = URLEncoder.encode(zipFile.getName(), "UTF-8");

		//更新上样板的下载次数
		List<String> boardIdList = Arrays.asList(checkedBoardId.split(","));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("boardIdList", boardIdList);
		params.put("recordType", LimsConstants.RECORD_TYPE_SYTABLE);
		boardInfoService.updateBoardDownloadCount(params);

		return SUCCESS;
	}


	/**
	 * 取样任务列表
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		if(boardInfoQuery == null){
			boardInfoQuery = new BoardInfoQuery();
			boardInfoQuery.setSortColumn("importDate");
			boardInfoQuery.setSortOrder("DESC");
		}else{
			boardInfoQuery = trimQueryOptions(boardInfoQuery);
		}

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		boardInfoQuery.setStartRowNum((pageIndex-1)*pageSize + 1);
		boardInfoQuery.setEndRowNum(pageIndex*pageSize);

		recordCount = boardInfoService.findBoardInfoListCount(boardInfoQuery);
		boardInfoList = boardInfoService.findBoardInfoList(boardInfoQuery);

		importUserList = sysUserService.findSysUserList(new SysUser());

		return SUCCESS;
	}


	public String view() throws Exception {
		initDictInfo();
		operateType = BaseAction.OPERATE_TYPE_UPDATE;

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


	private String generateSyTable(BoardInfo bi, Date currentDate) throws Exception {
		List<BoardSampleMap> boardSampleList = boardInfoService.findBoardSampleListByBoardId(bi.getId());
		String generatePath = ConfigUtil.getProperty(ConfigUtil.DOCUMENT_GENERATE_DIR_KEY);

	    String newFilename = bi.getBoardNo() + ".txt";
		//目标完整路径
		String targetFileName = generatePath + System.getProperty("file.separator") + "上样表"
									+ System.getProperty("file.separator") + DateUtil.getYearStr(currentDate)
									+ System.getProperty("file.separator") + DateUtil.getMonthStr(currentDate)
									+ System.getProperty("file.separator") + DateUtil.getDayStr(currentDate);
		File targetFile = new File(targetFileName);
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}

		targetFileName = targetFileName + System.getProperty("file.separator") + newFilename;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileName));
			bw.write("Container Name" + "\t" + "Plate ID" + "\t" + "Description" + "\t" + "ContainerType" + "\t" + 	"AppType" + "\t" + "Owner" + "\t" + "Operator" + "\t" + "PlateSealing" + "\t" + "SchedulingPref" + "\n");
			bw.write(bi.getBoardNo() + "\t" + bi.getBoardNo() + "\t" + "" + "\t" + "96-Well" + "\t" + "Regular" + "\t" + "1" + "\t" + "1" + "\t" + "Septa" + "\t" + "1234" + "\n");
			bw.write("AppServer" + "\t" + "AppInstance" + "\n");
			bw.write("GeneMapper" + "\t" + "GeneMapper_Generic_Instance" + "\n");

			bw.write("Well" + "\t" + "Sample Name" + "\t" + "Comment" + "\t" + "Size Standard" + "\t" + "Snp Set" + "\t" + "User-Defined 3" + "\t"
						+ "User-Defined 2" + "\t" + "User-Defined 1" + "\t" + "Panel" + "\t" + "Study" + "\t" + "Sample Type" + "\t" + "Analysis Method"
						+ "\t" + "Results Group 1" + "\t" + "Instrument Protocol 1" + "\n");

			for(String positionStr : LimsConstants.verticalPositionArr){
        		if("A01".equals(positionStr)){
        			String a01Sample = "CK";
        			for(BoardSampleMap boardSampleMap : boardSampleList){
            			if(positionStr.equals(boardSampleMap.getSamplePosition())){
            				a01Sample = boardSampleMap.getSampleNo();
            				break;
            			}
        			}
        			bw.write("A01" + "\t"
							+ a01Sample + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "DNA_Results_Group" + "\t"
							+ "DNATyper-G5-Run" + "\n");
        			continue;
        		}

        		if("B01".equals(positionStr)){
        			bw.write("B01" + "\t"
							+ "9947" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "DNA_Results_Group" + "\t"
							+ "DNATyper-G5-Run" + "\n");
        			continue;
        		}

        		if("H03".equals(positionStr)){
        			bw.write("H03" + "\t"
							+ "Ladder" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "DNA_Results_Group" + "\t"
							+ "DNATyper-G5-Run" + "\n");
        			continue;
        		}

        		if("H10".equals(positionStr)){
        			bw.write("H10" + "\t"
							+ "Ladder" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "" + "\t"
							+ "DNA_Results_Group" + "\t"
							+ "DNATyper-G5-Run" + "\n");
        			continue;
        		}

        		for(BoardSampleMap boardSampleMap : boardSampleList){
        			if(positionStr.equals(boardSampleMap.getSamplePosition())){
        				bw.write(boardSampleMap.getSamplePosition() + "\t"
    							+ boardSampleMap.getSampleNo() + "\t"
    							+ "" + "\t"
    							+ "" + "\t"
    							+ "" + "\t"
    							+ "" + "\t"
    							+ "" + "\t"
    							+ "" + "\t"
    							+ "" + "\t"
    							+ "" + "\t"
    							+ "" + "\t"
    							+ "" + "\t"
    							+ "DNA_Results_Group" + "\t"
    							+ "DNATyper-G5-Run" + "\n");
        				break;
        			}

    			}
        	}

			bw.flush();
			bw.close();

			bi.setGenerateSyTableFlag(LimsConstants.FLAG_TRUE);
			bi.setGenerateSyTableDate(currentDate);
			boardInfoService.updateBoardInfo(bi, getLoginSysUser());

			return targetFileName;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("生成上样板失败!", e);
		}
	}

	public String deleteBoardInfo() throws Exception {
		boardInfoService.deleteBoardInfoById(boardInfo.getId());

		return SUCCESS;
	}

	//导入上样表
	public String submitBoardSampleFileImport() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(boardSampleFile));
		String str = null;

		//前面5行跳过
		for(int i = 0 ; i < 5; i++){
			br.readLine();
		}

		boardInfo = boardInfoService.findBoardInfoById(boardInfo.getId());
		SysUser sysUser = getLoginSysUser();
		List<Map<String, String>> sampleList = new ArrayList<Map<String, String>>();
		Map<String, String> sampleMap = null;
		while ((str = br.readLine()) != null) {
			sampleMap = new HashMap<String, String>();

			try {
				String[] sampleLine = str.split("\t");
				String positionNo = sampleLine[0];
				String sampleNo = sampleLine[1] + "";

				if(StaticSample.staticSampleList().contains(sampleNo)){
					sampleMap.put("positionNo", positionNo);
					sampleMap.put("sampleNo", "");
					sampleList.add(sampleMap);

					continue;
				}

				sampleMap.put("positionNo", positionNo);
				sampleMap.put("sampleNo", sampleNo);

				SampleInfoView si = sampleInfoService.findSampleInfoBySampleNo(sampleNo);
				if(si == null){
					SampleInfo sampleInfo = new SampleInfo();
					sampleInfo.setSampleNo(sampleNo);
					sampleInfo.setSampleName(sampleNo);
					sampleInfo.setCreateDate(new Date());
					sampleInfo.setCreateUser(sysUser.getId());
					sampleInfo.setConsignmentId(boardInfo.getConsignmentId());
					sampleInfo.setAcceptStatus(LimsConstants.FLAG_FALSE);

					BoardSampleMap bsm = new BoardSampleMap();
					bsm.setBoardId(boardInfo.getId());
					bsm.setSamplePosition(positionNo);
					bsm.setExtractDate(new Date());
					bsm.setExtractUser(sysUser.getId());
					boardInfoService.addSampleInBoard(sampleInfo, bsm);
				}else{
					//该样本不属于当前委托
					if(!si.getConsignmentId().equals(boardInfo.getConsignmentId())){
						continue;
					}

					BoardSampleMap bsm = new BoardSampleMap();
					bsm.setBoardId(boardInfo.getId());
					bsm.setSampleId(si.getId());
					bsm.setSamplePosition(positionNo);
					bsm.setExtractDate(new Date());
					bsm.setExtractUser(sysUser.getId());

					boardInfoService.addBoardSampleMap(bsm);
				}

				sampleList.add(sampleMap);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
		for(int i = 0; i < LimsConstants.horizontalPositionArr.length; i++) {
			String temp = LimsConstants.horizontalPositionArr[i];
			boolean isExists = false;
			for(Map<String, String> tempMap : sampleList){
				if(temp.equals(tempMap.get("positionNo"))){
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

		jsonString = "{\"success\":\"true\",\"sampleList\":[";

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

	/**
	 * 导入工作站文件
	 * @return
	 * @throws Exception
	 */
	public String importWorkstationFile() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(workstationFile));
		String str = null;

		String boardNo = br.readLine(); //第一行为上样板号
		int nobarcodeCount = 0;
		List<Map<String, String>> sampleList = new ArrayList<Map<String, String>>();
		Map<String, String> sampleMap = null;
		while ((str = br.readLine()) != null) {
			sampleMap = new HashMap<String, String>();
			String[] sampleLine =str.split("\t");
			sampleMap.put("positionNo", sampleLine[0]);

			try {
				sampleMap.put("sampleNo", sampleLine[2]);
			} catch(Exception e) {
				sampleMap.put("sampleNo", "");
				nobarcodeCount++;
			}

			sampleList.add(sampleMap);
		}

		List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
		for(int i = 0; i < LimsConstants.horizontalPositionArr.length; i++) {
			String temp = LimsConstants.horizontalPositionArr[i];
			boolean isExists = false;
			for(Map<String, String> tempMap : sampleList){
				if(temp.equals(tempMap.get("positionNo"))){
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

		String jsonResult = "{\"workstationFileName\":\"" + workstationFile.getName() + "\",\"boardNo\":\"" + boardNo + "\",\"sampleList\":[";

		for(int i = 0; i < 8; i++){
			jsonResult += "{\"prefix\":\"" + LimsConstants.horizontalPositionPrefixArr[i] + "\",\"samples\":[";
			int startIdx = i*12;
			for(int j = startIdx; j < (i+1) * 12; j++){
				Map<String, String> tempMap = tempList.get(j);
				jsonResult += "{\"positionNo\":\"" + tempMap.get("positionNo") + "\",\"sampleNo\":\"" + tempMap.get("sampleNo") + "\"},";
			}

			jsonResult = jsonResult.substring(0, jsonResult.length() - 1);
			jsonResult += "]},";
		}

		jsonResult = jsonResult.substring(0, jsonResult.length() - 1);
		jsonResult += "]}";

		HttpServletResponse response = this.getServletResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;

		try{
			out = response.getWriter();
			out.write(jsonResult);
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


	public String checkConsignmentNoExists() throws Exception {

		Consignment consignment = consignmentService.findConsignmentByNo(boardInfo.getConsignmentNo());
		BoardInfoQuery biq = new BoardInfoQuery();
		biq.setBoardNo(boardInfo.getBoardNo());
		List<BoardInfoView> boardInfoViewList = boardInfoService.findBoardInfoList(biq);

		HttpServletResponse response = this.getServletResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;

		try{
			out = response.getWriter();
			out.write("{\"consignmentNoExists\":" + ((consignment == null) ? "\"false\"" : "\"true\"")
					+ ", \"boardNoExists\":" + ((boardInfoViewList==null || boardInfoViewList.isEmpty()) ? "\"false\"" : "\"true\"") + "}");
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


	public String checkSmapleExists() throws Exception {
		jsonData = new HashMap<String, Object>();

		SampleInfoView si = sampleInfoService.findSampleInfoBySampleNo(inputSampleNo);
		if(si == null){
			jsonData.put("hasRepeat", false);
		}else{
			jsonData.put("hasRepeat", true);
		}

		return SUCCESS;
	}


	public String saveScanedSampleInBoard() throws Exception {
		SysUser sysUser = getLoginSysUser();
		jsonData = new HashMap<String, Object>();

		SampleInfoView si = sampleInfoService.findSampleInfoBySampleNo(inputSampleNo.trim());
		boardInfo = boardInfoService.findBoardInfoById(boardInfo.getId());

		if(si == null){
			SampleInfo sampleInfo = new SampleInfo();
			sampleInfo.setSampleNo(inputSampleNo);
			sampleInfo.setSampleName(inputSampleNo);
			sampleInfo.setCreateDate(new Date());
			sampleInfo.setCreateUser(sysUser.getId());
			sampleInfo.setConsignmentId(boardInfo.getConsignmentId());
			sampleInfo.setAcceptStatus(LimsConstants.FLAG_FALSE);

			BoardSampleMap bsm = new BoardSampleMap();
			bsm.setBoardId(boardInfo.getId());
			bsm.setSamplePosition(inputSamplePosition);
			bsm.setExtractDate(new Date());
			bsm.setExtractUser(sysUser.getId());
			boardInfoService.addSampleInBoard(sampleInfo, bsm);
		}else{
			//该样本不属于当前委托
			if(!si.getConsignmentId().equals(boardInfo.getConsignmentId())){
				jsonData.put("success", false);
				jsonData.put("otherConsignmentSample", true);
				jsonData.put("consignmentNo", si.getConsignmentNo());
				return SUCCESS;
			}

			BoardSampleMap bsm = new BoardSampleMap();
			bsm.setBoardId(boardInfo.getId());
			bsm.setSampleId(si.getId());
			bsm.setSamplePosition(inputSamplePosition);
			bsm.setExtractDate(new Date());
			bsm.setExtractUser(sysUser.getId());

			boardInfoService.addBoardSampleMap(bsm);
		}

		jsonData.put("success", true);

		return SUCCESS;
	}
	
	/**
	 * 清除指定板孔上的样本（置空该位置，不删除样本）
	 * @return
	 * @throws Exception
	 */
	public String deleteSampleOfBoardPosition() throws Exception {
		jsonData = new HashMap<String, Object>();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("boardId", this.boardInfo.getId());
		params.put("samplePosition", this.inputSamplePosition);
		
		try {
			boardInfoService.deleteSampleOfBoardPosition(params);
			jsonData.put("success", true);
		} catch(ServiceException e) {
			jsonData.put("success", false);
			jsonData.put("errorMsg", e.getMessage());
		}
		
		return SUCCESS;
	}

	private void initDictInfo() throws Exception {
		holeDiameterList = sysDictService.findDictItemListByDictInfoType(DictInfo.HOLE_DIAMETER);
		examineTypeList = sysDictService.findDictItemListByDictInfoType(DictInfo.EXAMINE_TYPE);
	}


	private BoardInfoQuery trimQueryOptions(BoardInfoQuery bi) {
		if(StringUtil.isNullOrEmpty(bi.getConsignmentNo())){
			bi.setConsignmentNo(null);
		}else{
			bi.setConsignmentNo(bi.getConsignmentNo().trim());
		}

		if(StringUtil.isNullOrEmpty(bi.getBoardNo())){
			bi.setBoardNo(null);
		}else{
			bi.setBoardNo(bi.getBoardNo().trim());
		}

		if(StringUtil.isNullOrEmpty(bi.getSampleNo())){
			bi.setSampleNo(null);
		}else{
			bi.setSampleNo(bi.getSampleNo().trim());
		}

		if(StringUtil.isNullOrEmpty(bi.getImportUser())){
			bi.setImportUser(null);
		}else{
			bi.setImportUser(bi.getImportUser().trim());
		}

		if(StringUtil.isNullOrEmpty(boardInfoQuery.getFuzzyFlag())){
			boardInfoQuery.setFuzzyFlag(null);
		}else{
			if("false".equals(boardInfoQuery.getFuzzyFlag())){
				boardInfoQuery.setFuzzyFlag(null);
			}else{
				boardInfoQuery.setFuzzyFlag(boardInfoQuery.getFuzzyFlag().trim());
			}
		}

		return bi;
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


	public SysDictService getSysDictService() {
		return sysDictService;
	}


	public void setSysDictService(SysDictService sysDictService) {
		this.sysDictService = sysDictService;
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


	public String[] getPositionNo() {
		return positionNo;
	}


	public void setPositionNo(String[] positionNo) {
		this.positionNo = positionNo;
	}


	public String[] getSampleNo() {
		return sampleNo;
	}


	public void setSampleNo(String[] sampleNo) {
		this.sampleNo = sampleNo;
	}


	public ConsignmentService getConsignmentService() {
		return consignmentService;
	}


	public void setConsignmentService(ConsignmentService consignmentService) {
		this.consignmentService = consignmentService;
	}


	public String getAnalysisFileName() {
		return analysisFileName;
	}


	public void setAnalysisFileName(String analysisFileName) {
		this.analysisFileName = analysisFileName;
	}


	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	public List<BoardInfoView> getBoardInfoList() {
		return boardInfoList;
	}


	public void setBoardInfoList(List<BoardInfoView> boardInfoList) {
		this.boardInfoList = boardInfoList;
	}


	public List<SysUser> getImportUserList() {
		return importUserList;
	}


	public void setImportUserList(List<SysUser> importUserList) {
		this.importUserList = importUserList;
	}


	public SysUserService getSysUserService() {
		return sysUserService;
	}


	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}


	public BoardInfoService getBoardInfoService() {
		return boardInfoService;
	}


	public void setBoardInfoService(BoardInfoService boardInfoService) {
		this.boardInfoService = boardInfoService;
	}


	public Map<String, Object> getJsonData() {
		return jsonData;
	}


	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}


	public String getJsonString() {
		return jsonString;
	}


	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}


	public BoardInfoQuery getBoardInfoQuery() {
		return boardInfoQuery;
	}


	public void setBoardInfoQuery(BoardInfoQuery boardInfoQuery) {
		this.boardInfoQuery = boardInfoQuery;
	}


	public String getInputSampleNo() {
		return inputSampleNo;
	}


	public void setInputSampleNo(String inputSampleNo) {
		this.inputSampleNo = inputSampleNo;
	}


	public SampleInfoService getSampleInfoService() {
		return sampleInfoService;
	}


	public void setSampleInfoService(SampleInfoService sampleInfoService) {
		this.sampleInfoService = sampleInfoService;
	}


	public String[] getHoleDiameter() {
		return holeDiameter;
	}


	public void setHoleDiameter(String[] holeDiameter) {
		this.holeDiameter = holeDiameter;
	}


	public String getInputSamplePosition() {
		return inputSamplePosition;
	}


	public void setInputSamplePosition(String inputSamplePosition) {
		this.inputSamplePosition = inputSamplePosition;
	}


	public File getBoardSampleFile() {
		return boardSampleFile;
	}


	public void setBoardSampleFile(File boardSampleFile) {
		this.boardSampleFile = boardSampleFile;
	}


	public String getCheckedBoardId() {
		return checkedBoardId;
	}


	public void setCheckedBoardId(String checkedBoardId) {
		this.checkedBoardId = checkedBoardId;
	}


	public String getZipedFilePath() {
		return zipedFilePath;
	}


	public void setZipedFilePath(String zipedFilePath) {
		this.zipedFilePath = zipedFilePath;
	}

}
