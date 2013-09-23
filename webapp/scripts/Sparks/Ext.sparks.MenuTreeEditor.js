/*
 * Ext JS Library 2.0.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Raidery
 * @since 2008-03-23
 * http://code.google.com/p/ridex/
 */
Ext.namespace("Ext.sparks");
/**
 *
 * @param config 需要的配置{}
 */
Ext.sparks.MenuTreeEditor = Ext.extend(Ext.Panel, {

	closable:true,
	layout:'fit',
	autoScroll : false,
	id : "MenuTreeEditor",
	title : "菜单管理",
	margins : '0 0 0 0',
	renderTo : Ext.getBody(),
	createPanel:function(){
		var panel = new Ext.Panel({
			layout:"border",
			border:false,
			width:335,
			height:570,
			items:[this.menuTreePanel,this.centerForm]
		});
		return panel;
	},
	initComponent: function() {

		 Ext.applyIf(this, {
            rootName: '分类',
        });

	this.createLeftMenuTree();
	this.createCenterPanel();
		Ext.sparks.MenuTreeEditor.superclass.initComponent.call(this);
		
		this.add(this.createPanel());
		
	},
	/**
	  *
	  */
	createLeftMenuTree:function(){
		 this.menuTreePanel =  new Ext.sparks.ExtendJsonTree({
				rootName:this.rootName,
				id: 'rpt_editor_tree',
				region:'west',
				urlGetAllTree: this.urlGetAllTree,
				urlInsertTree: this.urlInsertTree,
				urlRemoveTree: this.urlRemoveTree,
				urlSortTree: this.urlSortTree,
				urlLoadData: this.urlLoadData,
				urlUpdateTree: this.urlUpdateTree,
				formConfig:this.formConfig
			})
	},

	/**
	  *
	  */
 createCenterPanel:function(){
     this.centerForm = new Ext.Panel({
			layout:'anchor',
			region:'center',
			border:false,
			items:[]
	 });

       var reader = new Ext.data.JsonReader({
								root: 'results',
								totalProperty: 'totalProperty'
								},this.formConfig);

	this.basicform = new Ext.FormPanel({
		id:'menuBasicForm',
		name:'menuBasicForm',
        labelWidth: 75, // label settings here cascade unless overridden
        frame:true,
		anchor:'right 40%',
        title: '菜单信息配置',
        bodyStyle:'padding:5px 5px 0',
        url: this.urlUpdateTree,
		reader: reader,
        items: [{
            xtype:'fieldset',
            title: '基本信息',
            collapsible: false,
            autoHeight:true,
            defaults: {width: 210},
            defaultType: 'textfield',
            items :[this.formConfig]
        }],

        buttons: [{
                text: '保  存',
                handler: function() {
                    this.basicform.getForm().submit({
                        waitTitle: "请稍候",
                        waitMsg : '正在提交......',
                        success: function() {
                          Ext.Msg.alert('提示', '保存成功!');

                        }.createDelegate(this),
                        failure: function() {
                        }
                    });
                }.createDelegate(this)
            }]
    });

	this.extform = new Ext.FormPanel({
        labelWidth: 75, // label settings here cascade unless overridden
        url:'save-form.php',
        frame:true,
		anchor:'right 60%',
        title: '报表相关信息配置',
        bodyStyle:'padding:5px 5px 0',
       
        items: [{
            xtype:'fieldset',
			id:'extform_fieldset',
            checkboxToggle:true,
            title: '报表参数配置',
            autoHeight:true,
            defaults: {width: 210},
            defaultType: 'textfield',
            collapsed: true,
            items :[this.formConfig]
        }],

        buttons: [{
            text: '保 存'
        }]
    });

 	//var selectedNode = this.menuTreePanel.getSelectionModel().getSelectedNode();
	
	this.centerForm.add(this.basicform);
	this.centerForm.add(this.extform);
	this.createDynamicForm();

	
  },
	/**
	  *
	  */

 createDynamicForm:function(){
 
 
		    var dept =	new Ext.form.TextField({
								                fieldLabel:'部门编号',  
								                name:'deptno',   
								                id:'deptno',
								                //grow:true,
								                value:'dept-01',
								                allowBlank:false,  
								                blankText : "这个字段最好不要为空",    
								                maskRe:/[a-zA-z]/gi  
            				});


 

 Ext.getCmp('extform_fieldset').add(dept);


 }
});
