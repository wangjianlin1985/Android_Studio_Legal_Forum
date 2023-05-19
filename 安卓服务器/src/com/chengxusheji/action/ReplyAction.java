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
import com.chengxusheji.dao.ReplyDAO;
import com.chengxusheji.domain.Reply;
import com.chengxusheji.dao.PostInfoDAO;
import com.chengxusheji.domain.PostInfo;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ReplyAction extends BaseAction {

    /*�������Ҫ��ѯ������: ��������*/
    private PostInfo postInfoObj;
    public void setPostInfoObj(PostInfo postInfoObj) {
        this.postInfoObj = postInfoObj;
    }
    public PostInfo getPostInfoObj() {
        return this.postInfoObj;
    }

    /*�������Ҫ��ѯ������: �ظ���*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: �ظ�ʱ��*/
    private String replyTime;
    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }
    public String getReplyTime() {
        return this.replyTime;
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

    private int replyId;
    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }
    public int getReplyId() {
        return replyId;
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
    @Resource PostInfoDAO postInfoDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource ReplyDAO replyDAO;

    /*��������Reply����*/
    private Reply reply;
    public void setReply(Reply reply) {
        this.reply = reply;
    }
    public Reply getReply() {
        return this.reply;
    }

    /*��ת�����Reply��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�PostInfo��Ϣ*/
        List<PostInfo> postInfoList = postInfoDAO.QueryAllPostInfoInfo();
        ctx.put("postInfoList", postInfoList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���Reply��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddReply() {
        ActionContext ctx = ActionContext.getContext();
        try {
            PostInfo postInfoObj = postInfoDAO.GetPostInfoByPostInfoId(reply.getPostInfoObj().getPostInfoId());
            reply.setPostInfoObj(postInfoObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(reply.getUserObj().getUser_name());
            reply.setUserObj(userObj);
            replyDAO.AddReply(reply);
            ctx.put("message",  java.net.URLEncoder.encode("Reply��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Reply���ʧ��!"));
            return "error";
        }
    }

    /*��ѯReply��Ϣ*/
    public String QueryReply() {
        if(currentPage == 0) currentPage = 1;
        if(replyTime == null) replyTime = "";
        List<Reply> replyList = replyDAO.QueryReplyInfo(postInfoObj, userObj, replyTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        replyDAO.CalculateTotalPageAndRecordNumber(postInfoObj, userObj, replyTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = replyDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = replyDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("replyList",  replyList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("postInfoObj", postInfoObj);
        List<PostInfo> postInfoList = postInfoDAO.QueryAllPostInfoInfo();
        ctx.put("postInfoList", postInfoList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("replyTime", replyTime);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryReplyOutputToExcel() { 
        if(replyTime == null) replyTime = "";
        List<Reply> replyList = replyDAO.QueryReplyInfo(postInfoObj,userObj,replyTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Reply��Ϣ��¼"; 
        String[] headers = { "�ظ�id","��������","�ظ�����","�ظ���","�ظ�ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<replyList.size();i++) {
        	Reply reply = replyList.get(i); 
        	dataset.add(new String[]{reply.getReplyId() + "",reply.getPostInfoObj().getTitle(),
reply.getContent(),reply.getUserObj().getName(),
reply.getReplyTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Reply.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯReply��Ϣ*/
    public String FrontQueryReply() {
        if(currentPage == 0) currentPage = 1;
        if(replyTime == null) replyTime = "";
        List<Reply> replyList = replyDAO.QueryReplyInfo(postInfoObj, userObj, replyTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        replyDAO.CalculateTotalPageAndRecordNumber(postInfoObj, userObj, replyTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = replyDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = replyDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("replyList",  replyList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("postInfoObj", postInfoObj);
        List<PostInfo> postInfoList = postInfoDAO.QueryAllPostInfoInfo();
        ctx.put("postInfoList", postInfoList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("replyTime", replyTime);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Reply��Ϣ*/
    public String ModifyReplyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������replyId��ȡReply����*/
        Reply reply = replyDAO.GetReplyByReplyId(replyId);

        List<PostInfo> postInfoList = postInfoDAO.QueryAllPostInfoInfo();
        ctx.put("postInfoList", postInfoList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("reply",  reply);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Reply��Ϣ*/
    public String FrontShowReplyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������replyId��ȡReply����*/
        Reply reply = replyDAO.GetReplyByReplyId(replyId);

        List<PostInfo> postInfoList = postInfoDAO.QueryAllPostInfoInfo();
        ctx.put("postInfoList", postInfoList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("reply",  reply);
        return "front_show_view";
    }

    /*�����޸�Reply��Ϣ*/
    public String ModifyReply() {
        ActionContext ctx = ActionContext.getContext();
        try {
            PostInfo postInfoObj = postInfoDAO.GetPostInfoByPostInfoId(reply.getPostInfoObj().getPostInfoId());
            reply.setPostInfoObj(postInfoObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(reply.getUserObj().getUser_name());
            reply.setUserObj(userObj);
            replyDAO.UpdateReply(reply);
            ctx.put("message",  java.net.URLEncoder.encode("Reply��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Reply��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Reply��Ϣ*/
    public String DeleteReply() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            replyDAO.DeleteReply(replyId);
            ctx.put("message",  java.net.URLEncoder.encode("Replyɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Replyɾ��ʧ��!"));
            return "error";
        }
    }

}
