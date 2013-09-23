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

<script type="text/javascript">

Ext.BLANK_IMAGE_URL = 'ext-3.2.1/resources/images/default/s.gif';





Ext.onReady(function(){

	Ext.QuickTips.init();

	var AwesomeUploaderInstance1 = new AwesomeUploader({
		title:'Ext JS Super Uploader'
		,renderTo:'container1'
		,frame:true
		,flashUploadUrl:'/pdiserver/kettle/viewTtransStatus/sumydata'
		,width:700
		,gridHeight:200
		,gridWidth:600
		,height:300
	});
	
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





var form1 = new Ext.FormPanel({
    title: 'Multiple Upload With Default Uploader',
    renderTo: 'div-form-1', width: 320, buttonAlign: 'center',
    frame: true, fileUpload: true, style: 'margin: 0 auto;',
    items: [{
        xtype: 'fileuploadfield',
        emptyText: '',
        fieldLabel: 'File 1',
        buttonText: 'Select a File',
        name: 'ufile[]',
        id: 'form-file-1'
    }, {
        xtype: 'fileuploadfield',
        emptyText: '',
        fieldLabel: 'File 2',
        buttonText: 'Select a File',
        name: 'ufile[]',
        id: 'form-file-2'
    }, {
        xtype: 'fileuploadfield',
        emptyText: '',
        fieldLabel: 'File 3',
        buttonText: 'Select a File',
        name: 'ufile[]',
        id: 'form-file-3'
    }],
    buttons: [{
        text: 'Save',
        handler: function() {
            form1.getForm().submit({
                url: 'upload.php',
                waitMsg: 'Sending Data',
                success: function(form, o) {
                    alert(o.response.responseText);
                }
            });
        }
    }, {
        text: 'Reset',
        handler: function() {
            form1.getForm().reset();
        }
    }]
});
</script>
<b>If you are using firefox 3.6+ or Chrome 5+, drag a file from your desktop into here:</b><BR>
