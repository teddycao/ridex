<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ taglib uri="/WEB-INF/tlds/jatools.tld" prefix="jatools" %>
<%@ include file="/commons/meta-ext4.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表展示</title>

<script type="text/javascript">var ctx = "${ctx}";</script>
<script src="${ctx}/scripts/jquery.mini.js"></script>
<script src="${ctx}/scripts/toolsbar.js"></script>

<script type="text/javascript"></script>
</head>
<body style='overflow:hidden;margin:0;padding:30px 0 5px 0;'>


<%String rptFile = (String)request.getAttribute("rptFile");%>

<%
//System.out.println(rptFile+"--"+request.getParameter("CNY"));
%>
<jatools:report id="_report1" template="<%=rptFile%>" >
	<%@ include file='/WEB-INF/views/report/toolsbar.jsp'%>
</jatools:report>
</body>
</html>
