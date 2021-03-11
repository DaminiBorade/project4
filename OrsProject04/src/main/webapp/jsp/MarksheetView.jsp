<%@page import="in.co.rays.project4.controller.MarksheetCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<html>
<head>
<title>Marksheet Registration Page</title>
</head>
<body>

	<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.project4.bean.MarksheetBean"
			scope="request"></jsp:useBean>

		<%
			List l = (List) request.getAttribute("Studentlist");
		%>

		<center>
			<h1>
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				<tr>
					<th><font>Update Marksheet</font></th>
				</tr>
				<%
					} else {
				%>
				<tr>
					<th><font>Add Marksheet</font></th>
				</tr>
				<%
					}
				%>
			</h1>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>


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
					<th align="left">Roll No <span style="color: red">*</span>:
					</th>
					<td><input type="text" name="rollNo"
						placeholder="Enter Roll No"
						value="<%=DataUtility.getStringData(bean.getRoll_No())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>> </td>
						 <td style="position : fixed;"><font
						color="red"> <%=ServletUtility.getErrorMessage("rollNo", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Name <span style="color: red">*</span>:
					</th>
					<td><%=HTMLUtility.getList("StudentId", String.valueOf(bean.getStudent_Id()), l)%></td>
					<td style="position: fixed"> 
                    <font color="red" > <%=ServletUtility.getErrorMessage("StudentId", request)%></font></td>
         
				</tr>
				<tr>
					<th align="left">Physics <span style="color: red">*</span>:
					</th>
					<td><input type="text" name="physics"
						placeholder="Enter Physics No"
						value="<%=DataUtility.getStringData(bean.getPhysics())%>"></td>
						 <td style="position : fixed;"><font
						color="red"> <%=ServletUtility.getErrorMessage("physics", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Chemistry <span style="color: red">*</span>:
					</th>
					<td><input type="text" name="chemistry"
						placeholder="Enter Chemistry No"
						value="<%=DataUtility.getStringData(bean.getChemistry())%>"></td>
						 <td style="position : fixed;"><font
						color="red"> <%=ServletUtility.getErrorMessage("chemistry", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Maths <span style="color: red">*</span>:
					</th>
					<td><input type="text" name="maths"
						placeholder="Enter Maths No"
						value="<%=DataUtility.getStringData(bean.getMaths())%>"></td>
						 <td style="position : fixed;"><font
						color="red"> <%=ServletUtility.getErrorMessage("maths", request)%></font></td>

				</tr>
				<tr><th style="padding: 3px"></th></tr>
              
                <tr>
                    <th></th>
                        <%
							if(bean.getId()>0 && bean !=null){
						%>  
 	 <td> 
           &nbsp;
          <input type="submit" name ="operation"  value="<%=MarksheetCtl.OP_UPDATE%>">
         &nbsp;
          <input type="submit" name ="operation"  value="<%=MarksheetCtl.OP_CANCEL%>"></td>
 

					<%}else{ %>
                    
   <td colspan="2">
   
        &nbsp;  &emsp;
   <input type="submit" name="operation"   value="<%=MarksheetCtl.OP_SAVE%>"> 
   &nbsp;&nbsp;
   <input type="submit" name="operation"  value="<%=MarksheetCtl.OP_RESET%>"></td>
       
                        <%} %>
                        </tr>
                        </table>
        </form>
   </center>
	<%@ include file="Footer.jsp"%>
</body>
</html>
