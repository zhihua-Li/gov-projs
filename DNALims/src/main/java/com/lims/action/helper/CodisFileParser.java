/**
 * CodisFileParser.java
 *
 * 2013-7-8
 */
package com.lims.action.helper;

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

		String alleleValue1 = null;
		String alleleValue2 = null;

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
						} else if(valCount == 1) {
							String val = br.readLine();
							alleleValue1 = val;
							alleleValue2 = val;
						} else if(valCount == 0) {
							alleleValue1 = null;
							alleleValue2 = null;
						} else{
							fileStrInfo.setMessage("样本基因座 "+locusName+" 有多个位点！");
							alleleValue1 = null;
							alleleValue2 = null;
							break;
						}

						locusValues.setLocusName(locusName);
						locusValues.setAlleleValue1(alleleValue1);
						locusValues.setAlleleValue2(alleleValue2);
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
