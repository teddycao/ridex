<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tlds/jatools.tld" prefix="jatools" %>
<html>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表展示</title>
<!--OBJECT ID="_jatoolsPrinter" CLASSID="CLSID:B43D3361-D975-4BE2-87FE-057188254255" codebase="jatoolsPrinter/jatoolsPrinter.cab#version=5,0,0,0"></OBJECT-->

<script type="text/javascript">var ctx = "${ctx}";</script>

<script src="${ctx}/scripts/jquery.mini.js"></script>
<script src="${ctx}/scripts/toolsbar.js"></script>
</head>
<body style='overflow:hidden;margin:0;padding:30px 0 5px 0;'>
<%
String file = request.getParameter("file");
if(file != null && file.startsWith("contextRoot:"))
{

 String tplRoot=getServletConfig().getServletContext().getRealPath("/");
  
 if(!tplRoot.endsWith(File.separator))
 {
 	tplRoot+= File.separator;
 }
 
 tplRoot+=file.substring(file.indexOf(":")+1);
 file = tplRoot.replace('\\','/');
}
//-----------
String examplePath=getServletConfig().getServletContext().getRealPath("/");
if(!examplePath.endsWith(File.separator))
 {
 	examplePath+= File.separator ;
 }
 
 examplePath+=("demos"+File.separator+"templates"+File.separator);
 examplePath = examplePath.replace('\\','/');

String rptFile = examplePath+File.separator+file;
%>
<jatools:report id="_report1" template="<%=rptFile%>" >
<%@ include file='toolsbar.jsp'%>
</jatools:report>
</body>
</html>
