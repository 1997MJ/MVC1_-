<%@page import="dao.BbsDao"%>
<%@page import="dto.BbsDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
 int seq= Integer.parseInt(request.getParameter("seq"));
 
 boolean isS = BbsDao.getInstance().deleteBbs(seq);
 
 if(isS){
 	%>
 	<script type="text/javascript">
		alert("삭제를 완료했습니다.");
		location.href="bbslist.jsp";
		</script>
 	<% 
 }else{
 %>
 <script type="text/javascript">
		alert("삭제를 실패했습니다.");
		let seq="<%=seq%>";
		location.href="bbsdetail.jsp?seq="+seq;
		</script>
 <% 
 }
 %>
