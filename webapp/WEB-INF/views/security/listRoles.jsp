<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ include file="/commons/meta.jsp" %>
<script type="text/javascript">Ext.namespace("App");</script>
<script src="${ctx}/scripts/ux/Ext.lingo.JsonGrid.js"></script>
<script src="${ctx}/scripts/ux/Ext.ux.PageSizePlugin.js"></script>
<script src="${ctx}/scripts/ux/Ext.lingo.TreeField.js"></script>
<script src="${ctx}/scripts/ux/Ext.lingo.JsonCheckBoxTree.js"></script>
<title>资源管理</title>
</head>
<body>
<div id="contentview" align="center">
</div>


<script type="text/javascript">
<!--
var roleGrid = null;

var formConfig = [
        {fieldLabel: '角色名称', allowBlank:false , name: 'name',mapping: 'name', readOnly: true,width:180},
        {fieldLabel: '角色标题', allowBlank:false , name: 'title',mapping: 'title',width:180},
        {fieldLabel: '角色描述', allowBlank:false , name: 'description',xtype:'textarea',width:260}
    ];

var formNewConfig = [
        {fieldLabel: '角色名称',allowBlank:false , name: 'name',mapping: 'name',width:180},
        {fieldLabel: '角色标题',allowBlank:false, name: 'title',mapping: 'title',width:180},
        {fieldLabel: '角色描述',allowBlank:false, name: 'description',xtype:'textarea',width:260}
    ];


var roleFormPanel;
App.RoleGrid = Ext.extend(Ext.lingo.JsonGrid, {
    id: 'role',
    urlPagedQuery: "${ctx}/s/showRoles.do",
	urlLoadUser: "${ctx}/s/retrievePerm.do",
    urlLoadData: "${ctx}/s/retrieveRole.do",
    urlSave: "${ctx}/s/saveRole.do",
    urlUpdate: "${ctx}/s/updateRole.do",
	urlRemove: "${ctx}/s/removePermissions.do",
    dlgWidth: 300,
    dlgHeight: 240,
    formConfig: formConfig,
	formNewConfig:formNewConfig,
	formEditConfig:formNewConfig,

	buildToolbar: function() {
        //
        var checkItems = new Array();
        for (var i = 0; i < this.formConfig.length; i++) {
            var meta = this.formConfig[i];
            if (meta.showInGrid == false) {
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

        this.tbar = new Ext.Toolbar([{
            id      : 'add',
            text    : '新增',
            iconCls : 'add',
            tooltip : '新增',
            handler : this.add.createDelegate(this)
        }, {
            id      : 'edit',
            text    : '修改',
            iconCls : 'edit',
            tooltip : '修改',
            handler : this.edit.createDelegate(this)
        }, {
            id      : 'del',
            text    : '删除',
            iconCls : 'delete',
            tooltip : '删除',
            handler : this.del.createDelegate(this)
        }, {
            id      : 'selectResc',
            text    : '配置资源',
            iconCls : 'config',
            handler : this.selectResc.createDelegate(this)
        }, {
            id      : 'selectMenu',
            text    : '配置菜单',
            iconCls : 'cog',
            handler : this.selectMenu.createDelegate(this)
        }, '->', this.filterButton, this.filter]);

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
    renderResource: function(value, p, record) {
        if(record.data['authorized'] == true) {
            return String.format("<b><font color=green>已分配</font></b>");
        } else {
            return String.format("<b><font color=red>未分配</font></b>");
        }
    },
    renderNamePlain: function(value) {
        return String.format('{0}', value);
    },
		   // 创建弹出式对话框
    createDialog : function(formVarConfig) {
		var items = this.formConfig;
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
								successProperty:'success',
								totalProperty: 'totalProperty'
								},this.formConfig);

        this.formPanel = new Ext.FormPanel({
			id:'formPanel',
            defaultType: 'textfield',
            labelAlign: 'right',
            labelWidth: 70,
            frame: true,
            autoScroll: true,
            title: '详细信息',
            reader: reader,
            url: this.urlSave,
            items: items,
            buttons: [{
                text: '确定',
                handler: function() {
                    if (this.formPanel.getForm().isValid()) {
                        this.formPanel.getForm().submit({
                            waitTitle: "请稍候",
                            waitMsg : '提交数据，请稍候...',
                            success: function() {
                                this.dialog.close();
								this.dialog = null;
                                this.refresh();
                            },
                            failure: function() {
                            },
                            scope: this
                        });
                    }
                }.createDelegate(this)
            },{
                text: '取消',
                handler: function() {
                    this.dialog.close();
					this.dialog = null;
                }.createDelegate(this)
            }]
        });
		roleFormPanel =  this.formPanel;
        this.dialog = new Ext.Window({
            layout: 'fit',
            width: this.dlgWidth ? this.dlgWidth : 400,
            height: this.dlgHeight ? this.dlgHeight : 300,
            closeAction: 'hide',
            items: [this.formPanel]
        });
    },
		    // 弹出添加对话框，添加一条新记录
    add : function() {
        if (!this.dialog) {
            this.createDialog(formNewConfig);
        }
        this.formPanel.getForm().reset();
        this.dialog.show(Ext.get("add"));
    },
	// 弹出修改对话框
    edit : function() {
        if (!this.dialog) {
            this.createDialog();
        }

        if (this.checkOne()) {
            this.dialog.show(Ext.get('edit'));
			//this.formEditPanel.load({params:{username:userName,rd:rand}});
			//form1.form.load( {
			var sels = this.getSelectionModel().getSelections();
            this.formPanel.form.load({
                method: 'GET',
				url: this.urlLoadData + "?name=" + sels[0].data.name,
				waitMsg : '正在载入数据...',
				success : function(form,action) {
					 //Ext.MessageBox.alert('提示', '载入成功！');
                },failure : function(form,action) {

					 Ext.getCmp("formPanel").getForm().setValues(action.reader.jsonData.result);
					 //Ext.MessageBox.alert('提示', '载入失败!');
                }
            });
			this.formPanel.form.url=this.urlUpdate;
        }
    },
    selectResc: function() {
        if (this.getSelectionModel().getSelections().length <= 0){
            Ext.MessageBox.alert('提示', '请选择需要配置的角色！');
            return;
        }
        if (this.getSelectionModel().getSelections().length > 1){
            Ext.MessageBox.alert('提示', '不能选择多行！');
            return;
        }

        if (!this.selectRescWin) {
            var rec = Ext.data.Record.create([
                {name: "id",         mapping: "id",         type: "int"},
                {name: "resType",    mapping: "resType",    type: "string"},
                {name: "name",       mapping: "name",       type: "string"},
                {name: "resString",  mapping: "resString",  type: "string"},
                {name: "descn",      mapping: "descn",      type: "string"},
                {name: "authorized", mapping: "authorized", type: "boolean"}
            ]);
            var ds = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:'./security/role!getRescs.do'}),
                reader: new Ext.data.JsonReader({
                    root: '',
                    totalProperty: 'totalCount'
                }, rec),
                remoteSort: false
            });
            var cm = new Ext.grid.ColumnModel([{
                // 设置了id值，我们就可以应用自定义样式 (比如 .x-grid-col-topic b { color:#333 })
                id        : 'id',
                header    : "编号",
                dataIndex : "id",
                width     : 80,
                sortable  : true,
                renderer  : this.renderNamePlain,
                css       : 'white-space:normal;'
            }, {
                id        : 'name',
                header    : "资源名称",
                dataIndex : "name",
                sortable  : true,
                width     : 150 ,
                css       : 'white-space:normal;'
            }, {
                id        : 'resType',
                header    : "资源类型",
                dataIndex : "resType",
                sortable  : true,
                width     : 80
            }, {
                id        : 'resString',
                header    : "资源地址",
                dataIndex : "resString",
                sortable  : true,
                width     : 150
            }, {
                id        : 'descn',
                header    : "资源描述",
                dataIndex : "descn",
                sortable  : true,
                width     : 80
            }, {
                id        : 'authorized',
                header    : "是否授权",
                dataIndex : "authorized",
                sortable  : true,
                width     : 80,
                renderer  : this.renderResource
            }]);
            var roleGrid = this;
            var grid = new Ext.grid.GridPanel( {
                ds: ds,
                cm: cm,
                selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
                enableColLock:false,
                loadMask: false,
                viewConfig: {
                    forceFit: true
                },
                bbar: new Ext.Toolbar([{
                    pressed: true,
                    enableToggle:true,
                    text: ' 授 权 ',
                    toggleHandler: function(){
                        //授权事件
                        var mResc = grid.getSelections();
                        var mRole = roleGrid.getSelections();
                        if (mResc.length <= 0) {
                            Ext.MessageBox.alert('提示', '请选择至少一个资源操作！');
                            return;
                        }else if(mRole.length == 1) {
                            roleId = mRole[0].get('id');
                            rescId = mResc[0].get('id');
                            Ext.Ajax.request({
                                url: './security/role!authResc.do',
                                success: function() {
                                    Ext.MessageBox.alert('提示', '授权成功！');
                                    ds.reload();
                                },
                                params: 'auth=true&roleId=' + roleId + '&rescId=' + rescId
                            });
                        }else{
                            for(var i = 0, len = mRole.length; i < len; i++){
                                roleId = mRole[0].get('id');
                                rescId = mResc[i].get('id');
                                Ext.Ajax.request({
                                    url: './security/role!authResc.do',
                                    success: function() {
                                        Ext.MessageBox.alert('提示', '授权成功！');
                                        ds.reload();
                                    },
                                    params: 'auth=true&roleId=' + userId + '&rescId=' + roleId
                                });
                            }
                        }
                    }
                }, '-', {
                    pressed: true,
                    enableToggle:true,
                    text: ' 取消授权 ',
                    toggleHandler: function(){
                        //授权事件
                        var mResc = grid.getSelections();
                        var mRole = roleGrid.getSelections();
                        if(mResc.length<=0){
                            Ext.MessageBox.alert('提示', '请选择至少一个资源操作！');
                            return;
                        }else if(mRole.length==1){
                            roleId = mRole[0].get('id');
                            rescId = mResc[0].get('id');
                            Ext.Ajax.request({
                                url: './security/role!authResc.do',
                                success: function() {
                                    Ext.MessageBox.alert('提示', '取消授权成功！');
                                    ds.reload();
                                },
                                params: 'auth=false&roleId=' + roleId + '&rescId=' + rescId
                            });
                        }else{
                            for(var i = 0, len = mResc.length; i < len; i++){
                                roleId = mRole[0].get('id');
                                rescId = mResc[i].get('id');
                                Ext.Ajax.request({
                                    url: './security/role!authResc.do',
                                    success: function() {
                                        Ext.MessageBox.alert('提示', '取消授权成功！');
                                        ds.reload();
                                    },
                                    params: 'auth=false&roleId=' + roleId + '&rescId=' + rescId
                                });
                            }
                        }
                    }
                }])
            });
            this.rescGrid = grid;
            this.selectRescWin = new Ext.Window({
                layout: 'fit',
                height: 300,
                width: 530,
                closeAction: 'hide',
                items: [grid]
            });
        }
        this.selectRescWin.show();
        this.rescGrid.getStore().baseParams.roleId = this.getSelectionModel().getSelections()[0].get("id");
        this.rescGrid.getStore().reload();
    },
    selectMenu: function() {
        if (this.getSelectionModel().getSelections().length <= 0){
            Ext.MessageBox.alert('提示', '请选择需要配置的角色！');
            return;
        }
        if (this.getSelectionModel().getSelections().length > 1){
            Ext.MessageBox.alert('提示', '不能选择多行！');
            return;
        }

        var roleId = this.getSelectionModel().getSelections()[0].get("name");
        if (!this.selectMenuWin) {
            var menuTree = new Ext.tree.TreePanel({
                autoScroll: true,
                animate : true,
                loader  : new Ext.tree.CustomUITreeLoader({
                    dataUrl : '${ctx}/s/getRoleMenus.do',
                    baseAttr : {
                        uiProvider : Ext.tree.CheckboxNodeUI
                    }
                }),
                enableDD        : false,
                containerScroll : true,
                rootUIProvider  : Ext.tree.CheckboxNodeUI,
                selModel        : new Ext.tree.CheckNodeMultiSelectionModel(),
                rootVisible     : false,
                bbar: new Ext.Toolbar([{
                    text: ' 保存 ',
                    handler: function() {
                        Ext.Ajax.request({
                            url: '${ctx}/s/asignMenuRole.do',
                            success: function() {
                                menuTree.getLoader().load(menuTree.getRootNode());
                            },
                            failure: function() {
                                //
                            },
                            params: 'ids=' + menuTree.getChecked().join(",") + "&roleId=" + roleId
                        });
                    }
                }])
            });
            menuTree.getLoader().on('load', function(o, node) {
                if (node.isRoot) {
                    menuTree.expandAll();
                }
            });
            // 设置根节点
            var root = new Ext.tree.AsyncTreeNode({
                text       : 'root',
                draggable  : false//,
                //uiProvider : Ext.tree.CheckboxNodeUI
            });
            menuTree.setRootNode(root);

            this.menuTree = menuTree;
            this.selectMenuWin = new Ext.Window({
                layout: 'fit',
                height: 590,
                width: 450,
                closeAction: 'hide',
                items: [menuTree]
            });
            this.menuTree.getLoader().baseParams.roleId = roleId;
        } else {
            this.menuTree.getLoader().baseParams.roleId = roleId;
            this.menuTree.getLoader().load(this.menuTree.getRootNode());
        }
        this.selectMenuWin.show();

    }
});




Ext.onReady(function(){
   
    Ext.QuickTips.init();
    roleGrid = new App.RoleGrid();

     var viewport = new Ext.Viewport({
                layout:'fit',
                anchorSize: {width:800, height:600},
					items:[roleGrid]
            });




	//store.load({params:{start:0, limit:30}});
});	

//-->
</script>
</body>
</html>