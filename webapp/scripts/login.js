/**
 * login.js Power by YUI-EXT and JSON.
 * 
 * @author seraph
 * @email seraph115@gmail.com
 */
 
var Login = { 
	author: "Raidery",
	version: "0.1.0"
};

var loginPanel;

Ext.QuickTips.init();

Ext.onReady(function(){
	
		var userNames = [];
	    var store = new Ext.data.Store({
	        proxy: new Ext.data.MemoryProxy(userNames),
	        reader: new Ext.data.ArrayReader({}, [{name: 'userName'}])
	    });
	    store.load();

		var loginFormPanel = new Ext.FormPanel({
	        id: 'login-form',
	        renderTo: Ext.getBody(),
	        labelWidth: 55,
	        frame: false,
	        bodyStyle:'background-color: #DFE8F6; padding-top: 25px; padding-left: 20px; border: 0px solid;',
	        defaults: {
	        	width: 230,
	        	anchor: '88%',
	        	allowBlank: false,
            	selectOnFocus: true,
	        	msgTarget: 'side'
	        },
	        defaultType: 'textfield',
	        method: 'POST',
			bodyBorder: false,
			border: false,
	        items: [
	        	{   xtype: 'combo',
	        		store: store,
					id: 'j_username',
	                name: 'j_username',
	                fieldLabel: '用户名',
	    	        displayField: 'userName',
	    	        valueField: 'userName',
	    	        typeAhead: true,
	    	        mode: 'local',
	    	        triggerAction: 'all',
	    	        selectOnFocus: true,
	    	        allowBlank: false,
	                blankText: '请输入用户名'
	            },{
					id: 'j_password',
	                name: 'j_password',
	                fieldLabel: '密&amp;nbsp;&amp;nbsp;&amp;nbsp;码',
	                inputType: 'password',
	                blankText: '请输入密码'
	            },{
	                xtype: 'checkboxgroup',
	                fieldLabel: '记住我',
	                height: 20,
	                allowBlank: true,
	                items: [{
	                    boxLabel: '&amp;nbsp;&amp;nbsp;&lt;img style="height: 10px;" src="../images/platform/icon/question_small_no_border.png" ext:qtip="勾选后，5日内无需登录" /&gt;',
	                    itemCls : 'required',
	                    id: '_spring_security_remember_me',
	                    name: '_spring_security_remember_me',
	                    inputValue: 'true'
	                }]
	            }
	        ],
	        buttons: [{
	                    text:'登录',
	                    handler: function(){
	                    	if(loginFormPanel.getForm().isValid()){
			                    var sb = Ext.getCmp('form-statusbar');
			                    sb.showBusy('登录中...');
			                    // loginFormPanel.getEl().mask();
			                    
			                    var rememberMe = null;
			                    if($('_spring_security_remember_me').checked) {
			                    	rememberMe = $('_spring_security_remember_me').value;
			                    }

			                    Ext.Ajax.request({
			             		   url: 'j_acegi_security_check',
			             		   success: function(response) {
			             		   		var messager = response.responseText.evalJSON();
			             		   		if(messager.success) {
						                    sb.setStatus({
						                    	text: '登录成功!', 
						                    	iconCls: '',
						                    	clear: true
						                    });
						                    // loginFormPanel.getEl().unmask();
						                    location.href = messager.contents.targetUrl;
			             		   		} else {
				             		   		sb.setStatus({
						                    	text: '登录失败! 原因: ' + messager.contents.error, 
						                    	iconCls: '',
						                    	clear: true
						                    });
				             				userNames.push(new Array([messager.contents.key]));
				             				store.reload();
						                    // loginFormPanel.getEl().unmask();
			             		   		}
			             		   },
			             		   params: {j_username: $('j_username').value, j_password: $('j_password').value, _spring_security_remember_me: rememberMe, ajax: true}
			             		});
	                    	}
						}
	                },{
	                    text: '重置',
	                    handler: function(){
	                    	loginFormPanel.form.reset();
	                    }
	        		}]
	    });
	
		loginPanel = new Ext.Window({
			el: 'login-window',
	        layout:'fit',
	        title: '请登录',
			width: 300,
	        height: 200,
			resizable : false,
			closeAction: 'hide',
			items: loginFormPanel,
			iconCls:'login-win',
	        bbar: new Ext.StatusBar({
	            id: 'form-statusbar',
	            defaultText: '待登录',
				plugins: new Ext.ux.ValidationStatus({form:'login-form'})
        	})
		});
		loginPanel.show();
});

var LoginPanel = {
		
	show : function() {
		loginPanel.show();
	}
}