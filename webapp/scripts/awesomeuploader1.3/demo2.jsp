<html>
<head>
<title>Ext JS Awesome Uploader</title>
<%@ include file="/commons/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="Ext.ux.form.FileUploadField.css" />
<link rel="stylesheet" type="text/css" href="AwesomeUploader.css" />


<c:set var="aupload2" value="${pageContext.request.contextPath}/extjs-3.2/plugins/aupload2"/>
<BR>
<script type="text/javascript" src="ext-3.2.1/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="ext-3.2.1/ext-all.js"></script>

<script type="text/javascript" src="Ext.ux.form.FileUploadField.js"></script>
<script type="text/javascript" src="Ext.ux.XHRUpload.js"></script>
<script type="text/javascript" src="swfupload.js"></script>
<script type="text/javascript" src="swfupload.swfobject.js"></script>
<script type="text/javascript" src="AwesomeUploader.js"></script>
</head>
<style>
body {
	margin: 40px;
	font-family: arial;
}
pre {
	background: none;
	font-family: Courier New, Lucida Console;
	font-style: normal;
}
</style>
<body>
<div id="container1"></div>
<BR>

<BR>
<div id="container2"></div>

<BR>

<BR>


<div id="container3"></div>

<BR>

<BR>
<div id="div-form-1"></div>

<script type="text/javascript">

Ext.onReady(function(){

	Ext.QuickTips.init();

	var AwesomeUploaderInstance2 = new Ext.Panel({
		title:'Awesome Uploader via xtype'
		,renderTo:'container2'
		,frame:true
		,width:500
		,height:500
		,items:{
			xtype:'awesomeuploader'
			,gridHeight:100
			,height:160
		,flashUploadUrl:'/pdiserver/kettle/uplaod/action/'
			,listeners:{
				scope:this
				,fileupload:function(uploader, success, result){
					if(success){
						Ext.Msg.alert('File Uploaded!','A file has been uploaded!');
					}
				}
			}
		}
	});





	

});






</script>


<script> 
Ext.onReady(function(){
	var AwesomeUploaderInstance3 = new Ext.Window({
		title:'Awesome Uploader in a Window!'
		,closeAction:'hide'
		,frame:true
		,width:500
		,height:200
		,items:{
			xtype:'awesomeuploader'
			,gridHeight:100
			,height:160
				,flashUploadUrl:'/pdiserver/kettle/uplaod/action/'
			,awesomeUploaderRoot:'./'
			,listeners:{
				scope:this
				,fileupload:function(uploader, success, result){
					if(success){
						Ext.Msg.alert('File Uploaded!','A file has been uploaded!');
					}
				}
			}
		}
	});
	var AwesomeUploaderInstance3button = new Ext.Panel({
		renderTo:'container3'
		,frame:true
		,width:250
		,height:75
		,items:{
			xtype:'button'
			,text:'Show Uploader Window'
			,handler:function(){
				AwesomeUploaderInstance3.show();
			}
		}
	});
});
</script>


<b>If you are using firefox 3.6+ or Chrome 5+, drag a file from your desktop into here:</b><BR>
