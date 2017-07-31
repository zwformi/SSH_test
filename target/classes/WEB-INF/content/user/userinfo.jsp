<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
<title>userInfo</title>
</head>
<body>
	全部用户信息：
	<c:forEach items="${ userInfos}" var="user">
	    <div>  姓名：${user.user_name }  密码：${user.password }   年龄：${user.age }    角色： ${user.roleid }  <a target="_blank" href="user/userinfo!detail.action?id=${user.id}">json详情</a>&nbsp;&nbsp;<a aid=${user.id} href="javascript:void(0);" onclick="info.delete(this)">删除</a></div>
	</c:forEach>
</body>
<script type="text/javascript">
window.onload=function(){


}
var info={};
info.delete=function(sl){
    var id = $(sl).attr("aid");//user/userinfo!delete.action?id=${user.id}
    $.ajax({  
         type : "post",  
          url : "user/userinfo!delete.action?id="+id,  
          data : {},  
          async : false,  
          success : function(data){  
            data = JSON.parse(data);  
            alert(data.message);
            window.location.reload();  
          }  
     }); 
}
</script>
</html>