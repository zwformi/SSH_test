package com.zw.dao.impl;

import java.util.List;

import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zw.dao.UserInfoDao;
import com.zw.entity.UserInfo;

@Repository("userInfoDao")
public class UserInfoDaoImpl implements UserInfoDao{

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	private Session getOpenSession(){
		return this.sessionFactory.openSession();
	}
	@Override
	public UserInfo load(Integer id) {
		// TODO Auto-generated method stub
		return  (UserInfo) this.getCurrentSession().load(UserInfo.class, id);
	}

	@Override
	public UserInfo get(Integer id) {
		// TODO Auto-generated method stub
		Session session = this.getOpenSession();	
		UserInfo userInfo = null;
		try{
			userInfo = (UserInfo) session.get(UserInfo.class, id);
		}catch(Exception e){
			e.getStackTrace();
		}finally{
			session.close(); 
		}	
		return userInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> findAll() {
		// TODO Auto-generated method stub
		Session session = this.getOpenSession();	
		List<UserInfo> userInfos = null;
		org.hibernate.Transaction ts = null;
		try{
			ts = session.beginTransaction();
			userInfos = session.createQuery("from UserInfo").list();
		}catch(Exception e){
			e.getStackTrace();
		}finally{
			session.close(); 
		}		
		return userInfos;
	}

	@Override
	public void persist(UserInfo entity) {
		// TODO Auto-generated method stub
		this.getCurrentSession().persist(entity);
	}

	@Override
	public Integer save(UserInfo entity){
		// TODO Auto-generated method stub
		Integer rs = 0;
		Session session = this.getCurrentSession();	
		org.hibernate.Transaction ts = null;
		try{
			ts = session.beginTransaction();
		    rs = (Integer) session.save(entity);
		    ts.commit();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			try {
				ts.rollback();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs;
	}

	@Override
	public void saveOrUpdate(UserInfo entity) {
		// TODO Auto-generated method stub
		Session session = this.getCurrentSession();	
		org.hibernate.Transaction ts = null;
		try{
			ts = session.beginTransaction();
		    session.saveOrUpdate(entity);
		    ts.commit();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			try {
				ts.rollback();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Integer delete(Integer id) {
		// TODO Auto-generated method stub
		Session session = this.getCurrentSession();	
		org.hibernate.Transaction ts = null;
		Integer rs= 0;
		try{
			ts = session.beginTransaction();
			UserInfo entity = this.load(id);
			session.delete(entity);
		    ts.commit();
		    rs = 1;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			try {
				ts.rollback();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		this.getCurrentSession().flush();

	}

	
}
