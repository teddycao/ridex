<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
var cfgToolBarItems = [cfgToolBarItems,{
            id      : 'audit',
            text    : '审批',
            iconCls : 'audit',
            tooltip : '审批',
			handler : function() {
				hutGrid.audit();
			}
        },'&nbsp;'];

/**
 * 通用数据展示grid
 */
App.HutGrid = Ext.extend(Ext.lingo.HutJsonGrid, {
    id: 'list_${meta.tableName}',
	beanName:'${param.name}',
	primaryKey:primaryKey,
	flex:1,
	pageSize:20,
    urlPagedQuery: "${ctx}/s/hut/loadPageData.do?name=${param.name}",
    urlLoadData: "${ctx}/s/hut/loadData.do",
	loadOneData: '${ctx}/s/hut/loadOneData.do',
    urlSave: "${ctx}/s/hut/add.do",
    urlRemove: "${ctx}/s/hut/remove.do",
	urlUpdate: "${ctx}/s/hut/update.do",
	urlAuditPass: "${ctx}/s/fee/aduitPass.do",
	urlAuditNoPass: "${ctx}/s/fee/aduitNoPass.do",
    dlgWidth: dlgWidth,
    dlgHeight: dlgHeight,
    formConfig:formViewConfig,
	formEditConfig:formEditConfig,
	recordMapping:recordMapping,
	toolBarItems	:cfgToolBarItems,
	formNewConfig:formNewConfig,//弹出审核对话框
    audit : function() {
        if (!this.dialog2) {
           this.createDialog2(formEditConfig);
        }

        if (this.checkOne()) {
            this.dialog2.show(Ext.get('audit'));
			var postParam = this.getSelectedParams()[0];
			var jsonPara = Ext.util.JSON.encode(postParam);
			var rand = Math.round(314159267*Math.random());
            this.formPanel2.load({
				method:'POST',
				url:this.loadOneData,
				params:{name:this.beanName,rd:rand,keysMap:jsonPara},
				text:'查询数据中...'
			
			});
			//改变Form提交地址	
			//this.formPanel2.form.url=this.urlUpdate;
        }
    },

    // 创建审批弹出式对话框
    createDialog2 : function(formVarConfig) {
		var items = this.formVarConfig;
		 if( typeof(formVarConfig) != "undefined" && formVarConfig != null){
			items = formVarConfig;
		 }

        Ext.each(items, function(item) {
            Ext.applyIf(item, {
                anchor: '90%'
            });
        });


       var reader2 = new Ext.data.JsonReader({
								root: 'result',
								totalProperty: 'totalProperty'
								},this.recordMapping/**Ext.data.Record.create(this.recordMapping)*/);
       this.formPanel2 = new Ext.FormPanel({
			id:'formPanel2',
            defaultType: 'textfield',
            labelAlign: 'right',
            labelWidth: 110,
			bodyStyle: 'padding:0 10px 0;',
			width: this.dlgWidth ? this.dlgWidth : 400,
            frame: true,
            autoScroll: true,
            title: '详细信息',
            reader: reader2,
            url: this.urlSave,
            items: items,
            buttons: [{
                text: '审批通过',
                handler: function() {
					if(this.beanName == ""){
						Ext.MessageBox.alert("错误","beanName必须赋值!");
					}
					this.formPanel2.findById('name').setValue(this.beanName);
					this.formPanel2.form.url=this.urlAuditPass;
                    if (this.formPanel2.getForm().isValid()) {
                        this.formPanel2.getForm().submit({
                            waitTitle: "请稍候",
							method:'POST', 
                            waitMsg : '提交数据，请稍候...',
                            success: function(form, action) {
                        		this.dialog2.close();
								this.dialog2 = null;
                                this.refresh();
                            },
                            failure: function(form, action) {
                            	if(action.result.message != undefined){
                            		Ext.MessageBox.alert("提示",action.result.message);
                            		this.dialog2.close();
    								this.dialog2 = null;
                                    this.refresh();
                            	}else{
                            		Ext.MessageBox.alert("异常","系统发生异常,请联系管理员!");
                            	}
                            },
                            scope: this
                        });
                    }
                }.createDelegate(this)
            },{
                text: '审批不通过',
                handler: function() {
					if(this.beanName == ""){
						Ext.MessageBox.alert("错误","beanName必须赋值!");
					}
					this.formPanel2.findById('name').setValue(this.beanName);
					this.formPanel2.form.url=this.urlAuditNoPass;
                    if (this.formPanel2.getForm().isValid()) {
                        this.formPanel2.getForm().submit({
                            waitTitle: "请稍候",
							method:'POST', 
                            waitMsg : '提交数据，请稍候...',
                            success: function() {
                                this.dialog2.close();
								this.dialog2 = null;
                                this.refresh();
                            },
                            failure: function() {
								Ext.MessageBox.alert("异常","系统发生异常,请联系管理员!");
                            },
                            scope: this
                        });
                    }
                }.createDelegate(this)
            },{
                text: '取  消',
                handler: function() {
                    this.dialog2.close();
					this.dialog2 = null;
                }.createDelegate(this)
            }]
        });
        this.dialog2 = new Ext.Window({
            layout: 'fit',
			modal : true,
            width: this.dlgWidth ? this.dlgWidth : 400,
            height: this.dlgHeight ? this.dlgHeight : 300,
            closeAction: 'hide',
            items: [this.formPanel2]
        });
    }
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