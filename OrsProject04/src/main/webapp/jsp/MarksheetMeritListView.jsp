<%@page import="in.co.rays.project4.controller.MarksheetMeritListCtl"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="in.co.rays.project4.bean.MarksheetBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<head>
<title>MarksheetMerit View</title>
</head>>
<html>
 <script src="<%=ORSView.APP_CONTEXT %>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT %>/js/Checkbox11.js"></script>

<body>
    <%@include file="Header.jsp"%>
    <center>
        <h1>Marksheet Merit List</h1>

        <form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" method="POST">
            <br>
            <table border="1" width="100%">
                <tr style="background: teal;">
<th ><input type="checkbox" id="select_all" name ="Select">Select All.</th>
                    <th>ID</th>
                    <th>Roll No</th>
                    <th>Name</th>
                    <th>Physics</th>
                    <th>Chemistry</th>
                    <th>Maths</th>

                </tr>
                <tr>
                    <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
                </tr>
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<MarksheetBean> it = list.iterator();

                    while (it.hasNext()) {

                        MarksheetBean bean = it.next();
                %>
                <tr>
 <td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"></td>
                    <td align="center"><%=index++%></td>
                    <td align="center"><%=bean.getRoll_No()%></td>
                    <td align="center"><%=bean.getName()%></td>
                    <td align="center"><%=bean.getPhysics()%></td>
                    <td align="center"><%=bean.getChemistry()%></td>
                    <td align="center"><%=bean.getMaths()%></td>

                </tr>

                <%
                    }
                %>
            </table>
            <table>
                <tr>
                    <td align="right"><input type="submit" name="operation"
                        value="<%=MarksheetMeritListCtl.OP_BACK%>"></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
                type="hidden" name="pageSize" value="<%=pageSize%>">
        </form>
    </center>
    <%@include file="Footer.jsp"%>
</body>
</html>
