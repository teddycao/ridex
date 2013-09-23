<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<%@ include file="/commons/meta.jsp" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<head>
<link  rel="stylesheet" type="text/css" href="${ctx}/styles/index.css" />
<script src="${ctx}/extjs-3/ext-basex.js"></script>
<script src="${ctx}/scripts/ux/Ext.form.VTypes.js"></script>
<style type="text/css">

#app-header {
    color: #596F8F;
    font-size: 22px;
    font-weight: 200;
    padding: 8px 15px;
    text-shadow: 0 1px 0 #fff;
}

.float-right {float:right;padding-top:0px;margin-top:0px;font-weight:bold;}    
</style>


<title>运营支撑分析平台</title>
</head>

    <div id="loading-mask" style=""></div>
    <div id="loading">
        <div class="loading-indicator"><img src="${ctx}/images/extanim32.gif" width="32" height="32" style="margin-right:8px;" align="absmiddle"/>正在加载数据...</div>
    </div>


	<div id="ologo"></div>


    <div id="nav_area" class="top-title" bgcolor="#99BBE8">
        <table cellspacing="0" border="0" cellpadding="0" width="100%">
            <tr>
                <td><div style="font-size:18px;font-weight:bold;"><img src="images/logo.gif"></div></td>
                <td align="right" valign="middle" style="color: #FFF;">运营支撑分析平台</td>
            </tr>
        </table>
    </div>
	
 	   <div id="state_area">
	   <div style="float:left;margin:5px;font:normal 12px tahoma, arial, sans-serif, 宋体;">
				Power By:<span style="color:blue">运营支撑分析平台</span> &nbsp;
		</div>
		<div style="float:right;margin:5px;font:normal 12px tahoma, arial, sans-serif, 宋体;">
				版权所有Copyright
				<sup>&copy;</sup>
				<span>inwiss</span>
		</div>
</div>
	


<script language="javascript">	
<!--
Ext.ns("App");

App.createAccordion = function() {
    return {
        id: 'mainAccordion',
        region: 'west',
        title: '功能菜单',
        layout: 'accordion',
		iconCls: 'acc_home',
        width: 200,
        minSize: 175,
        maxSize: 400,
        split: true,
		margins:'0 0 0 0',
        collapsible: true,
		split:true,
		collapsible :true,
		collapseMode: 'mini',
        defaults: {
            lines: true,
            autoScroll: true,
            collapseFirst: true
        },    
		layoutConfig:{
			animate:true,
			hideCollapseTool:false  
		 },items:[]
    };
};

App.addContent = function(locUrl, title, iconCls) {
    var tabs = App.centerTabPanel;
    var tabItem = tabs.getItem(locUrl);
    if (tabItem == null) {
        var tabComp = null;
         if (locUrl == 'user') {
             tabComp = new Ext.lingo.JsonGrid(this.formConfig[locUrl]);
        } else {				 
            tabComp = new Ext.ux.ManagedIframePanel({
                id: 'tab_001'+locUrl,
				name: 'tab_001'+locUrl,
                defaultSrc: locUrl
            });

        } 
        tabComp.setTitle(title, iconCls);
        tabItem = tabs.add(tabComp);
    }
    tabs.activate(tabItem);
};


function treeClick(node,event) {
	var tabs = App.centerTabPanel;
	 if(node.isLeaf()){
		event.stopEvent();
		var n = tabs.getComponent(node.id);
		if (!n) {
           if (tabs.items.length>6)
           {
              Ext.MessageBox.alert('出错!',"您打开的页签超过了限制,请关闭一些页签后再访问");
			 return;
           }
			var n = tabs.add({
				'id' : node.id,
				'title' : node.text,
				closable:true,
				html : '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+node.attributes['location']+'"></iframe>'
				
				});
		  
		}
		tabs.setActiveTab(n);
	 }
}
App.logout = function() {
	 window.navigate(ctx+"/logout.do");
}


App.rebuildMenu = function(info) {
        var mainAccordion = Ext.getCmp('mainAccordion');
        for (var i = 0; i < info.length; i++) {
            var p = new Ext.tree.TreePanel({
				id:'xtree-'+info[i].id,
                title: info[i].name,
                iconCls: info[i].iconCls,
                rootVisible: false,
				containerScroll: true,
				autoShow:false,  
				enableDD:false,
				animate:true,
                loader: new Ext.tree.TreeLoader({
					dataUrl: '${ctx}/s/loadTreeItems.do',
					async: false,//false-同步 true-异步
					requestMethod:'GET',
					baseParams:{id:info[i].id}
				}),

                root: new Ext.tree.AsyncTreeNode({
                    id:info[i].id,
					nodeType: 'async',
					text:'菜单功能项',
                    //children: info[i].children,
					draggable:false
                })
            });

            p.on('complete', function(node,event) {
                 treeLoader.expand(true);
            });

		    p.on('click', function(node,event) {
                if (node.attributes.location != '') {
                    //App.addContent(node.attributes.location, node.attributes.name, node.attributes.iconCls);
					treeClick(node,event);
                }
            });

			p.on('beforeload', function(treeLoader,node) {
               
            });
			var task=new Ext.util.DelayedTask(Ext.emptuFn);
			//task.delay(3000);
			//task.cancel();
            mainAccordion.add(p);
        }
        mainAccordion.doLayout();
};


App.centerTabPanel = new Ext.TabPanel({
    deferredRender: false,
    enableTabScroll: true,
    monitorResize: true,
    activeTab: 0,
	plugins: new Ext.ux.TabCloseMenu(),
    border: false,
    defaults: {
        closable: true,
        border: true,
        viewConfig: {
            forceFit: true
        }
    },
    items: [{
        id:'homePage',
        title: '欢迎您',
        closable: false,
        autoScroll: true,
        iconCls: 'welcome',
        html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="${ctx}/s/fee/main.do"></iframe>'
    }]
});

App.centerTabPanel.on('remove', function(){
    if (this.centerTabPanel.items.getCount() == 0) {
        this.centerTabPanel.hide();
    }
}, App);

App.centerPanel = new Ext.Panel({
    border: false,
    region: 'center',
    layout: 'fit',
    items: App.centerTabPanel
});


App.loadWestAccTreePanel = function(){
        // 检测用户是否已登录
        Ext.Ajax.request({
            url: '${ctx}/s/getUserMenuThree.do',
            success: function(response) {
                var resJson = Ext.decode(response.responseText);
               if (resJson.success) {
                    App.rebuildMenu(resJson.result);
                } else {
                    //App.loginWindow.show();
                }
            },
            failure: function(response) {
                Ext.Msg.alert('错误', '无法访问服务器。');
            }
        });
 };

App.init = function() {
    // 开启提示功能
    Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='under'; //提示的方式，枚举值为  
    // Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    // 左侧功能菜单
    //var accordion = App.createAccordion();
  

    // 登录窗口
   	App.loadWestAccTreePanel();
	var topBar = new Ext.Toolbar(['->',
	<%if(SecurityUtils.getSubject().getPrincipal()!=null){ %>
				{
				iconCls: 'login_user',
                text: ' &nbsp;当前用户：&nbsp; <%=SecurityUtils.getSubject().getPrincipal()%>&nbsp;&nbsp;',
                //scale: 'medium',
                handler: function() {
                   
                },
                menu: {items:['-',{
                    	   id: 'modify',
                    	   text: '用户密码修改',
                    	   iconCls: 'login_user',
                    	   handler:fnQueryHandler
                      }]}
            },<%} %> '-',{
                text: '&nbsp;WebIM&nbsp;',
                iconCls: 'nav_im',
                handler: function() {
                    //webim.show();
                }
            },'-', {
                text: '&nbsp;设 置&nbsp;',
                iconCls: 'cog',
                handler: function() {
                }
            },'-', {
                text: '&nbsp;注 销&nbsp;',
                iconCls: 'user_delete',
                handler: function() {
                    App.logout();
                }
            }]);
    // 布局
    var viewport = new Ext.Viewport({
        layout: 'border',
        items: [{
            region: 'north',
            height: 100,
            contentEl: 'nav_area',
			//bodyStyle: 'background-color:#99BBE8;',
			bodyStyle: 'background-image: url(${ctx}/images/pics/vistaBlue.jpg)',
            bbar: topBar
        },{
            region: 'south',
            height: 30,
            bodyStyle: 'background-color:#D3E1F1;',
            contentEl: 'state_area'
        },App.createAccordion(),App.centerPanel]
    });

    setTimeout(function(){
        Ext.get('loading').remove();
        Ext.get('loading-mask').fadeOut({remove:true});
    }, 500);

    //用户密码修改弹出框
	function fnQueryHandler() {
		var win;
		if (!win) {
        	var urlSave = "${ctx}/s/saveUserPwd.do";
        	var formPanel = new Ext.FormPanel({
            	id: 'userPwdForm',
            	labelWidth: 75,
            	url: urlSave,
            	frame: true,
            	title: '用户密码修改',
            	bodyStyle: 'padding:5px 5px 0',
            	width: 350,
            	defaults: {width: 230},
            	defaultType: 'textfield',
            	items: [{
						//xtype:'label',
                		fieldLabel: '用户ID',
                		name: 'name',
                		allowBlank: false,
						readOnly:true,
						labelStyle: 'font-weight:normal;',
						itemCls: 'float-right',
						text: "<%=SecurityUtils.getSubject().getPrincipal()%>",
						textStyle:'margin-top:-10px;',
                		value: '<%=SecurityUtils.getSubject().getPrincipal()%>'
            		},{
						xtype: 'textfield',
						id: 'currentpwd',
						name: 'currentpwd',
						fieldLabel: '当前密码',
                    	allowBlank: false,
                    	inputType: 'password',
                    	vtype: 'afterValidate',
                    	url: '${ctx}/s/checkCurrentPwd.do?username=<%=SecurityUtils.getSubject().getPrincipal()%>'
                	},{
                    	id: 'pwd',
                		name: 'password',
                    	fieldLabel: '新密码',
                    	allowBlank: false,
                    	inputType: 'password'
                	},{
                    	id: 'confirmPwd',
                		name: 'confirmPassword',
                    	fieldLabel: '确认密码',
                    	allowBlank: false,
                    	inputType: 'password',
                    	vtype: 'repassword',
                    	confirmTo: 'pwd',
                    	confirmToText: '两次输入的密码不一致'
                	}
            	],

            	buttons: [{
                	text: '保存',
                	handler: function() {
						//return alert(formPanel.getForm().isValid());
                    	if (formPanel.getForm().isValid()) {
                        	formPanel.getForm().submit({
                        		method:'POST',
                            	waitTitle: "请稍候",
                            	waitMsg : '提交数据，请稍候...',
                            	success: function() {
                        			Ext.MessageBox.alert('提示','密码修改成功.');
                        			win.close();
									win = null;
                            	},
                            	failure: function() {
                            		Ext.MessageBox.alert('提示','保存失败！');
                            		win.close();
									win = null;
                            	},
                            	scope: this
                        	});
                    	}
                	}
            	},{
                	text: '取消',
                	handler: function() {
                    	win.close();
						win = null;
                	}
            	}]
        	});
        
        	var win = new Ext.Window({
            	id:'mywin',
            	layout: 'fit',
            	plain: true,
            	width: 400,
            	height: 300,
				modal : true,
            	closeAction: 'close',
            	items: [formPanel]
        	});
		}
		win.show();
    }
};

Ext.onReady(App.init, App);
//js对文字进行编码涉及3个函数：escape,encodeURI,encodeURIComponent，相应3个解码函数：unescape,decodeURI,decodeURIComponent
//-->
</SCRIPT>

</body></html>