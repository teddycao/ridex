<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ include file="/commons/meta.jsp" %>
<script type="text/javascript">Ext.namespace("App");</script>
<script src="${ctx}/scripts/ux/Ext.lingo.JsonGrid.js"></script>
<script src="${ctx}/scripts/ux/Ext.ux.PageSizePlugin.js"></script>
<script src="${ctx}/scripts/ux/Ext.lingo.TreeField.js"></script>
<title>资源管理</title>
</head>
<body>
<div id="contentview" align="center">
</div>


<script type="text/javascript">
<!--

var rescGrid;
App.RescGrid = Ext.extend(Ext.lingo.JsonGrid, {
    id: 'permission',
    urlPagedQuery: "${ctx}/s/showPermissions.do",
	urlLoadUser: "${ctx}/s/retrievePerm.do",
    urlLoadData: "${ctx}/s/showPerms.do",
    urlSave: "${ctx}/s/savePermissions.do",
    urlRemove: "${ctx}/s/removePermissions.do",
    dlgWidth: 300,
    dlgHeight: 240,
    formConfig: [
        {fieldLabel: '编号',     name: 'id', readOnly: true},
        {fieldLabel: '资源类型', name: 'resType'},
        {fieldLabel: '资源名称', name: 'name'},
        {fieldLabel: '资源内容', name: 'resString'},
        {fieldLabel: '资源备注', name: 'descn'}
    ]
});
 
Ext.onReady(function(){
   
    Ext.QuickTips.init();
    rescGrid = new App.RescGrid();

     var viewport = new Ext.Viewport({
                layout:'fit',
                anchorSize: {width:800, height:600},
					items:[rescGrid]
            });




	//store.load({params:{start:0, limit:30}});
});	

//-->
</script>
</body>
</html>