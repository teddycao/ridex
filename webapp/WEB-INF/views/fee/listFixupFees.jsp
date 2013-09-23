<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ include file="/commons/meta.jsp"%>


<%@ include file="/commons/ext_models.jsp"%>
<title>固定用户退费</title>
</head>
<body>
<div id="contentview" align="center"></div>
<div id="fi-form"></div>

<script type="text/javascript">
<!--

var sFixupFeeGrid;
var qSPannel;
var dlgWidth = 1200;
var dlgHeight = 400;
App.FixupFeeGrid = Ext.extend(Ext.sparks.ListJsonGrid, {
    id: 'fixupfee_grid',
    rootName: '固定用户退费',
    enableDD: false,
		flex:1,
    urlPagedQuery: "${ctx}/s/fee/showFixupFees.do",
    //urlLoadData: "${ctx}/s/fee/searchFixupFees.do",
    //urlSave: "${ctx}/s/saveRptInfo.do",
    //urlRemove: "${ctx}/s/removeRptInfo.do",
    pageSize: 15,
    dlgWidth:dlgWidth,
    dlgHeight:dlgHeight,
    autoScroll: true,
    
    formConfig: [
        {name : 'callType', fieldLabel : "呼叫类型", vType : "chn"},
        {name : 'startDate',fieldLabel : "通话日期", vType : "chn"},
        {name : 'startTime', fieldLabel : "通话时间", vType : "chn"},
        {name : 'callDuration', fieldLabel : "通话时长", vType : "integer"},
        {name : 'otherParty', fieldLabel : "对方号码", vType : "chn"},
        {name : 'originNo', fieldLabel : "前转号码", vType : "chn"},
        {name : 'officeCode', fieldLabel : "话单位置", vType : "chn"},
        {name : 'bizType', fieldLabel : "长途类型", vType : "chn"},
        {name : 'roamType', fieldLabel : "漫游类型", vType : "chn"},
        {name : 'cfee', fieldLabel : "基本费", vType : "number"},
        {name : 'lfee', fieldLabel : "长途费", vType : "number"},
        {name : 'totalFee', fieldLabel : "总费用", vType : "number"},
        {name : 'cellId', fieldLabel : "基站号", vType : "chn"},
        {name : 'vpnId', fieldLabel : "网络标识", vType : "chn"},
        {name : 'sourceType', fieldLabel : "数据源类型", vType : "chn"},
        {name : 'msisdn', fieldLabel : "手机号码", vType : "chn",hideGrid:true}
    ]
    
});

/**
 *生成查询界面
 */
var filterBar = 
	[
		'手机号码',
		{
			xtype:'textfield',
			text: "手机号码",
			id:'msisdn', 
			name: 'msisdn'
		},'-',
		'通话日期',
		{
			xtype:'datefield',
			text: "通话日期",
			id:'startDate', 
			name: 'startDate',
			renderTo: document.body,
            plugins :'monthPickerPlugin',
            format  : 'Y-m'
		},'-',
		{
			text: '&nbsp;&nbsp;查询&nbsp;',
			scale: 'medium',
            iconCls: 'findIcon',
            cls: 'x-btn-text-icon',
			handler:fnQueryHandler
		},'-',
		{
			id:"fileImport",
			text: '&nbsp;&nbsp;EXCEL文件导入&nbsp;',
			scale: 'medium',
            iconCls: 'findIcon',
            cls: 'x-btn-text-icon',
			handler:fnFileImportHandler
		}
    ];

function fnQueryHandler(){
    var queryParams = {filterTxt:'',filterValue:'',
    		msisdn: Ext.getCmp('msisdn').getValue(),
    		startDate: Ext.getCmp('startDate').getValue()};
	
	sFixupFeeGrid.queryGridByParams(queryParams);
}

function fnFileImportHandler(){
	var win;
	var button = Ext.get("fileImport");
	button.on('click', function(){
		if (!win) {
			this.formPanel = new Ext.form.FormPanel( {
				id : 'formPanel',
				fileUpload : true,
				labelAlign : 'right',
				frame : true,
				//autoHeight: true,
		        bodyStyle : 'padding: 10px 10px 0 10px;',
		        labelWidth : 60,
		        //height:60,
		        defaults : {
		            anchor: '95%',
		            allowBlank: false,
		            msgTarget: 'side'
		        },
				title : '文件导入',
				url : "${ctx}/s/fee/importFile.do",
				items : [{
				  id: "fileName",
		      	  xtype: 'textfield',   
		      	  fieldLabel: '文件名',   
		      	  name: 'fileName',   
		      	  inputType: 'file',//文件类型   
		      	  blankText: '此域为必输项'
		     	}],
				buttons : [{
					text : '导入',
					handler : function() {
						var fileName = Ext.get("fileName").dom.value;
						if (this.formPanel.getForm().isValid()) {
							this.formPanel.getForm().submit( {
								params:{fileName:fileName},
								waitTitle : "请稍候",
								waitMsg : '正在导入数据，请稍候...',
								success : function() {
									win.hide();
									this.refresh();
								},
								failure : function() {
								},
								scope : this
							});
						}
					}.createDelegate(this)
				},{
	                 text: '重置',
	                 handler: function() {
	                     //fp.getForm().reset();
	                 }
				},{
					text : '取消',
					handler : function() {
						win.hide();
					}.createDelegate(this)
				}]
			});
			win = new Ext.Window({
		         layout: 'fit',
		         width: this.dlgWidth ? this.dlgWidth : 450,
		         height: this.dlgHeight ? this.dlgHeight : 148,
		         closeAction: 'hide',
		         items: [this.formPanel]
		    });
			win.show(this);
		}		
	});
}

//----------------Grid上方查询栏-----------
App.QPannel = Ext.extend(Ext.FormPanel, {
	frame: false,
	id: 'queryForm',
	height: 40,
    flex:2,
	defaultType: 'textfield',
    buildBar: function() {},
	tbar: {
		height:60,
		items:['-','&nbsp;',filterBar]}

});
 
Ext.onReady(function(){
    Ext.QuickTips.init();
    sFixupFeeGrid = new App.FixupFeeGrid();
    qSPannel = new App.QPannel();
    var viewport = new Ext.Viewport({
            layout:'vbox',
            anchorSize: {width:800, height:600},
			items:[qSPannel,sFixupFeeGrid]
    });
});	

//-->
</script>
</body>
</html>