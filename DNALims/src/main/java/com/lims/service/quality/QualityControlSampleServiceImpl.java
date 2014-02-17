/**
 * QualityControlSampleServiceImpl.java
 *
 * 2013-7-12
 */
package com.lims.service.quality;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.constants.LimsConstants;
import com.lims.domain.po.PolluteRecord;
import com.lims.domain.po.QualityControlSample;
import com.lims.domain.po.SysSequence;
import com.lims.domain.query.PolluteRecordQuery;
import com.lims.domain.vo.PolluteRecordView;
import com.lims.domain.vo.QualityControlSampleView;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;
import com.lims.util.DateUtil;
import com.lims.util.StringUtil;

/**
 * @author lizhihua
 *
 */
@Service("qualityControlSampleService")
@Transactional
public class QualityControlSampleServiceImpl extends LimsBaseServiceImpl
	implements QualityControlSampleService  {


	/**
	 * 查询质控人员列表
	 * @param qcs
	 * @return
	 * @throws ServiceException
	 */
	public List<QualityControlSampleView> findQualityControlSampleList(QualityControlSample qcs)
			throws ServiceException {
		try {
			return dao.getSqlSession().selectList("QualityControlSampleMapper.findQualityControlSampleList", qcs);
		} catch(Exception e) {
			log.error("invoke QualityControlSampleService.findQualityControlSampleList error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 查询质控人员个数
	 * @param qcs
	 * @return
	 * @throws ServiceException
	 */
	public int findQualityControlSampleListCount(QualityControlSample qcs)
			throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("QualityControlSampleMapper.findQualityControlSampleListCount", qcs);
		} catch(Exception e) {
			log.error("invoke QualityControlSampleService.findQualityControlSampleListCount error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 根据id获取质控人员信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public QualityControlSampleView findQualityControlSampleById(String id) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("QualityControlSampleMapper.findQualityControlSampleById", id);
		} catch(Exception e) {
			log.error("invoke QualityControlSampleService.findQualityControlSampleById error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 新增质控人员
	 * @param qualityControlSample
	 * @throws ServiceException
	 */
	public void insertQualityControlSample(QualityControlSampleView qualityControlSample) throws ServiceException {
		try {
			qualityControlSample.setId(keyGeneratorService.getNextKey());
			qualityControlSample.setCreateDate(DateUtil.currentDate());

			if(StringUtil.isNullOrEmpty(qualityControlSample.getSampleNo())){
				String newSampleNo = getNextQualitySampleNo();
				while(isSampleNoRepeat(newSampleNo)){
					newSampleNo = getNextQualitySampleNo();
				}

				qualityControlSample.setSampleNo(newSampleNo);
			}

			dao.getSqlSession().insert("QualityControlSampleMapper.insertQualityControlSample", qualityControlSample);
		} catch(Exception e) {
			log.error("invoke QualityControlSampleService.insertQualityControlSample error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 更新质控人员
	 * @param qualityControlSample
	 * @throws ServiceException
	 */
	public void updateQualityControlSample(QualityControlSampleView qualityControlSample) throws ServiceException {
		try {
			qualityControlSample.setUpdateDate(DateUtil.currentDate());

			dao.getSqlSession().update("QualityControlSampleMapper.updateQualityControlSample", qualityControlSample);
		} catch(Exception e) {
			log.error("invoke QualityControlSampleService.updateQualityControlSample error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据id删除质控人员
	 * @param qualityControlSample
	 * @throws ServiceException
	 */
	public void deleteQualityControlSample(String id) throws ServiceException {
		try {
			dao.getSqlSession().delete("QualityControlSampleMapper.deleteQualityControlSample", id);
		} catch(Exception e) {
			log.error("invoke QualityControlSampleService.deleteQualityControlSample error!", e);
			throw new ServiceException(e);
		}
	}

	private synchronized String getNextQualitySampleNo() throws ServiceException {
		try {
			String sequenceType = LimsConstants.QUALITY_SAMPLE_NO_PREFIX + "000";
			SysSequence sysSequence = dao.getSqlSession().selectOne("SysSequenceMapper.findSysSequenceByType", sequenceType);

			String todayMaxSequenceNo = null;
			if(sysSequence == null){
				todayMaxSequenceNo = StringUtil.getFullChar("1", '0', LimsConstants.SEQUENCE_NO_LENGTH);
				String nextSequenceNo = StringUtil.getFullChar("2", '0', LimsConstants.SEQUENCE_NO_LENGTH);

				sysSequence = new SysSequence();
				sysSequence.setCurrentDate(DateUtil.currentDateStr("yyyyMM"));
				sysSequence.setSequenceType(sequenceType);
				sysSequence.setId(keyGeneratorService.getNextKey());
				sysSequence.setSequenceNo(nextSequenceNo);
				sysSequence.setUpdateDate(DateUtil.currentDate());
				dao.getSqlSession().insert("SysSequenceMapper.insertSysSequence", sysSequence);
			}else{
				if(DateUtil.currentDateStr("yyyyMM").equals(sysSequence.getCurrentDate())){
					todayMaxSequenceNo = sysSequence.getSequenceNo();

					int intSequenceNo = Integer.parseInt(todayMaxSequenceNo);
					intSequenceNo = intSequenceNo + 1;
					sysSequence.setSequenceNo(StringUtil.getFullChar(String.valueOf(intSequenceNo), '0', LimsConstants.SEQUENCE_NO_LENGTH));
				}else{
					todayMaxSequenceNo = StringUtil.getFullChar("1", '0', LimsConstants.SEQUENCE_NO_LENGTH);
					String nextSequenceNo = StringUtil.getFullChar("2", '0', LimsConstants.SEQUENCE_NO_LENGTH);

					sysSequence.setCurrentDate(DateUtil.currentDateStr("yyyyMM"));
					sysSequence.setSequenceNo(nextSequenceNo);
				}
				sysSequence.setUpdateDate(DateUtil.currentDate());
				dao.getSqlSession().update("SysSequenceMapper.updateSysSequence", sysSequence);
			}

			return sequenceType + DateUtil.currentDateStr("yyyyMM") + todayMaxSequenceNo;
		} catch(Exception e){
			log.error("invoke QualityControlSampleService.getNextConsignmentNo error!", e);
			throw new ServiceException(e);
		}
	}

	private boolean isSampleNoRepeat(String sampleNo) throws ServiceException {
		try {
			QualityControlSample qualityControlSample = new QualityControlSample();
			qualityControlSample.setSampleNo(sampleNo);
			List<QualityControlSample> qualityControlSampleList = dao.getSqlSession().selectList("QualityControlSampleMapper.findQualityControlSampleList", qualityControlSample);

			return qualityControlSampleList != null && qualityControlSampleList.size() > 0;
		} catch(Exception e) {
			log.error("invoke QualityControlSampleService.isConsignmentRepeat error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 获取污染记录列表
	 * @return
	 * @throws ServiceException
	 */
	public List<PolluteRecordView> findPolluteRecordViewList(PolluteRecordQuery polluteRecordQuery) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("PolluteRecordMapper.findPolluteRecordViewList", polluteRecordQuery);
		} catch(Exception e) {
			log.error("invoke QualityControlSampleService.findPolluteRecordViewList error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取污染记录条数
	 * @return
	 * @throws ServiceException
	 */
	public int findPolluteRecordViewListCount(PolluteRecordQuery polluteRecordQuery) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("PolluteRecordMapper.findPolluteRecordViewListCount", polluteRecordQuery);
		} catch(Exception e) {
			log.error("invoke QualityControlSampleService.findPolluteRecordViewListCount error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 添加污染记录
	 * @param pr
	 * @throws ServiceException
	 */
	public void addPolluteRecord(PolluteRecord pr) throws ServiceException {
		try {
			pr.setId(keyGeneratorService.getNextKey());

			dao.getSqlSession().insert("PolluteRecordMapper.insertPolluteRecord", pr);
		} catch(Exception e) {
			log.error("invoke QualityControlSampleService.addPolluteRecord error!", e);
			throw new ServiceException(e);
		}
	}

}
