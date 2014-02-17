/**
 * SingleSampleQuickCompareAction.java
 *
 * 2014-1-11
 */
package com.lims.action.quickcompare;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lims.action.BaseAction;
import com.lims.action.helper.GeneInfoTableConverter;
import com.lims.codis.helper.CodisFileParser;
import com.lims.codis.helper.FileStrInfo;
import com.lims.codis.helper.LocusValues;
import com.lims.domain.po.LocusInfo;
import com.lims.domain.vo.CompareResultView;
import com.lims.service.comparison.QuickComparisonService;
import com.lims.service.locus.LocusInfoService;
import com.lims.util.ListUtil;

/**
 * @author lizhihua
 *
 */
public class SingleSampleQuickCompareAction extends BaseAction {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


//	private String geneInfoStr;
	private String geneInfoHtmlStr;

	private int diffLimit;		//容差上限

	private int sameLimit;		//匹配下限


	private List<String> alleleValue1;

	private List<String> alleleValue2;

	private List<String> alleleValue3;

	private String inputGeneInfoStr;

	private List<LocusInfo> allLocusInfoList;

	private String allLocusNameStr;

	private List<CompareResultView> compareResultList;


	@Resource
	private LocusInfoService locusInfoService;
	@Resource
	private QuickComparisonService quickComparisonService;

	private Map<String, Object> jsonData;

	private File codisFile;

	private String codisFileFileName;

	private String codisPath;

	public String into() throws Exception {
		allLocusInfoList = locusInfoService.findAllLocusInfoList();
//		String geneInfoStr = GeneInfoTableConverter.convertGeneInfoToDbStr(allLocusInfoList, null, null, null, null);
		geneInfoHtmlStr = GeneInfoTableConverter.convertGeneInfoToHtmlStr(allLocusInfoList, null, null);

		return SUCCESS;
	}


	public String submit() throws Exception {
		allLocusInfoList = locusInfoService.findAllLocusInfoList();
		inputGeneInfoStr = GeneInfoTableConverter.convertGeneInfoToDbStr(allLocusInfoList, null,
				alleleValue1, alleleValue2, alleleValue3);

		Map<String, Object> compareParams = new HashMap<String, Object>();
		compareParams.put("sameLimit", this.sameLimit);
		compareParams.put("diffLimit", this.diffLimit);
		List<Map<String, Object>> compareResultMapList = quickComparisonService.singleSampleCompare(inputGeneInfoStr, compareParams);
		if(!ListUtil.isEmptyList(compareResultMapList)){
			compareResultList = new ArrayList<CompareResultView>();
			CompareResultView compareResultView = null;
			for(Map<String, Object> resultMap : compareResultMapList){
				String matchedGeneId = resultMap.get("matchedGeneId").toString();
				compareResultView = this.quickComparisonService.findQuickCompareMatchedSample(matchedGeneId);
				compareResultView.setSameNum((Integer)(resultMap.get("sameCount")));
				compareResultView.setDiffNum((Integer)(resultMap.get("diffCount")));

				compareResultList.add(compareResultView);
			}

			allLocusNameStr = "";
			for(LocusInfo locusInfo : allLocusInfoList){
				allLocusNameStr += locusInfo.getGenoName() + ";";
			}
			allLocusNameStr = allLocusNameStr.substring(0, allLocusNameStr.length()-1);
		}

		return SUCCESS;
	}


	public String intoSelectCodisSample() throws Exception {

		return SUCCESS;
	}

	public String selectCodisSample() throws Exception {

		jsonData = new HashMap<String, Object>();
		try {
			List<FileStrInfo> fileStrInfoList = CodisFileParser.getDataFromDat(this.codisFile);

			if(ListUtil.isEmptyList(fileStrInfoList)){
				jsonData.put("success", false);
				jsonData.put("errorMsg", "CODIS文件中无样本。");
			}else{
				List<Map<String, Object>> codisSampleList = new ArrayList<Map<String, Object>>();
				Map<String, Object> tempMap = null;
				for(FileStrInfo fileStrInfo : fileStrInfoList){
					tempMap = new HashMap<String, Object>();
					tempMap.put("sampleNo", fileStrInfo.getSampleNo());
					tempMap.put("strInfo", convertGenotypeStr(fileStrInfo.getLocusValuesList()));

					codisSampleList.add(tempMap);
				}

				jsonData.put("sampleList", codisSampleList);
				jsonData.put("success", true);
			}
		} catch(Exception e){
			jsonData.put("success", false);
			jsonData.put("errorMsg", "CODIS文件解析失败。" + e.getMessage());
		}

		return SUCCESS;
	}

	private String convertGenotypeStr(List<LocusValues> locusValuesList) throws Exception {
		String resultStr = "";

		List<LocusInfo> locusInfoList = locusInfoService.findAllLocusInfoList();
		for(LocusInfo locusInfo : locusInfoList){
			String locusInfoNameInDb = locusInfo.getGenoName();
			String locusInfoAliasInDb = locusInfo.getGenoAlias();

			boolean hasLocusInCodis = false;
			for(LocusValues lv : locusValuesList){
				String locusNameInCodis = lv.getLocusName();
				if(locusNameInCodis.equalsIgnoreCase(locusInfoNameInDb)
						|| (locusInfoAliasInDb != null
								&& locusInfoAliasInDb.toUpperCase().indexOf(locusNameInCodis.toUpperCase()) > -1)){
					resultStr += lv.getAlleleValue1() + "/" + lv.getAlleleValue2() + "/" + lv.getAlleleValue3() + ";";
					hasLocusInCodis = true;
					break;
				}
			}

			if(!hasLocusInCodis){
				resultStr += "//;";
			}
		}

		resultStr = resultStr.substring(0, resultStr.length()-1);

		return resultStr;
	}

	public String getGeneInfoHtmlStr() {
		return geneInfoHtmlStr;
	}


	public void setGeneInfoHtmlStr(String geneInfoHtmlStr) {
		this.geneInfoHtmlStr = geneInfoHtmlStr;
	}


	public int getDiffLimit() {
		return diffLimit;
	}


	public void setDiffLimit(int diffLimit) {
		this.diffLimit = diffLimit;
	}


	public int getSameLimit() {
		return sameLimit;
	}


	public void setSameLimit(int sameLimit) {
		this.sameLimit = sameLimit;
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


	public List<String> getAlleleValue3() {
		return alleleValue3;
	}


	public void setAlleleValue3(List<String> alleleValue3) {
		this.alleleValue3 = alleleValue3;
	}


	public List<CompareResultView> getCompareResultList() {
		return compareResultList;
	}


	public void setCompareResultList(List<CompareResultView> compareResultList) {
		this.compareResultList = compareResultList;
	}


	public String getInputGeneInfoStr() {
		return inputGeneInfoStr;
	}


	public void setInputGeneInfoStr(String inputGeneInfoStr) {
		this.inputGeneInfoStr = inputGeneInfoStr;
	}


	public List<LocusInfo> getAllLocusInfoList() {
		return allLocusInfoList;
	}


	public void setAllLocusInfoList(List<LocusInfo> allLocusInfoList) {
		this.allLocusInfoList = allLocusInfoList;
	}


	public String getAllLocusNameStr() {
		return allLocusNameStr;
	}


	public void setAllLocusNameStr(String allLocusNameStr) {
		this.allLocusNameStr = allLocusNameStr;
	}


	public File getCodisFile() {
		return codisFile;
	}


	public void setCodisFile(File codisFile) {
		this.codisFile = codisFile;
	}


	public String getCodisFileFileName() {
		return codisFileFileName;
	}


	public void setCodisFileFileName(String codisFileFileName) {
		this.codisFileFileName = codisFileFileName;
	}


	public String getCodisPath() {
		return codisPath;
	}


	public void setCodisPath(String codisPath) {
		this.codisPath = codisPath;
	}


	public Map<String, Object> getJsonData() {
		return jsonData;
	}


	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}

}
