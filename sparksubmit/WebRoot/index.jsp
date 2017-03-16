<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="css/type.css" type="text/css"></link>
  </head>
  
  <body>
    <div class="main" >
   <div class="head" style=" text-align:left;">
   <div class="title"><h2>Spark Submit System</h2></div>
   </div >
   <div class="bottom">
   <div class="work">
   <form name="uploadForm" action="Submit" method="post" enctype="multipart/form-data">
     选择文件&nbsp;<input type="file" name="file"><br/><br/>
     统计&nbsp;<select name="type">
     <option>--选择类型--</option>
     <option>告警类型</option>
     <option>告警原因</option>
     </select><br/><br/>
     <input type="submit" value="提交任务"/>&nbsp;&nbsp;<input type="reset" value="重新填写"/>&nbsp;&nbsp;
     <a href="#">任务进度</a>
     </form>
     </div>
   </div>
   </div>   
  </body>
</html>
