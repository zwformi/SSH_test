package com.zw.service;

import java.util.List;

import com.zw.entity.UserInfo;

public interface UserInfoService {

	UserInfo load(Integer id);

	UserInfo get(Integer id);

	List<UserInfo> findAll();

	void persist(UserInfo entity);

	Integer save(UserInfo entity);

	void saveOrUpdate(UserInfo entity);

	Integer delete(Integer id);

	void flush();
}
