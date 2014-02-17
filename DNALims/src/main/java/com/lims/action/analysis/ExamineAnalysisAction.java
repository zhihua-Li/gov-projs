/**
 * ExamineAnalysisAction.java
 *
 * 2013-6-20
 */
package com.lims.action.analysis;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.BeanUtils;

import com.lims.action.BaseAction;
import com.lims.action.helper.GeneInfoTableConverter;
import com.lims.constants.LimsConstants;
import com.lims.domain.bo.ConsignmentBo;
import com.lims.domain.po.AnalysisRecord;
import com.lims.domain.po.BoardInfo;
import com.lims.domain.po.ConsignOrganization;
import com.lims.domain.po.Consignment;
import com.lims.domain.po.DictInfo;
import com.lims.domain.po.DictItem;
import com.lims.domain.po.ExamineRecord;
import com.lims.domain.po.LocusInfo;
import com.lims.domain.po.PcrRecord;
import com.lims.domain.po.ReagentKit;
import com.lims.domain.po.ReviewRecord;
import com.lims.domain.po.SampleSourceGene;
import com.lims.domain.po.SysUser;
import com.lims.domain.query.BoardInfoQuery;
import com.lims.domain.query.SampleInfoQuery;
import com.lims.domain.query.SampleSourceGeneQuery;
import com.lims.domain.vo.BoardInfoView;
import com.lims.domain.vo.SampleInfoView;
import com.lims.domain.vo.SampleSourceGeneView;
import com.lims.service.ExamineRecordGenerator;
import com.lims.service.consignment.ConsignmentService;
import com.lims.service.dict.SysDictService;
import com.lims.service.examine.ExamineService;
import com.lims.service.gene.SampleSourceGeneService;
import com.lims.service.locus.LocusInfoService;
import com.lims.service.locus.ReagentKitService;
import com.lims.service.organization.ConsignOrganizationService;
import com.lims.service.sample.BoardInfoService;
import com.lims.service.sample.SampleInfoService;
import com.lims.service.sysuser.SysUserService;
import com.lims.util.ConfigUtil;
import com.lims.util.DateUtil;
import com.lims.util.ListUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 */
public class ExamineAnalysisAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private SampleSourceGeneService sampleSourceGeneService;
	@Resource
	private SysDictService sysDictService;
	@Resource
	private ReagentKitService reagentKitService;
	@Resource
	private LocusInfoService locusInfoService;
	@Resource
	private ConsignOrganizationService consignOrganizationService;
	@Resource
	private ConsignmentService consignmentService;
	@Resource
	private BoardInfoService boardInfoService;
	@Resource
	private SampleInfoService sampleInfoService;
	@Resource
	private ExamineService examineService;
	@Resource
	private SysUserService sysUserService;

	private List<DictItem> reviewStatusList;

	private List<DictItem> examineTypeList;

	private List<ReagentKit> reagentKitList;

	private List<ConsignOrganization> organizationList;

	private List<SampleSourceGeneView> sampleSourceGeneViewList;

	private SampleSourceGene sampleSourceGene;

	private SampleSourceGeneView sampleSourceGeneView;

	private SampleSourceGeneQuery sampleSourceGeneQuery;

	private BoardInfoQuery boardInfoQuery;

	private SampleInfoQuery sampleInfoQuery;

	private BoardInfo boardInfo;

	private List<BoardInfoView> boardInfoList;

	private List<SampleInfoView> sampleInfoList;

	private String checkedId;
	private String reviewDesc;

	private List<String> alleleValue1;

	private List<String> alleleValue2;

	private List<String> alleleValue3;

	private Map<String, Object> jsonData;

	private String downloadFileName;
	private InputStream inputStream;

	private List<List<SampleInfoView>> qualityCheckPassedList;

	private List<List<SampleInfoView>> qualityCheckNotPassList;

	private String checkedBoardId;

	private String zipedFilePath;


	public String query() throws Exception{

//		reviewStatusList = sysDictService.findDictItemListByDictInfoType(DictInfo.REVIEW_STATUS);

		if(boardInfoQuery == null){
			boardInfoQuery = new BoardInfoQuery();
			boardInfoQuery.setSortColumn("importDate");
			boardInfoQuery.setSortOrder("DESC");
		}else{
			trimBoardInfoQueryOptions();
		}
		boardInfoQuery.setImportResultFlag(LimsConstants.FLAG_TRUE);

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		boardInfoQuery.setStartRowNum((pageIndex-1)*pageSize + 1);
		boardInfoQuery.setEndRowNum(pageIndex*pageSize);

		recordCount = boardInfoService.findBoardInfoListCount(boardInfoQuery);
		boardInfoList = boardInfoService.findBoardInfoList(boardInfoQuery);

		return SUCCESS;
	}

	public String intoSampleSourceGeneList() throws Exception {

		if(sampleSourceGeneQuery == null){
			sampleSourceGeneQuery = new SampleSourceGeneQuery();
		}else{
			trimSampleSourceGeneQueryOptions();
		}

		boardInfo = boardInfoService.findBoardInfoById(sampleSourceGeneQuery.getBoardId());
//		sampleInfoList = sampleInfoService.findSampleInfoViewList(sampleInfoQuery);
		sampleSourceGeneViewList = sampleSourceGeneService.findSampleSourceGeneViewList(sampleSourceGeneQuery);

		//TODO 质检
//		if(boardInfo.getExamineType().equals(LimsConstants.EXAMINE_TYPE_QUALITY)){
//			qualityCheckPassedList = new ArrayList<List<SampleInfoView>>();
//			qualityCheckNotPassList = new ArrayList<List<SampleInfoView>>();
//
//			for(SampleInfoView si : sampleInfoList){
//				List<SampleInfoView> innerSampleInfoList = new ArrayList<SampleInfoView>();
//
//				BoardInfoQuery query = new BoardInfoQuery();
//				query.setSampleNo(si.getSampleNo());
//				List<BoardInfoView> samplesInBoardInfoList = boardInfoService.findBoardInfoList(query);
//
//				sampleSourceGeneQuery = new SampleSourceGeneQuery();
//				sampleSourceGeneQuery.setSampleId(si.getId());
//				sampleSourceGeneQuery.setBoardId(boardInfo.getId());
//				List<SampleSourceGeneView> qualityResultList = sampleSourceGeneService.findSampleSourceGeneViewList(sampleSourceGeneQuery);
//				List<SampleSourceGeneView> firstCheckResultList = null;
//
//				for(BoardInfoView biv : samplesInBoardInfoList){
//					if(biv.getId().equals(boardInfo.getId())){
//						continue;
//					}
//
//					if(biv.getExamineType()!= null && LimsConstants.EXAMINE_TYPE_FIRST.equals(biv.getExamineType())){
//						sampleSourceGeneQuery = new SampleSourceGeneQuery();
//						sampleSourceGeneQuery.setSampleId(si.getId());
//						sampleSourceGeneQuery.setBoardId(biv.getId());
//
//						firstCheckResultList = sampleSourceGeneService.findSampleSourceGeneViewList(sampleSourceGeneQuery);
//
//						SampleInfoView siv1 = new SampleInfoView();
//						BeanUtils.copyProperties(si, siv1);
//						siv1.setBoardId(biv.getId());
//						siv1.setBoardNo(biv.getBoardNo());
//						siv1.setExamineTypeName("首检");
//						innerSampleInfoList.add(0, siv1);
//					}
//
//					if(biv.getExamineType()!= null && LimsConstants.EXAMINE_TYPE_RECHECK.equals(biv.getExamineType())){
//						SampleInfoView siv2 = new SampleInfoView();
//						BeanUtils.copyProperties(si, siv2);
//						siv2.setBoardId(biv.getId());
//						siv2.setBoardNo(biv.getBoardNo());
//						siv2.setExamineTypeName("复检");
//						innerSampleInfoList.add(siv2);
//					}
//				}
//
//				SampleInfoView siv3 = new SampleInfoView();
//				BeanUtils.copyProperties(si, siv3);
//				siv3.setBoardId(boardInfo.getId());
//				siv3.setBoardNo(boardInfo.getBoardNo());
//				siv3.setExamineTypeName("质检");
//				innerSampleInfoList.add(siv3);
//
//				if(!ListUtil.isEmptyList(qualityResultList) && !ListUtil.isEmptyList(firstCheckResultList)){
//					SampleSourceGene qualityResult = qualityResultList.get(0);
//					SampleSourceGene firstCheckResult = firstCheckResultList.get(0);
//
//					if(qualityResult.getGenotypeInfo().equals(firstCheckResult.getGenotypeInfo())){
//						qualityCheckPassedList.add(innerSampleInfoList);
//					}else{
//						qualityCheckNotPassList.add(innerSampleInfoList);
//					}
//				}else{
//					qualityCheckNotPassList.add(innerSampleInfoList);
//				}
//			}
//
//			return "quality";
//		}

//		sampleSourceGeneViewList = sampleSourceGeneService.findSampleSourceGeneViewList(sampleSourceGeneQuery);

		return SUCCESS;
	}


	/**
	 * 复核通过
	 * @return
	 * @throws Exception
	 */
	public String sampleSourceGeneReviewPass() throws Exception {

		SysUser sysUser = getLoginSysUser();
		jsonData = new HashMap<String, Object>();

		try {
			sampleSourceGeneService.updateGeneReviewStatusBatch(ListUtil.stringArrayToList(checkedId.split(",")), LimsConstants.REVIEW_STATUS_PASSED, null, sysUser);

			jsonData.put("success", true);
		} catch(Exception e) {
			jsonData.put("success", false);
			jsonData.put("errMsg", e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * 复核不通过
	 * @return
	 * @throws Exception
	 */
	public String sampleSourceGeneReviewNotPass() throws Exception {

		SysUser sysUser = getLoginSysUser();
		jsonData = new HashMap<String, Object>();

		try {
			sampleSourceGeneService.updateGeneReviewStatusBatch(ListUtil.stringArrayToList(checkedId.split(",")), LimsConstants.REVIEW_STATUS_NOTPASSED, reviewDesc, sysUser);

			jsonData.put("success", true);
		} catch(Exception e) {
			jsonData.put("success", false);
			jsonData.put("errMsg", e.getMessage());
		}

		return SUCCESS;
	}


	public String viewSampleSourceGeneList() throws Exception {
		if(sampleSourceGeneQuery == null){
			sampleSourceGeneQuery = new SampleSourceGeneQuery();
		}else{
			trimSampleSourceGeneQueryOptions();
		}

		boardInfo = boardInfoService.findBoardInfoById(sampleSourceGeneQuery.getBoardId());
		sampleSourceGeneViewList = sampleSourceGeneService.findSampleSourceGeneViewList(sampleSourceGeneQuery);

		return SUCCESS;
	}

//	public String batchReview() throws Exception {
//		sampleSourceGeneService.updateGeneReviewStatusBatch(checkedId, getLoginSysUser());
//
//		return SUCCESS;
//	}

//	public String singleReview() throws Exception {
//		List<String> checkedIdList = new ArrayList<String>();
//		checkedIdList.add(sampleSourceGeneQuery.getBoardId());
//
//		sampleSourceGeneService.updateGeneReviewStatusBatch(checkedIdList, getLoginSysUser());
//
//		return SUCCESS;
//	}

	public String intoResultDetail() throws Exception {
		reagentKitList = reagentKitService.finalAllReagentKitList();
		List<SampleSourceGeneView> tempList = sampleSourceGeneService.findSampleSourceGeneViewList(sampleSourceGeneQuery);
		List<LocusInfo> allLocusInfoList = locusInfoService.findAllLocusInfoList();

		if(tempList != null && tempList.size() > 0){
			sampleSourceGeneView = tempList.get(0);
			operateType = BaseAction.OPERATE_TYPE_UPDATE;

			ReagentKit rk = StringUtil.isNullOrEmpty(sampleSourceGeneView.getReagentKitId())
						? null
							: reagentKitService.findReagentKitById(sampleSourceGeneView.getReagentKitId());
			List<String> locusOrderOfReagent = null;
			if(rk != null) {
				locusOrderOfReagent = ListUtil.stringArrayToList(StringUtil.stringToArray(rk.getLocusOrder(), "/"));
			}

			String geneInfoHtml = GeneInfoTableConverter.convertGeneInfoToHtmlStr(allLocusInfoList, locusOrderOfReagent, sampleSourceGeneView.getGenotypeInfo());
			sampleSourceGeneView.setGeneInfoHtmlForPage(geneInfoHtml);
		}else{
			operateType = BaseAction.OPERATE_TYPE_ADD;
			SampleInfoView si = sampleInfoService.findSampleInfoById(sampleSourceGeneQuery.getSampleId());
			sampleSourceGeneView = new SampleSourceGeneView();
			sampleSourceGeneView.setSampleId(sampleSourceGeneQuery.getSampleId());
			sampleSourceGeneView.setBoardId(sampleSourceGeneQuery.getBoardId());
			sampleSourceGeneView.setGeneType(LimsConstants.GENE_TYPE_STR);
			sampleSourceGeneView.setSampleNo(si.getSampleNo());
			sampleSourceGeneView.setConsignmentNo(si.getConsignmentNo());

			String geneInfoHtml = GeneInfoTableConverter.convertGeneInfoToHtmlStr(allLocusInfoList, null, null);
			sampleSourceGeneView.setGeneInfoHtmlForPage(geneInfoHtml);
		}

		return SUCCESS;
	}

	public String submitReview() throws Exception {
		SysUser sysUser = getLoginSysUser();
		ReagentKit rk = StringUtil.isNullOrEmpty(sampleSourceGene.getReagentKitId())
							? null
								: reagentKitService.findReagentKitById(sampleSourceGene.getReagentKitId());

		List<String> locusOrderOfReagent = null;
		List<LocusInfo> allLocusInfoList = locusInfoService.findAllLocusInfoList();
		if(rk != null) {
			locusOrderOfReagent = ListUtil.stringArrayToList(StringUtil.stringToArray(rk.getLocusOrder(), "/"));
		}

		String genotypeInfoStr = GeneInfoTableConverter.convertGeneInfoToDbStr(allLocusInfoList, locusOrderOfReagent,
										alleleValue1, alleleValue2, alleleValue3);
		sampleSourceGene.setGenotypeInfo(genotypeInfoStr);
//		if(StringUtils.isEmpty(sampleSourceGene.getReviewStatus())
//				|| LimsConstants.REVIEW_STATUS_WAITING.equals(sampleSourceGene.getReviewStatus())){
//			sampleSourceGene.setReviewStatus(LimsConstants.REVIEW_STATUS_PASSED);
//			sampleSourceGene.setReviewDate(DateUtil.currentDate());
//			sampleSourceGene.setReviewUser(sysUser.getId());
//		}

		if(BaseAction.OPERATE_TYPE_UPDATE.equals(operateType)){
			sampleSourceGene.setUpdateUser(sysUser.getId());
			sampleSourceGeneService.updateSampleSourceGene(sampleSourceGene);
		}else if(BaseAction.OPERATE_TYPE_ADD.equals(operateType)){
			sampleSourceGene.setCreateUser(sysUser.getId());
			sampleSourceGeneService.insertSampleSourceGene(sampleSourceGene);
		}

		jsonData = new HashMap<String, Object>();
		jsonData.put("success", true);

		return SUCCESS;
	}


	public String changeReagentKit() throws Exception {
		sampleSourceGeneView = sampleSourceGeneService.findSampleSourceGeneById(sampleSourceGene.getId());
		ReagentKit rk = StringUtil.isNullOrEmpty(sampleSourceGene.getReagentKitId())
							? null
								: reagentKitService.findReagentKitById(sampleSourceGene.getReagentKitId());
		List<String> locusOrderOfReagent = null;
		List<LocusInfo> allLocusInfoList = locusInfoService.findAllLocusInfoList();
		if(rk != null) {
			locusOrderOfReagent = ListUtil.stringArrayToList(StringUtil.stringToArray(rk.getLocusOrder(), "/"));
		}

		String geneInfoHtml = GeneInfoTableConverter.convertGeneInfoToHtmlStr(allLocusInfoList, locusOrderOfReagent, sampleSourceGeneView.getGenotypeInfo());

		HttpServletResponse response = getServletResponse();
		PrintWriter pw = response.getWriter();
		pw.write(geneInfoHtml);
		pw.flush();

		return NONE;
	}

	public String examineAnalysisQuery() throws Exception{
		reviewStatusList = sysDictService.findDictItemListByDictInfoType(DictInfo.REVIEW_STATUS);

		if(boardInfoQuery == null){
			boardInfoQuery = new BoardInfoQuery();
		}else{
			trimBoardInfoQueryOptions();
		}

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		boardInfoQuery.setStartRowNum((pageIndex-1)*pageSize + 1);
		boardInfoQuery.setEndRowNum(pageIndex*pageSize);

		recordCount = boardInfoService.findBoardInfoListCount(boardInfoQuery);
		boardInfoList = boardInfoService.findBoardInfoList(boardInfoQuery);

		return SUCCESS;
	}

	public String examineRecord() throws Exception{
		examineTypeList = sysDictService.findDictItemListByDictInfoType(DictInfo.EXAMINE_TYPE);
		organizationList = consignOrganizationService.findAllConsignOrganizationList();

		if(boardInfoQuery == null){
			boardInfoQuery = new BoardInfoQuery();
			boardInfoQuery.setSortColumn("boardNo");
			boardInfoQuery.setSortOrder("ASC");
		}else{
			trimBoardInfoQueryOptions();
		}
		boardInfoQuery.setPcrFlag(LimsConstants.FLAG_TRUE);
//		已复核状态
//		sampleSourceGeneQuery.setReviewStatus(LimsConstants.REVIEW_STATUS_PASSED);

		if(pageSize == 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		pageIndex = this.pageIndex == 0 ? 1 : this.pageIndex;
		boardInfoQuery.setStartRowNum((pageIndex-1)*pageSize + 1);
		boardInfoQuery.setEndRowNum(pageIndex*pageSize);

		recordCount = boardInfoService.findBoardInfoListCount(boardInfoQuery);
		boardInfoList = boardInfoService.findBoardInfoList(boardInfoQuery);

		return SUCCESS;
	}


	public String downloadExamineInfo() throws Exception {
		BoardInfo boardInfo = boardInfoService.findBoardInfoById(sampleSourceGeneQuery.getBoardId());
		PcrRecord pcrRecord = examineService.findPcrRecordByBoardInfoId(boardInfo.getId());
		Consignment consignment = consignmentService.findConsignmentByNo(boardInfo.getConsignmentNo());

		ConsignmentBo consignmentBo = new ConsignmentBo();
		BeanUtils.copyProperties(consignment, consignmentBo);

		List<DictItem> pcrSystemList = sysDictService.findDictItemListByDictInfoType(DictInfo.PCR_SYSTEM);
		List<DictItem> sampleCarrierList = sysDictService.findDictItemListByDictInfoType(DictInfo.SAMPLE_CARRIER);
		List<DictItem> holeDiameterList = sysDictService.findDictItemListByDictInfoType(DictInfo.HOLE_DIAMETER);
		List<DictItem> loopCountList = sysDictService.findDictItemListByDictInfoType(DictInfo.PCR_LOOP_COUNT);

		consignmentBo.setSampleCarrierList(sampleCarrierList);
		consignmentBo.setPcrSystem(pcrRecord.getPcrSystem());
		consignmentBo.setPcrSystemList(pcrSystemList);
		consignmentBo.setLoopCount(pcrRecord.getLoopCount());
		consignmentBo.setLoopCountList(loopCountList);
		consignmentBo.setHoleDiameter(boardInfo.getHoleDiameter());
		consignmentBo.setHoleDiameterOther(boardInfo.getHoleDiameterOther());
		consignmentBo.setHoleDiameterList(holeDiameterList);

		String templatePath = ServletActionContext.getServletContext().getRealPath("/template/examinetemplate");
	    String generatePath = ConfigUtil.getProperty(ConfigUtil.DOCUMENT_GENERATE_DIR_KEY);

	    String templateFileName = "examineInfoTemplate.xml";
	    String newFilename = "(" + consignment.getConsignmentNo() + ")样本检验信息表.doc";

	  //编码格式
		String encode = "UTF-8";

		//目标完整路径
		String targetFileName = generatePath + System.getProperty("file.separator") + "样本检验信息表";
		File targetFile = new File(targetFileName);
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}

		targetFileName = targetFileName + System.getProperty("file.separator") + newFilename;

		ExamineRecordGenerator arg = new ExamineRecordGenerator(templatePath);

        //-----------------------公用信息 end-------------------------//
		//-----------------------------生成鉴定书 begin------------------------------//
		boolean isSuccess = arg.builderExamineInfoDoc(consignmentBo, templateFileName, targetFileName, encode);
		if(isSuccess){
			inputStream = new BufferedInputStream(new FileInputStream(targetFileName));
			try{
				downloadFileName = URLEncoder.encode(newFilename,"UTF-8");
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}

		return SUCCESS;
	}

	private String generatePcrExamineRecord(BoardInfo bi, Date currentDate) throws Exception {
		List<SysUser> sysUserList = sysUserService.findSysUserList(new SysUser());
//		BoardInfo boardInfo = boardInfoService.findBoardInfoById(sampleSourceGeneQuery.getBoardId());
		PcrRecord pcrRecord = examineService.findPcrRecordByBoardInfoId(bi.getId());
		ExamineRecord examineRecord = examineService.findExamineRecordByBoardInfoId(bi.getId());
		ReviewRecord reviewRecord = examineService.findReviewRecordByBoardInfoId(bi.getId());
		AnalysisRecord analysisRecord = examineService.findAnalysisRecordByBoardId(bi.getId());
		Consignment consignment = consignmentService.findConsignmentByNo(bi.getConsignmentNo());

		ConsignmentBo consignmentBo = new ConsignmentBo();
		BeanUtils.copyProperties(consignment, consignmentBo);

		List<DictItem> pcrSystemList = sysDictService.findDictItemListByDictInfoType(DictInfo.PCR_SYSTEM);
		List<DictItem> holeDiameterList = sysDictService.findDictItemListByDictInfoType(DictInfo.HOLE_DIAMETER);
		List<DictItem> loopCountList = sysDictService.findDictItemListByDictInfoType(DictInfo.PCR_LOOP_COUNT);
		List<DictItem> examineInstrumentList = sysDictService.findDictItemListByDictInfoType(DictInfo.EXAMINE_INSTRUMENT);

		String holeDiameter = "";
		if(StringUtil.isNotEmpty(bi.getHoleDiameter())){
			String[] holeDiameterArr = bi.getHoleDiameter().split(",");
			for(String hd : holeDiameterArr){
				for(DictItem di : holeDiameterList){
					if(di.getDictKey().equals(hd)){
						holeDiameter += di.getDictValue() + "  ";
						break;
					}
				}
			}
		}

		if(StringUtil.isNotEmpty(bi.getHoleDiameterOther())){
			holeDiameter += bi.getHoleDiameterOther();
		}
		consignmentBo.setHoleDiameter(holeDiameter);
//		consignmentBo.setHoleDiameterList(holeDiameterList);
		consignmentBo.setBoardNo(bi.getBoardNo());
		consignmentBo.setExtractor(findSysUserName(sysUserList, bi.getImportUser()));
		consignmentBo.setExtractorDate((bi.getImportDate() == null ? "" : DateUtil.dateToString(bi.getImportDate(), "yyyy-MM-dd")));

		if(pcrRecord != null){
			consignmentBo.setPcrUser(findSysUserName(sysUserList, pcrRecord.getPcrUser()));
			consignmentBo.setPcrDate((pcrRecord.getCreateDate() == null ? "" : DateUtil.dateToString(pcrRecord.getCreateDate(), "yyyy-MM-dd")));
			consignmentBo.setPcrInstrument(pcrRecord.getPcrInstrument() == null ? "" : pcrRecord.getPcrInstrument());
			consignmentBo.setReagentNo(pcrRecord.getReagentNo() == null ? "" : pcrRecord.getReagentNo());

			for(DictItem di1 : pcrSystemList){
				if(di1.getDictKey().equals(pcrRecord.getPcrSystem())){
					consignmentBo.setPcrSystem(di1.getDictValue());
					break;
				}
			}

			for(DictItem di2 : loopCountList){
				if(di2.getDictKey().equals(pcrRecord.getLoopCount())){
					consignmentBo.setLoopCount(di2.getDictValue());
					break;
				}
			}
		}

		if(examineRecord != null){
			consignmentBo.setExamineUser(findSysUserName(sysUserList, examineRecord.getExamineUserId()));
			consignmentBo.setExamineDate((examineRecord.getExamineDate() == null ? "" : DateUtil.dateToString(examineRecord.getExamineDate(), "yyyy-MM-dd")));

			for(DictItem di3 : examineInstrumentList){
				if(di3.getDictKey().equals(examineRecord.getExamineInstrumentNo())){
					consignmentBo.setExamineInstrument(di3.getDictValue());
					break;
				}
			}
		}

		if(analysisRecord != null){
			consignmentBo.setAnalysisUser(findSysUserName(sysUserList, analysisRecord.getCreateUser()));
			consignmentBo.setAnalysisDate((analysisRecord.getAnalysisDate() == null ? "" : DateUtil.dateToString(analysisRecord.getAnalysisDate(), "yyyy-MM-dd")));
		}

		if(reviewRecord != null){
			consignmentBo.setReviewUser(findSysUserName(sysUserList, reviewRecord.getReviewUserId()));
			consignmentBo.setReviewDate((reviewRecord.getReviewDate() == null ? "" : DateUtil.dateToString(reviewRecord.getReviewDate(), "yyyy-MM-dd")));
		}


		consignmentBo.setBoardSampleMapList(boardInfoService.findBoardSampleListByBoardId(bi.getId()));

		int sampleCountOfBoard = sampleInfoService.selectSampleCountByBoardId(bi.getId());
		consignmentBo.setSampleCountOfBoard(sampleCountOfBoard);

		String templatePath = ServletActionContext.getServletContext().getRealPath("/template/examinetemplate");
	    String generatePath = ConfigUtil.getProperty(ConfigUtil.DOCUMENT_GENERATE_DIR_KEY);

	    String templateFileName = "pcrExamineRecordTemplate.xml";
	    String newFilename = bi.getBoardNo() + "-直接扩增检验记录表.doc";
		//目标完整路径
		String targetFileName = generatePath + System.getProperty("file.separator") + "直接扩增检验记录表"
									+ System.getProperty("file.separator") + DateUtil.getYearStr(currentDate)
									 + System.getProperty("file.separator") + DateUtil.getMonthStr(currentDate)
									 + System.getProperty("file.separator") + DateUtil.getDayStr(currentDate);
		File targetFile = new File(targetFileName);
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}

		targetFileName = targetFileName + System.getProperty("file.separator") + newFilename;
		ExamineRecordGenerator arg = new ExamineRecordGenerator(templatePath);

        //-----------------------公用信息 end-------------------------//
		//-----------------------------生成鉴定书 begin------------------------------//
		boolean isSuccess = arg.builderPcrExamineInfoDoc(consignmentBo, templateFileName, targetFileName, "UTF-8");

		if(isSuccess){
			bi.setGenerateRecordFlag(LimsConstants.FLAG_TRUE);
			bi.setGenerateRecordDate(new Date());
			boardInfoService.updateBoardInfo(bi, getLoginSysUser());

			return targetFileName;
		}else{
			return null;
		}
	}


	public String zipDownloadPcrExamineInfo() throws Exception {
		String[] checkedBoardIdArr = checkedBoardId.split(",");
		BoardInfo tempBoardInfo = null;

		String zipFileName = "直接扩增检验记录表_" + DateUtil.currentDateStr("yyyyMMddHHmmss") + ".zip";
		zipedFilePath = DOWNLOAD_TMP_PATH + System.getProperty("file.separator") + zipFileName;
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipedFilePath));
		byte[] buffer = new byte[1024];
		String generatedFilePath = null;
		File generatedFile = null;

		for(String checkedId : checkedBoardIdArr){

			tempBoardInfo = boardInfoService.findBoardInfoById(checkedId);
//			if(tempBoardInfo.getGenerateRecordFlag() == null
//					|| LimsConstants.FLAG_FALSE.equals(tempBoardInfo.getGenerateRecordFlag())){
				Date currentDate = new Date();
				generatedFilePath = this.generatePcrExamineRecord(tempBoardInfo, currentDate);
				generatedFile = new File(generatedFilePath);
//			}else{
//			    String newFilename = tempBoardInfo.getBoardNo() + ".txt";
//				//目标完整路径
//			    generatedFilePath = new StringBuffer().append(ConfigUtil.getProperty(ConfigUtil.DOCUMENT_GENERATE_DIR_KEY))
//											.append(System.getProperty("file.separator")).append("直接扩增检验记录表")
//											.append(System.getProperty("file.separator"))
//											.append(DateUtil.getYearStr(tempBoardInfo.getGenerateRecordDate()))
//											.append(System.getProperty("file.separator"))
//											.append(DateUtil.getMonthStr(tempBoardInfo.getGenerateRecordDate()))
//											.append(System.getProperty("file.separator"))
//											.append(DateUtil.getDayStr(tempBoardInfo.getGenerateRecordDate()))
//											.append(System.getProperty("file.separator")).append(newFilename)
//											.toString();
//			    generatedFile = new File(generatedFilePath);
//			    if(!generatedFile.exists()){
//			    	Date currentDate = new Date();
//					generatedFilePath = this.generatePcrExamineRecord(tempBoardInfo, currentDate);
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

	public String downloadPcrExamineInfo() throws Exception {
		File zipFile = new File(zipedFilePath);
		inputStream = new FileInputStream(zipFile);
		downloadFileName = URLEncoder.encode(zipFile.getName(), "UTF-8");

		//更新上样板的下载次数
		List<String> boardIdList = Arrays.asList(checkedBoardId.split(","));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("boardIdList", boardIdList);
		params.put("recordType", LimsConstants.RECORD_TYPE_EXAMINATION);
		boardInfoService.updateBoardDownloadCount(params);

		return SUCCESS;
//
//		BoardInfo boardInfo = boardInfoService.findBoardInfoById(sampleSourceGeneQuery.getBoardId());
//
//	    String generatePath = ConfigUtil.getProperty(ConfigUtil.DOCUMENT_GENERATE_DIR_KEY);
//	    String newFilename = boardInfo.getBoardNo() + "-直接扩增检验记录表.doc";
//
//		//目标完整路径
//		String targetFileName = generatePath + System.getProperty("file.separator") + "直接扩增检验记录表"
//									+ System.getProperty("file.separator") + DateUtil.getYearStr(boardInfo.getGenerateRecordDate())
//									 + System.getProperty("file.separator") + DateUtil.getMonthStr(boardInfo.getGenerateRecordDate())
//									 + System.getProperty("file.separator") + DateUtil.getDayStr(boardInfo.getGenerateRecordDate())
//									 + System.getProperty("file.separator") + newFilename;
//		inputStream = new BufferedInputStream(new FileInputStream(targetFileName));
//		try{
//			downloadFileName = URLEncoder.encode(newFilename, "UTF-8");
//		}catch(UnsupportedEncodingException e){
//			e.printStackTrace();
//		}
//
//		return SUCCESS;
	}

	private void trimSampleSourceGeneQueryOptions() {

		if(StringUtil.isNullOrEmpty(sampleSourceGeneQuery.getSampleNo())){
			sampleSourceGeneQuery.setSampleNo(null);
		}else{
			sampleSourceGeneQuery.setSampleNo(sampleSourceGeneQuery.getSampleNo().trim());
		}

	}

	private void trimBoardInfoQueryOptions(){
		if(StringUtil.isNullOrEmpty(boardInfoQuery.getConsignmentNo())){
			boardInfoQuery.setConsignmentNo(null);
		}else{
			boardInfoQuery.setConsignmentNo(boardInfoQuery.getConsignmentNo().trim());
		}

		if(StringUtil.isNullOrEmpty(boardInfoQuery.getBoardNo())){
			boardInfoQuery.setBoardNo(null);
		}else{
			boardInfoQuery.setBoardNo(boardInfoQuery.getBoardNo().trim());
		}

		if(StringUtil.isNullOrEmpty(boardInfoQuery.getSampleNo())){
			boardInfoQuery.setSampleNo(null);
		}else{
			boardInfoQuery.setSampleNo(boardInfoQuery.getSampleNo().trim());
		}

		if(StringUtil.isNullOrEmpty(boardInfoQuery.getExamineType())){
			boardInfoQuery.setExamineType(null);
		}else{
			boardInfoQuery.setExamineType(boardInfoQuery.getExamineType().trim());
		}

		if(StringUtil.isNullOrEmpty(boardInfoQuery.getOrganizationId())){
			boardInfoQuery.setOrganizationId(null);
		}else{
			boardInfoQuery.setOrganizationId(boardInfoQuery.getOrganizationId().trim());
		}

		if(StringUtil.isNullOrEmpty(boardInfoQuery.getReviewStatus())){
			boardInfoQuery.setReviewStatus(null);
		}else{
			boardInfoQuery.setReviewStatus(boardInfoQuery.getReviewStatus().trim());
		}
	}

	private String findSysUserName(List<SysUser> sysUserList, String userId){
		for(SysUser su : sysUserList){
			if(su.getId().equals(userId)){
				return su.getFullName();
			}
		}

		return "";
	}


	public SampleSourceGeneService getSampleSourceGeneService() {
		return sampleSourceGeneService;
	}


	public void setSampleSourceGeneService(
			SampleSourceGeneService sampleSourceGeneService) {
		this.sampleSourceGeneService = sampleSourceGeneService;
	}


	public List<SampleSourceGeneView> getSampleSourceGeneViewList() {
		return sampleSourceGeneViewList;
	}


	public void setSampleSourceGeneViewList(
			List<SampleSourceGeneView> sampleSourceGeneViewList) {
		this.sampleSourceGeneViewList = sampleSourceGeneViewList;
	}


	public SampleSourceGeneView getSampleSourceGeneView() {
		return sampleSourceGeneView;
	}


	public void setSampleSourceGeneView(SampleSourceGeneView sampleSourceGeneView) {
		this.sampleSourceGeneView = sampleSourceGeneView;
	}


	public SampleSourceGeneQuery getSampleSourceGeneQuery() {
		return sampleSourceGeneQuery;
	}


	public void setSampleSourceGeneQuery(SampleSourceGeneQuery sampleSourceGeneQuery) {
		this.sampleSourceGeneQuery = sampleSourceGeneQuery;
	}


	public SysDictService getSysDictService() {
		return sysDictService;
	}


	public void setSysDictService(SysDictService sysDictService) {
		this.sysDictService = sysDictService;
	}


	public List<DictItem> getReviewStatusList() {
		return reviewStatusList;
	}


	public void setReviewStatusList(List<DictItem> reviewStatusList) {
		this.reviewStatusList = reviewStatusList;
	}


	public String getCheckedId() {
		return checkedId;
	}


	public void setCheckedId(String checkedId) {
		this.checkedId = checkedId;
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


	public SampleSourceGene getSampleSourceGene() {
		return sampleSourceGene;
	}


	public void setSampleSourceGene(SampleSourceGene sampleSourceGene) {
		this.sampleSourceGene = sampleSourceGene;
	}


	public LocusInfoService getLocusInfoService() {
		return locusInfoService;
	}


	public void setLocusInfoService(LocusInfoService locusInfoService) {
		this.locusInfoService = locusInfoService;
	}


	public List<String> getAlleleValue1() {
		return alleleValue1;
	}


	public void setAlleleValue1(List<String> alleleValue1) {
		this.alleleValue1 = alleleValue1;
	}


	public List<String> getAlleleValue2() {
		return alleleValue2;
	}


	public void setAlleleValue2(List<String> alleleValue2) {
		this.alleleValue2 = alleleValue2;
	}


	public Map<String, Object> getJsonData() {
		return jsonData;
	}


	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}


	public List<DictItem> getExamineTypeList() {
		return examineTypeList;
	}


	public void setExamineTypeList(List<DictItem> examineTypeList) {
		this.examineTypeList = examineTypeList;
	}


	public List<ConsignOrganization> getOrganizationList() {
		return organizationList;
	}


	public void setOrganizationList(List<ConsignOrganization> organizationList) {
		this.organizationList = organizationList;
	}


	public ConsignOrganizationService getConsignOrganizationService() {
		return consignOrganizationService;
	}


	public void setConsignOrganizationService(
			ConsignOrganizationService consignOrganizationService) {
		this.consignOrganizationService = consignOrganizationService;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ConsignmentService getConsignmentService() {
		return consignmentService;
	}

	public void setConsignmentService(ConsignmentService consignmentService) {
		this.consignmentService = consignmentService;
	}

	public BoardInfoService getBoardInfoService() {
		return boardInfoService;
	}

	public void setBoardInfoService(BoardInfoService boardInfoService) {
		this.boardInfoService = boardInfoService;
	}

	public ExamineService getExamineService() {
		return examineService;
	}

	public void setExamineService(ExamineService examineService) {
		this.examineService = examineService;
	}

	public SysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public BoardInfoQuery getBoardInfoQuery() {
		return boardInfoQuery;
	}

	public void setBoardInfoQuery(BoardInfoQuery boardInfoQuery) {
		this.boardInfoQuery = boardInfoQuery;
	}

	public List<BoardInfoView> getBoardInfoList() {
		return boardInfoList;
	}

	public void setBoardInfoList(List<BoardInfoView> boardInfoList) {
		this.boardInfoList = boardInfoList;
	}

	public BoardInfo getBoardInfo() {
		return boardInfo;
	}

	public void setBoardInfo(BoardInfo boardInfo) {
		this.boardInfo = boardInfo;
	}

	public SampleInfoService getSampleInfoService() {
		return sampleInfoService;
	}

	public void setSampleInfoService(SampleInfoService sampleInfoService) {
		this.sampleInfoService = sampleInfoService;
	}

	public List<SampleInfoView> getSampleInfoList() {
		return sampleInfoList;
	}

	public void setSampleInfoList(List<SampleInfoView> sampleInfoList) {
		this.sampleInfoList = sampleInfoList;
	}

	public SampleInfoQuery getSampleInfoQuery() {
		return sampleInfoQuery;
	}

	public void setSampleInfoQuery(SampleInfoQuery sampleInfoQuery) {
		this.sampleInfoQuery = sampleInfoQuery;
	}


	public List<List<SampleInfoView>> getQualityCheckPassedList() {
		return qualityCheckPassedList;
	}

	public void setQualityCheckPassedList(
			List<List<SampleInfoView>> qualityCheckPassedList) {
		this.qualityCheckPassedList = qualityCheckPassedList;
	}

	public List<List<SampleInfoView>> getQualityCheckNotPassList() {
		return qualityCheckNotPassList;
	}

	public void setQualityCheckNotPassList(
			List<List<SampleInfoView>> qualityCheckNotPassList) {
		this.qualityCheckNotPassList = qualityCheckNotPassList;
	}

	public String getReviewDesc() {
		return reviewDesc;
	}

	public void setReviewDesc(String reviewDesc) {
		this.reviewDesc = reviewDesc;
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

	public List<String> getAlleleValue3() {
		return alleleValue3;
	}

	public void setAlleleValue3(List<String> alleleValue3) {
		this.alleleValue3 = alleleValue3;
	}

}
