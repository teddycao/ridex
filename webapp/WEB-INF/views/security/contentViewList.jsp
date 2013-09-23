<%@ page contentType="text/html;charset=UTF-8" %>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>

<head>
	<%@ include file="/commons/meta.jsp" %>
		<script src="${ctx}/extjs-3.2/examples/ux/RowEditor.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/extjs-3.2/examples/ux/css/RowEditor.css" />
	<base href="<%=basePath%>">
	<title>ETL运行状态</title>
<style type="text/css">            
.batchup {
    background-image: url(${ctx}/images/disk.png) !important;
}
.findIcon {
    background-image: url(${ctx}/images/query.png) !important;
}
</style> 	
</head>

<body>
<div id="contentview" align="center">
<div id="taskTableGrid"></div>
</div>


<script type="text/javascript">
<!--
var pageWidth =document.body.clientWidth ;
var pageHeight = document.body.clientHeight;
 	var tblWidth = 800;
	var panelHight = 340;
	

var EdgeGrid = {};


//////////////////////////////////////////////////////////////////////////////////////////////
// The data store for topics
 var Content = Ext.data.Record.create(
   [
	{name: 'contentId', mapping: 'contentId',type: 'string'},
    {name: 'docName',mapping: 'docName',type: 'string'}, 
	{name: 'friCatalogId',mapping: 'friCatalogId',type: 'string'},
	{name: 'senCatalogId',mapping: 'senCatalogId',type: 'string'},
	{name: 'thrCatalogId',mapping: 'thrCatalogId',type: 'string'},
	{name: 'docDesc',mapping: 'docDesc',type: 'string'},
	{name: 'docPath',mapping: 'docPath',type: 'string'},
	{name: 'imgId',mapping: 'imgId',type: 'string'},
	{name: 'docDate',mapping: 'docDate',type: 'string'},
	{name: 'docSize',mapping: 'docSize', type: 'string'}
   ]
 );
    

 var proxy = new Ext.data.HttpProxy({
        api: {
			load: {url: '/users/load', method: 'POST'},
            read : {url:'${ctx}/kettle/cms/view', method: 'POST'},
            create :{url: '${ctx}/kettle/cms/create', method: 'POST'},
            update: {url:'${ctx}/kettle/cms/update', method: 'POST'},
            destroy: {url:'${ctx}/kettle/cms/delete', method: 'POST'}
        },
			method: 'POST'
    });
    
    var reader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    }, 
    Content);


 // The new DataWriter component.
    var writer = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });
    
 // Typical Store collecting the Proxy, Reader and Writer together.
    var store = new Ext.data.Store({
        id: 'dataStore',
        proxy: proxy,
		restful: true,
        reader: reader,
		// <-- plug a DataWriter into the store just as you would a Reader
        writer: writer,  
		// <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
        autoSave: false,
		listeners: { 
		
		'remove': function(thiz,record,index){ 
			

		},
		'write': function(store, action, result, res, rs) { 

          //Ext.Msg.alert(" 信息 ","msg");

		}
	  }

		
    });

    //read the data from simple array
    //store.load();
    
    Ext.data.DataProxy.addListener('exception', function(proxy, type, action, options, response) {
		var respText = Ext.util.JSON.decode(response.responseText); 

	Ext.MessageBox.show({
		title:"标题",
		msg:respText.message,
		buttons:{"ok":"确定"},
		fn:function(e){
			if(e == 'ok'){
				//grid.store.reload();
			}},
		width:200,
		icon:Ext.MessageBox.INFO

		});
	
    });

    
    var editor = new Ext.ux.grid.RowEditor({
        saveText: ' 修改 ',
			cancelText:' 取消 ',
			listeners:{
                    beforeedit:function(rowedit,index){
                            // 如果是 update 状态不允许修改 id
                            var rec=store.getAt(index);
                            if(rec.data.cid){
                                    //ideditor.disable();
                            }else{
                                    //ideditor.enable();
                            }
                    }
            }
    });
    



//////////////////////////////////////////////////////////////////////////////////////////////////
var sm = new Ext.grid.CheckboxSelectionModel({
     singleSelect:false,
     listeners: {
     selectionchange: function(sel){
      var rec = sel.getSelected();
		  //topGridSelected(rec);
        }
      }
});


//image path 
var IMG_DOWNLOAD = '${ctx}/images/download-icon.png'; 
//renderer function location.href = './article/'+ m[0].get('Fileconten');
function fileDown(val) {   
	Ext.Ajax.request({
			url: '${ctx}/kettle/cms/downSing',
	  success: function(r, o) {
			//obj = Ext.util.JSON.decode(r.responseText);
			try {
					Ext.destroy(Ext.get('downloadIframe'));
			}catch(e) {}

			Ext.DomHelper.append(document.body, {
					tag: 'iframe',
					id:'downloadIframe',
					frameBorder: 0,
					width: 0,
					height: 0,
					css: 'display:none;visibility:hidden;height:0px;',
					src: '${ctx}/kettle/cms/download?id='+val
				});
	},
	failure: function(r, o){},
	headers: {'my-header': 'csvoutput'},
	params: {id: val}
  });
} 

var cataStore1 = new Ext.data.JsonStore({
             url: '${ctx}/kettle/cms/catalog?level=FRI',
             remoteSort: false,
             autoLoad:false,
             idProperty: 'id',
             root: 'data',
             totalProperty: 'total',
             fields: ['friCatalogId', 'friCatalog']
         });

var cataStore2 = new Ext.data.JsonStore({
             url: '${ctx}/kettle/cms/catalog?level=SEN',
             remoteSort: false,
             autoLoad:false,
             idProperty: 'id',
             root: 'data',
             totalProperty: 'total',
             fields: ['senCatalogId', 'senCatalog']
         });

var cataStore3 = new Ext.data.JsonStore({
             url: '${ctx}/kettle/cms/catalog?level=THR',
             remoteSort: false,
             autoLoad:false,
             idProperty: 'id',
             root: 'data',
             totalProperty: 'total',
             fields: ['thrCatalogId', 'thrCatalog']
         });
//////////////////////////////////////////////////////////////////////////////////////////////
var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm,
	{id: 'contentId', header: "contentId",dataIndex: 'contentId',hidden: true},
	{id: 'docName', header: "文档名称",dataIndex: 'docName',width: 120,editor: {
                xtype: 'textfield',
				readOnly:true,
                allowBlank: false
            }},
	{id: 'friCatalogId', header: "文档大类",dataIndex: 'friCatalogId',width: 100,editor: {
                xtype: 'combo',
				typeAhead: true,
                triggerAction: 'all',
					displayField:'friCatalog',
			valueField:'friCatalogId',
                // transform the data already specified in html
                store: cataStore1,
                //lazyRender: true,
                listClass: 'x-combo-list-small',
				editable: false,
                allowBlank: false
            }},
	{id: 'senCatalogId', header: "文档小类",dataIndex: 'senCatalogId',width: 100,editor: {
                xtype: 'combo',
				typeAhead: true,
                triggerAction: 'all',
				displayField:'senCatalog',
				valueField:'senCatalogId',
                // transform the data already specified in html
                store: cataStore2,
                //lazyRender: true,
                listClass: 'x-combo-list-small',
				editable: false,
                allowBlank: false
            }},
	{id: 'thrCatalogId', header: "文档子类",dataIndex: 'thrCatalogId',width: 100,editor: {
                xtype: 'combo',
				typeAhead: true,
                triggerAction: 'all',
				displayField:'thrCatalog',
				valueField:'thrCatalogId',
                // transform the data already specified in html
                store: cataStore3,
                //lazyRender: true,
                listClass: 'x-combo-list-small',
				editable: false,
                allowBlank: false
            }},
	{id: 'docDesc', header: "文档描述",dataIndex: 'docDesc',width: 150, editor: new Ext.form.TextField({})},
	{id: 'docPath', header: "路径",dataIndex: 'docPath',width: 180},
	{id: 'docSize', header: "文件大小",dataIndex: 'docSize',width: 90},
	{id: 'docDate', header: "文档日期",dataIndex: 'docDate',width: 120},
	{id: 'download', header: "下载文件",dataIndex: 'transName',
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				return "<a href=javascript:fileDown('" + record.data.contentId + "')><img  src='" + IMG_DOWNLOAD + "'></a>"; 
			},width: 120}
	
	]);
    cm.defaultSortable = true;
//////////////////////////////////////////////////////////////////////////////////////////////
// create the Grid


var grid = new Ext.grid.GridPanel({
			id:'top-grid',
		    store: store,
			cm:cm,
			sm:sm,
		    stripeRows: false,
			height:pageHeight-70,
		    //autoHeight: true,
		    autoWidth:true,
		    frame:true,
				 viewConfig:{forcefit:true},
        plugins: [editor],
			loadMask: {msg:'数据加载中...'},
		    border:false,

		    bbar: new Ext.PagingToolbar({
                pageSize: 30,
                store: store,
                displayInfo: true,
                firstText:"首页",
				lastText:"最后一页",
				nextText:"下一页",
				prevText:"上一页",
				refreshText:"刷新",
				displayMsg: '显示第 {0} 条到 {1} 条记录，总共 {2} 条',
				emptyMsg: "无显示数据",
				beforePageText :"当前页",
				afterPageText:"共{0}页"
            })			    
		});	


  

var store1 = new Ext.data.SimpleStore({
        fields: ['dataFieldName', 'displayFieldName'],
        data: [['FLC', 'Carrier'], ['DES', 'Destination'], 
            ['CTY', 'Country'], ['MON', 'Month']],
        autoLoad: false
    });
/**
 var levelstore  = new Ext.data.JsonStore({
    url: '<%=basePath%>/getSysLevel.di',
    root: 'total',
    fields: ['sysLevelID', 'sysLevelName']
}); */

			
// 扩展窗体
ContentWin = function(){
    var curFormWin;
    return {
	 width : 600,
      height : 550,
	  showBatchWin : function() {
            // 显示添加子目录窗口
            var win_url =  "${ctx}/cms/batchUploadView.jsp";
            var window = this.createWin("windirnew", "批量文件归档", win_url, function() {
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
                    maximizable : true,
						 layout:'fit',
						 plain:true,
                    modal : true,
                    html : "<iframe width='100%' height='100%' frameborder='0' src='"
                            + iframePage + "'></iframe>"
                });
                closeFun;
            }
            curFormWin = win;
            return win;
        },
		gridReload : function() {
			grid.store.reload();  
		},
		close : function() {
            if(curFormWin){
                curFormWin.close();
            }
        }
	}
}();

var doneFunction = function(form,action) {
			Ext.MessageBox.alert("状态","删除数据成功!");
			grid.store.reload();
}

var errorFunction = function() {
			Ext.MessageBox.alert("状态","删除数据失败!");
}	

var deleteRemote = function(){

    editor.stopEditing();
	Ext.MessageBox.confirm('提示', '确认删除选中记录?', function(btn, text){
			if (btn == 'yes'){
				var sels = grid.getSelectionModel().getSelections();
			  for(var i = 0, rec; rec = sels[i]; i++){
				
       
				 Ext.Ajax.request({
					url:'${ctx}/kettle/cms/delete',
					params: { 'data': rec.data.contentId }, 
					success: doneFunction,
					failure:errorFunction
				   });
				 
				  store.remove(rec);
				}
			 }
	});
}
/////////////////////////////////////////////////////////////////////////////
var top_tb = new Ext.Toolbar();
 top_tb.add({
		text:'&nbsp;文件批量归档&nbsp;',
		scale: 'medium',
		handler : function() {
            ContentWin.showBatchWin();
         },
		icon: "${ctx}/images/upload-icon24.png"
	}, '&nbsp;',{
        //iconCls: 'icon-update',
		icon: "${ctx}/images/edit-icon24.png",
        text: '&nbsp;&nbsp;&nbsp;批量修改&nbsp; ',
        handler: function(){
            editor.stopEditing();
            //var s = grid.getSelectionModel().getSelections();
			grid.store.save();
			var t=setTimeout("grid.store.reload()",3000)
          
			
        }
    }, '&nbsp;',{
        //iconCls: 'icon-update',
		 icon: "${ctx}/images/delete-icon16.png",
        text: '&nbsp;&nbsp;删除 &nbsp; ',
        handler: deleteRemote
    }
);

//////////////////////////////////////////////////////////////////////////////////////////////		    
	var s_pannel = new Ext.Panel({
				region:'center',
                margins:'0 0 0 0',
				frame: false,
				autoHeight:true,
				defaultType: 'textfield',
				tbar: ['&nbsp;文档名称：',
				{
            xtype: 'textfield',
			id: 'docName',
            name: 'docName',
            width: 120
          },'&nbsp;','文档大类：',
					{
		  id:'friCatalog'
		  ,name:'friCatalog'
         ,fieldLabel:'文档大类'
        ,displayField:'friCatalog'
        ,valueField:'friCatalogId'
		,editable: false
		,width: 115
		,allowBlank:true
		,xtype: 'combo'
        ,store: new Ext.data.JsonStore({
             url: '${ctx}/kettle/cms/catalog?level=FRI',
             remoteSort: false,
             autoLoad:true,
             idProperty: 'id',
             root: 'data',
             totalProperty: 'total',
             fields: ['friCatalogId', 'friCatalog']
         })
        ,triggerAction:'all'
        ,mode:'local'
        ,listeners:{select:{fn:function(combo, value) {
			friCatalogVal = combo.getValue();
           // var comboCity = Ext.getCmp('combo-city-local');
            //comboCity.clearValue();
            //comboCity.store.filter('stateId', combo.getValue());
            }}
        }
 
    },'&nbsp;&nbsp;文档小类：', ' ',{
		 id:'senCatalog'
		 ,name:'senCatalog'
        ,fieldLabel:'文档小类'
        ,displayField:'senCatalog'
        ,valueField:'senCatalogId'
		,width: 115
		,xtype: 'combo'
		,editable: false
		,allowBlank:true
        ,store: new Ext.data.JsonStore({
             url: '${ctx}/kettle/cms/catalog?level=SEN',
             remoteSort: false,
             autoLoad:true,
             idProperty: 'id',
             root: 'data',
             totalProperty: 'total',
             fields: ['senCatalogId', 'senCatalog']
         })
        ,triggerAction:'all'
        ,mode:'local'
        ,listeners:{select:{fn:function(combo, value) {
			senCatalogVal = combo.getValue();
			}}
        }
 
    }, '&nbsp;&nbsp;文档子类：', {
		 id:'thrCatalog'
		 ,name:'thrCatalog'
        ,fieldLabel:'文档子类'
        ,displayField:'thrCatalog'
        ,valueField:'thrCatalogId'
		,width: 115
		,xtype: 'combo'
		,editable: false
		,allowBlank:true
        ,store: new Ext.data.JsonStore({
             url: '${ctx}/kettle/cms/catalog?level=THR',
             remoteSort: false,
             autoLoad:true,
             idProperty: 'id',
             root: 'data',
             totalProperty: 'total',
             fields: ['thrCatalogId', 'thrCatalog']
         })
        ,triggerAction:'all'
        ,mode:'local'
        ,listeners:{select:{fn:function(combo, value) {
			thrCatalogVal = combo.getValue();
			}}
        }
 
    }
	,'&nbsp;文档日期：', {
				    id: 'startTime', 
				    xtype:"datefield",
					fieldLabel:"文档日期",
					
					name:"startTime",
					value:new Date()
					,format:'Y-m-d'
				 }, '--',
							{
				    id: 'endTime', 
				    xtype:"datefield",
					fieldLabel:"文档日期",
					name:"endTime",
					value:new Date()
					,format:'Y-m-d'
				 },
						{
                        	xtype: "tbseparator",
								scale: 'medium'
                    	},
						{
                        text: '&nbsp;查询&nbsp;',
						scale: 'medium',
                        icon: "${ctx}/images/find-icon24.png",
                        cls: "x-btn-text-icon",
                        handler: function(){
                            // 这里是关键，重新载入数据源，并把搜索表单值提交
							if((Ext.get('startTime').dom.value==''&&Ext.get('endTime').dom.value!='')
							|| (Ext.get('startTime').dom.value!=''&&Ext.get('endTime').dom.value=='')){
								Ext.MessageBox.alert('信息','时间必须同时输入起始和结束时间');
								return;
							}
							if(Ext.get('startTime').dom.value!=''&&Ext.get('endTime').dom.value!=''){
								if(Ext.get('startTime').dom.value>Ext.get('endTime').dom.value){
									Ext.MessageBox.alert('信息','起始时间应不能大于结束时间');
									return;
								}
							}
                          store.load({params:{start:0,
                          					limit:30,
                          					docName:encodeURIComponent(Ext.get('docName').dom.value),
											friCatalog:encodeURIComponent(Ext.get('friCatalog').dom.value),
											senCatalog:encodeURIComponent(Ext.get('senCatalog').dom.value),
											thrCatalog:encodeURIComponent(Ext.get('thrCatalog').dom.value),
                          					startDate:Ext.get('startTime').dom.value,
                          					endDate:encodeURIComponent(Ext.get('endTime').dom.value)
                          					
                          					}});   
                        }
                    },'-',{
                        text: '&nbsp;查询全部',
						scale: 'medium',
                        icon: "${ctx}/images/refresh-icon24.png",
                        cls: "x-btn-text-icon",
                        handler: function(){
					
                
                          store.load({params:{start:0,
                          					limit:30                          					
                          					}});   
                        }
                    }  ],
				items: [top_tb,grid]
		});			


//////////////////////////////////////////////////////////////////////////////////////////////
Ext.onReady(function(){
   
    Ext.QuickTips.init();

   var viewport = new Ext.Viewport({
	layout:'border',
	items:[s_pannel]
	});
	
//alert(s_pannel.getHeight());
	store.load({params:{start:0, limit:30}});
});



		function getSelectedItems(){
			   if(sm.getSelected())
			{
 				var records = sm.getSelections();
				 var recordsLen = records.length;
				 var delId = '';
				for(var i=0;i<recordsLen;i++){
					delId += records[i].get('id')+',';
				 }
                var delIdList = delId.substring(0,delId.length-1);

				Ext.MessageBox.confirm('提示', '确认删除任务?'+delIdList, function(btn, text){
			    if (btn == 'yes'){
					Ext.Ajax.request({url:'<%=request.getContextPath()%>/RemoveFailedTasksServlet',success: doneFunction,failure:
					errorFunction,params:{taskID:delIdList}});
			    }
				});
				return ;
			
			} else{
				Ext.MessageBox.alert("错误!","请选择一行!");
			}
		}





//-->
</script>

</body>
</html>
