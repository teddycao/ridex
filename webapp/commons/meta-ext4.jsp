<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- ExtJS4 css -->
<link rel="stylesheet" type="text/css" href="${ctx}/js/extjs-4/resources/css/ext-all.css">
<script type="text/javascript" src="${ctx}/js/extjs-4/ext-all.js"></script>
<script type="text/javascript" src="${ctx}/js/extjs-4/locale/ext-lang-zh_CN.js"></script>
<!-- ** Javascript ** -->
<script type="text/javascript">
	Ext.BLANK_IMAGE_URL = '${ctx}/extjs-3/resources/images/default/s.gif';
	var ctx = "${ctx}";
</script>
