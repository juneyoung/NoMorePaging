<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TEST BBS</title>
</head>
${result }
<body>
<h3>${result.model.title }</h3>
<h6>${result.model.writer }<small>${result.model.regdate }</small></h6>
${result.model.desc }

<a href="list?page=${result.pageInfos.page }">list</a>
</body>
</html>
<%--
 --%>