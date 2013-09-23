/*
 * Ext JS Library 2.0.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author lvhq
 * @since 2011-08-24
 * http://code.google.com/p/ridex/
 */
Ext.namespace("Ext.sparks");
/**
 *
 * @param config 需要的配置{}
 */
Ext.sparks.RptTreeEditor = Ext.extend(Ext.Panel, {

	closable:true,
	layout:'fit',
	autoScroll : false,
	id : "RptTreeEditor",
	title : "报表管理",
	margins : '0 0 0 0',
	renderTo : Ext.getBody(),
	createPanel:function(){
		var panel = new Ext.Panel({
			layout:"border",
			border:false,
			width:335,
			height:570,
			items:[this.rptTreePanel,this.rptParamGrid]
		});
		return panel;
	},
	initComponent: function() {
		 Ext.applyIf(this, {
            rootName: '分类'
         });

		 this.createLeftRptTree();
		 //this.createCenterPanel();
		 this.createRightGrid();
		 Ext.sparks.RptTreeEditor.superclass.initComponent.call(this);		
		 this.add(this.createPanel());		
	},
	/**
	  *
	  */
	createLeftRptTree:function(){
		 this.rptTreePanel =  new Ext.sparks.ExtendJsonTreeForRpt({
				rootName:this.rootName,
				id: 'rpt_param_tree',
				region:'west',
				width:400,
				urlGetAllTree: this.urlGetAllTree,
				urlInsertTree: this.urlInsertTree,
				urlRemoveTree: this.urlRemoveTree,
				urlSortTree: this.urlSortTree,
				urlLoadData: this.urlLoadData,
				urlUpdateTree: this.urlUpdateTree,
				formConfig:this.formConfig
			})
	},
	
	createRightGrid:function(){
		 this.rptParamGrid =  new Ext.lingo.JsonGrid({
				rootName:this.rootName,
				id: 'rpt_param_editor_grid',
				region:'center',
				width: 210,
				urlPagedQuery: this.urlPagedQuery,
				urlLoadData: this.urlLoadData,
				urlSave: this.urlSave,
				urlRemove: this.urlRemove,
				pageSize: 20,
				formConfig:this.paramFormConfig
			})
	}

	/**
	  *
	  */
 /**createCenterPanel:function(){
     this.centerForm = new Ext.Panel({
			layout:'anchor',
			region:'center',
			border:false,
			items:[]
	 });

    var reader = new Ext.data.JsonReader({
			root: 'results',
			successProperty:'success',
			totalProperty: 'totalProperty'
	},this.formConfig);
    
	this.extform = new Ext.FormPanel({
        labelWidth: 75, // label settings here cascade unless overridden
        url:'save-form.php',
        frame:true,
		anchor:'right 100%',
        title: '报表相关参数配置',
        bodyStyle:'padding:5px 5px 0',
       
        items: [{
            xtype:'fieldset',
			id:'extform_fieldset',
            checkboxToggle:true,
            title: '参数信息',
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
	
	//this.centerForm.add(this.basicform);
	this.centerForm.add(this.extform);
	//this.createDynamicForm();
  },**/
	/**
	  *
	  */
//--------------------------------
});
