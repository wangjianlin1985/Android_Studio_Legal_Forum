<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.PostInfo" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    PostInfo postInfo = (PostInfo)request.getAttribute("postInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改帖子</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var title = document.getElementById("postInfo.title").value;
    if(title=="") {
        alert('请输入帖子标题!');
        return false;
    }
    var content = document.getElementById("postInfo.content").value;
    if(content=="") {
        alert('请输入帖子内容!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="PostInfo/PostInfo_ModifyPostInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>帖子id:</td>
    <td width=70%><input id="postInfo.postInfoId" name="postInfo.postInfoId" type="text" value="<%=postInfo.getPostInfoId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>帖子标题:</td>
    <td width=70%><input id="postInfo.title" name="postInfo.title" type="text" size="80" value='<%=postInfo.getTitle() %>'/></td>
  </tr>

  <tr>
    <td width=30%>帖子内容:</td>
    <td width=70%><textarea id="postInfo.content" name="postInfo.content" rows=5 cols=50><%=postInfo.getContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>浏览量:</td>
    <td width=70%><input id="postInfo.hitNum" name="postInfo.hitNum" type="text" size="8" value='<%=postInfo.getHitNum() %>'/></td>
  </tr>

  <tr>
    <td width=30%>发帖人:</td>
    <td width=70%>
      <select name="postInfo.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(postInfo.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>发帖时间:</td>
    <td width=70%><input id="postInfo.addTime" name="postInfo.addTime" type="text" size="30" value='<%=postInfo.getAddTime() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
