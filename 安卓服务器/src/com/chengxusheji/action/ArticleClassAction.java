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
import com.chengxusheji.dao.ArticleClassDAO;
import com.chengxusheji.domain.ArticleClass;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ArticleClassAction extends BaseAction {

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

    private int articleClassId;
    public void setArticleClassId(int articleClassId) {
        this.articleClassId = articleClassId;
    }
    public int getArticleClassId() {
        return articleClassId;
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
    @Resource ArticleClassDAO articleClassDAO;

    /*��������ArticleClass����*/
    private ArticleClass articleClass;
    public void setArticleClass(ArticleClass articleClass) {
        this.articleClass = articleClass;
    }
    public ArticleClass getArticleClass() {
        return this.articleClass;
    }

    /*��ת�����ArticleClass��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���ArticleClass��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddArticleClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            articleClassDAO.AddArticleClass(articleClass);
            ctx.put("message",  java.net.URLEncoder.encode("ArticleClass��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ArticleClass���ʧ��!"));
            return "error";
        }
    }

    /*��ѯArticleClass��Ϣ*/
    public String QueryArticleClass() {
        if(currentPage == 0) currentPage = 1;
        List<ArticleClass> articleClassList = articleClassDAO.QueryArticleClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        articleClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = articleClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = articleClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("articleClassList",  articleClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryArticleClassOutputToExcel() { 
        List<ArticleClass> articleClassList = articleClassDAO.QueryArticleClassInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ArticleClass��Ϣ��¼"; 
        String[] headers = { "���·���id","���·�������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<articleClassList.size();i++) {
        	ArticleClass articleClass = articleClassList.get(i); 
        	dataset.add(new String[]{articleClass.getArticleClassId() + "",articleClass.getArticleClassName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"ArticleClass.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯArticleClass��Ϣ*/
    public String FrontQueryArticleClass() {
        if(currentPage == 0) currentPage = 1;
        List<ArticleClass> articleClassList = articleClassDAO.QueryArticleClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        articleClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = articleClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = articleClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("articleClassList",  articleClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�ArticleClass��Ϣ*/
    public String ModifyArticleClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������articleClassId��ȡArticleClass����*/
        ArticleClass articleClass = articleClassDAO.GetArticleClassByArticleClassId(articleClassId);

        ctx.put("articleClass",  articleClass);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�ArticleClass��Ϣ*/
    public String FrontShowArticleClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������articleClassId��ȡArticleClass����*/
        ArticleClass articleClass = articleClassDAO.GetArticleClassByArticleClassId(articleClassId);

        ctx.put("articleClass",  articleClass);
        return "front_show_view";
    }

    /*�����޸�ArticleClass��Ϣ*/
    public String ModifyArticleClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            articleClassDAO.UpdateArticleClass(articleClass);
            ctx.put("message",  java.net.URLEncoder.encode("ArticleClass��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ArticleClass��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ArticleClass��Ϣ*/
    public String DeleteArticleClass() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            articleClassDAO.DeleteArticleClass(articleClassId);
            ctx.put("message",  java.net.URLEncoder.encode("ArticleClassɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ArticleClassɾ��ʧ��!"));
            return "error";
        }
    }

}
