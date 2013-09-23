<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%@ include file="/commons/meta.jsp" %>
<%@ include file="/commons/ext_models.jsp" %>
<script src="${ctx}/scripts/ux/Ext.lingo.JsonTree.js"></script>

<script src="${ctx}/scripts/Sparks/Ext.sparks.ExtendJsonTreeForRpt.js"></script>
<script src="${ctx}/scripts/Sparks/Ext.sparks.RptTreeEditor.js"></script>

<title>报表管理</title>
</head>
<body>
<div id="contentview" align="center">
</div>


<script type="text/javascript">
<!--

var sRptTree;
App.RptTree = Ext.extend(Ext.sparks.RptTreeEditor, {
    id: 'rpt_tree',
    rootName: '报表信息',
    enableDD: false,
    urlGetAllTree: "${ctx}/s/loadRptParameterTrees.do",
    //urlLoadData: "${ctx}/s/showRptInfotrees.do",
    
    formConfig: [
        {name : 'id',      fieldLabel : "流水号",       vType : "integer",  allowBlank : true,  readOnly: true},
        {name : 'rptId',fieldLabel : "报表编号", vType : "chn",      allowBlank : false},
        {name : 'name',    fieldLabel : "报表名称", vType : "chn",      allowBlank : false},
        {name : 'rptUrl',    fieldLabel : "URL", vType : "chn",      allowBlank : true, hidden: true},
        {name : 'rptDesc', fieldLabel : "报表描述",     vType : "chn", allowBlank : true, hidden: true},
        {name : 'rptOrg',   fieldLabel : "所属部门",     vType : "chn",      allowBlank : true, hidden: true},
        {name : 'rptStatu',   fieldLabel : "报表状态",     vType : "chn",      allowBlank : true, hidden: true},
        {name : 'parentId',   fieldLabel : "父流水号",     vType : "chn",      allowBlank : true, hidden: true}
    ],
    urlPagedQuery: "${ctx}/s/loadRptParamByRptId.do",
	urlLoadUser: "${ctx}/s/retrieveRptParam.do",
    urlLoadData: "${ctx}/s/loadRptParamByRptId.do",
    urlSave: "${ctx}/s/saveRptParam.do",
    urlRemove: "${ctx}/s/removeRptParam.do",
    paramFormConfig: [
        {name : 'id',      fieldLabel : "流水号",       vType : "integer",  allowBlank : true, hidden: true,hideGrid:true},
        {name : 'rptId', fieldLabel : "报表编号", vType : "chn",      allowBlank : false},
        {name : 'rptName',    fieldLabel : "报表名称", vType : "chn",      allowBlank : false},
        {name : 'paramName',    fieldLabel : "参数名称", vType : "chn",      allowBlank : false, hidden: false},
        {name : 'paramTitle', fieldLabel : "参数标题",     vType : "chn", allowBlank : false, hideGrid:true},
        {name : 'paramDesc',   fieldLabel : "参数描述",     vType : "chn",      allowBlank : true, hidden: false},
        {name : 'paramType',   fieldLabel : "参数类型",     vType : "chn",      allowBlank : false, hidden: false},
        {name : 'paramRef',   fieldLabel : "参数参考号",     vType : "chn",      allowBlank : true, hideGrid:true},
        {name : 'dftVal',   fieldLabel : "默认值",     vType : "chn",      allowBlank : true, hideGrid:true},
        {name : 'isNeed',   fieldLabel : "是否必输",     vType : "chn",      allowBlank : false, hideGrid:false},
        {name : 'isPrompt',   fieldLabel : "是否是提示类型参数",     vType : "chn",      allowBlank : false, hideGrid:false},
        {name : 'isDisplay',   fieldLabel : "是否显示",     vType : "chn",      allowBlank : false, hideGrid:false}
    ]
});

 
Ext.onReady(function(){   
    Ext.QuickTips.init();
    sRptTree = new App.RptTree();

    var viewport = new Ext.Viewport({
            layout:'fit',
            anchorSize: {width:800, height:600},
			items:[sRptTree]
    });
});	

//-->
</script>
</body>
</html>