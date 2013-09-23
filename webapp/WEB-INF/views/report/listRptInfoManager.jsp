<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ include file="/commons/meta.jsp"%>


<%@ include file="/commons/ext_models.jsp"%>
<title>报表参数管理</title>
</head>
<body>
<div id="contentview" align="center"></div>


<script type="text/javascript">
<!--

var sRptInfoGrid;
App.RptInfoGrid = Ext.extend(Ext.lingo.JsonGrid, {
    id: 'rpt_info_grid',
    rootName: '报表信息',
    enableDD: false,
    urlPagedQuery: "${ctx}/s/showRptInfos.do",
    urlLoadData: "${ctx}/s/retrieveRptInfo.do",
    urlSave: "${ctx}/s/saveRptInfo.do",
    urlRemove: "${ctx}/s/removeRptInfo.do",
    pageSize: 20,
    
    formConfig: [
        {name : 'id',      fieldLabel : "流水号",       vType : "integer",  allowBlank : true,  readOnly: true, hidden: true},
        {name : 'rptId',fieldLabel : "报表编号", vType : "chn",      allowBlank : false},
        {name : 'name',    fieldLabel : "报表名称", vType : "chn",      allowBlank : false},
        {name : 'rptUrl',    fieldLabel : "URL", vType : "chn",      allowBlank : true, hidden: false},
        {name : 'rptDesc', fieldLabel : "报表描述",     vType : "chn", allowBlank : true, hidden: false},
        {name : 'rptOrg',   fieldLabel : "所属部门",     vType : "chn",      allowBlank : true, hidden: false},
        {name : 'rptStatu',   fieldLabel : "报表状态",     vType : "chn",      allowBlank : true, hidden: false},
        {name : 'parentId',   fieldLabel : "父流水号",     vType : "chn",      allowBlank : true, hidden: false}
    ]
    
});

 
Ext.onReady(function(){   
    Ext.QuickTips.init();
    sRptInfoGrid = new App.RptInfoGrid();

    var viewport = new Ext.Viewport({
            layout:'fit',
            anchorSize: {width:800, height:600},
			items:[sRptInfoGrid]
    });
});	

//-->
</script>
</body>
</html>