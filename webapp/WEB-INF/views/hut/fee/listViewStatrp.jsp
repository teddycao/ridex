<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/meta.jsp" %>
<script type="text/javascript">Ext.namespace("App");</script>
<script src="${ctx}/scripts/ux/Ext.lingo.HutJsonGrid.js"></script>
<script src="${ctx}/scripts/ux/Ext.ux.PageSizePlugin.js"></script>
<script src="${ctx}/scripts/ux/Ext.lingo.TreeField.js"></script>
<script src="${ctx}/s/hut/jsapi.do?format=js&name=${param.name}"></script>

<title><c:out value="${meta.title}"/>数据列表</title>
</head>
<body>
<div id="contentview" align="center">
</div>


<script type="text/javascript">
<!--

/**
 * 通用数据展示grid
 */
App.HutGrid = Ext.extend(Ext.lingo.HutJsonGrid, {
    id				: 'list_${meta.tableName}',
	beanName		:'${param.name}',
	primaryKey		:primaryKey,
	flex			:1,
	pageSize		:20,
    urlPagedQuery	:'${ctx}/s/hut/loadPageData.do?name=${param.name}',
    urlLoadData		:'${ctx}/s/hut/loadData.do',
	loadOneData		:'${ctx}/s/hut/loadOneData.do',
    urlSave			:'${ctx}/s/hut/add.do',
    urlRemove		:'${ctx}/s/hut/remove.do',
	urlUpdate		:'${ctx}/s/hut/update.do',
    dlgWidth		:dlgWidth,
    dlgHeight		:dlgHeight,
    formConfig		:formViewConfig,
	formEditConfig	:formEditConfig,
	recordMapping	:recordMapping,
	formNewConfig	:formNewConfig,
	toolBarItems	:cfgToolBarItems,
    filterTxt		:  filterTxt,
	filterValue		:  filterValue,
	useHistory		:false
	
	
});
//----------------Grid上方查询栏-----------
App.QPannel = Ext.extend(Ext.Panel, {
	frame: false,
	id: 'queryForm',
	height: 40,
    flex:2,
	defaultType: 'textfield',
    buildBar: function() {},
	tbar: {
		height:60,
		items:[{
                text: '菜单操作',
                iconCls: 'holly',
                //scale: 'medium',
                iconAlign: 'top'
				//,menu: [{text: '文件导入'}]
            },'-','&nbsp;',filterBar]}

});





Ext.onReady(function(){
   
    Ext.QuickTips.init();
	  Ext.form.Field.prototype.msgTarget = 'side';

    hutGrid = new App.HutGrid();
	qSPannel = new App.QPannel();
     var viewport = new Ext.Viewport({
                layout: 'vbox',
                anchorSize: {width:800, height:600},
					items:[qSPannel,hutGrid]
            });

	//store.load({params:{start:0, limit:30}});
});	


//-->
</script>
</body>
</html>