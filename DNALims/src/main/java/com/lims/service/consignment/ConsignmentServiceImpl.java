/**
 * ConsignmentServiceImpl.java
 *
 * 2013-6-16
 */
package com.lims.service.consignment;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.Consignment;
import com.lims.domain.query.ConsignmentQuery;
import com.lims.domain.vo.ConsignmentView;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;

/**
 * @author lizhihua
 *
 */
@Service("consignmentService")
@Transactional
public class ConsignmentServiceImpl extends LimsBaseServiceImpl implements
		ConsignmentService {


	/**
	 * 根据id获取委托信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ConsignmentView findConsignmentViewById(String id) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("ConsignmentMapper.findConsignmentViewById", id);
		} catch(Exception e) {
			log.error("invoke ConsignmentService.findConsignmentViewById error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据id获取委托信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public Consignment findConsignmentById(String id) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("ConsignmentMapper.findConsignmentById", id);
		} catch(Exception e) {
			log.error("invoke ConsignmentService.findConsignmentById error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据委托号获取委托信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public Consignment findConsignmentByNo(String consignmentNo) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("ConsignmentMapper.findConsignmentByNo", consignmentNo);
		} catch(Exception e) {
			log.error("invoke ConsignmentService.findConsignmentByNo error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 *  保存委托信息
	 * @param consignment
	 * @return
	 * @throws ServiceException
	 */
	public Consignment addConsignmentInfo(Consignment consignment) throws ServiceException {
		try {
			consignment.setId(keyGeneratorService.getNextKey());
//			if(StringUtil.isNullOrEmpty(consignment.getConsignmentNo())){
//				String organizationCode = ((ConsignOrganization) (dao.getSqlSession().selectOne("ConsignOrganizationMapper.findConsignOrganizationById", consignment.getOrganizationId()))).getOrganizationCode();
//				String newConsignmentNo = getNextConsignmentNo(organizationCode);
//				while(isConsignmentRepeat(newConsignmentNo)){
//					newConsignmentNo = getNextConsignmentNo(organizationCode);
//				}
//
//				consignment.setConsignmentNo(newConsignmentNo);
//			}

			dao.getSqlSession().insert("ConsignmentMapper.addConsignment", consignment);

//			//新增收样的所有样本
//			List<SampleInfo> sampleInfos = new ArrayList<SampleInfo>();
//			SampleInfo si = null;
//			for(String sampleNo : scanedSamples){
//				si = new SampleInfo();
//
//				si.setId(keyGeneratorService.getNextKey());
//				si.setSampleNo(sampleNo);
//				si.setSampleName(sampleNo);
//				si.setCreateDate(consignment.getCreateDate());
//				si.setCreateUser(consignment.getCreateUser());
//
//				sampleInfos.add(si);
//			}
//
//			dao.getSqlSession().insert("SampleInfoMapper.insertBatchSampleInfo", sampleInfos);

			return consignment;
		} catch(Exception e) {
			log.error("invoke ConsignmentService.addConsignmentInfo error!", e);
			throw new ServiceException(e);
		}
	}

	public void updateConsignment(Consignment consignment) throws ServiceException {
		try {
			dao.getSqlSession().update("ConsignmentMapper.updateConsignment", consignment);
		} catch(Exception e) {
			log.error("invoke ConsignmentService.updateConsignment error!", e);
			throw new ServiceException(e);
		}
	}

	/*
	 * 删除委托及委托相关的业务数据
	 */
	public void deleteConsignmentById(String id) throws ServiceException {
		try {

			//删除检验记录
			dao.getSqlSession().delete("PcrRecordMapper.deletePcrRecordByConsignmentId", id);
			dao.getSqlSession().delete("AnalysisRecordMapper.deleteAnalysisRecordByConsignmentId", id);

			//删除基因信息
			dao.getSqlSession().delete("SampleSourceGeneMapper.deleteSampleSourceGeneByConsignmentId", id);
			dao.getSqlSession().delete("GeneInfoMapper.deleteGeneInfoByConsignmentId", id);

			//删除上样板及样本信息
			dao.getSqlSession().delete("BoardInfoMapper.deleteBoardSampleMapByConsignmentId", id);
			dao.getSqlSession().delete("BoardInfoMapper.deleteBoardInfoByConsignmentId", id);
			dao.getSqlSession().delete("SampleInfoMapper.deleteSampleInfoByConsignmentId", id);

			dao.getSqlSession().delete("ConsignmentMapper.deleteConsignmentById", id);
		} catch(Exception e) {
			log.error("invoke ConsignmentService.deleteConsignmentById error!", e);
			throw new ServiceException(e);
		}
	}

//	private synchronized String getNextConsignmentNo(String organizationCode) throws ServiceException {
//		try {
//			String sequenceType = LimsConstants.CONSIGNMENT_NO_PREFIX + organizationCode;
//			SysSequence sysSequence = dao.getSqlSession().selectOne("SysSequenceMapper.findSysSequenceByType", sequenceType);
//
//			String todayMaxSequenceNo = null;
//			if(sysSequence == null){
//				todayMaxSequenceNo = StringUtil.getFullChar("1", '0', LimsConstants.SEQUENCE_NO_LENGTH);
//				String nextSequenceNo = StringUtil.getFullChar("2", '0', LimsConstants.SEQUENCE_NO_LENGTH);
//
//				sysSequence = new SysSequence();
//				sysSequence.setCurrentDate(DateUtil.currentDateStr("yyyyMM"));
//				sysSequence.setSequenceType(sequenceType);
//				sysSequence.setId(keyGeneratorService.getNextKey());
//				sysSequence.setSequenceNo(nextSequenceNo);
//				sysSequence.setUpdateDate(DateUtil.currentDate());
//				dao.getSqlSession().insert("SysSequenceMapper.insertSysSequence", sysSequence);
//			}else{
//				if(DateUtil.currentDateStr("yyyyMM").equals(sysSequence.getCurrentDate())){
//					todayMaxSequenceNo = sysSequence.getSequenceNo();
//
//					int intSequenceNo = Integer.parseInt(todayMaxSequenceNo);
//					intSequenceNo = intSequenceNo + 1;
//					sysSequence.setSequenceNo(StringUtil.getFullChar(String.valueOf(intSequenceNo), '0', LimsConstants.SEQUENCE_NO_LENGTH));
//				}else{
//					todayMaxSequenceNo = StringUtil.getFullChar("1", '0', LimsConstants.SEQUENCE_NO_LENGTH);
//					String nextSequenceNo = StringUtil.getFullChar("2", '0', LimsConstants.SEQUENCE_NO_LENGTH);
//
//					sysSequence.setCurrentDate(DateUtil.currentDateStr("yyyyMM"));
//					sysSequence.setSequenceNo(nextSequenceNo);
//				}
//				sysSequence.setUpdateDate(DateUtil.currentDate());
//				dao.getSqlSession().update("SysSequenceMapper.updateSysSequence", sysSequence);
//			}
//
//			return sequenceType + DateUtil.currentDateStr("yyyyMM") + todayMaxSequenceNo;
//		} catch(Exception e){
//			log.error("invoke ConsignmentService.getNextConsignmentNo error!", e);
//			throw new ServiceException(e);
//		}
//	}

//	private boolean isConsignmentRepeat(String consignmentNo) throws ServiceException {
//		try {
//			ConsignmentQuery consignment = new ConsignmentQuery();
//			consignment.setConsignmentNo(consignmentNo);
//			List<Consignment> list = dao.getSqlSession().selectList("ConsignmentMapper.findConsignmentList", consignment);
//
//			return list != null && list.size() > 0;
//		} catch(Exception e) {
//			log.error("invoke ConsignmentService.isConsignmentRepeat error!", e);
//			throw new ServiceException(e);
//		}
//	}


	/**
	 * 获取委托列表
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public List<ConsignmentView> findConsignmentList(ConsignmentQuery query) throws ServiceException {
		try {
			return dao.getSqlSession().selectList("ConsignmentMapper.findConsignmentList", query);
		} catch(Exception e) {
			log.error("invoke ConsignmentService.findConsignmentList error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取委托列表个数
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public int findConsignmentListCount(ConsignmentQuery query) throws ServiceException {
		try {
			return dao.getSqlSession().selectOne("ConsignmentMapper.findConsignmentListCount", query);
		} catch(Exception e) {
			log.error("invoke ConsignmentService.findConsignmentListCount error!", e);
			throw new ServiceException(e);
		}
	}
}
