<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>������̳app-��ҳ</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">��ҳ</a></li>
			<li><a href="<%=basePath %>UserInfo/UserInfo_FrontQueryUserInfo.action" target="OfficeMain">�û�</a></li> 
			<li><a href="<%=basePath %>News/News_FrontQueryNews.action" target="OfficeMain">����</a></li> 
			<li><a href="<%=basePath %>PostInfo/PostInfo_FrontQueryPostInfo.action" target="OfficeMain">����</a></li> 
			<li><a href="<%=basePath %>Reply/Reply_FrontQueryReply.action" target="OfficeMain">���ӻظ�</a></li>
			<li><a href="<%=basePath %>Flzs/Flzs_FrontQueryFlzs.action" target="OfficeMain">����֪ʶ</a></li>
			<li><a href="<%=basePath %>Article/Article_FrontQueryArticle.action" target="OfficeMain">����</a></li> 
			<li><a href="<%=basePath %>ArticleClass/ArticleClass_FrontQueryArticleClass.action" target="OfficeMain">���·���</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>��̨��½</font></a></p>
	</div>
</div>
</body>
</html>
