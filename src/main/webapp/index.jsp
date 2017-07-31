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
<title>Index</title>
</head>
<body>
<h2>Hello World!</h2>	
	添加用户：
	<form method="post" action="user/userinfo!save.action">
	     姓名：<input type="text" name="name" id="name"/>
	     <br>密码：<input type="text" name="password" id="password"/>
	     <br>年龄：<input type="text" name="age" id="age"/>
	     <br>角色：<input type="text" name="roleid" id="roleid"/>
	     <br><input type="submit" value="提交"/>
	</form>
</body>
</html>
