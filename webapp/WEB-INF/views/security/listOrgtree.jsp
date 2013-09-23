<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ include file="/commons/meta.jsp" %>

<%@ include file="/commons/ext_models.jsp" %>

<script src="${ctx}/scripts/ux/Ext.lingo.JsonTree.js"></script>
<script src="${ctx}/scripts/Sparks/Ext.sparks.ExtendJsonTree.js"></script>
<script src="${ctx}/scripts/Sparks/Ext.sparks.OrgTreeGridEditor.js"></script>
<script src="${ctx}/scripts/Sparks/Ext.sparks.ExtendJsonTreeForOrg.js"></script>
<script src="${ctx}/scripts/Sparks/Ext.sparks.UsersJsonGrid.js"></script>

<title>机构管理</title>
</head>
<body>
<div id="contentview" align="center">
</div>


<script type="text/javascript">
<!--

var smenuTree;
App.OrgTree = Ext.extend(Ext.sparks.OrgTreeGridEditor, {
    id: 'org',
    rootName: '系统资源机构',
    enableDD: false,
    urlGetAllTree: "${ctx}/s/loadOrgTreeItems.do",
    urlInsertTree: "${ctx}/s/insertOrgTreeItem.do",
    urlRemoveTree: "${ctx}/s/removeOrgTreeItem.do",
    //urlSortTree: "${ctx}/s/sortMenuTree.do",
    urlLoadData: "${ctx}/s/loadOrgItemData.do",
    urlUpdateTree: "${ctx}/s/updateOrgTreeItem.do",
    formConfig: [
        {name : 'id',      fieldLabel : "机构编号",       vType : "integer",  allowBlank : true,  readOnly: false},
        {name : 'location',     fieldLabel : "链接地址", vType : "location",      allowBlank : false},
        {name : 'name',    fieldLabel : "机构名称", vType : "chn",      allowBlank : false},
        {name : 'qtip',    fieldLabel : "提示信息", vType : "chn",      allowBlank : true},
        {name : 'iconCls', fieldLabel : "图标",     vType : "alphanum", allowBlank : true},
        {name : 'descn',   fieldLabel : "描述",     vType : "chn",      allowBlank : true}
    ],
    urlPagedQuery: "${ctx}/s/loadUsersByOrgId.do",
	urlLoadUser: "${ctx}/s/retrieveUser.do",
    urlLoadData: "${ctx}/s/loadUsersByOrgId.do",
    urlSave: "${ctx}/s/saveUsers.do",
    urlRemove: "${ctx}/s/removeUser.do",
    userFormConfig: [
        {fieldLabel: '用户名', name: 'name',allowBlank: false},
        {fieldLabel: '密码', name: 'password', inputType: 'password',hideGrid:true},
       	{fieldLabel: '重复密码', name: 'confirmPassword', inputType: 'password',hideGrid:true},
        {fieldLabel: '部门', name: 'dept', xtype: 'textfield'},
        {fieldLabel: '电子邮件', name: 'email',allowBlank: false},
        {fieldLabel: '电话号码', name: 'phoneNumber'}
    ]
    //urlPagedQuery: "${ctx}/s/loadUsersByOrgId.do",
    //urlLoadDataByOrgId: "${ctx}/s/loadUsersByOrgId.do",
});

 
Ext.onReady(function(){
   
    Ext.QuickTips.init();
    sorgTree = new App.OrgTree();

     var viewport = new Ext.Viewport({
                layout:'fit',
                anchorSize: {width:800, height:600},
					items:[sorgTree]
            });

	//store.load({params:{start:0, limit:30}});
});	

//-->
</script>
</body>
</html>