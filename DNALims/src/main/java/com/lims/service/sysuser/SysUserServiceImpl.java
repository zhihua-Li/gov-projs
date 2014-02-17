/**
 * SysUserServiceImpl.java
 * 2013-05-23
 */
package com.lims.service.sysuser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lims.domain.po.SysUser;
import com.lims.domain.po.SysUserRole;
import com.lims.exception.ServiceException;
import com.lims.service.LimsBaseServiceImpl;
import com.lims.util.Md5Util;

/**
 * @author lizhihua
 *
 */
@Service("sysUserService")
@Transactional
public class SysUserServiceImpl extends LimsBaseServiceImpl implements SysUserService {


	/**
	 * 根据用户名、密码获取用户信息
	 * @param userName	用户名
	 * @param password	密码
	 * @return
	 * @throws Exception
	 */
	@Override
	public SysUser findSysUserByUserNameAndPassword(String userName,
			String password) throws Exception {
		SysUser sysUser = new SysUser();
		sysUser.setUserName(userName);
		sysUser.setUserPassword(Md5Util.GetMd5(password));

//		for(int i = 0; i < 200; i++){
//			System.out.println(keyGeneratorService.getNextKey() + "  -----------------------------------------------");
//		}

		try {
			return dao.getSqlSession().selectOne("SysUserMapper.findSysUser", sysUser);
		} catch(Exception e){
			log.error("invoke SysUserService.findSysUserByUserNameAndPassword error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取用户列表
	 *
	 * @param sysUser
	 * @return 符合条件的系统用户列表
	 * @throws Exception
	 */
	public List<SysUser> findSysUserList(SysUser sysUser) throws Exception {
		try {
			return dao.getSqlSession().selectList("SysUserMapper.findSysUser", sysUser);
		} catch(Exception e){
			log.error("invoke SysUserService.findSysUserByUserNameAndPassword error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 * 获取用户个数
	 *
	 * @param sysUser
	 * @return 符合条件的系统用户个数
	 * @throws Exception
	 */
	public int findSysUserListCount(SysUser sysUser) throws Exception {
		try {
			return (Integer) (dao.getSqlSession().selectOne("SysUserMapper.findSysUserListCount", sysUser));
		} catch(Exception e){
			log.error("invoke SysUserService.findSysUserListCount error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据用户id获取用户信息
	 * @param id
	 * @return
	 */
	public SysUser findSysUserById(String id) throws Exception {
		try {
			return dao.getSqlSession().selectOne("SysUserMapper.findSysUserById", id);
		} catch(Exception e) {
			log.error("invoke SysUserService.findSysUserById error!", e);
			throw new ServiceException(e);
		}
	}

	/**
	 *
	 * 添加系统用户
	 *
	 * CK
	 * 9947
	 * Ladder
	 * Ladder
	 *
	 * @param
	 * @return
	 * @throws Exception
	 */
	public String addSysUser(SysUser sysUser, List<String> roleIdList) throws Exception {
		try {
			String id = keyGeneratorService.getNextKey();
			sysUser.setId(id);
			sysUser.setUserPassword(Md5Util.GetMd5(sysUser.getUserPassword()));

			dao.getSqlSession().insert("SysUserMapper.addSysUser", sysUser);

			List<SysUserRole> sysUserRoleList = new ArrayList<SysUserRole>(roleIdList.size());
			SysUserRole sur = null;
			for(String roleId : roleIdList){
				sur = new SysUserRole();
				sur.setId(keyGeneratorService.getNextKey());
				sur.setUserId(id);
				sur.setRoleId(roleId);

				sysUserRoleList.add(sur);
			}

			dao.getSqlSession().insert("SysUserMapper.addSysUserRole", sysUserRoleList);

			return id;
		} catch(Exception e) {
			log.error("invoke SysUserService.addSysUser error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 *
	 * 更新系统用户
	 *
	 * @param
	 * @return
	 * @throws Exception
	 */
	public boolean updateSysUser(SysUser sysUser, List<String> roleIdList) throws Exception {
		try {
			dao.getSqlSession().update("SysUserMapper.updateSysUser", sysUser);
			dao.getSqlSession().delete("SysUserMapper.deleteSysUserRoleByUserId", sysUser.getId());

			List<SysUserRole> sysUserRoleList = new ArrayList<SysUserRole>(roleIdList.size());
			SysUserRole sur = null;
			for(String roleId : roleIdList){
				sur = new SysUserRole();
				sur.setId(keyGeneratorService.getNextKey());
				sur.setUserId(sysUser.getId());
				sur.setRoleId(roleId);

				sysUserRoleList.add(sur);
			}

			dao.getSqlSession().insert("SysUserMapper.addSysUserRole", sysUserRoleList);
		} catch(Exception e) {
			log.error("invoke SysUserService.updateSysUser error!", e);
			throw new ServiceException(e);
		}

		return true;
	}


	public void updateUserPassword(SysUser sysUser) throws Exception {
		try {
			dao.getSqlSession().update("SysUserMapper.updateUserPassword", sysUser);
		} catch(Exception e) {
			log.error("invoke SysUserService.updateUserPassword error!", e);
			throw new ServiceException(e);
		}
	}


	/**
	 *
	 * 删除系统用户
	 *
	 * @param
	 * @return
	 * @throws Exception
	 */
	public boolean deleteSysUser(String id) throws Exception {
		try {
			dao.getSqlSession().update("SysUserMapper.deleteSysUserById", id);
		} catch(Exception e) {
			log.error("invoke SysUserService.deleteSysUser error!", e);
			throw new ServiceException(e);
		}

		return true;
	}

}
