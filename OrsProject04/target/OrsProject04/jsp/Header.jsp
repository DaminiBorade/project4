<%-- <%@page import="in.co.rays.ors.controller.LoginCtl"%> --%>
<%@ page import="in.co.rays.project4.controller.LoginCtl"%>
<%@page import="in.co.rays.project4.bean.RoleBean"%>
<%@page import="in.co.rays.project4.bean.UserBean"%>
<%@page import="in.co.rays.project4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<HEAD>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js">
	
</script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() {
    $( "#datepicker" ).datepicker({
      changeMonth: true,
      changeYear: true,
	  yearRange:'1980:2020',
	  dateFormat:'dd/MM/yy'
    });
  } );
  </script>

</HEAD>
<body>
	<%
		UserBean userBean = (UserBean) session.getAttribute("user");
		boolean userLoggedIn = userBean != null;
		String welcomeMsg = "Hi, ";
		if (userLoggedIn) {
			String role = (String) session.getAttribute("role");
			welcomeMsg += userBean.getFrist_Name() + " (" + role + ")";
		} else {
			welcomeMsg += "Guest";
		}
	%>

	<table>
		<tr>
			<th></th>
			<td width="90%"><a href="<%=ORSView.WELCOME_CTL%>">Welcome</b></a>
				| <%
				if (userLoggedIn) {
			%> <a
				href=" <%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">Logout</b></a>

				<%
					} else {
				%> <a href="<%=ORSView.LOGIN_CTL%>">Login</b></a> <%
 	}
 %></td>
			<td rowspan="2">
				<h1 align="right">
					<img src="<%=ORSView.APP_CONTEXT%>/img/Logo.jpg" width="250"
						height="50">
				</h1>
			</td>
		</tr>

		<tr>
			<th></th>
			<td>
				<h3><%=welcomeMsg%></h3>
			</td>
		</tr>

		<%
			if (userLoggedIn) {
		%>

		<tr>
			<th></th>
			<td colspan="2"><font style="font-size: 18px"> <a
					href="<%=ORSView.MY_PROFILE_CTL%>">MyProfile</b></a> | <a
					href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</b></a> | <a
					href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</b></a> | <a
					href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet
						MeritList</b>
				</a> |   <%
            if (userBean.getRoll_Id()== RoleBean.ADMIN ) {
        %> 
       
        <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> |       
        <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |
        <a href="<%=ORSView.USER_CTL%>">Add User</b></a> | 
        <a href="<%=ORSView.USER_LIST_CTL%>">User List</b></a> |         
        <a href="<%=ORSView.COLLEGE_CTL%>">Add College</b></a> |        
        <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.ROLE_CTL%>">Add Role</b></a> |        
        <a href="<%=ORSView.ROLE_LIST_CTL%>">Role List</b></a> |
        <br>
        <a href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.COURSE_CTL %>" >Add Course</b></a> |       
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
        <a href="<%=ORSView.SUBJECT_CTL %>" >Add Subject</b></a> |       
        <a href="<%=ORSView.SUBJECT_LIST_CTL %>">Subject List</b></a> |       
        <a href="<%=ORSView.FACULTY_CTL %>" >Add Faculty</b></a> |       
        <a href="<%=ORSView.FACULTY_LIST_CTL %>">Faculty List</b></a> |
        <a href="<%=ORSView.TIMETABLE_CTL %>" >Add TimeTable</b></a> |       
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> | 
        <a target="blank" href="<%=ORSView.JAVA_DOC_VIEW%>">Java Doc</b></a> |
       <%
     		}	
 		%>
 		 <%
            if (userBean.getRoll_Id() == RoleBean.STUDENT) {
        %> 
       
        <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
        <a href="<%=ORSView.SUBJECT_LIST_CTL %>">Subject List</b></a> |       
        <a href="<%=ORSView.FACULTY_LIST_CTL %>">Faculty List</b></a> |
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> | 
       <%
     		}
 		%>
		
 		<%
            if (userBean.getRoll_Id() == RoleBean.KIOSK) {
        %> 
         <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |        
       <%
     		}
 		%>
 		 <%
            if (userBean.getRoll_Id() == RoleBean.COLLEGE_SCHOOL) {
            System.out.println("======>><><>"+userBean.getRoll_Id());	
        %> 
       
        <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> |       
        <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |
        <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
        <a href="<%=ORSView.SUBJECT_CTL %>" >Add Subject</b></a> |   
        <br>    
        <a href="<%=ORSView.SUBJECT_LIST_CTL %>">Subject List</b></a> |       
        <a href="<%=ORSView.TIMETABLE_CTL %>" >Add TimeTable</b></a> |       
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> | 
       <%
     		}
 		%>
 		 <%
            if (userBean.getRoll_Id() == RoleBean.KIOSK) {
           System.out.println("======>><><>"+userBean.getRoll_Id());	
          %> 
       
        <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> |       
        <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |
        <a href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.FACULTY_LIST_CTL %>">Faculty List</b></a> |
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
       <%
     		}
 		%>
 		
 		</font>
 		</td>
    </tr>
    	<%
        	}
   		 %>
			</font></td>
		</tr>
		
	</table>
	<hr>
</body>
</html>