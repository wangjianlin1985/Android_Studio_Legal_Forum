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

    /*界面层需要查询的属性: 被回帖子*/
    private PostInfo postInfoObj;
    public void setPostInfoObj(PostInfo postInfoObj) {
        this.postInfoObj = postInfoObj;
    }
    public PostInfo getPostInfoObj() {
        return this.postInfoObj;
    }

    /*界面层需要查询的属性: 回复人*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 回复时间*/
    private String replyTime;
    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }
    public String getReplyTime() {
        return this.replyTime;
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

    private int replyId;
    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }
    public int getReplyId() {
        return replyId;
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
    @Resource PostInfoDAO postInfoDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource ReplyDAO replyDAO;

    /*待操作的Reply对象*/
    private Reply reply;
    public void setReply(Reply reply) {
        this.reply = reply;
    }
    public Reply getReply() {
        return this.reply;
    }

    /*跳转到添加Reply视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的PostInfo信息*/
        List<PostInfo> postInfoList = postInfoDAO.QueryAllPostInfoInfo();
        ctx.put("postInfoList", postInfoList);
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加Reply信息*/
    @SuppressWarnings("deprecation")
    public String AddReply() {
        ActionContext ctx = ActionContext.getContext();
        try {
            PostInfo postInfoObj = postInfoDAO.GetPostInfoByPostInfoId(reply.getPostInfoObj().getPostInfoId());
            reply.setPostInfoObj(postInfoObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(reply.getUserObj().getUser_name());
            reply.setUserObj(userObj);
            replyDAO.AddReply(reply);
            ctx.put("message",  java.net.URLEncoder.encode("Reply添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Reply添加失败!"));
            return "error";
        }
    }

    /*查询Reply信息*/
    public String QueryReply() {
        if(currentPage == 0) currentPage = 1;
        if(replyTime == null) replyTime = "";
        List<Reply> replyList = replyDAO.QueryReplyInfo(postInfoObj, userObj, replyTime, currentPage);
        /*计算总的页数和总的记录数*/
        replyDAO.CalculateTotalPageAndRecordNumber(postInfoObj, userObj, replyTime);
        /*获取到总的页码数目*/
        totalPage = replyDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryReplyOutputToExcel() { 
        if(replyTime == null) replyTime = "";
        List<Reply> replyList = replyDAO.QueryReplyInfo(postInfoObj,userObj,replyTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Reply信息记录"; 
        String[] headers = { "回复id","被回帖子","回复内容","回复人","回复时间"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Reply.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Reply信息*/
    public String FrontQueryReply() {
        if(currentPage == 0) currentPage = 1;
        if(replyTime == null) replyTime = "";
        List<Reply> replyList = replyDAO.QueryReplyInfo(postInfoObj, userObj, replyTime, currentPage);
        /*计算总的页数和总的记录数*/
        replyDAO.CalculateTotalPageAndRecordNumber(postInfoObj, userObj, replyTime);
        /*获取到总的页码数目*/
        totalPage = replyDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Reply信息*/
    public String ModifyReplyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键replyId获取Reply对象*/
        Reply reply = replyDAO.GetReplyByReplyId(replyId);

        List<PostInfo> postInfoList = postInfoDAO.QueryAllPostInfoInfo();
        ctx.put("postInfoList", postInfoList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("reply",  reply);
        return "modify_view";
    }

    /*查询要修改的Reply信息*/
    public String FrontShowReplyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键replyId获取Reply对象*/
        Reply reply = replyDAO.GetReplyByReplyId(replyId);

        List<PostInfo> postInfoList = postInfoDAO.QueryAllPostInfoInfo();
        ctx.put("postInfoList", postInfoList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("reply",  reply);
        return "front_show_view";
    }

    /*更新修改Reply信息*/
    public String ModifyReply() {
        ActionContext ctx = ActionContext.getContext();
        try {
            PostInfo postInfoObj = postInfoDAO.GetPostInfoByPostInfoId(reply.getPostInfoObj().getPostInfoId());
            reply.setPostInfoObj(postInfoObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(reply.getUserObj().getUser_name());
            reply.setUserObj(userObj);
            replyDAO.UpdateReply(reply);
            ctx.put("message",  java.net.URLEncoder.encode("Reply信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Reply信息更新失败!"));
            return "error";
       }
   }

    /*删除Reply信息*/
    public String DeleteReply() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            replyDAO.DeleteReply(replyId);
            ctx.put("message",  java.net.URLEncoder.encode("Reply删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Reply删除失败!"));
            return "error";
        }
    }

}
