<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project4.controller.SubjectCtl"%>
<%@page import="in.co.rays.project4.bean.CourseBean"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="in.co.rays.project4.util.DataUtility"%>



<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Subject</title>
</head>
<body>
	<jsp:useBean id="bean" class="in.co.rays.project4.bean.SubjectBean"
		scope="request"></jsp:useBean>
	<form action="<%=ORSView.SUBJECT_CTL%>" method="post">


		<%@include file="Header.jsp"%>


		<%
			ArrayList<CourseBean> l = (ArrayList<CourseBean>) request.getAttribute("Courselist");
		%>
		<center>
			<h1>
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				<tr>
					<th>Update Subject</th>
				</tr>
				<%
					} else {
				%>
				<tr>
					<th>Add Subject</th>
				</tr>
				<%
					}
				%>
			</h1>
			<div>
				<h3>
					<font style="color: green"><%=ServletUtility.getSuccessMessage(request)%>
					</font>
					<font style="color: red"><%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>
			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdby" value="<%=bean.getCreated_By()%>">
			<input type="hidden" name="modifiedby"
				value="<%=bean.getModified_By()%>"> <input type="hidden"sss
				name="createddatetime" value="<%=bean.getCreated_Date_Time()%>">
			<input type="hidden" name="modifieddatetime"
				value="<%=bean.getModified_Date_Time()%>">

			<table>

				<tr>
					<th align="left">Course Name <font color="red">*</font>:</th>
					<td><%=HTMLUtility.getList("coursename", String.valueOf(bean.getCourse_Id()), l)%>
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("coursename", request)%>
					</font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<th align="left">Subject Name<font color="red">*</font> :</th>
				<td><input type="text" name="name"
					placeholder="Enter Subject Name" size="20"
					value="<%=DataUtility.getStringData(bean.getSubject_Name())%>">
				</td>
				<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%>
				</font></td>
				</th>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<th align="left">Description<font color="red">*</font> :</th>
				<td><input type="text" name="description"
					placeholder="Enter Description" size="20"
					value="<%=DataUtility.getStringData(bean.getDescription())%>">
				</td>
				<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("description", request)%>
				</font></td>
				</th>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td>&nbsp; &emsp; <input type="submit" name="operation"
						value="<%=SubjectCtl.OP_UPDATE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=SubjectCtl.OP_CANCEL%>">
					</td>
					<%
						} else {
					%>

					<td>&nbsp; &emsp; <input type="submit" name="operation"
						value="<%=SubjectCtl.OP_SAVE%>" > &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=SubjectCtl.OP_RESET%>">
					</td>
					<%
						}
					%>

				</tr>
			</table>
	</form>
	</center>

	<%@include file="Footer.jsp"%>
</body>
</html>