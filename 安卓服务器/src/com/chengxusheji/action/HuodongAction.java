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
import com.chengxusheji.dao.HuodongDAO;
import com.chengxusheji.domain.Huodong;
import com.chengxusheji.dao.ShetuanDAO;
import com.chengxusheji.domain.Shetuan;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class HuodongAction extends BaseAction {

    /*界面层需要查询的属性: 活动名称*/
    private String huodongName;
    public void setHuodongName(String huodongName) {
        this.huodongName = huodongName;
    }
    public String getHuodongName() {
        return this.huodongName;
    }

    /*界面层需要查询的属性: 活动时间*/
    private String huodongTime;
    public void setHuodongTime(String huodongTime) {
        this.huodongTime = huodongTime;
    }
    public String getHuodongTime() {
        return this.huodongTime;
    }

    /*界面层需要查询的属性: 活动社团*/
    private Shetuan shetuanObj;
    public void setShetuanObj(Shetuan shetuanObj) {
        this.shetuanObj = shetuanObj;
    }
    public Shetuan getShetuanObj() {
        return this.shetuanObj;
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

    private int huodongId;
    public void setHuodongId(int huodongId) {
        this.huodongId = huodongId;
    }
    public int getHuodongId() {
        return huodongId;
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
    @Resource HuodongDAO huodongDAO;

    /*待操作的Huodong对象*/
    private Huodong huodong;
    public void setHuodong(Huodong huodong) {
        this.huodong = huodong;
    }
    public Huodong getHuodong() {
        return this.huodong;
    }

    /*跳转到添加Huodong视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Shetuan信息*/
        List<Shetuan> shetuanList = shetuanDAO.QueryAllShetuanInfo();
        ctx.put("shetuanList", shetuanList);
        return "add_view";
    }

    /*添加Huodong信息*/
    @SuppressWarnings("deprecation")
    public String AddHuodong() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Shetuan shetuanObj = shetuanDAO.GetShetuanByStUserName(huodong.getShetuanObj().getStUserName());
            huodong.setShetuanObj(shetuanObj);
            huodongDAO.AddHuodong(huodong);
            ctx.put("message",  java.net.URLEncoder.encode("Huodong添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Huodong添加失败!"));
            return "error";
        }
    }

    /*查询Huodong信息*/
    public String QueryHuodong() {
        if(currentPage == 0) currentPage = 1;
        if(huodongName == null) huodongName = "";
        if(huodongTime == null) huodongTime = "";
        List<Huodong> huodongList = huodongDAO.QueryHuodongInfo(huodongName, huodongTime, shetuanObj, currentPage);
        /*计算总的页数和总的记录数*/
        huodongDAO.CalculateTotalPageAndRecordNumber(huodongName, huodongTime, shetuanObj);
        /*获取到总的页码数目*/
        totalPage = huodongDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = huodongDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("huodongList",  huodongList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("huodongName", huodongName);
        ctx.put("huodongTime", huodongTime);
        ctx.put("shetuanObj", shetuanObj);
        List<Shetuan> shetuanList = shetuanDAO.QueryAllShetuanInfo();
        ctx.put("shetuanList", shetuanList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryHuodongOutputToExcel() { 
        if(huodongName == null) huodongName = "";
        if(huodongTime == null) huodongTime = "";
        List<Huodong> huodongList = huodongDAO.QueryHuodongInfo(huodongName,huodongTime,shetuanObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Huodong信息记录"; 
        String[] headers = { "活动名称","活动时间","活动社团"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<huodongList.size();i++) {
        	Huodong huodong = huodongList.get(i); 
        	dataset.add(new String[]{huodong.getHuodongName(),new SimpleDateFormat("yyyy-MM-dd").format(huodong.getHuodongTime()),huodong.getShetuanObj().getShetuanName()
});
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
			response.setHeader("Content-disposition","attachment; filename="+"Huodong.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Huodong信息*/
    public String FrontQueryHuodong() {
        if(currentPage == 0) currentPage = 1;
        if(huodongName == null) huodongName = "";
        if(huodongTime == null) huodongTime = "";
        List<Huodong> huodongList = huodongDAO.QueryHuodongInfo(huodongName, huodongTime, shetuanObj, currentPage);
        /*计算总的页数和总的记录数*/
        huodongDAO.CalculateTotalPageAndRecordNumber(huodongName, huodongTime, shetuanObj);
        /*获取到总的页码数目*/
        totalPage = huodongDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = huodongDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("huodongList",  huodongList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("huodongName", huodongName);
        ctx.put("huodongTime", huodongTime);
        ctx.put("shetuanObj", shetuanObj);
        List<Shetuan> shetuanList = shetuanDAO.QueryAllShetuanInfo();
        ctx.put("shetuanList", shetuanList);
        return "front_query_view";
    }

    /*查询要修改的Huodong信息*/
    public String ModifyHuodongQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键huodongId获取Huodong对象*/
        Huodong huodong = huodongDAO.GetHuodongByHuodongId(huodongId);

        List<Shetuan> shetuanList = shetuanDAO.QueryAllShetuanInfo();
        ctx.put("shetuanList", shetuanList);
        ctx.put("huodong",  huodong);
        return "modify_view";
    }

    /*查询要修改的Huodong信息*/
    public String FrontShowHuodongQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键huodongId获取Huodong对象*/
        Huodong huodong = huodongDAO.GetHuodongByHuodongId(huodongId);

        List<Shetuan> shetuanList = shetuanDAO.QueryAllShetuanInfo();
        ctx.put("shetuanList", shetuanList);
        ctx.put("huodong",  huodong);
        return "front_show_view";
    }

    /*更新修改Huodong信息*/
    public String ModifyHuodong() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Shetuan shetuanObj = shetuanDAO.GetShetuanByStUserName(huodong.getShetuanObj().getStUserName());
            huodong.setShetuanObj(shetuanObj);
            huodongDAO.UpdateHuodong(huodong);
            ctx.put("message",  java.net.URLEncoder.encode("Huodong信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Huodong信息更新失败!"));
            return "error";
       }
   }

    /*删除Huodong信息*/
    public String DeleteHuodong() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            huodongDAO.DeleteHuodong(huodongId);
            ctx.put("message",  java.net.URLEncoder.encode("Huodong删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Huodong删除失败!"));
            return "error";
        }
    }

}
