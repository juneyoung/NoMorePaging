<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="d" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/resources/js/bbs.js"></script>
<title>TEST BBS</title>
</head>
${result }
<body>
	<d:set var="pattern" value="yyyy-MM-dd"/>
	<table align="center" style="border-style: solid;">
		<tr>
			<td>no</td>
			<td>title</td>
			<td>writer</td>
			<td>register</td>
			<td>reads</td>
		</tr>
		<d:set var="itemLen" value="${fn:length(result.list) }"/>
		<d:forEach var="item" items="${result.list}" varStatus="status">
			<tr>
				<td>${item.id}</td>
				<td><a href="view?id=${item.id}">${item.title }</a></td>
				<td>${item.writer }</td>
				<td><fmt:formatDate pattern="${pattern }" value="${item.regdate }"/></td>
				<td>${item.reads }</td>
			</tr>
		</d:forEach>
	</table>
		${result.pageInfos.currentPage}
	<div id="paging" align="center">
		<d:if test="${result.pageInfos.ablePrvUnit }">
			<a href="list?page=${result.pageInfos.prvUnitPage }">◀◀︎</a>
		</d:if>
	
		<d:if test="${result.pageInfos.ablePrv }">
			<a href="list?page=${result.pageInfos.currentPage - 1 }">◀︎</a>
		</d:if>
		
		<d:forEach var="page" items="${result.pageInfos.pageEntry}">
			<a href="list?page=${page}">${page}</a>
		</d:forEach>
		
		<d:if test="${result.pageInfos.ableNxt }">
			<a href="list?page=${result.pageInfos.currentPage + 1 }">▷</a>
		</d:if>
		
		<d:if test="${result.pageInfos.ableNxtUnit }">
			<a href="list?page=${result.pageInfos.nxtUnitPage }">▷▷</a>
		</d:if>
		
	</div>
</body>
</html>