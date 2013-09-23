<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- ExtJS css -->
 <link rel="stylesheet" type="text/css" href="${ctx}/extjs-3/resources/css/ext-all.css" />
 <link rel="stylesheet" type="text/css" href="${ctx}/extjs-3/resources/css/main.css" />
 <link rel="stylesheet" type="text/css"  href="${ctx}/styles/menu.css" />
  <link rel="stylesheet" type="text/css"  href="${ctx}/styles/ext-patch.css" />
 <!-- ** Javascript ** -->
 <!-- ExtJS library: base/adapter -->
 <script src="${ctx}/extjs-3/adapter/ext/ext-base.js"></script>
	
 <!-- ExtJS library: all widgets -->
 <script src="${ctx}/extjs-3/ext-all.js"></script>
 <script src="${ctx}/extjs-3/ext-lang-zh_CN.js"></script>
  <script src="${ctx}/scripts/ux/Ext.ux.TabCloseMenu.js"></script>
  <script src="${ctx}/scripts/commons.js"></script>

 <script type="text/javascript">
		Ext.BLANK_IMAGE_URL = '${ctx}/extjs-3/resources/images/default/s.gif';
	var ctx = "${ctx}";
 </script>
