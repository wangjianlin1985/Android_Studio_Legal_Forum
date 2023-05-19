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
import com.chengxusheji.dao.PostInfoDAO;
import com.chengxusheji.domain.PostInfo;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class PostInfoAction extends BaseAction {

    /*�������Ҫ��ѯ������: ���ӱ���*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
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

    private int postInfoId;
    public void setPostInfoId(int postInfoId) {
        this.postInfoId = postInfoId;
    }
    public int getPostInfoId() {
        return postInfoId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource PostInfoDAO postInfoDAO;

    /*��������PostInfo����*/
    private PostInfo postInfo;
    public void setPostInfo(PostInfo postInfo) {
        this.postInfo = postInfo;
    }
    public PostInfo getPostInfo() {
        return this.postInfo;
    }

    /*��ת�����PostInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���PostInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddPostInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(postInfo.getUserObj().getUser_name());
            postInfo.setUserObj(userObj);
            postInfoDAO.AddPostInfo(postInfo);
            ctx.put("message",  java.net.URLEncoder.encode("PostInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PostInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯPostInfo��Ϣ*/
    public String QueryPostInfo() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(addTime == null) addTime = "";
        List<PostInfo> postInfoList = postInfoDAO.QueryPostInfoInfo(title, userObj, addTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        postInfoDAO.CalculateTotalPageAndRecordNumber(title, userObj, addTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = postInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = postInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("postInfoList",  postInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("addTime", addTime);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryPostInfoOutputToExcel() { 
        if(title == null) title = "";
        if(addTime == null) addTime = "";
        List<PostInfo> postInfoList = postInfoDAO.QueryPostInfoInfo(title,userObj,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "PostInfo��Ϣ��¼"; 
        String[] headers = { "����id","���ӱ���","�����","������","����ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<postInfoList.size();i++) {
        	PostInfo postInfo = postInfoList.get(i); 
        	dataset.add(new String[]{postInfo.getPostInfoId() + "",postInfo.getTitle(),postInfo.getHitNum() + "",postInfo.getUserObj().getName(),
postInfo.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"PostInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯPostInfo��Ϣ*/
    public String FrontQueryPostInfo() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(addTime == null) addTime = "";
        List<PostInfo> postInfoList = postInfoDAO.QueryPostInfoInfo(title, userObj, addTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        postInfoDAO.CalculateTotalPageAndRecordNumber(title, userObj, addTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = postInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = postInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("postInfoList",  postInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("addTime", addTime);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�PostInfo��Ϣ*/
    public String ModifyPostInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������postInfoId��ȡPostInfo����*/
        PostInfo postInfo = postInfoDAO.GetPostInfoByPostInfoId(postInfoId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("postInfo",  postInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�PostInfo��Ϣ*/
    public String FrontShowPostInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������postInfoId��ȡPostInfo����*/
        PostInfo postInfo = postInfoDAO.GetPostInfoByPostInfoId(postInfoId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("postInfo",  postInfo);
        return "front_show_view";
    }

    /*�����޸�PostInfo��Ϣ*/
    public String ModifyPostInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(postInfo.getUserObj().getUser_name());
            postInfo.setUserObj(userObj);
            postInfoDAO.UpdatePostInfo(postInfo);
            ctx.put("message",  java.net.URLEncoder.encode("PostInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PostInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��PostInfo��Ϣ*/
    public String DeletePostInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            postInfoDAO.DeletePostInfo(postInfoId);
            ctx.put("message",  java.net.URLEncoder.encode("PostInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("PostInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
