<%@page import="dto.MemberDto"%>
<%@page import="dao.BbsDao"%>
<%@page import="dto.BbsDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	
	MemberDto login= (MemberDto)session.getAttribute("login");
	System.out.print(login);
	if(login == null){
	%>
	<script>
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

dao.countBbs(seq);
%>
<!DOCTYPE html>


<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">

table{
	margin-top:50px;
}

</style>
</head>
<body>
<script type="text/javascript">

</script>
<div align="center">
	<table border="1">
		<tr>
			<th>작성자</th>
			<td><%=dto.getId()%></td>
		</tr>

		<tr>
			<th >제목</th>
			<td><%=dto.getTitle()%></td>
		</tr>

		<tr>
			<th>작성일</th>
			<td><%=dto.getWdate()%></td>
		</tr>

		<tr>
			<th>조회수</th>
			<td><%=dto.getReadcount()%></td>
		</tr>

		<tr>
			<th>답글 정보</th>
			<td><%=dto.getRef() %>-<%=dto.getStep() %>-<%=dto.getDepth() %></td>
		</tr>

		<tr>
			<th>내용</th>
			<td><textarea rows="15" cols="50" ><%=dto.getContent()%></textarea> </td>
		</tr>
		
	</table>
		<button onclick="answerBbs(<%=dto.getSeq()%>)">답글</button>
		<button onclick="location.href='bbslist.jsp'">글 목록</button>
		
		<%
		if(dto.getId().equals(login.getId())){
			
		%>
		
		<button onclick="updateBbs(<%=dto.getSeq()%>)">수정</button>
		
		<button onclick="deleteBbs(<%=dto.getSeq()%>)">삭제</button>
		<%
		}
		%>
</div>
<script type="text/javascript">

function answerBbs(seq) {
	location.href="answer.jsp?seq="+seq;
}
function updateBbs(seq) {
	location.href="update.jsp?seq="+seq;
}
function deleteBbs(seq) {
	location.href="delete.jsp?seq="+seq;
}


</script>
</body>
</html>