<%@page import="in.co.rays.project4.controller.LoginCtl"%>
<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<form action="<%=ORSView.LOGIN_CTL%>" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.project4.bean.UserBean"
			scope="request"></jsp:useBean>
			
			
			<% String URI = (String)request.getAttribute("uri");%>

					
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreated_By()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModified_By()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreated_Date_Time())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModified_Date_Time())%>">


<center>
			<h1>Login</h1>

			<h2>
				<font style="color: red"><%=ServletUtility.getErrorMessage(request)%></font>
			
			
				<font style="color: Green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h2>

			<table>

				<%
					String msg = (String) request.getAttribute("message");

					if (msg != null) {
				%>
				<h2 align="center">
					<font color="red"><%=msg%></font>
				</h2>
				<%
					}
				%>
				<tr>
					<th align ="left">LoginId <span style="color:red">*</span>:</th>
					<td><input type="text" name="login" placeholder="Enter LoginId" size=30
						value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
						
						
						<td style="position : fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<tr>
					<th align ="left">Password <span style="color:red">*</span>:</th>
					<td><input type="password" name="password" placeholder="Enter Password" size=30
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
						
						<td style="position : fixed;"><font color="red"><%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<td align="center" "2"><input type="submit" name="operation"
						value="<%=LoginCtl.OP_SIGN_IN%>"> &nbsp; <input
						type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_UP%>">
						&nbsp;</td>
				</tr>
				<tr>
					<th></th>
					<td align="center"><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget
								my password</b></a>&nbsp;</td>
				</tr>
			</table>
			<input type="hidden" name="uri" value="<%=URI%>"> 
			
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>