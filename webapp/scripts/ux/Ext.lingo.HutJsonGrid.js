/*
 * Ext JS Library 2.0.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Raidery
 * @since 2011-09-01
 */
Ext.namespace("Ext.lingo");
/**
 * 拥有CRUD功能的表格.
 *
 * @param config    需要的配置{}
 */
Ext.lingo.HutJsonGrid = Ext.extend(Ext.grid.GridPanel, {
    loadMask: true,
    stripeRows: true,
	postParam:{},
    // 初始化
    initComponent: function() {
        //this.useHistory = this.useHistory !== false;
		Ext.QuickTips.init();
        Ext.applyIf(this, {
		beanName:'',
		urlPagedQuery: "${ctx}/s/hut/loadPageData.do?name="+this.beanName,
		urlLoadData: "${ctx}/s/hut/loadData.do",
		loadOneData: '${ctx}/s/hut/loadOneData.do',
		urlSave: "${ctx}/s/hut/add.do",
		urlRemove: "${ctx}/s/hut/remove.do",
		urlUpdate: "${ctx}/s/hut/update.do",
		recordMapping:{name: 'ID', mapping: 'ID'},
		filterTbar:true,
		toolBarItems:[{
            id      : 'add',
            text    : '新增',
            iconCls : 'add',
            tooltip : '新增',
            handler : this.add.createDelegate(this)
        }, '-',{
            id      : 'edit',
            text    : '修改',
            iconCls : 'edit',
            tooltip : '修改',
            handler : this.edit.createDelegate(this)
        },'-', {
            id      : 'del',
            text    : '删除',
            iconCls : 'delete',
            tooltip : '删除',
            handler : this.del.createDelegate(this)
        }],
         pageSize: 15
      });

        this.buildColumnModel();
        this.buildRecord();
        this.buildDataStore();
        if (this.createHeader != false) {
            this.buildToolbar();
        }
        // 设置baseParams
        this.setBaseParams();

        Ext.lingo.HutJsonGrid.superclass.initComponent.call(this);

        this.on('rowdblclick', this.edit, this);

        //右键菜单
        this.on('rowcontextmenu', this.contextmenu, this);
    },

    // 初始化ColumnModel
    buildColumnModel: function() {
        this.sm = new Ext.grid.CheckboxSelectionModel();
        var columnHeaders = new Array();
        columnHeaders[0] = new Ext.grid.RowNumberer();
        columnHeaders[1] = this.sm;

        for (var i = 0; i < this.formConfig.length; i++) {
            var col = this.formConfig[i];
            if (col.hideGrid === true) {
                continue;
            }
            columnHeaders.push({
                header: col.fieldLabel,
                sortable: col.sortable,
                dataIndex: col.name,
				width    : col.width,
				tooltip  : col.tooltip,
				align    : col.align,          
                renderer: col.renderer,
				resizable: true
            });
        }
        this.cm = new Ext.grid.ColumnModel(columnHeaders);
        this.cm.defaultSortable = true;
        this.columnModel = this.cm;
    },

    buildRecord: function() {
        this.dataRecord = Ext.data.Record.create(this.formConfig);
    },

    buildDataStore: function() {
        this.store = new Ext.data.Store({
            proxy  : new Ext.data.HttpProxy({url:this.urlPagedQuery}),
            reader : new Ext.data.JsonReader({
                root          : "result",
                totalProperty : "totalCount",
                id            : "id"
            }, this.dataRecord),
            remoteSort : true
        });
        // this.store.setDefaultSort("id", "DESC");
    },

    buildToolbar: function() {
        //
        var checkItems = new Array();
        for (var i = 0; i < this.formConfig.length; i++) {
            var meta = this.formConfig[i];
            if (meta.showInGrid === false) {
                continue;
            }
            var item = new Ext.menu.CheckItem({
                text         : meta.fieldLabel,
                value        : meta.name,
                checked      : true,
                group        : "filter",
                checkHandler : this.onItemCheck.createDelegate(this)
            });
            checkItems[checkItems.length] = item;
        }

        this.filterButton = new Ext.Toolbar.SplitButton({
            iconCls  : "filter",
            text     : this.formConfig[0].fieldLabel,
            tooltip  : "选择搜索的字段",
            menu     : checkItems,
            minWidth : 105
        });
        // 输入框
        this.filter = new Ext.form.TextField({
            'name': 'filter'
        });


        this.tbar = new Ext.Toolbar([this.toolBarItems, '->', this.filterButton, this.filter]);

        this.filter.on('specialkey', this.onFilterKey.createDelegate(this));

        // 把分页工具条，放在页脚
        var paging = new Ext.PagingToolbar({
            pageSize: this.pageSize,
            store: this.store,
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
            emptyMsg: "没有记录",
            plugins: [new Ext.ux.PageSizePlugin()]
        });
		this.setBaseParams();
        this.store.load({
            params:{start:0, limit:paging.pageSize}
        });
        this.bbar = paging;
    },

    // 设置baseParams
    setBaseParams : function() {
        // 读取数据
        this.store.on('beforeload', function() {
            this.store.baseParams = {
                //filterValue : this.filter.getValue(),
				filterValue : this.filterValue,
                filterTxt   : this.filterTxt
            };
        }.createDelegate(this));

	/**Extjs 4使用以下方式 http://hexawing.blog.cd/?tag=extraparams
	store.on('beforeload', function (store, options) {
        var new_params = { name: Ext.getCmp('search').getValue() };
        Ext.apply(store.proxy.extraParams, new_params);
        // alert('beforeload');
    });
	**/


    },
	/**
	 *通过条件查询
	 */
	queryGridByParams:function(refParam) {

       this.store.on('beforeload', function(store, options) {
            this.store.baseParams = {
                filterValue : this.filterValue,
                filterTxt   : this.filterTxt
            };
			Ext.apply(this.store.baseParams, refParam);
        }.createDelegate(this));

		
		this.store.load({
				 params: { start: 0, limit: this.pageSize }
		})
    },
    // 选中搜索属性选项时
   // 选中搜索属性选项时
    onItemCheck : function(item, checked) {
        if(checked) {
            this.filterButton.setText(item.text + ':');
            this.filterTxt = item.value;
        }
    },
   filterKeys : function(e){
        if(e.ctrlKey){
            return;
        }
        var k = e.getKey();
        if(Ext.isGecko && (e.isNavKeyPress() || k == e.BACKSPACE || (k == e.DELETE && e.button == -1))){
            return;
        }
        var c = e.getCharCode(), cc = String.fromCharCode(c);
        if(!Ext.isGecko && e.isSpecialKey() && !cc){
            return;
        }
        if(!this.maskRe.test(cc)){
            e.stopEvent();
        }
    },
    // 监听模糊搜索框里的按键
    onFilterKey: function(field, e) {
        //var filterTxt = this.store.baseParams.filterTxt;
		
        if (typeof this.filterTxt == 'undefined' || this.filterTxt == '') {
            Ext.Msg.alert('提示', '请先选择搜索的字段');
            return;
        }
		var k = e.getKey();
        if(e.getKey() == e.ENTER && field.getValue().length > 0) {
            this.filterValue = field.getValue();

        } else if ((k == e.DELETE && e.button == -1) && (field.getValue().length() == 0)) {
			field.setValue('');
			this.filterValue = '';
        } else {
            return;
        }


       // delete this.store.lastOptions.params.meta;
		this.queryGridByParams();
        //this.store.reload();
    },

    // 检测至少选择一个
    checkOne: function() {
		//var sels = grid.getSelectionModel().getSelections();

        var selections = this.getSelectionModel().getSelections();
        if (selections.length == 0) {
            Ext.MessageBox.alert("提示", "请选择一条的记录！");
            return false;
        } else if (selections.length != 1) {
            Ext.MessageBox.alert("提示", "不能选择多行！");
            return false;
        }
        return true;
    },

    // 检测必须选择一个
    checkMany: function() {
        var selections = this.getSelectionModel().getSelections();
        if (selections.length == 0) {
            Ext.MessageBox.alert("提示", "请至少选择一条的记录！");
            return false;
        }
        return true;
    },
   getSelectedParams : function(params) {

	    //this.postParam = Ext.apply(params);
		var sels = this.getSelectionModel().getSelections();
		 var params = new Array();
		//var keyMap = new Ext.util.MixedCollection();
        for(var i = 0, len = sels.length; i < len; i++){
            try {
               //从选取的对象中获取记录主键数据
			    var currKey,currVal,keyRecd
			    var keyMap = new Object();
 
				for(var j = 0, jlen = this.primaryKey.length-1; j < jlen; j++){
					currKey = this.primaryKey[j];
					currVal = encodeURI(sels[i].get(currKey));
					keyMap[currKey] = currVal;
				}

				params[i] = keyMap;
            } catch (e) {
				 Ext.MessageBox.alert('提示', '操作失败！');
            }
            
        }
		
		return params;

    },
    batchSubmit: function(butchUrl) {
        if (this.checkMany()) {
            Ext.Msg.confirm("提 示", "是否确定对多条记录进行操作？", function(btn, text) {
                if (btn == 'yes') {
                     var postParams = this.getSelectedParams();
					//alert(Ext.util.JSON.encode(postParams));
                    this.body.mask('提交数据,请稍候...', 'x-mask-loading');
                    Ext.Ajax.request({
                        url     : butchUrl, //+ '?ids=' + ids,
						params: {name:this.beanName,keysMap:Ext.util.JSON.encode(postParams)},
                        success : function() {
							this.body.unmask();
                            this.refresh();
							 Ext.MessageBox.alert('提 示', '操作成功！');
                        }.createDelegate(this),
                        failure : function(){
                            this.el.unmask();
                            Ext.MessageBox.alert('提 示', '操作失败！');
                        }
                    });
                }
            }.createDelegate(this));
        }
    },
	 test : function() {
		Ext.MessageBox.alert("test fun");
	},
    // 弹出添加对话框，添加一条新记录
    add : function() {
        if (!this.dialog) {
             this.createDialog(this.formNewConfig);
        }
        this.formPanel.getForm().reset();
        this.dialog.show(Ext.get("add"));
    },


    // 弹出修改对话框
    edit : function() {
        if (!this.dialog) {
           this.createDialog(this.formEditConfig);
        }

        if (this.checkOne()) {
            this.dialog.show(Ext.get('edit'));
			var postParam = this.getSelectedParams()[0];
			var jsonPara = Ext.util.JSON.encode(postParam);
			var rand = Math.round(314159267*Math.random());
            this.formPanel.load({
				method:'POST',
				url:this.loadOneData,
				params:{name:this.beanName,rd:rand,keysMap:jsonPara},
				text:'查询数据中...'
			
			});
			//改变Form提交地址	
			this.formPanel.form.url=this.urlUpdate;

        }
    },

    // 删除记录
    del: function() {
        this.batchSubmit(this.urlRemove);
    },
	
    // 创建弹出式对话框
    createDialog : function(formVarConfig) {
		var items = formVarConfig;
		 if( typeof(formVarConfig) != "undefined" && formVarConfig != null){
			items = formVarConfig;
		 }

        Ext.each(items, function(item) {
            Ext.applyIf(item, {
                anchor: '90%'
            });
        });


       var reader = new Ext.data.JsonReader({
								root: 'result',
								totalProperty: 'totalProperty'
								},this.recordMapping/**Ext.data.Record.create(this.recordMapping)*/);

        this.formPanel = new Ext.FormPanel({
			id:'formPanel',
            defaultType: 'textfield',
            labelAlign: 'right',
            labelWidth: 110,
			bodyStyle: 'padding:0 10px 0;',
			width: this.dlgWidth ? this.dlgWidth : 400,
            frame: true,
            autoScroll: true,
            title: '详细信息',
            reader: reader,
            url: this.urlSave,
            items: items,
            buttons: [{
                text: '确  定',
                handler: function() {
					if(this.beanName == ""){
						Ext.MessageBox.alert("错误","beanName必须赋值!");
					}
					//this.formPanel.findById('name').setValue(this.beanName);
					//为hidden类型的form item赋值
					this.setHiddenItemValue(items);
                    if (this.formPanel.getForm().isValid()) {
                        this.formPanel.getForm().submit({
                            waitTitle: "请稍候",
							method:'POST', 
                            waitMsg : '提交数据，请稍候...',
                            success: function() {
                                this.dialog.close();
								this.dialog = null;
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
                    this.dialog.close();
					this.dialog = null;
                }.createDelegate(this)
            }]
        });
		

        this.dialog = new Ext.Window({
            layout: 'fit',
			modal : true,
            width: this.dlgWidth ? this.dlgWidth : 500,
            height: this.dlgHeight ? this.dlgHeight : 600,
            closeAction: 'hide',
            items: [this.formPanel]
        });
    },
	/**
	 *为hidden类型的form item赋值
	 */
   setHiddenItemValue : function(items){
   	//为hidden类型的form item赋值
		if(items[1].useType == 'new'){
			var formItems = items[1].items;
			
		  for(var i = 0, len = formItems.length; i < len; i++){
			  var inItem = formItems[i];
		    if((inItem.xtype == 'textfield' && inItem.hidden && inItem.hideLabel) 
					&& typeof(inItem.value) != "undefined"){
				//alert(inItem.id+"--->"+inItem.value);
				this.formPanel.findById(inItem.id).setValue(inItem.value);
			 }
		  }
		}//end
   
   },

    // 弹出右键菜单
    // 修改，和批量删除的功能
    // 多选的时候，不允许修改就好了
    contextmenu : function(grid, rowIndex, e) {
        e.preventDefault();
        e.stopEvent();

        var updateMenu = new Ext.menu.Item({
            iconCls : 'edit',
            id      : 'updateMenu',
            text    : '修改',
            handler : this.edit.createDelegate(this)
        });
        var removeMenu = new Ext.menu.Item({
            iconCls : 'delete',
            id      : 'removeMenu',
            text    : '删除',
            handler :  this.del.createDelegate(this)
        });

        var selections = this.getSelectionModel().getSelections();

        if (selections.length > 1) {
            updateMenu.disable();
        }

        var menuList = [updateMenu, removeMenu];

        this.grid_menu = new Ext.menu.Menu({
            id    : 'mainMenu',
            items : menuList
        });

        var coords = e.getXY();
        grid.getSelectionModel().selectRow(rowIndex);
        this.grid_menu.showAt([coords[0], coords[1]]);
    },

    // 刷新表格数据
    refresh : function() {
        this.store.reload();
    },
    onDestroy : function(){
        if(this.rendered){
            if (this.grid_menu) {
                this.grid_menu.destory();
            }
            if (this.dialog) {
                this.dialog.destroy();
            }
        }
        Ext.lingo.HutJsonGrid.superclass.onDestroy.call(this);
    }
});

