<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv=”X-UA-Compatible” content=”IE=EmulateIE8″ />
<%@ include file="/commons/meta.jsp" %>
<script type="text/javascript">Ext.namespace("App");</script>


<script src="${ctx}/scripts/ux/Ext.lingo.JsonTree.js"></script>
<script src="${ctx}/scripts/Sparks/Ext.sparks.ExtendJsonTree.js"></script>
<script src="${ctx}/scripts/Sparks/Ext.sparks.MenuTreeEditor.js"></script>


<title>资源管理</title>
</head>
<body>
<div id="contentview" align="center">
</div>


<script type="text/javascript">
<!--

var menuView;
App.RptMenuView = Ext.extend(Ext.sparks.MenuTreeEditor, {
    rootName: '系统资源菜单',
    urlGetAllTree: "${ctx}/s/loadTreeItems.do",
    urlInsertTree: "${ctx}/s/insertTreeItem.do",
    urlRemoveTree: "${ctx}/s/removeTreeItem.do",
    urlSortTree: "${ctx}/s/sortMenuTree.do",
    urlLoadData: "${ctx}/s/loadItemData.do",
    urlUpdateTree: "${ctx}/s/updateTreeItem.do",
    formConfig: [
        {name : 'id',      fieldLabel : "ID",       vType : "integer",  allowBlank : true,  readOnly: true},
        {name : 'location',fieldLabel : "链接地址", vType : "location",  allowBlank : false},
        {name : 'name',    fieldLabel : "菜单名称", vType : "chn",      allowBlank : false},
        {name : 'qtip',  mapping: 'qtip',   fieldLabel : "提示信息", vType : "chn", qtip:'提示信息',     allowBlank : true},
        {name : 'iconCls', fieldLabel : "图标",     vType : "alphanum", allowBlank : true},
        {name : 'descn',   fieldLabel : "描述",     vType : "chn",      allowBlank : true}
    ]
});

 
Ext.onReady(function(){
   
    //Ext.QuickTips.init();
    menuView = new App.RptMenuView();

     var viewport = new Ext.Viewport({
                layout:'fit',
                anchorSize: {width:800, height:600},
					items:[menuView]
            });

	//store.load({params:{start:0, limit:30}});
});	

//-->
</script>
</body>
</html>