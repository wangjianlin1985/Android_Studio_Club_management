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

    /*界面层需要查询的属性: 申请的社团*/
    private Shetuan shentuanObj;
    public void setShentuanObj(Shetuan shentuanObj) {
        this.shentuanObj = shentuanObj;
    }
    public Shetuan getShentuanObj() {
        return this.shentuanObj;
    }

    /*界面层需要查询的属性: 姓名*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*界面层需要查询的属性: 学号*/
    private String xuehao;
    public void setXuehao(String xuehao) {
        this.xuehao = xuehao;
    }
    public String getXuehao() {
        return this.xuehao;
    }

    /*界面层需要查询的属性: 申请人*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 申请时间*/
    private String sqTime;
    public void setSqTime(String sqTime) {
        this.sqTime = sqTime;
    }
    public String getSqTime() {
        return this.sqTime;
    }

    /*界面层需要查询的属性: 审核状态*/
    private String shenHeState;
    public void setShenHeState(String shenHeState) {
        this.shenHeState = shenHeState;
    }
    public String getShenHeState() {
        return this.shenHeState;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource ShetuanDAO shetuanDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource ShenqingDAO shenqingDAO;

    /*待操作的Shenqing对象*/
    private Shenqing shenqing;
    public void setShenqing(Shenqing shenqing) {
        this.shenqing = shenqing;
    }
    public Shenqing getShenqing() {
        return this.shenqing;
    }

    /*跳转到添加Shenqing视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Shetuan信息*/
        List<Shetuan> shetuanList = shetuanDAO.QueryAllShetuanInfo();
        ctx.put("shetuanList", shetuanList);
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加Shenqing信息*/
    @SuppressWarnings("deprecation")
    public String AddShenqing() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Shetuan shentuanObj = shetuanDAO.GetShetuanByStUserName(shenqing.getShentuanObj().getStUserName());
            shenqing.setShentuanObj(shentuanObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(shenqing.getUserObj().getUser_name());
            shenqing.setUserObj(userObj);
            shenqingDAO.AddShenqing(shenqing);
            ctx.put("message",  java.net.URLEncoder.encode("Shenqing添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Shenqing添加失败!"));
            return "error";
        }
    }

    /*查询Shenqing信息*/
    public String QueryShenqing() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(xuehao == null) xuehao = "";
        if(sqTime == null) sqTime = "";
        if(shenHeState == null) shenHeState = "";
        List<Shenqing> shenqingList = shenqingDAO.QueryShenqingInfo(shentuanObj, name, xuehao, userObj, sqTime, shenHeState, currentPage);
        /*计算总的页数和总的记录数*/
        shenqingDAO.CalculateTotalPageAndRecordNumber(shentuanObj, name, xuehao, userObj, sqTime, shenHeState);
        /*获取到总的页码数目*/
        totalPage = shenqingDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryShenqingOutputToExcel() { 
        if(name == null) name = "";
        if(xuehao == null) xuehao = "";
        if(sqTime == null) sqTime = "";
        if(shenHeState == null) shenHeState = "";
        List<Shenqing> shenqingList = shenqingDAO.QueryShenqingInfo(shentuanObj,name,xuehao,userObj,sqTime,shenHeState);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Shenqing信息记录"; 
        String[] headers = { "申请的社团","姓名","学号","申请人","申请时间","审核状态"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Shenqing.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询Shenqing信息*/
    public String FrontQueryShenqing() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(xuehao == null) xuehao = "";
        if(sqTime == null) sqTime = "";
        if(shenHeState == null) shenHeState = "";
        List<Shenqing> shenqingList = shenqingDAO.QueryShenqingInfo(shentuanObj, name, xuehao, userObj, sqTime, shenHeState, currentPage);
        /*计算总的页数和总的记录数*/
        shenqingDAO.CalculateTotalPageAndRecordNumber(shentuanObj, name, xuehao, userObj, sqTime, shenHeState);
        /*获取到总的页码数目*/
        totalPage = shenqingDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Shenqing信息*/
    public String ModifyShenqingQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键shenqingId获取Shenqing对象*/
        Shenqing shenqing = shenqingDAO.GetShenqingByShenqingId(shenqingId);

        List<Shetuan> shetuanList = shetuanDAO.QueryAllShetuanInfo();
        ctx.put("shetuanList", shetuanList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("shenqing",  shenqing);
        return "modify_view";
    }

    /*查询要修改的Shenqing信息*/
    public String FrontShowShenqingQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键shenqingId获取Shenqing对象*/
        Shenqing shenqing = shenqingDAO.GetShenqingByShenqingId(shenqingId);

        List<Shetuan> shetuanList = shetuanDAO.QueryAllShetuanInfo();
        ctx.put("shetuanList", shetuanList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("shenqing",  shenqing);
        return "front_show_view";
    }

    /*更新修改Shenqing信息*/
    public String ModifyShenqing() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Shetuan shentuanObj = shetuanDAO.GetShetuanByStUserName(shenqing.getShentuanObj().getStUserName());
            shenqing.setShentuanObj(shentuanObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(shenqing.getUserObj().getUser_name());
            shenqing.setUserObj(userObj);
            shenqingDAO.UpdateShenqing(shenqing);
            ctx.put("message",  java.net.URLEncoder.encode("Shenqing信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Shenqing信息更新失败!"));
            return "error";
       }
   }

    /*删除Shenqing信息*/
    public String DeleteShenqing() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            shenqingDAO.DeleteShenqing(shenqingId);
            ctx.put("message",  java.net.URLEncoder.encode("Shenqing删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Shenqing删除失败!"));
            return "error";
        }
    }

}
