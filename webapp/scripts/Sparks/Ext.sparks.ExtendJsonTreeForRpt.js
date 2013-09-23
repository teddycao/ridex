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
Ext.sparks.ExtendJsonTreeForRpt =  Ext.extend(Ext.lingo.JsonTree, {


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

		 Ext.sparks.ExtendJsonTreeForRpt.superclass.initComponent.call(this);

		 this.on('click', this.handelTreeClick, this);
	},
    buildToolbar: function() {
        var toolbar = [{
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
	handelTreeClick: function(node, e) {
		var cmp = Ext.getCmp("rpt_param_editor_grid");
		var id = node.id;
		//alert("node=" + node);
        if (node.isLeaf()) {
			cmp.store.load({
				waitTitle: "请稍候",
                waitMsg : '正在查询...',
				url: this.urlLoadData,
				params:{id:id}
			});
			  
		 }
   }


//----------------------------------
});
