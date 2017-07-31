package com.zw.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.zw.entity.UserInfo;
import com.zw.service.UserInfoService;
import com.zw.utils.AjaxUtil;

//@Action(value = "userAction",results = {@Result(name="success",location="/WEB-INF/content/userinfo.jsp")}) 
@Namespace("/user")
public class UserinfoAction extends ActionSupport implements ModelDriven<UserInfo>, Preparable {
    
	private static final long serialVersionUID = -2301203156032690317L;

	private static final Logger LOGGER = Logger.getLogger(UserinfoAction.class);
	private Integer id;
	private UserInfo userInfo;
	private List<UserInfo> userInfos;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<UserInfo> getUserInfos() {
		return userInfos;
	}

	public void setUserInfos(List<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserInfo getModel() {
		// TODO Auto-generated method stub
		if (null != id) {
			userInfo = userInfoService.get(id);
		} else {
			userInfo = new UserInfo();
		}
		return userInfo;
	}
	
	@Override
	public String execute(){
		System.out.println("gooo!!");
		LOGGER.info("查询所有用户");
		System.out.println("gooo1!!");
		try{
			userInfos = userInfoService.findAll();
			System.out.println("gooo2!!");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		
		return SUCCESS;
		
	}
	
	public void detail() {
		String id = ServletActionContext.getRequest().getParameter("id");
		LOGGER.info("查看用户详情：" + id);
		userInfo = userInfoService.get(Integer.valueOf(id));
		AjaxUtil.ajaxJSONResponse(userInfo);

	}
	
	public void save(){
		String name = ServletActionContext.getRequest().getParameter("name");
		String password = ServletActionContext.getRequest().getParameter("password");
		Integer age = Integer.valueOf(ServletActionContext.getRequest().getParameter("age"));
		Integer roleid = Integer.valueOf(ServletActionContext.getRequest().getParameter("roleid"));
		UserInfo userInfo = new UserInfo();
		userInfo.setUser_name(name);
		userInfo.setPassword(password);
		userInfo.setAge(age);
		userInfo.setRoleid(roleid);
		Integer id = userInfoService.save(userInfo);
		LOGGER.info("添加用户：" + id);
		AjaxUtil.ajaxResponse("添加成功!");
	}
	
	public void delete(){
		String id = ServletActionContext.getRequest().getParameter("id");
		LOGGER.info("删除用户：" + id);
		Integer rs = userInfoService.delete(Integer.valueOf(id));
		Map<String,Object> map = new HashMap<String,Object>();
		if(rs.equals(1)){
			map.put("code", 1);
			map.put("message", "删除成功！");
		}
		else{
			map.put("code", 0);
			map.put("message", "删除失败！");
		}
		AjaxUtil.ajaxJSONResponse(map);
	}

}
