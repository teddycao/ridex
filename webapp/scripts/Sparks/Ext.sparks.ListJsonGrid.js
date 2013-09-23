/**
 * 2011-9-17
 */
Ext.namespace("Ext.sparks");
/**
 * 只用于数据展示，不具备CUD的功能。
 *
 * @param config    需要的配置{}
 */
Ext.sparks.ListJsonGrid = Ext.extend(Ext.grid.GridPanel, {
    loadMask: true,
    stripeRows: true,

    // 初始化
    initComponent: function() {
        //this.useHistory = this.useHistory !== false;
        Ext.applyIf(this, {
            urlPagedQuery: "pagedQuery.do",
            urlLoadData: "loadData.do",
            urlSave: "save.do",
            urlRemove: "remove.do",
            pageSize: 15
        });

        this.buildColumnModel();
        this.buildRecord();
        this.buildDataStore();
        if (this.createHeader !== false) {
            this.buildToolbar();
        }
        // 设置baseParams
        this.setBaseParams();

        Ext.sparks.ListJsonGrid.superclass.initComponent.call(this);

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
            iconCls  : "refresh",
            text     : this.formConfig[0].fieldLabel,
            tooltip  : "选择搜索的字段",
            menu     : checkItems,
            minWidth : 105
        });
        
        // 输入框
        this.filter = new Ext.form.TextField({
            'name': 'filter'
        });
        
        //this.tbar = new Ext.Toolbar([this.filterButton, this.filter]);

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
                filterValue : this.filter.getValue(),
                filterTxt   : this.filterTxt
            };
        }.createDelegate(this));
    },

    /**
	 *通过条件查询
	 */
	queryGridByParams:function(refParam) {
       this.store.on('beforeload', function(store, options) {
  			Ext.apply(this.store.baseParams, refParam);
        }.createDelegate(this));

		
		this.store.load({
				 params: { start: 0, limit: this.pageSize }
		})
    },
    
    // 选中搜索属性选项时
    onItemCheck: function(item, checked) {
        if(checked) {
            this.filterButton.setText(item.text + ':');
            this.filter.setValue('');
            // this.store.baseParams.filterTxt = item.value;
            // this.store.baseParams.filterValue = '';
        }
    },

    // 监听模糊搜索框里的按键
    onFilterKey: function(field, e) {
        var filterTxt = this.store.baseParams.filterTxt;
        if (typeof filterTxt == 'undefined' || filterTxt == '') {
            Ext.Msg.alert('提示', '请先选择搜索的字段');
            return;
        }
        if(e.getKey() == e.ENTER && field.getValue().length > 0) {
            this.store.baseParams.filterValue = field.getValue();
        } else if (e.getKey() == e.BACKSPACE && field.getValue().length() === 0) {
            this.store.baseParams.filterValue = '';
        } else {
            return;
        }
        // delete this.store.lastOptions.params.meta;
        this.store.reload();
    },

    // 选中搜索属性选项时
    onItemCheck : function(item, checked) {
        if(checked) {
            this.filterButton.setText(item.text + ':');
            this.filterTxt = item.value;
        }
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
        Ext.sparks.ListJsonGrid.superclass.onDestroy.call(this);
    }
});

