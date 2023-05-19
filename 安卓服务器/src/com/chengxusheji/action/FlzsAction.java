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
import com.chengxusheji.dao.FlzsDAO;
import com.chengxusheji.domain.Flzs;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class FlzsAction extends BaseAction {

	/*ͼƬ���ļ��ֶ�zsPhoto��������*/
	private File zsPhotoFile;
	private String zsPhotoFileFileName;
	private String zsPhotoFileContentType;
	public File getZsPhotoFile() {
		return zsPhotoFile;
	}
	public void setZsPhotoFile(File zsPhotoFile) {
		this.zsPhotoFile = zsPhotoFile;
	}
	public String getZsPhotoFileFileName() {
		return zsPhotoFileFileName;
	}
	public void setZsPhotoFileFileName(String zsPhotoFileFileName) {
		this.zsPhotoFileFileName = zsPhotoFileFileName;
	}
	public String getZsPhotoFileContentType() {
		return zsPhotoFileContentType;
	}
	public void setZsPhotoFileContentType(String zsPhotoFileContentType) {
		this.zsPhotoFileContentType = zsPhotoFileContentType;
	}
	/*ͼƬ���ļ��ֶ�zsFile��������*/
	private File zsFileFile;
	private String zsFileFileFileName;
	private String zsFileFileContentType;
	public File getZsFileFile() {
		return zsFileFile;
	}
	public void setZsFileFile(File zsFileFile) {
		this.zsFileFile = zsFileFile;
	}
	public String getZsFileFileFileName() {
		return zsFileFileFileName;
	}
	public void setZsFileFileFileName(String zsFileFileFileName) {
		this.zsFileFileFileName = zsFileFileFileName;
	}
	public String getZsFileFileContentType() {
		return zsFileFileContentType;
	}
	public void setZsFileFileContentType(String zsFileFileContentType) {
		this.zsFileFileContentType = zsFileFileContentType;
	}
    /*�������Ҫ��ѯ������: ֪ʶ����*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String author;
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() {
        return this.author;
    }

    /*�������Ҫ��ѯ������: ������*/
    private String publish;
    public void setPublish(String publish) {
        this.publish = publish;
    }
    public String getPublish() {
        return this.publish;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String publishDate;
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getPublishDate() {
        return this.publishDate;
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

    private int zsId;
    public void setZsId(int zsId) {
        this.zsId = zsId;
    }
    public int getZsId() {
        return zsId;
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
    @Resource FlzsDAO flzsDAO;

    /*��������Flzs����*/
    private Flzs flzs;
    public void setFlzs(Flzs flzs) {
        this.flzs = flzs;
    }
    public Flzs getFlzs() {
        return this.flzs;
    }

    /*��ת�����Flzs��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Flzs��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddFlzs() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*����֪ʶͼƬ�ϴ�*/
            String zsPhotoPath = "upload/noimage.jpg"; 
       	 	if(zsPhotoFile != null)
       	 		zsPhotoPath = photoUpload(zsPhotoFile,zsPhotoFileContentType);
       	 	flzs.setZsPhoto(zsPhotoPath);
            /*����֪ʶ�ļ��ϴ�*/
            String zsFilePath = ""; 
       	 	if(zsFileFile != null)
       	 		zsFilePath = fileUpload(zsFileFile, zsFileFileFileName);
       	 	flzs.setZsFile(zsFilePath);
            flzsDAO.AddFlzs(flzs);
            ctx.put("message",  java.net.URLEncoder.encode("Flzs��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Flzs���ʧ��!"));
            return "error";
        }
    }

    /*��ѯFlzs��Ϣ*/
    public String QueryFlzs() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(author == null) author = "";
        if(publish == null) publish = "";
        if(publishDate == null) publishDate = "";
        List<Flzs> flzsList = flzsDAO.QueryFlzsInfo(title, author, publish, publishDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        flzsDAO.CalculateTotalPageAndRecordNumber(title, author, publish, publishDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = flzsDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = flzsDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("flzsList",  flzsList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("author", author);
        ctx.put("publish", publish);
        ctx.put("publishDate", publishDate);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryFlzsOutputToExcel() { 
        if(title == null) title = "";
        if(author == null) author = "";
        if(publish == null) publish = "";
        if(publishDate == null) publishDate = "";
        List<Flzs> flzsList = flzsDAO.QueryFlzsInfo(title,author,publish,publishDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Flzs��Ϣ��¼"; 
        String[] headers = { "֪ʶ����","֪ʶͼƬ","����","������","��������","�Ķ���"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<flzsList.size();i++) {
        	Flzs flzs = flzsList.get(i); 
        	dataset.add(new String[]{flzs.getTitle(),flzs.getZsPhoto(),flzs.getAuthor(),flzs.getPublish(),new SimpleDateFormat("yyyy-MM-dd").format(flzs.getPublishDate()),flzs.getViewNum() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"Flzs.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯFlzs��Ϣ*/
    public String FrontQueryFlzs() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(author == null) author = "";
        if(publish == null) publish = "";
        if(publishDate == null) publishDate = "";
        List<Flzs> flzsList = flzsDAO.QueryFlzsInfo(title, author, publish, publishDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        flzsDAO.CalculateTotalPageAndRecordNumber(title, author, publish, publishDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = flzsDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = flzsDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("flzsList",  flzsList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("author", author);
        ctx.put("publish", publish);
        ctx.put("publishDate", publishDate);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Flzs��Ϣ*/
    public String ModifyFlzsQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������zsId��ȡFlzs����*/
        Flzs flzs = flzsDAO.GetFlzsByZsId(zsId);

        ctx.put("flzs",  flzs);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Flzs��Ϣ*/
    public String FrontShowFlzsQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������zsId��ȡFlzs����*/
        Flzs flzs = flzsDAO.GetFlzsByZsId(zsId);

        ctx.put("flzs",  flzs);
        return "front_show_view";
    }

    /*�����޸�Flzs��Ϣ*/
    public String ModifyFlzs() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*����֪ʶͼƬ�ϴ�*/
            if(zsPhotoFile != null) {
            	String zsPhotoPath = photoUpload(zsPhotoFile,zsPhotoFileContentType);
            	flzs.setZsPhoto(zsPhotoPath);
            }
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*����֪ʶ�ļ��ϴ�*/
            if(zsFileFile != null)
       	 		flzs.setZsFile(fileUpload(zsFileFile, zsFileFileFileName));
            flzsDAO.UpdateFlzs(flzs);
            ctx.put("message",  java.net.URLEncoder.encode("Flzs��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Flzs��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Flzs��Ϣ*/
    public String DeleteFlzs() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            flzsDAO.DeleteFlzs(zsId);
            ctx.put("message",  java.net.URLEncoder.encode("Flzsɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Flzsɾ��ʧ��!"));
            return "error";
        }
    }

}
