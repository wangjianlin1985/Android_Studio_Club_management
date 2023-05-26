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

@Service @Transactional
public class ShetuanDAO {

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
    public void AddShetuan(Shetuan shetuan) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(shetuan);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Shetuan> QueryShetuanInfo(String stUserName,String shetuanName,String bornDate,String fuzeren,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Shetuan shetuan where 1=1";
    	if(!stUserName.equals("")) hql = hql + " and shetuan.stUserName like '%" + stUserName + "%'";
    	if(!shetuanName.equals("")) hql = hql + " and shetuan.shetuanName like '%" + shetuanName + "%'";
    	if(!bornDate.equals("")) hql = hql + " and shetuan.bornDate like '%" + bornDate + "%'";
    	if(!fuzeren.equals("")) hql = hql + " and shetuan.fuzeren like '%" + fuzeren + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List shetuanList = q.list();
    	return (ArrayList<Shetuan>) shetuanList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Shetuan> QueryShetuanInfo(String stUserName,String shetuanName,String bornDate,String fuzeren) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Shetuan shetuan where 1=1";
    	if(!stUserName.equals("")) hql = hql + " and shetuan.stUserName like '%" + stUserName + "%'";
    	if(!shetuanName.equals("")) hql = hql + " and shetuan.shetuanName like '%" + shetuanName + "%'";
    	if(!bornDate.equals("")) hql = hql + " and shetuan.bornDate like '%" + bornDate + "%'";
    	if(!fuzeren.equals("")) hql = hql + " and shetuan.fuzeren like '%" + fuzeren + "%'";
    	Query q = s.createQuery(hql);
    	List shetuanList = q.list();
    	return (ArrayList<Shetuan>) shetuanList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Shetuan> QueryAllShetuanInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Shetuan";
        Query q = s.createQuery(hql);
        List shetuanList = q.list();
        return (ArrayList<Shetuan>) shetuanList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String stUserName,String shetuanName,String bornDate,String fuzeren) {
        Session s = factory.getCurrentSession();
        String hql = "From Shetuan shetuan where 1=1";
        if(!stUserName.equals("")) hql = hql + " and shetuan.stUserName like '%" + stUserName + "%'";
        if(!shetuanName.equals("")) hql = hql + " and shetuan.shetuanName like '%" + shetuanName + "%'";
        if(!bornDate.equals("")) hql = hql + " and shetuan.bornDate like '%" + bornDate + "%'";
        if(!fuzeren.equals("")) hql = hql + " and shetuan.fuzeren like '%" + fuzeren + "%'";
        Query q = s.createQuery(hql);
        List shetuanList = q.list();
        recordNumber = shetuanList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Shetuan GetShetuanByStUserName(String stUserName) {
        Session s = factory.getCurrentSession();
        Shetuan shetuan = (Shetuan)s.get(Shetuan.class, stUserName);
        return shetuan;
    }

    /*����Shetuan��Ϣ*/
    public void UpdateShetuan(Shetuan shetuan) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(shetuan);
    }

    /*ɾ��Shetuan��Ϣ*/
    public void DeleteShetuan (String stUserName) throws Exception {
        Session s = factory.getCurrentSession();
        Object shetuan = s.load(Shetuan.class, stUserName);
        s.delete(shetuan);
    }

}
