/*
 * Ext JS Library 2.0.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 *
 *
 * @author Raidery
 * @since 2011-07-15
 * http://code.google.com/p/ridex/
 */
Ext.namespace("Ext.sparks");
/**
 *
 * @param config 需要的配置{}
 */
Ext.sparks.ExtendJsonTree =  Ext.extend(Ext.lingo.JsonTree, {


	initComponent: function() {
		Ext.applyIf(this, {
				urlGetAllTree: this.urlGetAllTree,
				urlInsertTree: this.urlInsertTree,
				urlRemoveTree: this.urlRemoveTree,
				urlSortTree: this.urlSortTree,
				urlLoadData: this.urlLoadData,
				urlUpdateTree: this.urlUpdateTree,
				formConfig:this.formConfig
        });

		 Ext.sparks.ExtendJsonTree.superclass.initComponent.call(this);

		 this.on('click', this.handelTreeClick, this);
	},
    buildToolbar: function() {
        var toolbar = [{
            text    : '新增下级',
            iconCls : 'add',
            tooltip : '添加选中节点的下级分类',
            handler : this.createChild.createDelegate(this)
        }, {
            text    : '新增同级',
            iconCls : 'go',
            tooltip : '添加选中节点的同级分类',
            handler : this.createBrother.createDelegate(this)
        }, {
            text    : '修改节点',
            iconCls : 'edit',
            tooltip : '修改选中分类',
            handler : this.updateNode.createDelegate(this)
        }, {
            text    : '删除节点',
            iconCls : 'delete',
            tooltip : '删除一个分类',
            handler : this.removeNode.createDelegate(this)
        },{
            text    : '刷新',
            iconCls : 'refresh',
            tooltip : '刷新所有节点',
            handler : this.refresh.createDelegate(this)
        }];
        return toolbar;
    }, 
	
	/**
	 *
	 *
	 */
	leafOnClick: function() {
        // DEL快捷键，删除节点
        //this.on(Ext.EventObject.DELETE, this.removeNode);
        this.on('click', function() {
		    alert('---ttt---');
        }, this);
    },
	/**
	 *
	 *
	 */
	handelTreeClick: function(node, e) {
		var cmp = Ext.getCmp("menuBasicForm");
        if (node.isLeaf()) {
			cmp.load({
				waitTitle: "请稍候",
                waitMsg : '正在查询...',
				url: this.urlLoadData + "?id=" + node.id
			});
			  
		 }
		 
		 
   }











//----------------------------------
});
