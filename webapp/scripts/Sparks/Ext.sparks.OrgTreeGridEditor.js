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
Ext.sparks.OrgTreeGridEditor = Ext.extend(Ext.Panel, {

	closable:true,
	layout:'fit',
	autoScroll : false,
	id : "OrgTreeGridEditor",
	title : "机构管理",
	margins : '0 0 0 0',
	renderTo : Ext.getBody(),
	createPanel:function(){
		var panel = new Ext.Panel({
			layout:"border",
			border:false,
			width:335,
			height:570,
			items:[this.menuTreePanel,this.userGrid]
		});
		return panel;
	},
	initComponent: function() {

		 Ext.applyIf(this, {
            rootName: '分类'
        });

	this.createLeftMenuTree();
	this.createRightGrid();
	Ext.sparks.OrgTreeGridEditor.superclass.initComponent.call(this);
		
	this.add(this.createPanel());
		
	},
	/**
	  *
	  */
	createLeftMenuTree:function(){
		 this.menuTreePanel =  new Ext.sparks.ExtendJsonTreeForOrg({
				rootName:this.rootName,
				id: 'org_editor_tree',
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
	createRightGrid:function(){
		 this.userGrid =  new Ext.sparks.UsersJsonGrid({
				rootName:this.rootName,
				id: 'user_editor_grid',
				region:'center',
				width: 210,
				urlPagedQuery: this.urlPagedQuery,
				urlLoadData: this.urlLoadData,
				urlSave: this.urlSave,
				urlRemove: this.urlRemove,
				pageSize: 20,
				formConfig:this.userFormConfig
			})
	}
});
