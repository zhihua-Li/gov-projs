/**
 * AcceptionRecordGenerator.java
 *
 * 2013-6-17
 */
package com.lims.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.lims.domain.vo.ConsignmentView;
import com.lims.util.DateUtil;
import com.lims.util.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author lizhihua
 *
 */
public class AcceptionRecordGenerator {
	private Configuration cfg = null;


	/**
	 * 构造函数 初始化模板配置
	 * @throws Exception
	 *         初始化异常
	 */
	public AcceptionRecordGenerator(String path) throws Exception {
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
    private Map<String, Object> LoadData(ConsignmentView consignment)throws Exception{
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
    	public boolean builderAcceptionDoc(ConsignmentView consignment,String templateFileName,String targetFileName,String encode){
    		try {
                this.renderTemplate(templateFileName, targetFileName, LoadData(consignment), encode);
                return true;
            }
    		catch (Exception e) {
                e.printStackTrace();
    			return false;
            }
    	}

}
