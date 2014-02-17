/**
 * ExamineRecordGenerator.java
 *
 * 2013-7-12
 */
package com.lims.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.lims.constants.LimsConstants;
import com.lims.domain.bo.ConsignmentBo;
import com.lims.domain.po.BoardSampleMap;
import com.lims.util.DateUtil;
import com.lims.util.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author lizhihua
 *
 */
public class ExamineRecordGenerator {
	private Configuration cfg = null;


	/**
	 * 构造函数 初始化模板配置
	 * @throws Exception
	 *         初始化异常
	 */
	public ExamineRecordGenerator(String path) throws Exception {
		super();

        cfg = new Configuration();
		//setDirectoryForTemplateLoading该方法用来设置读取模板的文件夹路径
		cfg.setDirectoryForTemplateLoading(new File(path));
	}

/**
     * 根据模版文件和context生成输出文件
     * @param templateFileName 模版文件
     * @param outputFileName 输出文件
     * @param context 上下文
     * @param encoding 编码
     * @throws Exception 异常
     */
    public void renderTemplate(String templateFileName, String outputFileName, Map<String, Object> context, String encoding) throws Exception{
        Template temp = cfg.getTemplate(templateFileName, Locale.getDefault(), encoding);
        FileOutputStream fos = new FileOutputStream(outputFileName);
        Writer writer = new OutputStreamWriter(fos, encoding);

        try {
           temp.process(context, writer);
        } catch(Exception e) {
            e.printStackTrace();
        	throw new Exception("生成受理表错误！", e);
        }

        writer.flush();
        writer.close();
    }
    /**
     * 装载需要向xml文件写入的信息
     * @return Map
     * @throws Exception
     */
    private Map<String, Object> LoadData(ConsignmentBo consignment)throws Exception{
    	Map<String, Object> context = new HashMap<String, Object>();
    	context.put("organizationName", StringUtil.isNullOrEmpty(consignment.getOrganizationName()) ? "" : consignment.getOrganizationName());
    	context.put("organizationSubName", StringUtil.isNullOrEmpty(consignment.getOrganizationSubName()) ? "" : consignment.getOrganizationSubName());
    	context.put("consignDate", consignment.getConsignDate() == null ? "" : DateUtil.dateToString(consignment.getConsignDate(), "yyyy年MM月dd日"));
    	context.put("consignmentor", StringUtil.isNullOrEmpty(consignment.getConsignmentor()) ? "" : consignment.getConsignmentor());
    	context.put("phonenum", StringUtil.isNullOrEmpty(consignment.getPhonenum()) ? "" : consignment.getPhonenum());
    	context.put("consignMode", consignment.getConsignMethod());
    	context.put("consignBloodCount", StringUtil.isNullOrEmpty(consignment.getConsignBloodCount()) ? "0" : consignment.getConsignBloodCount());
    	context.put("consignSalivaCount", StringUtil.isNullOrEmpty(consignment.getConsignSalivaCount()) ? "0" : consignment.getConsignSalivaCount());
    	context.put("checkContentType", consignment.getCheckContentType());
    	context.put("consignmentNo", consignment.getConsignmentNo());
    	context.put("dataUploadDate", consignment.getDataUploadDate() == null ? "" : DateUtil.dateToString(consignment.getDataUploadDate(), "yyyy年MM月dd日"));
    	context.put("sampleRebackDate", consignment.getSampleRebackDate() == null ? "" : DateUtil.dateToString(consignment.getSampleRebackDate(), "yyyy年MM月dd日"));
    	context.put("agreementAmount", StringUtil.isNullOrEmpty(consignment.getAgreementAmount()) ? "" : consignment.getAgreementAmount());
    	context.put("expenseBalanceDate", consignment.getExpenseBalanceDate() == null ? "" : DateUtil.dateToString(consignment.getExpenseBalanceDate(), "yyyy年MM月dd日"));
    	context.put("remark", StringUtil.isNullOrEmpty(consignment.getRemark()) ? "" : consignment.getRemark());

    	context.put("sampleCarrier", StringUtil.isNullOrEmpty(consignment.getSampleCarrier()) ? "" : consignment.getSampleCarrier());
    	context.put("sampleCarrierOther", StringUtil.isNullOrEmpty(consignment.getSampleCarrierOther()) ? "" : consignment.getSampleCarrierOther());
    	context.put("sampleCarrierList", consignment.getSampleCarrierList());
    	context.put("pcrSystem", StringUtil.isNullOrEmpty(consignment.getPcrSystem()) ? "" : consignment.getPcrSystem());
    	context.put("pcrSystemList", consignment.getPcrSystemList());
    	context.put("loopCount", StringUtil.isNullOrEmpty(consignment.getLoopCount()) ? "" : consignment.getLoopCount());
    	context.put("loopCountList", consignment.getLoopCountList());
    	context.put("holeDiameter", StringUtil.isNullOrEmpty(consignment.getHoleDiameter()) ? "" : consignment.getHoleDiameter());
    	context.put("holeDiameterOther", StringUtil.isNullOrEmpty(consignment.getHoleDiameterOther()) ? "" : consignment.getHoleDiameterOther());
    	context.put("holeDiameterList", consignment.getHoleDiameterList());

    	return context;
    }
    	/**
    	 * builderIdentificationPaper
    	 * 根据鉴定信息生成鉴定书
    	 * @param identificationRecord  鉴定信息
    	 * @param templateFileName   模板文件的名称
    	 * @param targetFileName  鉴定生成后存放的路径
    	 * @param encode         编码格式
    	 * @return             生成鉴定书成功标志。（true表示成功，false表示不成功）
    	 */
    	public boolean builderExamineInfoDoc(ConsignmentBo consignment,String templateFileName,String targetFileName,String encode){
    		try {
                this.renderTemplate(templateFileName, targetFileName, LoadData(consignment), encode);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
    			return false;
            }
    	}

    	/**
    	 *
    	 * @param encode         编码格式
    	 * @return             生成成功标志。（true表示成功，false表示不成功）
    	 */
    	public boolean builderPcrExamineInfoDoc(ConsignmentBo consignment,String templateFileName,String targetFileName,String encode){
    		try {
                this.renderTemplate(templateFileName, targetFileName, LoadPcrExamineData(consignment), encode);
                return true;
            }
    		catch (Exception e) {
                e.printStackTrace();
    			return false;
            }
    	}


    	/**
         * 装载需要向xml文件写入的信息
         * @return Map
         * @throws Exception
         */
        private Map<String, Object> LoadPcrExamineData(ConsignmentBo consignment)throws Exception{
        	Map<String, Object> context = new HashMap<String, Object>();
        	context.put("organizationName", StringUtil.isNullOrEmpty(consignment.getOrganizationName()) ? "" : consignment.getOrganizationName());
        	context.put("organizationSubName", StringUtil.isNullOrEmpty(consignment.getOrganizationSubName()) ? "" : consignment.getOrganizationSubName());
        	context.put("consignDate", consignment.getConsignDate() == null ? "" : DateUtil.dateToString(consignment.getConsignDate(), "yyyy年MM月dd日"));
        	context.put("consignmentor", StringUtil.isNullOrEmpty(consignment.getConsignmentor()) ? "" : consignment.getConsignmentor());
        	context.put("phonenum", StringUtil.isNullOrEmpty(consignment.getPhonenum()) ? "" : consignment.getPhonenum());
        	context.put("consignMode", consignment.getConsignMethod() == null ? "" : consignment.getConsignMethod());
        	context.put("consignBloodCount", StringUtil.isNullOrEmpty(consignment.getConsignBloodCount()) ? "0" : consignment.getConsignBloodCount());
        	context.put("consignSalivaCount", StringUtil.isNullOrEmpty(consignment.getConsignSalivaCount()) ? "0" : consignment.getConsignSalivaCount());
        	context.put("checkContentType", consignment.getCheckContentType() == null ? "" : consignment.getCheckContentType());
        	context.put("consignmentNo", consignment.getConsignmentNo() == null ? "" : consignment.getConsignmentNo());
        	context.put("dataUploadDate", consignment.getDataUploadDate() == null ? "" : DateUtil.dateToString(consignment.getDataUploadDate(), "yyyy年MM月dd日"));
        	context.put("sampleRebackDate", consignment.getSampleRebackDate() == null ? "" : DateUtil.dateToString(consignment.getSampleRebackDate(), "yyyy年MM月dd日"));
        	context.put("agreementAmount", StringUtil.isNullOrEmpty(consignment.getAgreementAmount()) ? "" : consignment.getAgreementAmount());
        	context.put("expenseBalanceDate", consignment.getExpenseBalanceDate() == null ? "" : DateUtil.dateToString(consignment.getExpenseBalanceDate(), "yyyy年MM月dd日"));
        	context.put("remark", StringUtil.isNullOrEmpty(consignment.getRemark()) ? "" : consignment.getRemark());
        	context.put("sampleCountOfBoard", consignment.getSampleCountOfBoard());
        	context.put("pcrSystem", StringUtil.isNullOrEmpty(consignment.getPcrSystem()) ? "" : consignment.getPcrSystem());
        	context.put("loopCount", StringUtil.isNullOrEmpty(consignment.getLoopCount()) ? "" : consignment.getLoopCount());
        	context.put("holeDiameter", StringUtil.isNullOrEmpty(consignment.getHoleDiameter()) ? "" : consignment.getHoleDiameter());
//        	context.put("holeDiameterList", consignment.getHoleDiameterList());
        	context.put("boardNo", StringUtil.isNullOrEmpty(consignment.getBoardNo()) ? "" : consignment.getBoardNo());
        	context.put("extractor", StringUtil.isNullOrEmpty(consignment.getExtractor()) ? "" : consignment.getExtractor());
        	context.put("extractorDate", StringUtil.isNullOrEmpty(consignment.getExtractorDate()) ? "" : consignment.getExtractorDate());
        	context.put("pcrUser", StringUtil.isNullOrEmpty(consignment.getPcrUser()) ? "" : consignment.getPcrUser());
        	context.put("pcrDate", StringUtil.isNullOrEmpty(consignment.getPcrDate()) ? "" : consignment.getPcrDate());
        	context.put("pcrInstrument", StringUtil.isNullOrEmpty(consignment.getPcrInstrument()) ? "" : consignment.getPcrInstrument());
        	context.put("examineUser", StringUtil.isNullOrEmpty(consignment.getExamineUser()) ? "" : consignment.getExamineUser());
        	context.put("examineDate", StringUtil.isNullOrEmpty(consignment.getExamineDate()) ? "" : consignment.getExamineDate());
//        	context.put("examineInstrumentList", consignment.getExamineInstrumentList());
        	context.put("examineInstrument", consignment.getExamineInstrument()==null ? "" : consignment.getExamineInstrument());
        	context.put("reviewUser", consignment.getReviewUser() == null ? "" : consignment.getReviewUser());
        	context.put("reviewDate", consignment.getReviewDate() == null ? "" : consignment.getReviewDate());
        	context.put("analysisUser", StringUtil.isNullOrEmpty(consignment.getAnalysisUser()) ? "" : consignment.getAnalysisUser());
        	context.put("analysisDate", StringUtil.isNullOrEmpty(consignment.getAnalysisDate()) ? "" : consignment.getAnalysisDate());
        	context.put("reagentNo", StringUtil.isNullOrEmpty(consignment.getReagentNo()) ? "" : consignment.getReagentNo());
        	context = setSampleNoToContext(context, consignment.getBoardSampleMapList());

        	return context;
        }

        public Map<String, Object> setSampleNoToContext(Map<String, Object> context, List<BoardSampleMap> bsmList){

        	for(String positionStr : LimsConstants.verticalPositionArr){
        		if("A01".equals(positionStr)){
        			String a01Sample = "CK";
        			for(BoardSampleMap bsm : bsmList){
            			if(positionStr.equals(bsm.getSamplePosition())){
            				a01Sample = bsm.getSampleNo();
            				break;
            			}
            		}
        			context.put("A01", a01Sample);
        			continue;
        		}

        		if("B01".equals(positionStr)){
        			context.put("B01", "9947");
        			continue;
        		}

        		if("H03".equals(positionStr)){
        			context.put("H03", "Ladder");
        			continue;
        		}

        		if("H10".equals(positionStr)){
        			context.put("H10", "Ladder");
        			continue;
        		}

        		boolean hasPosition = false;
        		for(BoardSampleMap bsm : bsmList){
        			if(positionStr.equals(bsm.getSamplePosition())){
        				context.put(positionStr, bsm.getSampleNo() == null ? "" : bsm.getSampleNo());
        				hasPosition = true;
        				break;
        			}
        		}

        		if(!hasPosition){
        			context.put(positionStr, "");
        		}
        	}

        	return context;
        }
}
