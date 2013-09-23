/*!
 * Ext JS Library 3.4.0
 * Copyright(c) 2006-2011 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */
/** 
 * 存放自定义的单例函数 
 * @author
 */  


 var ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url:  ctx+'/s/fee/loadGridProduct.do',
			method:'POST'
        }),
        reader: new Ext.data.JsonReader({
            root: 'result',
            totalProperty: 'totalCount',
            id: 'product_id'
        },
		[
            {name: 'PRODUCT_ID', mapping: 'PRODUCT_ID'},
            {name: 'PRODUCT_NAME', mapping: 'PRODUCT_NAME'},
            {name: 'PRODUCT_STANDARD', mapping: 'PRODUCT_STANDARD'},
            {name: 'PRODUCT_DESC', mapping: 'PRODUCT_DESC'}
        ])//,baseParams:{prd_name:''}
    });

    // Custom rendering Template
    var resultTpl1 = new Ext.XTemplate(
        '<tpl for="."><div class="search-item">',
            '<h3><span>产品ID:{PRODUCT_ID}<br />&nbsp;&nbsp;&nbsp; 产品名称:{PRODUCT_NAME}</span></h3>',
            '&nbsp;&nbsp;&nbsp;资费标准：<span style="font:bold;color:red">{PRODUCT_STANDARD}</span>',
        '</div></tpl>'
    );

    var resultTpl = new Ext.XTemplate(
        '<tpl for="."><div class="search-item">',
            '<h3><span >资费：<font color="red">{PRODUCT_STANDARD}</font>&nbsp;(元)<br />产品ID： {PRODUCT_ID}</span>{PRODUCT_NAME}</h3>',
            '{PRODUCT_DESC}',
        '</div></tpl>'
    );

	  Ext.form.TriggerField.override({
	    afterRender : function(){
	        Ext.form.TriggerField.superclass.afterRender.call(this);
	        var y;
	        if(Ext.isIE && !this.hideTrigger && this.el.getY() != (y = this.trigger.getY())){
	            this.el.position();
	            this.el.setY(y);
	        }
	    }
	});

    var prd_search = new Ext.form.ComboBox({
        store: ds,
		fieldLabel: '产品类型',
        displayField:'prd_name',
		hiddenName: 'prd_id',
		//hiddenId:'prd_id',
		name:'name_product_id',
		id:'product_id',
        typeAhead: false,
        loadingText: '查询中...',
        width: 570,
        pageSize:10,
		triggerAction: 'all',
        hideTrigger:false,
		labelStyle: 'font-weight:bold;',
        tpl: resultTpl,
        //applyTo: 'search',
        itemSelector: 'div.search-item',
		minChars:0,
		 emptyText: '请输入产品名称,支持模糊搜索,键入空格显示全部',
        onSelect: function(record){ // override default onSelect to do redirect
			 //Ext.getCmp("product_id").setValue(record.data.PRODUCT_ID+"--"+record.data.PRODUCT_NAME);
			 prd_search.setRawValue(record.data.PRODUCT_ID);
			 this.collapse();
        }
    });




