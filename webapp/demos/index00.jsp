<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/meta.jsp" %>





<SCRIPT LANGUAGE="JavaScript">
<!--
setTimeout(function() {
					Ext.get('loading').remove();
					Ext.get('loading-mask').fadeOut({remove:true});
				}, 400
			  );
//-->
</SCRIPT>

	<head>
		<title>KettleServer@运行监控</title>
<style>
#ologo {background: url();width: 229px;height: 70px;position:absolute;left:0;top:0;}
#north {background: url() 229px top no-repeat;}
#north div strong {position:absolute;right:10px;top:0px;line-height:71px;font-size:18px;font-weight:700;color:#15428B;}
#north .api-title {height: 71px;line-height:71px;}
#north p {margin:0;}

a:link {
	color: #666666;
	font-size: 9pt;
	font-family: "Verdana", "Arial", "Helvetica", "sans-serif";
 }
a:visited {
 color: #666666;
 }
a:hover {
 color:#005FA9;
 text-decoration:underline;
 }
</style>

<style type="text/css">
    .x-panel-mc {
        font-family: Helvetica, Arial, sans-serif !important;
    }
    
    #all-demos {

    }
    #all-demos dd {
        cursor:pointer;
        float:left;
        height:100px;
        margin:5px 5px 5px 10px;
        width:300px;
        zoom:1;
    }
    #all-demos dd img {
        border: 1px solid #ddd;
        float:left;
        height:90px;
        margin:5px 0 0 5px;
        width:120px;
    }

    #all-demos dd div {
        float:left;
        margin-left:10px;
        width:160px;
    }

    #all-demos dd h4 {
        color:#555;
        font-size:11px;
        font-weight:bold;
    }
    #all-demos dd p {
        color:#777;
    }
    #all-demos dd.over {
        background: #F5FDE3 url(shared/extjs/images/sample-over.gif) no-repeat;
    }
    #loading-mask{
        background-color:white;
        height:100%;
        position:absolute;
        left:0;
        top:0;
        width:100%;
        z-index:20000;
    }
    #loading{
        height:auto;
        position:absolute;
        left:45%;
        top:40%;
        padding:2px;
        z-index:20001;
    }
    #loading a {
        color:#225588;
    }
    #loading .loading-indicator{
        background:white;
        color:#444;
        font:bold 13px Helvetica, Arial, sans-serif;
        height:auto;
        margin:0;
        padding:10px;
    }
    #loading-msg {
        font-size: 11pt;
        font-weight: blod;
    }

    #all-demos .x-panel-body {
        background-color:#fff;
        border:1px solid;
        border-color:#fafafa #fafafa #fafafa #fafafa;
    }
    #sample-ct {
        border:1px solid;
        border-color:#dadada #ebebeb #ebebeb #dadada;
        padding:2px;
    }

    #all-demos h2 {
        border-bottom: 2px solid #99bbe8;
        cursor:pointer;
        padding-top:6px;
    }
    #all-demos h2 div {
        background:transparent url(../resources/images/default/grid/group-expand-sprite.gif) no-repeat 3px -47px;
        color:#3764a0;
        font:bold 11px Helvetica, Arial, sans-serif;
        padding:4px 4px 4px 17px;
    }
    #all-demos .collapsed h2 div {
        background-position: 3px 3px;
    }
    #all-demos .collapsed dl {
        display:none;
    }
    .x-window {
        text-align:left;
    }
    #all-demos dd h4 span.new-sample{
        color: red;
    }

    #all-demos dd h4 span.updated-sample{
        color: blue;
    }
    </style>
</head>
	<SCRIPT LANGUAGE="JavaScript">
	function exit(){
     var  flag = confirm("是否退出系统");
if(flag){

window.location.href='/logOff.do';

} else return;
}
	</SCRIPT>
<body onunload="">
		<div id="loading-mask" style=""></div>
<div id="loading">
    <div class="loading-indicator"><img src="${ctx}/extjs-3.2/resources/images/default/extanim32.gif" width="48" height="48" style="margin-right:8px;float:left;vertical-align:top;"/>&nbsp;<span id="loading-msg">加载中...</span></div>
</div>

<div id="ologo"></div>
		
		<div id="north">
			<div class="api-title">
				<strong>KettleConsole</strong>
			</div>
		</div>
<!--<div style="border:solid 1px #c00"></div>-->

<div id="west"></div>				
		
		<div id="south">
			<div style="float:left;margin:5px;font:normal 12px tahoma, arial, sans-serif, 宋体;">
				Power By: <span style="color:blue">KettleConsole</span> &nbsp;
			</div>
			<div style="float:right;margin:5px;font:normal 12px tahoma, arial, sans-serif, 宋体;">
				版权所有Copyright
				<sup>
					&copy;
				</sup>
				<span>KettleConsole</span>
			</div>
		</div>
		
		<div id="center2">
			
		</div>
	
	
	


	<SCRIPT LANGUAGE="JavaScript">	
<!--
function logonOff(){

var  flag = confirm("是否退出系统");
if(flag){

window.location='/logOff.do';

} else return;


}


Ext.ux.TabCloseMenu = function(){
    var tabs, menu, ctxItem;
    this.init = function(tp){
        tabs = tp;
        tabs.on('contextmenu', onContextMenu);
    }

    function onContextMenu(ts, item, e){
        if(!menu){ // create context menu on first right click
            menu = new Ext.menu.Menu([{
                id: tabs.id + '-close',
                text: '关闭当前页签',
                handler : function(){
                    tabs.remove(ctxItem);
                }
            },{
                id: tabs.id + '-close-others',
                text: '关闭其它页签',
                handler : function(){
                    tabs.items.each(function(item){
                        if(item.closable && item != ctxItem){
                            tabs.remove(item);
                        }
                    });
                }
              
            },
				{
                id: tabs.id + '-close-all',
                text: '关闭所有页签',
                handler : function(){
                    tabs.items.each(function(item){
                        if(item.closable){
                            tabs.remove(item);
                        }
                    });
                }
              
            }
			
			]);
        }
        ctxItem = item;
        var items = menu.items;
        items.get(tabs.id + '-close').setDisabled(!item.closable);
        var disableOthers = true;
        tabs.items.each(function(){
            if(this != item && this.closable){
                disableOthers = false;
                return false;
            }
        });
        items.get(tabs.id + '-close-others').setDisabled(disableOthers);
	    items.get(tabs.id + '-close-all').setDisabled(disableOthers);
        menu.showAt(e.getPoint());
    }
};


 var Tree = Ext.tree;

//右边具体功能面板区
var tab = new Ext.TabPanel({
   region:'center',
   enableTabScroll:true,
   activeTab:0,
   plugins: new Ext.ux.TabCloseMenu(),
   items:[{
      id:'homePage',
      title:'首页',
      autoScroll:true,
      html:'<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src=""></iframe>'
   }]
});
		
	/**
在指定树上+节点
**/
function addNodeLeaf(rootNode,id,text,url)
{

  var node=new Ext.tree.TreeNode({
		id:id,
		//icon:'img/im2.gif',
		text:text,
        leaf:true,
        href:url
	});
 rootNode.appendChild(node);

}

function addNode(rootNode,id,text,url)
{

  var node=new Ext.tree.TreeNode({
		id:id,
		//icon:'img/im2.gif',
		text:text,
        href:url
	});
 rootNode.appendChild(node);
 return node;

}


function treeClick(node,e) {
	 if(node.isLeaf()){
		e.stopEvent();
		var n = tab.getComponent(node.id);
		if (!n) {
           if (tab.items.length>6)
           {
              Ext.MessageBox.alert('出错!',"您打开的页签超过了限制,请关闭一些页签后再访问");
			 return;
           }
			var n = tab.add({
				'id' : node.id,
				'title' : node.text,
				closable:true,
				html : '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+node.attributes['href']+'"></iframe>'
				
				});
		  
		}
		tab.setActiveTab(n);
	 }
}



Ext.onReady(function(){

   //layout
   var viewport = new Ext.Viewport({
		layout:'border',
		items:[
			new Ext.BoxComponent({
				region:'north',
				el: 'north',
				height:60,
items:[	new Ext.Button({
            text: '提交',
			id: 'mend'})]
			}),	
				new Ext.BoxComponent({
				region:'south',
				el: 'south',
				height:25
			}),{
			region:'west',
			id:'west-panel',
			split:true,
			width: 200,
			minSize: 175,
			maxSize: 400,
			margins:'0 0 0 0',
			layout:'accordion',
			title:'系统功能',
			collapsible :true,
			layoutConfig:{
				animate:true
				},
		    items: [  
			{title:'系统监控',border:false,html:'<div id="M01" style="overflow:auto;width:100%;height:100%"></div>'},
			{title:'参数设置',border:false,html:'<div id="M02" style="overflow:auto;width:100%;height:100%"></div>'}
		]
 }, 
 tab
 ]
}); 

var M01=new Ext.tree.TreePanel({
renderTo:"M01",
root:ROOT_M01,
animate:true,
enableDD:false,
border:false,
rootVisible:false,
containerScroll: true
});
M01.on('click',treeClick);
//人行监管上报



var M02=new Ext.tree.TreePanel({
renderTo:"M02",
root:ROOT_M02,
animate:true,
enableDD:false,
border:false,
rootVisible:false,
containerScroll: true
});
M02.on('click',treeClick);
});
//Define Tree Roots here... 

var ROOT_M01 = new Ext.tree.AsyncTreeNode({
text: '平台管理',
draggable:false,
id:'ROOT_M01'
});



var ROOT_M02 = new Ext.tree.AsyncTreeNode({
text: '收文文件分发',
draggable:false,
id:'ROOT_M02'

});



Ext.onReady(function(){
// 系统管理
var NODE_M0000000001=addNode(ROOT_M01,'NODE_M0000000001','运行监控');
// 机构维护
var NODE_M0000000002=addNode(NODE_M0000000001,'NODE_M0000000002','Trans监控');
// 机构信息维护
addNodeLeaf(NODE_M0000000002,'LEAF_M0000000003','Trans运行状态监控','${ctx}/kettle/viewTtransStatus/summary');
// 机构级别信息维护
addNodeLeaf(NODE_M0000000002,'LEAF_M0000000004','Trans运行日志查询','list.do?name=orglevel');
// 补录参数维护
var NODE_M0000000013=addNode(NODE_M0000000001,'NODE_M0000000013','Job监控');
// 历史流程管理
addNodeLeaf(NODE_M0000000013,'LEAF_M0000000016','分类维护','${ctx}/cms/catalogTree.jsp');
// 补录日志查询
addNodeLeaf(NODE_M0000000013,'LEAF_M0000000024','Job运行日志查询','${ctx}/kettle/cms/viewList');
// 流程定义
//addNodeLeaf(NODE_M0000000013,'LEAF_M0000000014','流程定义','list.do?name=flow');
// 补录数据项设置



});
	
//-->
</SCRIPT>

</body></html>