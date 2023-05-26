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
import com.chengxusheji.domain.Huodong;

@Service @Transactional
public class HuodongDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddHuodong(Huodong huodong) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(huodong);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Huodong> QueryHuodongInfo(String huodongName,String huodongTime,Shetuan shetuanObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Huodong huodong where 1=1";
    	if(!huodongName.equals("")) hql = hql + " and huodong.huodongName like '%" + huodongName + "%'";
    	if(!huodongTime.equals("")) hql = hql + " and huodong.huodongTime like '%" + huodongTime + "%'";
    	if(null != shetuanObj && !shetuanObj.getStUserName().equals("")) hql += " and huodong.shetuanObj.stUserName='" + shetuanObj.getStUserName() + "'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List huodongList = q.list();
    	return (ArrayList<Huodong>) huodongList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Huodong> QueryHuodongInfo(String huodongName,String huodongTime,Shetuan shetuanObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Huodong huodong where 1=1";
    	if(!huodongName.equals("")) hql = hql + " and huodong.huodongName like '%" + huodongName + "%'";
    	if(!huodongTime.equals("")) hql = hql + " and huodong.huodongTime like '%" + huodongTime + "%'";
    	if(null != shetuanObj && !shetuanObj.getStUserName().equals("")) hql += " and huodong.shetuanObj.stUserName='" + shetuanObj.getStUserName() + "'";
    	Query q = s.createQuery(hql);
    	List huodongList = q.list();
    	return (ArrayList<Huodong>) huodongList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Huodong> QueryAllHuodongInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Huodong";
        Query q = s.createQuery(hql);
        List huodongList = q.list();
        return (ArrayList<Huodong>) huodongList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String huodongName,String huodongTime,Shetuan shetuanObj) {
        Session s = factory.getCurrentSession();
        String hql = "From Huodong huodong where 1=1";
        if(!huodongName.equals("")) hql = hql + " and huodong.huodongName like '%" + huodongName + "%'";
        if(!huodongTime.equals("")) hql = hql + " and huodong.huodongTime like '%" + huodongTime + "%'";
        if(null != shetuanObj && !shetuanObj.getStUserName().equals("")) hql += " and huodong.shetuanObj.stUserName='" + shetuanObj.getStUserName() + "'";
        Query q = s.createQuery(hql);
        List huodongList = q.list();
        recordNumber = huodongList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Huodong GetHuodongByHuodongId(int huodongId) {
        Session s = factory.getCurrentSession();
        Huodong huodong = (Huodong)s.get(Huodong.class, huodongId);
        return huodong;
    }

    /*����Huodong��Ϣ*/
    public void UpdateHuodong(Huodong huodong) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(huodong);
    }

    /*ɾ��Huodong��Ϣ*/
    public void DeleteHuodong (int huodongId) throws Exception {
        Session s = factory.getCurrentSession();
        Object huodong = s.load(Huodong.class, huodongId);
        s.delete(huodong);
    }

}
