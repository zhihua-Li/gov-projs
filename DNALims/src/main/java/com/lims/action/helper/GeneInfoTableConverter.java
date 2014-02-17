/**
 * GeneInfoTableConverter.java
 *
 * 2013-7-10
 */
package com.lims.action.helper;

import java.util.List;

import com.lims.domain.po.LocusInfo;
import com.lims.util.ListUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 */
public class GeneInfoTableConverter {


	/**
	 * 根据试剂盒名称列表和基因位点信息生成页面html脚本
	 * @return
	 */
	public static String convertGeneInfoToHtmlStr(List<LocusInfo> allLocusInfoList, List<String> locusOrderOfReagent, String geneInfoStr) {
		String htmlStr = "";

		String alleleValues[] = null;
		if(StringUtil.isNullOrEmpty(geneInfoStr)){
			alleleValues = new String[0];
		}else{
			alleleValues = geneInfoStr.split(";");
		}

		if(locusOrderOfReagent != null && locusOrderOfReagent.size() > 0){
			for(int j = 0; j < locusOrderOfReagent.size(); j += 2){
					htmlStr += "<tr>";
					for(int k = 0; k < 2; k++){
						if(j+k >= allLocusInfoList.size()){
							htmlStr += "<td></td><td></td>";
							break;
						}
						if(j+k >= locusOrderOfReagent.size()){
							break;
						}
						String locusOfReagent = locusOrderOfReagent.get(j+k);
						LocusInfo locusInfo = null;
						int locusIdx = 0;
						for(int i = 0; i < allLocusInfoList.size(); i++){
							locusInfo = allLocusInfoList.get(i);
							if(locusOfReagent.equals(locusInfo.getGenoNo())){
								locusIdx = i;
								break;
							}
						}

						String locusName = locusInfo.getGenoName();
						String[] alleles = new String[0];
						try {
							alleles = alleleValues[locusIdx].split("/");
						}catch(Exception e){	alleles = new String[0];	}

						String alleleValue1 = null;
						try {
							alleleValue1 = alleles[0];
						} catch(ArrayIndexOutOfBoundsException a){		alleleValue1 = "";		}

						String alleleValue2 = null;
						try {
							alleleValue2 = alleles[1];
						} catch(ArrayIndexOutOfBoundsException a){		alleleValue2 = "";		}

						String alleleValue3 = null;
						try {
							alleleValue3 = alleles[2];
						} catch(ArrayIndexOutOfBoundsException a){		alleleValue3 = "";		}

						htmlStr += "<td>" + locusName + "</td><td>";
						htmlStr += "<input type=\"text\" class=\"text_two width80\" name=\"alleleValue1\" maxlength=\"4\" value=\"" + alleleValue1 + "\" />";
						htmlStr += " / <input type=\"text\" class=\"text_two width80\" name=\"alleleValue2\" maxlength=\"4\" value=\"" + alleleValue2 + "\" />";
						htmlStr += " / <input type=\"text\" class=\"text_two width80\" name=\"alleleValue3\" maxlength=\"4\" value=\"" + alleleValue3 + "\" />";
						htmlStr += "</td>";
					}
					htmlStr += "</tr>";
			}
		}else{
			for(int i = 0; i < allLocusInfoList.size(); i+=2){
				htmlStr += "<tr>";
				for(int k = 0; k < 2; k++){
					if(i+k >= allLocusInfoList.size()){
						htmlStr += "<td></td><td></td>";
						break;
					}

					LocusInfo locusInfo = allLocusInfoList.get(i + k);
					String locusName = locusInfo.getGenoName();
					String[] alleles = new String[0];
					try {
						alleles = alleleValues[i+k].split("/");
					}catch(Exception e){	alleles = new String[0];	}

					String alleleValue1 = null;
					try {
						alleleValue1 = alleles[0];
					} catch(ArrayIndexOutOfBoundsException a){		alleleValue1 = "";		}

					String alleleValue2 = null;
					try {
						alleleValue2 = alleles[1];
					} catch(ArrayIndexOutOfBoundsException a){		alleleValue2 = "";		}

					String alleleValue3 = null;
					try {
						alleleValue3 = alleles[2];
					} catch(ArrayIndexOutOfBoundsException a){		alleleValue3 = "";		}

					htmlStr += "<td>" + locusName + "</td><td>";
					htmlStr += "<input type=\"text\" class=\"text_two width80\" name=\"alleleValue1\" maxlength=\"4\" value=\"" + alleleValue1 + "\" />";
					htmlStr += " / <input type=\"text\" class=\"text_two width80\" name=\"alleleValue2\" maxlength=\"4\" value=\"" + alleleValue2 + "\" />";
					htmlStr += " / <input type=\"text\" class=\"text_two width80\" name=\"alleleValue3\" maxlength=\"4\" value=\"" + alleleValue3 + "\" />";
					htmlStr += "</td>";
				}
				htmlStr += "</tr>";
			}
		}

		return htmlStr;
	}

//
//	/**
//	 * 根据试剂盒名称列表和基因位点信息生成页面html脚本
//	 * @return
//	 */
//	public static String convertChangedReagentGeneInfoToHtmlStr(List<LocusInfo> allLocusInfoList, List<String> newLocusOrderOfReagent, List<String> oldLocusOrderOfReagent, String geneInfoStr) {
//		String htmlStr = "";
//
//		String alleleValues[] = null;
//		if(StringUtil.isNullOrEmpty(geneInfoStr)){
//			alleleValues = new String[0];
//		}else{
//			alleleValues = geneInfoStr.split(";");
//		}
//
//		if(newLocusOrderOfReagent != null && newLocusOrderOfReagent.size() > 0){
//			for(int j = 0; j < newLocusOrderOfReagent.size(); j += 2){
//					htmlStr += "<tr>";
//					for(int k = 0; k < 2; k++){
//						if(j+k >= allLocusInfoList.size()){
//							htmlStr += "<td></td><td></td>";
//							break;
//						}
//
//						String locusOfReagent = locusOrderOfReagent.get(j+k);
//						LocusInfo locusInfo = null;
//						for(int i = 0; i < allLocusInfoList.size(); i++){
//							locusInfo = allLocusInfoList.get(i);
//							if(locusOfReagent.equals(locusInfo.getGenoNo())){
//								break;
//							}
//						}
//
//						String locusName = locusInfo.getGenoName();
//						String[] alleles = alleleValues[j+k].split("/");
//
//						String alleleValue1 = null;
//						try {
//							alleleValue1 = alleles[0];
//						} catch(ArrayIndexOutOfBoundsException a){
//							alleleValue1 = "";
//						}
//
//						String alleleValue2 = null;
//						try {
//							alleleValue2 = alleles[1];
//						} catch(ArrayIndexOutOfBoundsException a){
//							alleleValue2 = "";
//						}
//
//						htmlStr += "<td>" + locusName + "</td><td>";
//						htmlStr += "<input type=\"text\" class=\"text_two width80\" name=\"alleleValue1\" maxlength=\"4\" value=\"" + alleleValue1 + "\" />";
//						htmlStr += " / <input type=\"text\" class=\"text_two width80\" name=\"alleleValue2\" maxlength=\"4\" value=\"" + alleleValue2 + "\" />";
//						htmlStr += "</td>";
//					}
//					htmlStr += "</tr>";
//			}
//		}else{
//			for(int i = 0; i < allLocusInfoList.size(); i+=2){
//				htmlStr += "<tr>";
//				for(int k = 0; k < 2; k++){
//					if(i+k >= allLocusInfoList.size()){
//						htmlStr += "<td></td><td></td>";
//						break;
//					}
//
//					LocusInfo locusInfo = allLocusInfoList.get(i + k);
//					String locusName = locusInfo.getGenoName();
//					String[] alleles = alleleValues[i+k].split("/");
//
//					String alleleValue1 = null;
//					try {
//						alleleValue1 = alleles[0];
//					} catch(ArrayIndexOutOfBoundsException a){
//						alleleValue1 = "";
//					}
//
//					String alleleValue2 = null;
//					try {
//						alleleValue2 = alleles[1];
//					} catch(ArrayIndexOutOfBoundsException a){
//						alleleValue2 = "";
//					}
//
//					htmlStr += "<td>" + locusName + "</td><td>";
//					htmlStr += "<input type=\"text\" class=\"text_two width80\" name=\"alleleValue1\" maxlength=\"4\" value=\"" + alleleValue1 + "\" />";
//					htmlStr += " / <input type=\"text\" class=\"text_two width80\" name=\"alleleValue2\" maxlength=\"4\" value=\"" + alleleValue2 + "\" />";
//					htmlStr += "</td>";
//				}
//				htmlStr += "</tr>";
//			}
//		}
//
//		return htmlStr;
//	}


	/**
	 * 根据试剂盒名称列表和基因位点信息生成入库的基因型大字段
	 * @return
	 */
	public static String convertGeneInfoToDbStr(List<LocusInfo> allLocusInfoList, List<String> locusOrderOfReagent,
				List<String> alleles1, List<String> alleles2, List<String> alleles3) {
		String geneStr = null;
		String alleleStr1 = "";
		String alleleStr2 = "";
		String alleleStr3 = "";
		StringBuffer geneStrBuffer = new StringBuffer();

		if(locusOrderOfReagent != null && locusOrderOfReagent.size() > 0){
			for(int i = 0; i < allLocusInfoList.size(); i++){
				LocusInfo locusInfo = allLocusInfoList.get(i);

				for(int j = 0; j < locusOrderOfReagent.size(); j++){
					if(locusOrderOfReagent.get(j).equals(locusInfo.getGenoNo())){
						try {
							alleleStr1 = ListUtil.isEmptyList(alleles1) ? "" : alleles1.get(j);
						} catch(Exception e){	alleleStr1 = "";	}

						try {
							alleleStr2 = ListUtil.isEmptyList(alleles2) ? "" : alleles2.get(j);
						} catch(Exception e){	alleleStr2 = "";	}

						try {
							alleleStr3 = ListUtil.isEmptyList(alleles3) ? "" : alleles3.get(j);
						} catch(Exception e){	alleleStr3 = "";	}

						//allele3等于空，且alleleStr1等于alleleStr2，将alleleStr2等于空字符
						if(!alleleStr1.equals("") && !alleleStr1.equals("")
								&& alleleStr3.equals("") && alleleStr1.equals(alleleStr2)){
							alleleStr2 = "";
						}

						break;
					}
				}

				geneStrBuffer.append(alleleStr1).append("/").append(alleleStr2).append("/").append(alleleStr3).append(";");
			}
		}else{
			for(int i = 0; i < allLocusInfoList.size(); i++){

				try {
					alleleStr1 = ListUtil.isEmptyList(alleles1) ? "" : alleles1.get(i);
				} catch(Exception e){	alleleStr1 = "";	}

				try {
					alleleStr2 = ListUtil.isEmptyList(alleles2) ? "" : alleles2.get(i);
				} catch(Exception e){	alleleStr2 = "";	}

				try {
					alleleStr3 = ListUtil.isEmptyList(alleles3) ? "" : alleles3.get(i);
				} catch(Exception e){	alleleStr3 = "";	}

				//allele3等于空，且alleleStr1等于alleleStr2，将alleleStr2等于空字符
				if(!alleleStr1.equals("") && !alleleStr1.equals("")
						&& alleleStr3.equals("") && alleleStr1.equals(alleleStr2)){
					alleleStr2 = "";
				}

				geneStrBuffer.append(alleleStr1).append("/").append(alleleStr2).append("/").append(alleleStr3).append(";");
			}
		}

		geneStr = geneStrBuffer.toString();
		return geneStr.substring(0, geneStr.length() - 1);
	}



}
