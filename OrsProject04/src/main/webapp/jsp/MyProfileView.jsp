<%@page import="in.co.rays.project4.controller.MyProfileCtl"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>MyProfile</title>
</head>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : "1980:2002",
		//maxDate:'0',
		// minDate:0
		//yearRange : "-40:-18"
		});
	});
</script>


<body>
<form action="<%=ORSView.MY_PROFILE_CTL%>" method="post">

        <%@ include file="Header.jsp"%>
        <script type="text/javascript" src="../js/calendar.js"></script>
        <jsp:useBean id="bean" class="in.co.rays.project4.bean.UserBean"
            scope="request"></jsp:useBean>

        <center>
            <h1>My Profile</h1>
            
            <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>
            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreated_By()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModified_By()%>">
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreated_Date_Time())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModified_Date_Time())%>">
            

            <table>
                <tr>
                    <th align="left">Login Id<span style="color: red">*</span> :</th>
                    <td><input type="text" name="login" value="<%=DataUtility.getStringData(bean.getLogin())%>" readonly="readonly"><font
                        color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
                </tr>

                <tr>
                    <tr><th align="left">First Name <span style="color: red">*</span> :</th>
                    <td><input type="text" name="firstName" value="<%=DataUtility.getStringData(bean.getFrist_Name())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
                </tr>
                <tr>
                  <tr><th align="left">Last Name<span style="color: red">*</span> :</th>
                    <td><input type="text" name="lastName"
                        value="<%=DataUtility.getStringData(bean.getLast_Name())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
                </tr>
                <tr>
                  <tr><th align="left">Gender <span style="color: red">*</span> :</th>
                    <td>
                        <%
                            HashMap map = new HashMap();
                            map.put("Male", "Male");
                            map.put("Female", "Female");

                            String htmlList = HTMLUtility.getList("gender", String.valueOf(bean.getGender()),
                                    map);
                        %> <%=htmlList%>
                    </td>
                </tr>
                <tr>
                  <tr><th align="left">Mobile No <span style="color: red">*</span> :</th>
                    <td><input type="text" name="mobileNo"
                        value="<%=DataUtility.getStringData(bean.getMobile_No())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
                </tr>
                
                
               <%--  <tr>
					<th align="left">Date Of Birth<font color="red">*</font></th>
					<td><input type="text" name="dob" id="datepicker"
						placeholder="Enter Date Of Birth" readonly="readonly"
						
						value="<%=DataUtility.getDateString(bean.getDOB())%>">
						&nbsp;<font style="position: fixed;" color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
 --%>
              <tr>
                    <tr><th align="left">Date Of Birth <span style="color: red">*</span> :</th>
                    <td><input type="text" name="dob" id="datepicker" readonly="readonly"
                        value="<%=DataUtility.getDateString(bean.getDOB())%>">
<!--                     <a href="javascript:getCalendar(document.forms[0].dob);"> 
<!--                             <img src="../img/cal.jpg" width="16" height="15" border="0"
<!--                          alt="Calender"> -->
                    </a><font
                        color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
                </tr>
                
            <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
                
                <tr>
                    <th></th>
                    <td colspan="2"><input type="submit" name="operation"
                        value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD %>"> &nbsp; <input type="submit"
                        name="operation" value="<%=MyProfileCtl.OP_SAVE %>"> &nbsp;</td>
                </tr>
            </table>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>