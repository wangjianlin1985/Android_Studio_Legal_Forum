<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>法律论坛app-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<li><a href="<%=basePath %>UserInfo/UserInfo_FrontQueryUserInfo.action" target="OfficeMain">用户</a></li> 
			<li><a href="<%=basePath %>News/News_FrontQueryNews.action" target="OfficeMain">新闻</a></li> 
			<li><a href="<%=basePath %>PostInfo/PostInfo_FrontQueryPostInfo.action" target="OfficeMain">帖子</a></li> 
			<li><a href="<%=basePath %>Reply/Reply_FrontQueryReply.action" target="OfficeMain">帖子回复</a></li>
			<li><a href="<%=basePath %>Flzs/Flzs_FrontQueryFlzs.action" target="OfficeMain">法律知识</a></li>
			<li><a href="<%=basePath %>Article/Article_FrontQueryArticle.action" target="OfficeMain">文章</a></li> 
			<li><a href="<%=basePath %>ArticleClass/ArticleClass_FrontQueryArticleClass.action" target="OfficeMain">文章分类</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>
