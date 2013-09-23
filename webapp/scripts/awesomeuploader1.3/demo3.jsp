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
    }],
    buttons: [{
        text: 'Save',
        handler: function() {
            form1.getForm().submit({
                url: '${ctx}/s/fee/feeGsmImport.do?name=gsmdetail',
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
