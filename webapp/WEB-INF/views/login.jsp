<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>宝鸡移动退费管理系统</title>
<link href="${ctx}/styles/login.css" rel="stylesheet" type="text/css">
<style>
.input1{ border:1px solid #84A1BD; width:100px; height:20px; line-height:23px;}
.input2{ border:1px solid #84A1BD; width:68px; height:20px; line-height:23px;}
.button1{
	border:none;
	width:70px;
	height:27px;
	line-height:23px;
	color:#525252;
	font-size:12px;
	font-weight:bold;
	background-image: url(${ctx}/images/bt001.jpg);
	background-repeat: no-repeat;
	background-position: 0px 0px;
}
.button2{
	border:none;
	width:70px;
	height:27px;
	line-height:23px;
	color:#525252;
	font-size:12px;
	font-weight:bold;
	background-image: url(${ctx}/images/bt002.jpg);
	background-repeat: no-repeat;
	background-position: 0px 0px;
}
.STYLE3 {
	color: #FF0000;
	font-weight: bold;
}
</style>
<script src="${ctx}/js/Main.js"></script>
<script src="${ctx}/js/jquery/jquery-1.6.min.js"></script>
 <script src="${ctx}/extjs-3/adapter/ext/ext-base.js"></script>
 <script src="${ctx}/extjs-3/ext-all.js"></script>
<script>

$(document).ready(function(){
  $("#username").focus();
});

function login(){
	
var un = Ext.get('username').dom.value;
var pwd = Ext.get('password').dom.value;	

 if(un.trim()==""|| pwd.trim()==""){
		alert("请输入用户名和密码");
		return;
 }

Ext.Ajax.request({    
                      //请求地址    
                     url: '${ctx}/login.do',    
                     //提交参数组    
                     params: {    
                         username:Ext.get('username').dom.value,    
                         password:Ext.get('password').dom.value    
                     },    
                     //成功时回调    
                     success: function(response, options) {    
                        //获取响应的json字符串   
                      var json = response.responseText;
                       var flag = Ext.util.JSON.decode(json);                                                 
                            if(flag.success==true){    
                                window.location.href='${ctx}/index.jsp';
                            }    
                            else{    
                               alert("用户名或密码有误");        
                            }    
                    } ,failure: function(response) {
  								alert('登录系统失败,请与管理员联系.');
  								
  					}    
            });



}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		login();
	}
}

Page.onLoad(function(){
	if(window.top.location != window.self.location){
		window.top.location = window.self.location;
	}else{
		$("#username").focus();
	
	}
	$("#username").focus();
});
</script>
</head>
<body>
<form id="form1" method="post" style=" display:block;height:100%;" action="">
<table width="100%" height="100%">
	<tr>
		<td align="center" valign="middle">
		<table
			style=" height:283px; width:620px; background:url(${ctx}/images/pics/loginbg.jpg) no-repeat 0px 0px;">
			<tr>
				<td>
				<div style="height:213px; width:620px;"></div>
				<div style="height:70px; width:620px;margin:0px auto 0px auto;">
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top:8px;">
					<tr>
						<td align="center">用户名：
					    <input name="username" type="text" style="width:120px"
							id="username" class="inputText" value="admin" onfocus="this.select();"/>
					    &nbsp;密码：
					    <input name="password" type="password" style="width:120px"
							id="password" class="inputText" value="admin" onfocus="this.select();"/>
					    <span id='spanVerifyCode'></span>	
						&nbsp;<img src="${ctx}/images/loginbutton.jpg" name="LoginImg" align="absmiddle" id="LoginImg" style="cursor:pointer"
							onClick="login();" /></td>
					</tr>
					<tr>
						<td height="23" align="center" valign="bottom">Copyright
						&copy; 版权所有</td>
					</tr>
				</table>
				</div>
				</td>
			</tr>
		</table>
		<br>
		
		</td>
	</tr>
</table>
</form>
</body>
</html>
