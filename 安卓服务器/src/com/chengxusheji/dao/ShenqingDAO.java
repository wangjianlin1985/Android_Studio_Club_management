package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.Shetuan;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.Shenqing;

@Service @Transactional
public class ShenqingDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddShenqing(Shenqing shenqing) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(shenqing);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Shenqing> QueryShenqingInfo(Shetuan shentuanObj,String name,String xuehao,UserInfo userObj,String sqTime,String shenHeState,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Shenqing shenqing where 1=1";
    	if(null != shentuanObj && !shentuanObj.getStUserName().equals("")) hql += " and shenqing.shentuanObj.stUserName='" + shentuanObj.getStUserName() + "'";
    	if(!name.equals("")) hql = hql + " and shenqing.name like '%" + name + "%'";
    	if(!xuehao.equals("")) hql = hql + " and shenqing.xuehao like '%" + xuehao + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and shenqing.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!sqTime.equals("")) hql = hql + " and shenqing.sqTime like '%" + sqTime + "%'";
    	if(!shenHeState.equals("")) hql = hql + " and shenqing.shenHeState like '%" + shenHeState + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List shenqingList = q.list();
    	return (ArrayList<Shenqing>) shenqingList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Shenqing> QueryShenqingInfo(Shetuan shentuanObj,String name,String xuehao,UserInfo userObj,String sqTime,String shenHeState) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Shenqing shenqing where 1=1";
    	if(null != shentuanObj && !shentuanObj.getStUserName().equals("")) hql += " and shenqing.shentuanObj.stUserName='" + shentuanObj.getStUserName() + "'";
    	if(!name.equals("")) hql = hql + " and shenqing.name like '%" + name + "%'";
    	if(!xuehao.equals("")) hql = hql + " and shenqing.xuehao like '%" + xuehao + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and shenqing.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!sqTime.equals("")) hql = hql + " and shenqing.sqTime like '%" + sqTime + "%'";
    	if(!shenHeState.equals("")) hql = hql + " and shenqing.shenHeState like '%" + shenHeState + "%'";
    	Query q = s.createQuery(hql);
    	List shenqingList = q.list();
    	return (ArrayList<Shenqing>) shenqingList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Shenqing> QueryAllShenqingInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Shenqing";
        Query q = s.createQuery(hql);
        List shenqingList = q.list();
        return (ArrayList<Shenqing>) shenqingList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Shetuan shentuanObj,String name,String xuehao,UserInfo userObj,String sqTime,String shenHeState) {
        Session s = factory.getCurrentSession();
        String hql = "From Shenqing shenqing where 1=1";
        if(null != shentuanObj && !shentuanObj.getStUserName().equals("")) hql += " and shenqing.shentuanObj.stUserName='" + shentuanObj.getStUserName() + "'";
        if(!name.equals("")) hql = hql + " and shenqing.name like '%" + name + "%'";
        if(!xuehao.equals("")) hql = hql + " and shenqing.xuehao like '%" + xuehao + "%'";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and shenqing.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!sqTime.equals("")) hql = hql + " and shenqing.sqTime like '%" + sqTime + "%'";
        if(!shenHeState.equals("")) hql = hql + " and shenqing.shenHeState like '%" + shenHeState + "%'";
        Query q = s.createQuery(hql);
        List shenqingList = q.list();
        recordNumber = shenqingList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Shenqing GetShenqingByShenqingId(int shenqingId) {
        Session s = factory.getCurrentSession();
        Shenqing shenqing = (Shenqing)s.get(Shenqing.class, shenqingId);
        return shenqing;
    }

    /*更新Shenqing信息*/
    public void UpdateShenqing(Shenqing shenqing) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(shenqing);
    }

    /*删除Shenqing信息*/
    public void DeleteShenqing (int shenqingId) throws Exception {
        Session s = factory.getCurrentSession();
        Object shenqing = s.load(Shenqing.class, shenqingId);
        s.delete(shenqing);
    }

}
