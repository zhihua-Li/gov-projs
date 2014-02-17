/**
 * LimsConstants.java
 *
 * 2013-6-16
 */
package com.lims.constants;

/**
 * @author lizhihua
 *
 */
public class LimsConstants {


	public final static String FLAG_TRUE = "1";
	public final static String FLAG_FALSE = "0";


	public final static String CONSIGNMENT_NO_PREFIX = "C";			//委托编号前缀

	public final static String QUALITY_SAMPLE_NO_PREFIX = "QC";			//质控人员编号前缀



	public final static int SEQUENCE_NO_LENGTH = 5;		//流水号长度

	public final static String RECORD_TYPE_SYTABLE = "1";		//上样表
	public final static String RECORD_TYPE_EXAMINATION = "2";	//检验记录表

	/**
	 * 检验状态
	 */
	public final static String EXAMINE_STATUS_EXTRACTED = "1";		//已提取
	public final static String EXAMINE_STATUS_PCRED = "2";			//已扩增
	public final static String EXAMINE_STATUS_EXAMINED = "3";		//已检测
	public final static String EXAMINE_STATUS_ANALYSISED = "4";		//已分析
	public final static String EXAMINE_STATUS_REVIEWED = "5";		//已复核

	/**
	 * 检验类型
	 */
	public final static String EXAMINE_TYPE_FIRST = "1";	//首检
	public final static String EXAMINE_TYPE_RECHECK = "2";	//复检
	public final static String EXAMINE_TYPE_QUALITY = "3";	//质检

	/**
	 * 基因类型
	 */
	public final static String GENE_TYPE_STR = "1";			//STR
	public final static String GENE_TYPE_YSTR = "2";		//Y-STR


	/**
	 * 复核状态
	 */
	public final static String REVIEW_STATUS_WAITING = "0";			//待复核
	public final static String REVIEW_STATUS_PASSED = "1";			//复核通过
	public final static String REVIEW_STATUS_NOTPASSED = "2";		//复核未通过

	public final static String[] horizontalPositionArr = {
		"A01","A02","A03","A04","A05","A06","A07","A08","A09","A10","A11","A12",
		"B01","B02","B03","B04","B05","B06","B07","B08","B09","B10","B11","B12",
		"C01","C02","C03","C04","C05","C06","C07","C08","C09","C10","C11","C12",
		"D01","D02","D03","D04","D05","D06","D07","D08","D09","D10","D11","D12",
		"E01","E02","E03","E04","E05","E06","E07","E08","E09","E10","E11","E12",
		"F01","F02","F03","F04","F05","F06","F07","F08","F09","F10","F11","F12",
		"G01","G02","G03","G04","G05","G06","G07","G08","G09","G10","G11","G12",
		"H01","H02","H03","H04","H05","H06","H07","H08","H09","H10","H11","H12"
	};

	public final static String[] horizontalPositionPrefixArr = {
		"A",
		"B",
		"C",
		"D",
		"E",
		"F",
		"G",
		"H"
	};

	public final static String verticalPositionArr[] = {
		"A01","B01","C01","D01","E01","F01","G01","H01",
		"A02","B02","C02","D02","E02","F02","G02","H02",
		"A03","B03","C03","D03","E03","F03","G03","H03",
		"A04","B04","C04","D04","E04","F04","G04","H04",
		"A05","B05","C05","D05","E05","F05","G05","H05",
		"A06","B06","C06","D06","E06","F06","G06","H06",
		"A07","B07","C07","D07","E07","F07","G07","H07",
		"A08","B08","C08","D08","E08","F08","G08","H08",
		"A09","B09","C09","D09","E09","F09","G09","H09",
		"A10","B10","C10","D10","E10","F10","G10","H10",
		"A11","B11","C11","D11","E11","F11","G11","H11",
		"A12","B12","C12","D12","E12","F12","G12","H12" };

}
