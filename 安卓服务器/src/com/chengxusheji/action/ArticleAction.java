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
import com.chengxusheji.dao.ArticleDAO;
import com.chengxusheji.domain.Article;
import com.chengxusheji.dao.ArticleClassDAO;
import com.chengxusheji.domain.ArticleClass;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ArticleAction extends BaseAction {

    /*�������Ҫ��ѯ������: ����*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*�������Ҫ��ѯ������: ���·���*/
    private ArticleClass articleClassObj;
    public void setArticleClassObj(ArticleClass articleClassObj) {
        this.articleClassObj = articleClassObj;
    }
    public ArticleClass getArticleClassObj() {
        return this.articleClassObj;
    }

    /*�������Ҫ��ѯ������: �����û�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String addTime;
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAddTime() {
        return this.addTime;
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

    private int articleId;
    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
    public int getArticleId() {
        return articleId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource ArticleDAO articleDAO;

    /*��������Article����*/
    private Article article;
    public void setArticle(Article article) {
        this.article = article;
    }
    public Article getArticle() {
        return this.article;
    }

    /*��ת�����Article��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�ArticleClass��Ϣ*/
        List<ArticleClass> articleClassList = articleClassDAO.QueryAllArticleClassInfo();
        ctx.put("articleClassList", articleClassList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���Article��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddArticle() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ArticleClass articleClassObj = articleClassDAO.GetArticleClassByArticleClassId(article.getArticleClassObj().getArticleClassId());
            article.setArticleClassObj(articleClassObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(article.getUserObj().getUser_name());
            article.setUserObj(userObj);
            articleDAO.AddArticle(article);
            ctx.put("message",  java.net.URLEncoder.encode("Article��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Article���ʧ��!"));
            return "error";
        }
    }

    /*��ѯArticle��Ϣ*/
    public String QueryArticle() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(addTime == null) addTime = "";
        List<Article> articleList = articleDAO.QueryArticleInfo(title, articleClassObj, userObj, addTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        articleDAO.CalculateTotalPageAndRecordNumber(title, articleClassObj, userObj, addTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = articleDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = articleDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("articleList",  articleList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("articleClassObj", articleClassObj);
        List<ArticleClass> articleClassList = articleClassDAO.QueryAllArticleClassInfo();
        ctx.put("articleClassList", articleClassList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("addTime", addTime);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryArticleOutputToExcel() { 
        if(title == null) title = "";
        if(addTime == null) addTime = "";
        List<Article> articleList = articleDAO.QueryArticleInfo(title,articleClassObj,userObj,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Article��Ϣ��¼"; 
        String[] headers = { "����id","����","���·���","�����","�����û�","����ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<articleList.size();i++) {
        	Article article = articleList.get(i); 
        	dataset.add(new String[]{article.getArticleId() + "",article.getTitle(),article.getArticleClassObj().getArticleClassName(),
article.getHitNum() + "",article.getUserObj().getName(),
article.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Article.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯArticle��Ϣ*/
    public String FrontQueryArticle() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(addTime == null) addTime = "";
        List<Article> articleList = articleDAO.QueryArticleInfo(title, articleClassObj, userObj, addTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        articleDAO.CalculateTotalPageAndRecordNumber(title, articleClassObj, userObj, addTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = articleDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = articleDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("articleList",  articleList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("articleClassObj", articleClassObj);
        List<ArticleClass> articleClassList = articleClassDAO.QueryAllArticleClassInfo();
        ctx.put("articleClassList", articleClassList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("addTime", addTime);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Article��Ϣ*/
    public String ModifyArticleQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������articleId��ȡArticle����*/
        Article article = articleDAO.GetArticleByArticleId(articleId);

        List<ArticleClass> articleClassList = articleClassDAO.QueryAllArticleClassInfo();
        ctx.put("articleClassList", articleClassList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("article",  article);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Article��Ϣ*/
    public String FrontShowArticleQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������articleId��ȡArticle����*/
        Article article = articleDAO.GetArticleByArticleId(articleId);

        List<ArticleClass> articleClassList = articleClassDAO.QueryAllArticleClassInfo();
        ctx.put("articleClassList", articleClassList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("article",  article);
        return "front_show_view";
    }

    /*�����޸�Article��Ϣ*/
    public String ModifyArticle() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ArticleClass articleClassObj = articleClassDAO.GetArticleClassByArticleClassId(article.getArticleClassObj().getArticleClassId());
            article.setArticleClassObj(articleClassObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(article.getUserObj().getUser_name());
            article.setUserObj(userObj);
            articleDAO.UpdateArticle(article);
            ctx.put("message",  java.net.URLEncoder.encode("Article��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Article��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Article��Ϣ*/
    public String DeleteArticle() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            articleDAO.DeleteArticle(articleId);
            ctx.put("message",  java.net.URLEncoder.encode("Articleɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Articleɾ��ʧ��!"));
            return "error";
        }
    }

}
