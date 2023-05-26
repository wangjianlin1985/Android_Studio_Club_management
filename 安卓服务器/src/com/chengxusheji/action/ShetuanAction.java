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
import com.chengxusheji.dao.ShetuanDAO;
import com.chengxusheji.domain.Shetuan;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ShetuanAction extends BaseAction {

	/*ͼƬ���ļ��ֶ�shetuanPhoto��������*/
	private File shetuanPhotoFile;
	private String shetuanPhotoFileFileName;
	private String shetuanPhotoFileContentType;
	public File getShetuanPhotoFile() {
		return shetuanPhotoFile;
	}
	public void setShetuanPhotoFile(File shetuanPhotoFile) {
		this.shetuanPhotoFile = shetuanPhotoFile;
	}
	public String getShetuanPhotoFileFileName() {
		return shetuanPhotoFileFileName;
	}
	public void setShetuanPhotoFileFileName(String shetuanPhotoFileFileName) {
		this.shetuanPhotoFileFileName = shetuanPhotoFileFileName;
	}
	public String getShetuanPhotoFileContentType() {
		return shetuanPhotoFileContentType;
	}
	public void setShetuanPhotoFileContentType(String shetuanPhotoFileContentType) {
		this.shetuanPhotoFileContentType = shetuanPhotoFileContentType;
	}
    /*�������Ҫ��ѯ������: �������˺�*/
    private String stUserName;
    public void setStUserName(String stUserName) {
        this.stUserName = stUserName;
    }
    public String getStUserName() {
        return this.stUserName;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String shetuanName;
    public void setShetuanName(String shetuanName) {
        this.shetuanName = shetuanName;
    }
    public String getShetuanName() {
        return this.shetuanName;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String bornDate;
    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }
    public String getBornDate() {
        return this.bornDate;
    }

    /*�������Ҫ��ѯ������: ������*/
    private String fuzeren;
    public void setFuzeren(String fuzeren) {
        this.fuzeren = fuzeren;
    }
    public String getFuzeren() {
        return this.fuzeren;
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

    /*��������Shetuan����*/
    private Shetuan shetuan;
    public void setShetuan(Shetuan shetuan) {
        this.shetuan = shetuan;
    }
    public Shetuan getShetuan() {
        return this.shetuan;
    }

    /*��ת�����Shetuan��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Shetuan��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddShetuan() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤�������˺��Ƿ��Ѿ�����*/
        String stUserName = shetuan.getStUserName();
        Shetuan db_shetuan = shetuanDAO.GetShetuanByStUserName(stUserName);
        if(null != db_shetuan) {
            ctx.put("error",  java.net.URLEncoder.encode("�ø������˺��Ѿ�����!"));
            return "error";
        }
        try {
            /*��������logo�ϴ�*/
            String shetuanPhotoPath = "upload/noimage.jpg"; 
       	 	if(shetuanPhotoFile != null)
       	 		shetuanPhotoPath = photoUpload(shetuanPhotoFile,shetuanPhotoFileContentType);
       	 	shetuan.setShetuanPhoto(shetuanPhotoPath);
            shetuanDAO.AddShetuan(shetuan);
            ctx.put("message",  java.net.URLEncoder.encode("Shetuan��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Shetuan���ʧ��!"));
            return "error";
        }
    }

    /*��ѯShetuan��Ϣ*/
    public String QueryShetuan() {
        if(currentPage == 0) currentPage = 1;
        if(stUserName == null) stUserName = "";
        if(shetuanName == null) shetuanName = "";
        if(bornDate == null) bornDate = "";
        if(fuzeren == null) fuzeren = "";
        List<Shetuan> shetuanList = shetuanDAO.QueryShetuanInfo(stUserName, shetuanName, bornDate, fuzeren, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        shetuanDAO.CalculateTotalPageAndRecordNumber(stUserName, shetuanName, bornDate, fuzeren);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = shetuanDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = shetuanDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("shetuanList",  shetuanList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("stUserName", stUserName);
        ctx.put("shetuanName", shetuanName);
        ctx.put("bornDate", bornDate);
        ctx.put("fuzeren", fuzeren);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryShetuanOutputToExcel() { 
        if(stUserName == null) stUserName = "";
        if(shetuanName == null) shetuanName = "";
        if(bornDate == null) bornDate = "";
        if(fuzeren == null) fuzeren = "";
        List<Shetuan> shetuanList = shetuanDAO.QueryShetuanInfo(stUserName,shetuanName,bornDate,fuzeren);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Shetuan��Ϣ��¼"; 
        String[] headers = { "�������˺�","��������","����logo","��������","������","��ϵ�绰"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<shetuanList.size();i++) {
        	Shetuan shetuan = shetuanList.get(i); 
        	dataset.add(new String[]{shetuan.getStUserName(),shetuan.getShetuanName(),shetuan.getShetuanPhoto(),new SimpleDateFormat("yyyy-MM-dd").format(shetuan.getBornDate()),shetuan.getFuzeren(),shetuan.getTelephone()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Shetuan.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯShetuan��Ϣ*/
    public String FrontQueryShetuan() {
        if(currentPage == 0) currentPage = 1;
        if(stUserName == null) stUserName = "";
        if(shetuanName == null) shetuanName = "";
        if(bornDate == null) bornDate = "";
        if(fuzeren == null) fuzeren = "";
        List<Shetuan> shetuanList = shetuanDAO.QueryShetuanInfo(stUserName, shetuanName, bornDate, fuzeren, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        shetuanDAO.CalculateTotalPageAndRecordNumber(stUserName, shetuanName, bornDate, fuzeren);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = shetuanDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = shetuanDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("shetuanList",  shetuanList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("stUserName", stUserName);
        ctx.put("shetuanName", shetuanName);
        ctx.put("bornDate", bornDate);
        ctx.put("fuzeren", fuzeren);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Shetuan��Ϣ*/
    public String ModifyShetuanQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stUserName��ȡShetuan����*/
        Shetuan shetuan = shetuanDAO.GetShetuanByStUserName(stUserName);

        ctx.put("shetuan",  shetuan);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Shetuan��Ϣ*/
    public String FrontShowShetuanQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stUserName��ȡShetuan����*/
        Shetuan shetuan = shetuanDAO.GetShetuanByStUserName(stUserName);

        ctx.put("shetuan",  shetuan);
        return "front_show_view";
    }

    /*�����޸�Shetuan��Ϣ*/
    public String ModifyShetuan() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*��������logo�ϴ�*/
            if(shetuanPhotoFile != null) {
            	String shetuanPhotoPath = photoUpload(shetuanPhotoFile,shetuanPhotoFileContentType);
            	shetuan.setShetuanPhoto(shetuanPhotoPath);
            }
            shetuanDAO.UpdateShetuan(shetuan);
            ctx.put("message",  java.net.URLEncoder.encode("Shetuan��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Shetuan��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Shetuan��Ϣ*/
    public String DeleteShetuan() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            shetuanDAO.DeleteShetuan(stUserName);
            ctx.put("message",  java.net.URLEncoder.encode("Shetuanɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Shetuanɾ��ʧ��!"));
            return "error";
        }
    }

}
