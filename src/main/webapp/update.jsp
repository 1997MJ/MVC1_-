<%@page import="dto.BbsDto"%>
<%@page import="dao.BbsDao"%>
<%@page import="dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%

	MemberDto login= (MemberDto)session.getAttribute("login");
	System.out.print(login);
	if(login == null){
	%>
	<script type="text/javascript">
		alert("로그인 해주십시오");
		location.href="login.jsp";
	</script>
	<%
	} 
	
	%>

<%



int seq = Integer.parseInt(request.getParameter("seq"));
System.out.println(seq);

BbsDao dao= BbsDao.getInstance();
BbsDto dto= dao.getBbs(seq);

%>	

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<div align="center">

<form action="updateAf.jsp" method="post">
<input type="hidden" name="seq" value="<%=seq%>">

<table border="1">
<col width="200"><col width="400">

<tr>
	<th>아이디</th>
	<td>
		<%=dto.getId()%>
	</td>
</tr>
<tr>
	<th>제목</th>
	<td>
		<input type="text" name="title" size="50px" value="<%=dto.getTitle()%>">
	</td>
</tr>
<tr>
	<th>내용</th>
	<td>
		<textarea rows="20" cols="50px" name="content"><%=dto.getContent()%></textarea>
	</td>
</tr>
<tr>
	<td colspan="2">
		<button type="submit">수정하기</button>
	</td>
</tr>

</table>

</form>

</div>
</body>
</html>