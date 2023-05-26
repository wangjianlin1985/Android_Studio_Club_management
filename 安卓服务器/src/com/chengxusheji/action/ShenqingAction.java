package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.ShenqingDAO;
import com.chengxusheji.domain.Shenqing;
import com.chengxusheji.dao.ShetuanDAO;
import com.chengxusheji.domain.Shetuan;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ShenqingAction extends BaseAction {

    /*�������Ҫ��ѯ������: ���������*/
    private Shetuan shentuanObj;
    public void setShentuanObj(Shetuan shentuanObj) {
        this.shentuanObj = shentuanObj;
    }
    public Shetuan getShentuanObj() {
        return this.shentuanObj;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*�������Ҫ��ѯ������: ѧ��*/
    private String xuehao;
    public void setXuehao(String xuehao) {
        this.xuehao = xuehao;
    }
    public String getXuehao() {
        return this.xuehao;
    }

    /*�������Ҫ��ѯ������: ������*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String sqTime;
    public void setSqTime(String sqTime) {
        this.sqTime = sqTime;
    }
    public String getSqTime() {
        return this.sqTime;
    }

    /*�������Ҫ��ѯ������: ���״̬*/
    private String shenHeState;
    public void setShenHeState(String shenHeState) {
        this.shenHeState = shenHeState;
    }
    public String getShenHeState() {
        return this.shenHeState;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int shenqingId;
    public void setShenqingId(int shenqingId) {
        this.shenqingId = shenqingId;
    }
    public int getShenqingId() {
        return shenqingId;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource ShetuanDAO shetuanDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource ShenqingDAO shenqingDAO;

    /*��������Shenqing����*/
    private Shenqing shenqing;
    public void setShenqing(Shenqing shenqing) {
        this.shenqing = shenqing;
    }
    public Shenqing getShenqing() {
        return this.shenqing;
    }

    /*��ת�����Shenqing��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Shetuan��Ϣ*/
        List<Shetuan> shetuanList = shetuanDAO.QueryAllShetuanInfo();
        ctx.put("shetuanList", shetuanList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���Shenqing��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddShenqing() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Shetuan shentuanObj = shetuanDAO.GetShetuanByStUserName(shenqing.getShentuanObj().getStUserName());
            shenqing.setShentuanObj(shentuanObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(shenqing.getUserObj().getUser_name());
            shenqing.setUserObj(userObj);
            shenqingDAO.AddShenqing(shenqing);
            ctx.put("message",  java.net.URLEncoder.encode("Shenqing��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Shenqing���ʧ��!"));
            return "error";
        }
    }

    /*��ѯShenqing��Ϣ*/
    public String QueryShenqing() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(xuehao == null) xuehao = "";
        if(sqTime == null) sqTime = "";
        if(shenHeState == null) shenHeState = "";
        List<Shenqing> shenqingList = shenqingDAO.QueryShenqingInfo(shentuanObj, name, xuehao, userObj, sqTime, shenHeState, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        shenqingDAO.CalculateTotalPageAndRecordNumber(shentuanObj, name, xuehao, userObj, sqTime, shenHeState);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = shenqingDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = shenqingDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("shenqingList",  shenqingList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("shentuanObj", shentuanObj);
        List<Shetuan> shetuanList = shetuanDAO.QueryAllShetuanInfo();
        ctx.put("shetuanList", shetuanList);
        ctx.put("name", name);
        ctx.put("xuehao", xuehao);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("sqTime", sqTime);
        ctx.put("shenHeState", shenHeState);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryShenqingOutputToExcel() { 
        if(name == null) name = "";
        if(xuehao == null) xuehao = "";
        if(sqTime == null) sqTime = "";
        if(shenHeState == null) shenHeState = "";
        List<Shenqing> shenqingList = shenqingDAO.QueryShenqingInfo(shentuanObj,name,xuehao,userObj,sqTime,shenHeState);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Shenqing��Ϣ��¼"; 
        String[] headers = { "���������","����","ѧ��","������","����ʱ��","���״̬"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<shenqingList.size();i++) {
        	Shenqing shenqing = shenqingList.get(i); 
        	dataset.add(new String[]{shenqing.getShentuanObj().getShetuanName(),
shenqing.getName(),shenqing.getXuehao(),shenqing.getUserObj().getName(),
shenqing.getSqTime(),shenqing.getShenHeState()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Shenqing.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯShenqing��Ϣ*/
    public String FrontQueryShenqing() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(xuehao == null) xuehao = "";
        if(sqTime == null) sqTime = "";
        if(shenHeState == null) shenHeState = "";
        List<Shenqing> shenqingList = shenqingDAO.QueryShenqingInfo(shentuanObj, name, xuehao, userObj, sqTime, shenHeState, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        shenqingDAO.CalculateTotalPageAndRecordNumber(shentuanObj, name, xuehao, userObj, sqTime, shenHeState);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = shenqingDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = shenqingDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("shenqingList",  shenqingList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("shentuanObj", shentuanObj);
        List<Shetuan> shetuanList = shetuanDAO.QueryAllShetuanInfo();
        ctx.put("shetuanList", shetuanList);
        ctx.put("name", name);
        ctx.put("xuehao", xuehao);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("sqTime", sqTime);
        ctx.put("shenHeState", shenHeState);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Shenqing��Ϣ*/
    public String ModifyShenqingQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������shenqingId��ȡShenqing����*/
        Shenqing shenqing = shenqingDAO.GetShenqingByShenqingId(shenqingId);

        List<Shetuan> shetuanList = shetuanDAO.QueryAllShetuanInfo();
        ctx.put("shetuanList", shetuanList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("shenqing",  shenqing);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Shenqing��Ϣ*/
    public String FrontShowShenqingQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������shenqingId��ȡShenqing����*/
        Shenqing shenqing = shenqingDAO.GetShenqingByShenqingId(shenqingId);

        List<Shetuan> shetuanList = shetuanDAO.QueryAllShetuanInfo();
        ctx.put("shetuanList", shetuanList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("shenqing",  shenqing);
        return "front_show_view";
    }

    /*�����޸�Shenqing��Ϣ*/
    public String ModifyShenqing() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Shetuan shentuanObj = shetuanDAO.GetShetuanByStUserName(shenqing.getShentuanObj().getStUserName());
            shenqing.setShentuanObj(shentuanObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(shenqing.getUserObj().getUser_name());
            shenqing.setUserObj(userObj);
            shenqingDAO.UpdateShenqing(shenqing);
            ctx.put("message",  java.net.URLEncoder.encode("Shenqing��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Shenqing��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Shenqing��Ϣ*/
    public String DeleteShenqing() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            shenqingDAO.DeleteShenqing(shenqingId);
            ctx.put("message",  java.net.URLEncoder.encode("Shenqingɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Shenqingɾ��ʧ��!"));
            return "error";
        }
    }

}
