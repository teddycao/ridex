<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ include file="/commons/meta.jsp" %>
<script type="text/javascript">Ext.namespace("App");</script>
<script src="${ctx}/scripts/ux/Ext.ux.PageSizePlugin.js"></script>
<script src="${ctx}/scripts/ux/Ext.lingo.TreeField.js"></script>

<title>用户管理</title>
</head>
<body>
<div id="contentview" align="center">
</div>


<script type="text/javascript">
<!--


var cliwid =document.body.clientWidth ;
var tblWidth = 800;
var panelHight = 775;
var EdgeGrid = {};
var  userGrid;
	

var easyUTF8 = function(gbk){  
    if(!gbk){return '';}  
    var utf8 = [];  
    for(var i=0;i<gbk.length;i++){  
        var s_str = gbk.charAt(i);  
        if(!(/^%u/i.test(escape(s_str)))){utf8.push(s_str);continue;}  
        var s_char = gbk.charCodeAt(i);  
        var b_char = s_char.toString(2).split('');  
        var c_char = (b_char.length==15)?[0].concat(b_char):b_char;  
        var a_b =[];  
        a_b[0] = '1110'+c_char.splice(0,4).join('');  
        a_b[1] = '10'+c_char.splice(0,6).join('');  
        a_b[2] = '10'+c_char.splice(0,6).join('');  
        for(var n=0;n<a_b.length;n++){  
            utf8.push('%'+parseInt(a_b[n],2).toString(16).toUpperCase());  
        }  
    }  
    return utf8.join('');  
}; 

/**
 * 拥有CRUD功能的表格.
 *
 * @param config    需要的配置{}
 */
App.UserGrid = Ext.extend(Ext.grid.GridPanel, {
    loadMask: true,
    stripeRows: true,


    // 初始化
    initComponent: function() {
        //this.useHistory = this.useHistory !== false;
        Ext.applyIf(this, {
          loadMask: {msg:'数据加载中...'},
	id: 'user',
    urlPagedQuery: "${ctx}/s/showUsers.do",
	urlLoadUser: "${ctx}/s/retrieveUser.do",
    urlLoadData: "${ctx}/s/showUsers.do",
    urlSave: "${ctx}/s/saveUsers.do",
    urlRemove: "${ctx}/s/removeUser.do",
    dlgWidth: 300,
    dlgHeight: 300,
    formConfig: [
          {fieldLabel: '用户ID', name: 'name',allowBlank: false},
		    {fieldLabel: '姓名', name: 'fullName',xtype: 'textfield',allowBlank: false },
          {fieldLabel: '密码', name: 'password', inputType: 'password',hideGrid:true},
		  {fieldLabel: '重复密码', name: 'confirmPassword', inputType: 'password',hideGrid:true},
          {fieldLabel: '部门', name: 'dept', mapping: 'name', xtype: 'textfield'},
          {fieldLabel: '电子邮件', name: 'email',allowBlank: false},
          {fieldLabel: '电话号码', name: 'phoneNumber'}
		],  
	formEditConfig: [
          {fieldLabel: '用户名', name: 'name',allowBlank: false},
			{fieldLabel: '姓名', name: 'fullName',xtype: 'textfield',allowBlank: false },
          {fieldLabel: '部门', name: 'deptName', mapping: 'name', xtype: 'textfield'},
          {fieldLabel: '电子邮件', name: 'email',allowBlank: false},
          {fieldLabel: '电话号码', name: 'phoneNumber'}
		],
		pageSize: 20

        });

        this.buildColumnModel();
        this.buildRecord();
        this.buildDataStore();
        if (this.createHeader !== false) {
            this.buildToolbar();
        }
        // 设置baseParams
        this.setBaseParams();

        App.UserGrid.superclass.initComponent.call(this);

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
                renderer: col.renderer
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
        for (var i = 0; i < this.formEditConfig.length; i++) {
            var meta = this.formEditConfig[i];
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

        this.tbar = new Ext.Toolbar([{
            id      : 'add',
            text    : '新增',
            iconCls : 'add',
            tooltip : '新增',
            handler : function(){
				var url = "${ctx}/s/viewUser.do"
				ContentWin.showModelWin("newUser","创建新用户",url,300,400);
				
				}
        }, {
            id      : 'edit',
            text    : '修改',
            iconCls : 'edit',
            tooltip : '修改',
            handler :  function(){
				
		 if (userGrid.checkOne()) {
			var sels = userGrid.getSelectionModel().getSelections();
			var userName = sels[0].data.name;
			var rand = Math.round(314159267*Math.random());
			var wurl = "${ctx}/s/viewUser.do?username="+encodeURI(userName)+"&rdm="+rand;

			 ContentWin.showModelWin("newUser","修改用户"+userName+"信息",wurl,300,400);
				
				}
			}
        }, {
            id      : 'del',
            text    : '删除',
            iconCls : 'delete',
            tooltip : '删除',
            handler : this.del.createDelegate(this)
        },{
            id      : 'selectRole',
            text    : '选择角色',
            iconCls : 'student',
            handler : this.selectRole.createDelegate(this)
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

    // 设置baseParams
    setBaseParams : function() {
        // 读取数据
/*
        this.store.on('beforeload', function() {
            this.store.baseParams = {
                filterValue : this.filter.getValue(),
                filterTxt   : this.filterTxt
            };
        }.createDelegate(this));
*/
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

    batchSubmit: function(url) {
        if (this.checkMany()) {
            Ext.Msg.confirm("提示", "是否确定？", function(btn, text) {
                if (btn == 'yes') {
                    var selections = this.getSelectionModel().getSelections();
                    var ids = new Array();
                    for(var i = 0, len = selections.length; i < len; i++){
                        try {
                            // 如果选中的record没有在这一页显示，remove就会出问题
                            selections[i].get("name");
                            ids[i] = selections[i].get("name");
                            //this.store.remove(selections[i]);//从表格中删除
                        } catch (e) {
                        }
                        //if (this.useHistory) {
                        //    this.grid.selModel.Set.clear();
                        //}
                    }

                    this.body.mask('提交数据，请稍候...', 'x-mask-loading');
                    Ext.Ajax.request({
                        url     : url, //+ '?ids=' + ids,
                        params: {ids:ids.toString()},
                        success : function() {
                            this.body.unmask();
                            Ext.MessageBox.alert('提示', '操作成功！');
                            this.refresh();
                        }.createDelegate(this),
                        failure : function(){
                            //this.el.unmask();
                            Ext.MessageBox.alert('提示', '操作失败！');
                        }
                    });
                }
            }.createDelegate(this));
        }
    },

    
    // 删除记录
    del: function() {
        this.batchSubmit(this.urlRemove);
    },
	
    // 创建弹出式对话框
    createDialog : function() {
        var items = this.formConfig;
        Ext.each(items, function(item) {
            Ext.applyIf(item, {
                anchor: '90%'
            });
        });

        var reader = new Ext.data.JsonReader({}, this.formConfig);
        this.formPanel = new Ext.form.FormPanel({
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
                }.createDelegate(this)
            }]
        });
        this.dialog = new Ext.Window({
            layout: 'fit',
            width: this.dlgWidth ? this.dlgWidth : 400,
            height: this.dlgHeight ? this.dlgHeight : 300,
				modal : true,
            closeAction: 'close',
            items: [this.formPanel]
        });
    },

    // 选中搜索属性选项时
    onItemCheck : function(item, checked) {
        if(checked) {
            this.filterButton.setText(item.text + ':');
            this.filterTxt = item.value;
        }
    },
    selectRole: function() {
		this.selectRoleWin = null;
		 var selections = this.getSelectionModel().getSelections();
        if (selections.length <= 0){
            Ext.MessageBox.alert('提示', '请选择需要授权的用户！');
            return;
        }
        if (selections.length > 1){
            Ext.MessageBox.alert('提示', '不能选择多行！');
            return;
        }
		var userName = selections[0].get('name');
        if (this.selectRoleWin == null) {
            // 建一个角色数据映射数组
            var recordTypeRole = Ext.data.Record.create([
                {name: "name", mapping:"name", type: "string"},
                {name: "title", mapping:"title", type: "string"},
                {name: "description", mapping:"description", type: "string"},
                {name: "authorized", mapping:"authorized", type: "boolean"}
            ]);
            //设置数据仓库，使用DWRProxy，ListRangeReader，recordType
			var rand = Math.round(314159267*Math.random());
            var dsRole = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({
					url:'${ctx}/s/getUserAllRoles.do',
					method: 'POST'
				}),
                reader: new Ext.data.JsonReader({
                    root: 'result',
                    totalProperty: 'totalCount'
                }, recordTypeRole),
				baseParams:{userName:userName,rand:rand},
                // 远端排序开关
                remoteSort: false
            });

			var gridSM = new Ext.grid.CheckboxSelectionModel({
						 singleSelect:false,
						 listeners: {
						 selectionchange: function(sel){
							 var rec = sel.getSelected();
									//topGridSelected(rec);
								}
						  }
					});
            //创建Role表格头格式
            var cmRole = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),gridSM,{
                // 设置了id值，我们就可以应用自定义样式 (比如 .x-grid-col-topic b { color:#333 })
                id: 'name',
                header: "角色名称",
                dataIndex: "name",
                sortable: true,
                css: 'white-space:normal;'
            },{
                id: 'title',
                header: "角色标题",
                dataIndex: "title",
                sortable: true,
                css: 'white-space:normal;'
            },{
                id: 'description',
                header: "角色描述",
                dataIndex: "description",
                sortable: true,
                css: 'white-space:normal;'
            },{
                id: 'authorized',
                header: "是否授权",
                dataIndex: "authorized",
                sortable: true,
				renderer:function(value, p, record,rowIndex){

				if(record.data['authorized']==true){
					grid.getSelectionModel().selectRow(rowIndex,true);
					return String.format("<b><font color=green>已分配</font></b>");
				}else{
					return String.format("<b><font color=red>未分配</font></b>");
			    }
    }
                //renderer:this.renderAuthorized
            }]);

            var userGrid = this;
            var grid = new Ext.grid.GridPanel( {
				id: 'roleGrid',
                ds: dsRole,
                cm: cmRole,
                //selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
				selModel:gridSM,
                enableColLock:false,
                loadMask: false,
                viewConfig: {
                    forceFit: true
                },
                bbar: new Ext.Toolbar([{
                    pressed: true,
					waitMsg : '正在授权...',
                    enableToggle:true,
                    text: ' 授权角色 ',
                    toggleHandler: function(){
                        //授权事件
                        var mRole = grid.getSelectionModel().getSelections();
                        var mUser = userGrid.getSelectionModel().getSelections();
						//console.log('rowselect',mRole);
                        if (mRole.length <= 0) {
                            Ext.MessageBox.alert('提示', '请选择至少一个角色操作！');
                            return;
                        }else if(mRole.length == 1) {
                            userId = mUser[0].get('name');
                            roleId = mRole[0].get('name');
                            Ext.Ajax.request({
                                url: '${ctx}/s/authRoleToUser.do',
								waitMsg : '正在授权...',
                                success: function() {
                                    Ext.MessageBox.alert('提示', '授权成功！');
                                    dsRole.reload();
                                },
                                params: 'auth=true&userId=' + userId + '&roleId=' + roleId
                            });
                        }else{
                            for(var i = 0, len = mRole.length; i < len; i++){
                                userId=mUser[0].get('name');
                                roleId=mRole[i].get('name');
                                Ext.Ajax.request({
                                    url: '${ctx}/s/authRoleToUser.do',
	                                    success: function() {
                                        Ext.MessageBox.alert('提示', '授权成功！');
                                        dsRole.reload();
                                    },
                                    params: 'auth=true&userId=' + userId + '&roleId=' + roleId
                                });
                            }
                        }
						
                    }.createDelegate(this)
                }, '-', {
                    pressed: true,
                    enableToggle:true,
                    text: '取消角色授权',
                    toggleHandler: function(){
                        //授权事件
						var mRole = grid.getSelectionModel().getSelections();
                        var mUser = userGrid.getSelectionModel().getSelections();
                        if(mRole.length<=0){
                            Ext.MessageBox.alert('提示', '请选择至少一个角色操作！');
                            return;
                        }else if(mRole.length == 1){
                            userId = mUser[0].get('name');
                            roleId = mRole[0].get('name');
                            Ext.Ajax.request({
                                url: '${ctx}/s/authRoleToUser.do',
                                success: function() {
                                    Ext.MessageBox.alert('提示', '取消授权成功！');
                                    dsRole.reload();
                                },
                                params: 'auth=false&userId=' + userId + '&roleId=' + roleId
                            });
                        }else{
                            for(var i = 0, len = mRole.length; i < len; i++){
                                userId = mUser[0].get('name');
                                roleId = mRole[i].get('name');
                                Ext.Ajax.request({
                                    url: '${ctx}/s/authRoleToUser.do',
                                    success: function() {
                                        Ext.MessageBox.alert('提示', '取消授权成功！');
                                        dsRole.reload();
                                    },
                                    params: 'auth=false&userId=' + userId + '&roleId=' + roleId
                                });
                            }
                        }
						
                    }.createDelegate(this)
                },'-',{
                text: '取消',
                handler: function() {
                    this.selectRoleWin.close();
					this.selectRoleWin = null;
                }.createDelegate(this)
            }])
            });
            this.roleGrid = grid;
			
            this.selectRoleWin = new Ext.Window({
				id: 'selectRoleWin',
                layout: 'fit',
                height: 400,
                width: 500,
                closeAction: 'close',
                items: [grid],
				listeners:{ 
					"show":function(){ 
						var rand = Math.round(314159267*Math.random());
						//Ext.getCmp('roleGrid').getStore().baseParams.rand=rand;
						//Ext.getCmp('roleGrid').store.reload();
					},
					"close":function(win){ 
						this.selectRoleWin=null;
					}
				}
            });
        }
		if (!this.selectRoleWin.isVisible()){
			this.selectRoleWin.show(this);
			//this.roleGrid.getStore().baseParams.userName = this.getSelectionModel().getSelections()[0].get("name");
			this.roleGrid.getStore().reload();
		}
    },
    renderAuthorized: function(value, p, record,rowIndex){

       if(record.data['authorized']==true){
            return String.format("<b><font color=green>已分配</font></b>");
        }else{
            return String.format("<b><font color=red>未分配</font></b>");
        }
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
            handler : function(){
				
		 if (userGrid.checkOne()) {
			var sels = userGrid.getSelectionModel().getSelections();
			var userName = sels[0].data.name;
			var rand = Math.round(314159267*Math.random());
			var wurl = encodeURI("${ctx}/s/viewUser.do?username="+userName+"&rdm="+rand);

			 ContentWin.showModelWin("newUser","修改用户"+userName+"信息",wurl,300,400);
				
				}
			}
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
        App.UserGrid.superclass.onDestroy.call(this);
    }
});


// 授权扩展窗体
ContentWin = function(){
	//meta = meta || {};
	var width = 300;
	var height = 400;
    var curFormWin;
    return {
	  width : width,
      height : height,
	  showModelWin : function(winId,winTitle,win_url,width,height) {
            // 显示添加子目录窗口
			this.width=width;
			this.height=height;
           //var win_url =  "${ctx}/s/viewUser.do?username=admin";
            var window = this.createWin(winId, winTitle, win_url, function() {
                grid.reload();
            });
            window.show();
        },
	
		createWin : function(winId, winTitle, iframePage, closeFun) {
            // 供各类型窗口创建时调用
            var win = Ext.getCmp(winId);
            if (!win) {
                win = new Ext.Window({
                    id : winId,
                    title :  winTitle,
                    width : this.width,
                    height : this.height,
                    maximizable : false,
					layout:'fit',
					plain:true,
                    modal : true,
                    html : "<iframe width='100%' height='100%' id='winform' frameborder='0' src='"
                            + iframePage + "'></iframe>"
									  
                });
                closeFun;
            }
            curFormWin = win;
            return win;
        },
		gridReload : function() {
			//grid.store.reload();  
			userGrid.refresh();
		},
		close : function() {
            if(curFormWin){
                curFormWin.close();
            }
        }
	}
}();

//////////////////////////////////////////////////////////////////////////////////////////////
Ext.onReady(function(){
   
    Ext.QuickTips.init();
    userGrid = new App.UserGrid();


     var viewport = new Ext.Viewport({
                layout:'fit',
                anchorSize: {width:800, height:600},
					items:[userGrid]
            });




	//store.load({params:{start:0, limit:30}});
});	

 

//-->
</script>
</body>
</html>