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

	/*图片或文件字段zsPhoto参数接收*/
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
	/*图片或文件字段zsFile参数接收*/
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
    /*界面层需要查询的属性: 知识标题*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*界面层需要查询的属性: 作者*/
    private String author;
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() {
        return this.author;
    }

    /*界面层需要查询的属性: 出版社*/
    private String publish;
    public void setPublish(String publish) {
        this.publish = publish;
    }
    public String getPublish() {
        return this.publish;
    }

    /*界面层需要查询的属性: 出版日期*/
    private String publishDate;
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getPublishDate() {
        return this.publishDate;
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

    private int zsId;
    public void setZsId(int zsId) {
        this.zsId = zsId;
    }
    public int getZsId() {
        return zsId;
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
    @Resource FlzsDAO flzsDAO;

    /*待操作的Flzs对象*/
    private Flzs flzs;
    public void setFlzs(Flzs flzs) {
        this.flzs = flzs;
    }
    public Flzs getFlzs() {
        return this.flzs;
    }

    /*跳转到添加Flzs视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加Flzs信息*/
    @SuppressWarnings("deprecation")
    public String AddFlzs() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*处理知识图片上传*/
            String zsPhotoPath = "upload/noimage.jpg"; 
       	 	if(zsPhotoFile != null)
       	 		zsPhotoPath = photoUpload(zsPhotoFile,zsPhotoFileContentType);
       	 	flzs.setZsPhoto(zsPhotoPath);
            /*处理知识文件上传*/
            String zsFilePath = ""; 
       	 	if(zsFileFile != null)
       	 		zsFilePath = fileUpload(zsFileFile, zsFileFileFileName);
       	 	flzs.setZsFile(zsFilePath);
            flzsDAO.AddFlzs(flzs);
            ctx.put("message",  java.net.URLEncoder.encode("Flzs添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Flzs添加失败!"));
            return "error";
        }
    }

    /*查询Flzs信息*/
    public String QueryFlzs() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(author == null) author = "";
        if(publish == null) publish = "";
        if(publishDate == null) publishDate = "";
        List<Flzs> flzsList = flzsDAO.QueryFlzsInfo(title, author, publish, publishDate, currentPage);
        /*计算总的页数和总的记录数*/
        flzsDAO.CalculateTotalPageAndRecordNumber(title, author, publish, publishDate);
        /*获取到总的页码数目*/
        totalPage = flzsDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryFlzsOutputToExcel() { 
        if(title == null) title = "";
        if(author == null) author = "";
        if(publish == null) publish = "";
        if(publishDate == null) publishDate = "";
        List<Flzs> flzsList = flzsDAO.QueryFlzsInfo(title,author,publish,publishDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Flzs信息记录"; 
        String[] headers = { "知识标题","知识图片","作者","出版社","出版日期","阅读量"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Flzs.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Flzs信息*/
    public String FrontQueryFlzs() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(author == null) author = "";
        if(publish == null) publish = "";
        if(publishDate == null) publishDate = "";
        List<Flzs> flzsList = flzsDAO.QueryFlzsInfo(title, author, publish, publishDate, currentPage);
        /*计算总的页数和总的记录数*/
        flzsDAO.CalculateTotalPageAndRecordNumber(title, author, publish, publishDate);
        /*获取到总的页码数目*/
        totalPage = flzsDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Flzs信息*/
    public String ModifyFlzsQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键zsId获取Flzs对象*/
        Flzs flzs = flzsDAO.GetFlzsByZsId(zsId);

        ctx.put("flzs",  flzs);
        return "modify_view";
    }

    /*查询要修改的Flzs信息*/
    public String FrontShowFlzsQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键zsId获取Flzs对象*/
        Flzs flzs = flzsDAO.GetFlzsByZsId(zsId);

        ctx.put("flzs",  flzs);
        return "front_show_view";
    }

    /*更新修改Flzs信息*/
    public String ModifyFlzs() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*处理知识图片上传*/
            if(zsPhotoFile != null) {
            	String zsPhotoPath = photoUpload(zsPhotoFile,zsPhotoFileContentType);
            	flzs.setZsPhoto(zsPhotoPath);
            }
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*处理知识文件上传*/
            if(zsFileFile != null)
       	 		flzs.setZsFile(fileUpload(zsFileFile, zsFileFileFileName));
            flzsDAO.UpdateFlzs(flzs);
            ctx.put("message",  java.net.URLEncoder.encode("Flzs信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Flzs信息更新失败!"));
            return "error";
       }
   }

    /*删除Flzs信息*/
    public String DeleteFlzs() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            flzsDAO.DeleteFlzs(zsId);
            ctx.put("message",  java.net.URLEncoder.encode("Flzs删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Flzs删除失败!"));
            return "error";
        }
    }

}
