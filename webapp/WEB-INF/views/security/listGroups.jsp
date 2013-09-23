<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/meta.jsp" %>
<title>组管理</title>
</head>
<body>
<div id="contentview" align="center">
</div>


<script type="text/javascript">
<!--
var cliwid =document.body.clientWidth ;
 	var tblWidth = 800;
	var panelHight = 565;

	var EdgeGrid = {};

//////////////////////////////////////////////////////////////////////////////////////////////
// The data store for topics
EdgeGrid.TopicStore = function(){
    EdgeGrid.TopicStore.superclass.constructor.call(this, {
        remoteSort: true,

        proxy: new Ext.data.HttpProxy({
            url: '${ctx}/report/security/showGroups'
        }),

        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'total',
            id: 'id'
        }, [
    {name: 'name',mapping: 'name',type: 'string'},
	{name: 'title',mapping: 'title', type: 'string'},
	{name: 'description',mapping: 'description', type: 'string'}
	])
    });

    this.setDefaultSort('name', 'ASC');
};


Ext.extend(EdgeGrid.TopicStore, Ext.data.Store, {
    loadForum : function(forumId){
        this.baseParams = {
            forumId: forumId
        };
        this.load({
            params: {
                start:0,
                limit:30
            }
        });
    }
});


var sm = new Ext.grid.CheckboxSelectionModel({
     singleSelect:false,
     listeners: {
     selectionchange: function(sel){
      var rec = sel.getSelected();
		  topGridSelected(rec);
        }
      }
});

//////////////////////////////////////////////////////////////////////////////////////////////
var updateRunLog = function(rec){
		if(rec){
           //Ext.getCmp('preview').body.update('<b><u>' + rec.get('id') + '</u></b><br /><br />运行日志.');
		   alert(rec.get('id') );
       }

}
//////////////////////////////////////////////////////////////////////////////////////////////
var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),sm,
        {
    		id: 'name',
    		header: "组编号",
    		dataIndex: 'name',
    		align: 'center',
        	width:150
 		},{
           id: 'title',
           header: "组名称",
           dataIndex: 'title',
           align: 'center',
           width:250
        },{
           id: 'description',
           header: "组描述信息",
           dataIndex: 'description',
           align: 'center',
           width:100
        }]);
    cm.defaultSortable = true;
//////////////////////////////////////////////////////////////////////////////////////////////
// create the Grid

    var store = new EdgeGrid.TopicStore();

	var grid = new Ext.grid.GridPanel({
			id:'top-grid',
		    store: store,
			cm:cm,
			sm:sm,
		    stripeRows: false,
			height:panelHight-66,
		    autoHeight: false,
		    autoWidth:true,
		    frame:true,
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


grid.on('cellclick',function(grid, rowIndex, columnIndex, e) {
    var record = grid.getStore().getAt(rowIndex);  
// 返回Record对象 Get the Record
    var fieldName = grid.getColumnModel().getDataIndex(columnIndex); 
	
// 返回字段名称 Get field name
    var data = record.get(fieldName);
	topGridSelected(record);
});


var opteraTrans = function(url,msg,remove){
	if(!sm.getSelected()){ 
		Ext.MessageBox.alert("错误!","请选择一行!");return;
	 }
 	Ext.MessageBox.confirm('提示', msg, function(btn, text){
			if (btn == 'yes'){
				var sels = grid.getSelectionModel().getSelections();
			  for(var i = 0, rec; rec = sels[i]; i++){       
				 Ext.Ajax.request({
					url:url,
					params: { 'id': rec.data.id }, 
					success: function(form,action) {
						grid.store.reload();
					},
					failure: function(form,action) {
						Ext.MessageBox.alert("状态","操作失败!");
					}
				   });
				 if(remove)
				  grid.store.remove(rec);
				}
			 }
	});
}
	    
//////////////////////////////////////////////////////////////////////////////////////////////		    
	var s_pannel = new Ext.Panel({
				region:'center',
                margins:'0 0 0 0',
				frame: false, 
				defaultType: 'textfield',
					tbar: [
					{
						text:" &nbsp;新增",
						scale: 'medium',
						icon: "${ctx}/extjs-3/examples/shared/icons/fam/add.gif",
						cls: "x-btn-text-icon",
                        handler: function(){opteraTrans('${ctx}/kettle/startTrans/')}
					},'-',
                    {
						text:" &nbsp;修改",
						scale: 'medium',
						icon: "${ctx}/extjs-3/examples/shared/icons/fam/cog_edit.png",
						cls: "x-btn-text-icon",
                        handler: function(){opteraTrans('${ctx}/kettle/pauseTrans/')}
					},'-',{
						text:" &nbsp;删除",
						scale: 'medium',
						icon: "${ctx}/extjs-3/examples/shared/icons/fam/delete.gif",
						cls: "x-btn-text-icon",
                        handler: function(){opteraTrans('${ctx}/kettle/stopTrans/','确认删除选中的组?',true)}
					} ,'-',{
						text:"  &nbsp;分配角色",
						scale: 'medium',
						icon: "${ctx}/extjs-3/examples/shared/icons/fam/user.png",
						cls: "x-btn-text-icon",
                        handler: function(){opteraTrans('${ctx}/kettle/removeTrans/')}
					}   
        				  ],
				items: [grid]
		});			
//////////////////////////////////////////////////////////////////////////////////////////////
var tab = new Ext.TabPanel({
	region:'center',
	enableTabScroll:true,
	title:'组信息展示',
   	activeTab:0,
	split:true,
	defaults: {autoScroll:true}
});


//////////////////////////////////////////////////////////////////////////////////////////////
Ext.onReady(function(){
   
    Ext.QuickTips.init();

   var viewport = new Ext.Viewport({
	layout:'border',
	items:[
		{
		region:'north',
		contentEl: 'contentview',
		items:[s_pannel],
		split:true,
		height: panelHight,
		weight:cliwid,
		autoScroll:true,
        collapsible: true,
        title:'组信息展示',
        margins:'0 0 0 0'
		},tab]
	});

	store.load({params:{start:0, limit:30}});
});	

var onFooEndFunc = function(fn) {  

  var delay = 50; // 根据实际情况可调整延时时间  

 var executionTimer;  

 return function() {  

   if (!!executionTimer) {  

        clearTimeout(executionTimer);  

    }  

       //这里延时执行你的函数  

       executionTimer = setTimeout(function() {  

           //alert('123');  

            fn();  

       }, delay);  

    };  

 }; 

//-->
</script>
</body>
</html>