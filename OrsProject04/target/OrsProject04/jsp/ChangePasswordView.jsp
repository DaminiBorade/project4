<%@page import="in.co.rays.project4.controller.ChangePasswordCtl"%>

<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Change Password</title>


</head>
<body>
	<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">
		<%@ include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.co.rays.project4.bean.UserBean"
			scope="request"></jsp:useBean>
		<center>

			<div align="center">

				<h1>Change Password</h1>
				<H2>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
				</H2>
			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreated_By()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModified_By()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreated_Date_Time())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModified_Date_Time())%>">

			<table>
				<tr>
					<th align="left">Old Password <span style="color: red;">*</span>
						:
					</th>
					<td><input type="password" name="oldPassword"
						placeholder="Enter Old Password" size="25"
						value=<%=DataUtility.getStringData(request.getParameter("oldPassword"))%>>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("oldPassword", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">New Password <span style="color: red;">*</span>
						:
					</th>
					<td><input type="password" name="newPassword"
						placeholder="Enter New Password" size="25"
						value=<%=DataUtility.getStringData(request.getParameter("newPassword"))%>>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("newPassword", request)%></font></td>
				</tr>


				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<!--                 <tr> -->
				<!--                     <th align="left">Confirm Password <span style="color:red ;">*</span>:</th> -->
				<!--                     <td><input type="password" name="confirmPassword" placeholder="Re-Enter New Password" size="25" -->
				<%--                         value=<%=DataUtility.getStringData(request.getParameter("confirmPassword"))%>> --%>
				<%--                      </td><td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td> --%>
				<!--                 </tr> -->

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th></th>
					<td colspan="2">&nbsp; <input type="submit" name="operation"
						value="<%=ChangePasswordCtl.OP_SAVE%>"> &nbsp; <input
						type="submit" name="operation"
						value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>">
					</td>
				</tr>

			</table>
	</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>
