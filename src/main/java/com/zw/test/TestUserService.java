package com.zw.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zw.entity.UserInfo;
import com.zw.service.UserInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-hibernate.xml" })
public class TestUserService {

	private static final Logger LOGGER = Logger
			.getLogger(TestUserService.class);

	@Autowired
	private UserInfoService userInfoService;

	@Test
	public void save() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUser_name("zw3");
		userInfo.setPassword("123456");
		userInfo.setAge(24);
		userInfo.setRoleid(3);
		Integer id = userInfoService.save(userInfo);
		LOGGER.info(JSON.toJSONString(id));
	}

}
