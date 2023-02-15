<%@page import="dao.BbsDao"%>
<%@page import="dto.BbsDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     넘어온 파라미터의 인코딩 설정을 UTF-8로 설정
 <%
 request.setCharacterEncoding("utf-8");
 int seq= Integer.parseInt(request.getParameter("seq"));
 String title=request.getParameter("title");
 String content=request.getParameter("content");
 System.out.println(title+" "+content+" "+seq);
 boolean isS = BbsDao.getInstance().updateBbs(seq,title,content);
 
 if(isS){
 	%>
 	<script type="text/javascript">
		alert("수정을 완료했습니다.");
		location.href="bbslist.jsp";
		</script>
 	<% 
 }else{
 %>
 <script type="text/javascript">
		alert("수정을 실패했습니다.");
		
		let seq="<%=seq%>";
		location.href="update.jsp?seq="+seq;
		</script>
 <% 
 }
 %>
