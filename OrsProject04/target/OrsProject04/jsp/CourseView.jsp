<%@page import="in.co.rays.project4.controller.CourseCtl"%>
<%@page import="in.co.rays.project4.controller.BaseCtl"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="in.co.rays.project4.bean.CourseBean"%>
<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="in.co.rays.project4.util.HTMLUtility" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course Registration Page</title>
</head>
<body>
	<jsp:useBean id="bean" class="in.co.rays.project4.bean.CourseBean" scope="request"></jsp:useBean>
<form action="<%=ORSView.COURSE_CTL %>" method="post">	
		<%@include file ="Header.jsp"%>
	
	<center>	
			<h1> 
			<%
				if(bean != null && bean.getId() > 0 )
				{
			%>
				<tr><th>Update Course</th></tr>
			<%}else{ %>
				<tr><th>Add Course</th></tr>
			<% } %>
			</h1>
	<div align="center">	
		<h3>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>	
		</h2>
		<h2>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>	
		</h2>
	</div>
	
	<input type ="hidden" name="id" value="<%=bean.getId() %>">
	<input type ="hidden" name="createdby" value="<%=bean.getCreated_By()%>">
	<input type ="hidden" name="modifiedby" value="<%=bean.getModified_By()%>">
	<input type ="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreated_Date_Time()) %>">
	<input type ="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModified_Date_Time()) %>">

<table>
	<tr>
	<th align="left">Course Name <span style="color: red">*</span> :</th>
	<td><input type="text" name ="name" placeholder="Enter Course Name" size="20" value="<%=DataUtility.getStringData(bean.getCourse_Name()) %>">
	</td><td style="position: fixed"><font  color="red"><%=ServletUtility.getErrorMessage("name", request) %></font>
	</td>
	</tr>
<tr><th style="padding: 3px"></th></tr>	
	
	<tr>
	<th align="left">Duration <span style="color:red">*</span> :</th>
	<td>
	<%
	LinkedHashMap map = new LinkedHashMap();
	//HashMap map = new HashMap();
		map.put("1 Year", "1 Year");
    	map.put("2 Year", "2 Year");
    	map.put("3 Year", "3 Year");
    	map.put("4 Year", "4 Year");
    	map.put("5 Year", "5 Year");
    	map.put("6 Year", "6 Year");
    	
  	  String htmlList = HTMLUtility.getList("duration", bean.getDuration(), map);
	%> 
	<%=htmlList%>
	
	</td><td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("duration", request) %></font>
	</td>
	</tr>
<tr><th style="padding: 3px"></th></tr>	
	<tr>
	<th align="left">Description <span style="color: red" >*</span> :</th>
	<td><input type="text" name ="discription" placeholder="Enter Discription" size="20" value="<%=DataUtility.getStringData(bean.getDiscription())%>">
	</td><td style="position: fixed"  ><font color="red"><%=ServletUtility.getErrorMessage("discription", request) %></font>
	</td>
	</tr>
<tr><th style="padding: 3px"></th></tr>
	<tr><th></th>
	<%
	if(bean.getId() > 0){
	%>
	<td>
	 &nbsp;  &emsp;
	<input type="submit" name ="operation" value="<%=CourseCtl.OP_UPDATE %>">
	 &nbsp;  &nbsp;
	<input type="submit" name ="operation" value="<%=CourseCtl.OP_CANCEL %>">
	</td>
	<%}else{ %>
	<td>
	 &nbsp;  &emsp;
	<input type="submit" name ="operation" value="<%=CourseCtl.OP_SAVE %>">
		 &nbsp;  &nbsp;
		<input type="submit" name ="operation" value="<%=CourseCtl.OP_RESET %>">
	</td>
	<%} %>
	</tr>
</table>
</form>	
	</center>
	

	<%@include file ="Footer.jsp"%>
</body></html>