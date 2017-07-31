package com.zw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zw.dao.UserInfoDao;
import com.zw.entity.UserInfo;
import com.zw.service.UserInfoService;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService{

	@Autowired
	private UserInfoDao userInfoDao;

	
	@Override
	public UserInfo load(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserInfo get(Integer id) {
		// TODO Auto-generated method stub
		return userInfoDao.get(id);
	}

	@Override
	public List<UserInfo> findAll() {
		// TODO Auto-generated method stub
		return userInfoDao.findAll();
	}

	@Override
	public void persist(UserInfo entity) {
		// TODO Auto-generated method stub
		userInfoDao.persist(entity);
	}

	@Override
	public Integer save(UserInfo entity) {
		// TODO Auto-generated method stub
		return userInfoDao.save(entity);
	}

	@Override
	public void saveOrUpdate(UserInfo entity) {
		// TODO Auto-generated method stub
		userInfoDao.saveOrUpdate(entity);
	}

	@Override
	public Integer delete(Integer id) {
		// TODO Auto-generated method stub
		return userInfoDao.delete(id);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		userInfoDao.flush();
	}

}
