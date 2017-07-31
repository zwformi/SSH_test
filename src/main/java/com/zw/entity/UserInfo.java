package com.zw.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_t", catalog = "test")
public class UserInfo implements Serializable{

	private static final long serialVersionUID = 5127853981202454040L;
	private Integer id;
	private String user_name;
	private String password;
	private Integer age;
	private Integer roleid;
	
	public UserInfo() {
	}

	public UserInfo(Integer id, String user_name) {
		this.id = id;
		this.user_name = user_name;
	}

	public UserInfo(Integer id, String user_name,String password, Integer age, Integer roleid) {
		this.id = id;
		this.user_name = user_name;
		this.password = password;
		this.age = age;
		this.roleid = roleid;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "user_name", nullable = false)
	public String getUser_name() {
		return this.user_name;
	}

	public void setUser_name(String name) {
		this.user_name = name;
	}

	@Column(name = "password")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "age")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Column(name = "roleid")
	public Integer getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	
}
