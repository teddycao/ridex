<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<%@ include file="/commons/meta.jsp" %>

<script type="text/javascript">Ext.ns("App");</script>
<script src="${ctx}/scripts/ux/Ext.ux.PageSizePlugin.js"></script>
<script src="${ctx}/scripts/ux/Ext.lingo.TreeField.js"></script>

<title>用户管理</title>
</head>
<body>
<div id="contentview" align="center">
</div>
<%
String userName = request.getParameter("username");
%>

<script type="text/javascript">
<!--

//////////////////////////////////////////////////////////////////////////////////////////////
Ext.onReady(function(){

Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
var newUserConfig;
var urlSave;
<%if(userName == null || userName.equals("")){%>
 urlSave = "${ctx}/s/saveUsers.do";
newUserConfig =  [
          {fieldLabel: '用户名', name: 'name',allowBlank: false},
          {fieldLabel: '密码', name: 'password', inputType: 'password',hideGrid:true},
		  {fieldLabel: '重复密码', name: 'confirmPassword', inputType: 'password',hideGrid:true},
          {fieldLabel: '部门', name: 'dept', mapping: 'name', xtype: 'textfield'},
          {fieldLabel: '电子邮件', name: 'email',allowBlank: false},
          {fieldLabel: '电话号码', name: 'phoneNumber'},
		  {name:"jsonData",xtype:"hidden",id:"jsonData",hidden:true, hiddenLabel:true}
 ];
<%}else{%>
urlSave = "${ctx}/s/updateUser.do";
newUserConfig =  [
          {fieldLabel: '用户名', name: 'name',allowBlank: false},
         /**{fieldLabel: '部门', name: 'deptName', mapping: 'name', xtype: 'textfield'},*/
          {fieldLabel: '电子邮件', name: 'email',allowBlank: false},
          {fieldLabel: '电话号码', name: 'phoneNumber'},
		  {  name:"jsonData",xtype:"hidden",id:"jsonData",hidden:true, hiddenLabel:true}
 ];
<%}%>



    var reader =  new Ext.data.JsonReader(   
         {
			successProperty: 'success',
			root: 'data' 
         },   
         newUserConfig
     );



	var userForm = new Ext.FormPanel({
			id:'userForm',
            defaultType: 'textfield',
            labelAlign: 'right',
            labelWidth: 70,
            frame: true,
            autoScroll: true,
            title: '详细信息',
            url: urlSave,
            items: newUserConfig,
				  buttons : [{
					text:'&nbsp; 完 成 &nbsp;',
						 icon: "${ctx}/images/upload-icon24.png",
						handler:function(){
						var ff = Ext.getCmp('userForm').getForm();  
						var jsonData = Ext.util.JSON.encode(ff.getValues());		 
						if (userForm.getForm().isValid()) {
							Ext.getCmp('jsonData').setValue(jsonData); 
							userForm.getForm().submit({
                            waitTitle: "请稍候",
                            waitMsg : '提交数据，请稍候...',
								
                            success: function() {
                               parent.ContentWin.gridReload(); 
							   parent.ContentWin.close();
                            },
                            failure: function() {
                            },
                            scope: this
                        });
                    }

							
					}
					},{
						text:' &nbsp;取 消 &nbsp;',handler:function(){
						parent.ContentWin.close();
					}
				}]
            
        });



	userForm.render( Ext.getBody());
	<%if(userName != null && !userName.equals("")){%>
    userForm.load({url: '${ctx}/s/retrieveUser.do',params:{username:"<%=userName%>"},method:'GET'});  
	<%}%>
});	











MyNewClass = function(arg1, arg2, etc) {  
   //调用父类（基类）构造器  
   MyNewClass.superclass.constructor.call(this, arg1, arg2, etc);   
};  
   
MyComponent = Ext.extend(Ext.Component , {  
  theDocument: Ext.get(document),  
  myNewFn1: function() {  
    alert("-------");  
  },  
  myNewFn2: function() {  
   // etc.  
  }  
});  


MyComponent = Ext.extend(Ext.Component, {
    //缺省构造参数，可被自定义设置覆盖
    propA: 1,
 
    initComponent: function(){
       //在组件初始化期间调用的代码
 
        //因为配置对象应用到了“this”，所以属性可以在这里被覆盖，或者添加新的属性
        //（如items,tools,buttons）
        Ext.apply(this, {
            propA: 3
        });
 
       //调用父类代码之前        
 
        //调用父类构造函数（必须）
        MyComponent.superclass.initComponent.apply(this, arguments);
 
       //调用父类代码之后
        //如：设置事件处理和渲染组件
    },
 
    //覆盖其他父类方法
    onRender: function(){
	alert("onRender");
        //调用父类代码之前
 
        //调用父类相应方法（必须）
        //MyScope.superclass.onRender.apply(this, arguments);
 
        //调用父类代码之后
 
    }
});

//Ext.reg('mycomponentxtype', MyComponent);


var myComponent = new MyComponent({
    propA: 2
});

//myComponent.onRender();


var test = new Ext.Window({  
  title: 'jsontest',  
  width: 400,  
  height: 400,  
  closable: true,  
  items: [{  
    xtype: 'form',  
    id: 'myFormPanel',  
    defaultType: 'textfield',  
    items: [{name: 'value1'},{name: 'value2'}]  
  }],  
  buttons: [{  
    text: 'submit',  
    handler: function(){  
      var f = Ext.getCmp('myFormPanel').getForm();  
      Ext.Ajax.request({  
         url: 'submithandler.php',  
         headers: {'Content-Type':'application/json; charset=utf-8'},  
         params: {data:Ext.util.JSON.encode(f.getValues())} 
      });  
    }  
  }]  
})//.show(); 
















Ext.MyCmp = function(config){  
    Ext.apply(this,config);  
    this.message = 'show message method run';  
    this.buttons = [  
       {  
           xtype:'button',  
           text:'click',  
           handler:this.showMessage.createDelegate(this)  
       }  
    ];  
    Ext.MyCmp.superclass.constructor.call(this);  
}  
Ext.extend(Ext.MyCmp,Ext.Window,{  
   showMessage : function(){  
      Ext.Msg.alert('提示',this.message);  
   }  
});  





var BaseClass = Ext.extend(Ext.util.Observable, {
	arr: null,
	init: function() {
		this.arr = []; // init arr here.
	}
});

var SubClass1 = Ext.extend(BaseClass, {
	init: function() {
		SubClass1.superclass.init.call(this); // call base class 's init function 
		this.arr.push(1);
	}
});

var SubClass2 = Ext.extend(BaseClass, {
	init: function() {
		SubClass2.superclass.init.call(this); // call base class 's init function
		this.arr.push(2);
	}
});

function extTest() {
	var c1 = new SubClass1();
	c1.init();
	alert(c1.arr); 

	var c2 = new SubClass2();
	c2.init();
	alert(c2.arr);
}
//-->
</script>
</body>
</html>