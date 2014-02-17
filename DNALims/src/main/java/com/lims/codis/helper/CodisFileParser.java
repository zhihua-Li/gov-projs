/**
 * CodisFileParser.java
 *
 * 2013-7-8
 */
package com.lims.codis.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.lims.util.StringUtil;


/**
 * @author lizhihua
 *
 * CODIS文件解析器
 *
 */
public class CodisFileParser {

	public static List<FileStrInfo> getDataFromDat(File codisFile) throws Exception {
		List<FileStrInfo> fileStrInfoList = new ArrayList<FileStrInfo>();
		BufferedReader br = new BufferedReader(new FileReader(codisFile));
		String str = null;
		//基因座个数
		int locusCount = 0;

		String alleleValue1 = "";
		String alleleValue2 = "";
		String alleleValue3 = "";

		while ((str = br.readLine()) != null) {

			if(StringUtil.isNullOrEmpty(str)){
				continue;
			}

			try {
				if ("DNA Analysis Result".equalsIgnoreCase(str)) {
					int readCount = 0;
					//峰值个数
					int valCount = 0;

					//2个无用信息
					for(int i =0; i < 2; i++){
						br.readLine();
					}

					FileStrInfo fileStrInfo = new FileStrInfo();
					List<LocusValues> locusValuesList = new ArrayList<LocusValues>();
					LocusValues locusValues = null;
					//样本实验室编号
					fileStrInfo.setSampleNo(br.readLine());

					//5个无用信息
					for(int i = 0; i < 5; i++) {
						br.readLine();
					}

					locusCount = Integer.parseInt(br.readLine());
					while(readCount < locusCount) {
						locusValues = new LocusValues();

						String locusName = br.readLine();
						if(StringUtil.isNullOrEmpty(locusName)){
							break;
						}
						//4个无用信息
						for(int i=0;i<4;i++) {
							br.readLine();
						}

						valCount = Integer.parseInt(br.readLine());
						if(valCount == 2) {
							alleleValue1 = br.readLine();
							alleleValue2 = br.readLine();
							alleleValue3 = "";
						} else if(valCount == 3) {
							alleleValue1 = br.readLine();
							alleleValue2 = br.readLine();
							alleleValue3 = br.readLine();
						} else if(valCount == 1) {
							alleleValue1 = br.readLine();
							alleleValue2 = "";
							alleleValue3 = "";
						} else if(valCount == 0) {
							alleleValue1 = "";
							alleleValue2 = "";
							alleleValue3 = "";
						} else{
							fileStrInfo.setMessage("样本基因座 "+locusName+" 有" + valCount +"个位点，暂不支持！");
							alleleValue1 = "";
							alleleValue2 = "";
							alleleValue3 = "";
							break;
						}

						locusValues.setLocusName(locusName);
						locusValues.setAlleleValue1(alleleValue1);
						locusValues.setAlleleValue2(alleleValue2);
						locusValues.setAlleleValue3(alleleValue3);
						locusValuesList.add(locusValues);

						readCount++;
					}

					fileStrInfo.setLocusValuesList(locusValuesList);
					fileStrInfoList.add(fileStrInfo);
				}
			} catch(Exception e) {
				throw new Exception("文件格式有误或基因数据有误，未能读出有效基因数据，请检查Codis文件格式是否正确！");
			}
		}

		return fileStrInfoList;
	}

}
