<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<title>批量文件上传 Uploader</title>
<%@ include file="/commons/meta.jsp" %>
<c:set var="uploader" value="${pageContext.request.contextPath}/scripts/awesomeuploader1.3"/>
<link rel="stylesheet" type="text/css" href="${uploader}/Ext.ux.form.FileUploadField.css" />
<link rel="stylesheet" type="text/css" href="${uploader}/AwesomeUploader.css" />



<script type="text/javascript" src="${uploader}/Ext.ux.form.FileUploadField.js"></script>
<script type="text/javascript" src="${uploader}/Ext.ux.XHRUpload.js"></script>
<script type="text/javascript" src="${uploader}/swfupload.js"></script>
<script type="text/javascript" src="${uploader}/swfupload.swfobject.js"></script>
<script type="text/javascript" src="${uploader}/AwesomeUploader.js"></script>

<script src="${ctx}/js/jquery/jquery-1.6.min.js" type="text/javascript"></script>
</head>
<style>


body {
	margin: 0px;
	font-family: arial;
}
pre {
	background: none;
	font-family: Courier New, Lucida Console;
	font-style: normal;
}
</style>
<body>
<div id="uploader_div"></div>

<script type="text/javascript">
var statusSnifURI = "${importMeta.teskExcuteMeta.statusURI}";

Ext.onReady(function(){
	var pageWidth =document.body.clientWidth ;
    var pageHeight = document.body.clientHeight;
	Ext.QuickTips.init();

var senCatalogVal;
var friCatalogVal;
var thrCatalogVal;
var docDateVal;
var store2 = new Ext.data.SimpleStore({
        fields: ['name', 'val'],
        data: [['是', 'Y'], ['否', 'N']],
        autoLoad: false
 });

	  
var form1 = new Ext.form.FormPanel({
      width:570,
      frame:true,//圆角和浅蓝色背景
	  defaults: {xtype:'textfield'},
      bodyStyle:"padding:5px 15px 0",
      items:[{
		  id:'fri_catalog'
         ,fieldLabel:'退费手机号码'
		 ,width:120
		,editable: true
		,allowBlank:false
		,xtype: 'textfield'
        ,mode:'local'
        ,listeners:{select:{fn:function(combo, value) {
			friCatalogVal = combo.getValue();
           // var comboCity = Ext.getCmp('combo-city-local');
            //comboCity.clearValue();
            //comboCity.store.filter('stateId', combo.getValue());
            }}
        }
 
    },{
		id:'docDate'
        ,fieldLabel:'用户帐期'
		,allowBlank:false
		,xtype:"datefield"
		,name:"docDate"
	    ,value:new Date()
		,format:'Y-m'
        ,listeners:{select:{fn:function(combo, value) {
			senCatalogVal = combo.getValue();
			}}
        }
 
    },{
		id:'thr_catalog'
        ,name:'thr_catalog'
		,fieldLabel:'是否双倍退费'
        ,displayField:'name'
        ,valueField:'val'
		,width:100
		,xtype: 'combo'
		,editable: true
		,allowBlank:true
        ,store: store2
		,hiddenField:'thr_catalog'
        ,triggerAction:'all'
		,value:'N'
        ,mode:'local'
        ,listeners:{select:{fn:function(combo, value) {
			thrCatalogVal = combo.getValue();
		  }}
        }
 
    }
          
      ],
	
	  buttons : [{
					text:'&nbsp; 确 定 &nbsp;',
						 icon: "${ctx}/images/upload-icon24.png",
						handler:function(){
							//parent.ContentWin.close();
							
							//batchUplpader.remove( Ext.getCmp('uploader_win'));
							addAMTPanel();

					}
					},{
						text:' &nbsp;取 消 &nbsp;',handler:function(){
						parent.fileUploadWin.close();
					}
				}]		  
});


var uploaddUrl = '${ctx}/s/hut/batchUpload.do?name=${param.name}';

getSyncPostParam = function(){
    var postParamObj = new Object;
	var fri_catalog;
	var sen_catalog;
	var thr_catalog;
	var doc_date;
	postParamObj.save_path = "/temp";
	

	postParamObj.friCatalog = friCatalogVal;
	postParamObj.senCatalog = senCatalogVal;
	postParamObj.thrCatalog = thrCatalogVal;
	postParamObj.docDate = docDateVal;
	postParamObj.name = '${param.name}';

	//在用户没有选择的情况下可以输入类别
	if(typeof(friCatalogVal) ==  'undefined'  || friCatalogVal == null){
		fri_catalog =Ext.getCmp("fri_catalog").getRawValue();
		postParamObj.friCatalog = fri_catalog;
	}


	if(typeof(thrCatalogVal) ==  'undefined'  || thrCatalogVal == null){
		thr_catalog =Ext.getCmp("thr_catalog").getRawValue();
		postParamObj.thrCatalog = thr_catalog;
	}

	if(typeof(docDateVal) ==  'undefined'  || docDateVal == null){
		docDate =Ext.getCmp("docDate").getRawValue();
		postParamObj.docDate = docDate;
	}
	//return { 'save_path': '/temp' }
	return postParamObj;
}

/**
* Flash上传文件组件
* fireEvent(Object thisUploader, Bool uploadSuccessful, Object serverResponse)
* Bool uploadSuccessful - indicates success or failure of upload
* Object serverResponse - response from server. will at minimum have a property "error" if an error occurs
*/

var batchUplpader = new Ext.Panel({
		title:'---选择要上传文件(Excel格式).xls--- '
		,renderTo:'uploader_div'
		,frame:true
		,id:'uploader_panel'
		,width:pageWidth
		,height:pageHeight
		,items:[/**form1,*/{
			 xtype:'awesomeuploader'
			 ,id:'uploader_win'
			,height:400
			,gridHeight:330
			,width:570
			,gridWidth:540
			//,extraPostData: { 'save_path': '/temp' }
			,awesomeUploaderRoot:'${uploader}/'
		    ,flashUploadUrl:uploaddUrl
			,listeners:{
				scope:this
				,fileupload:function(uploader, success, result){
					if(success){
						//Ext.Msg.alert('上传完成','文件已成功上传!');
						
						readyProgress(result);
					}else{
						Ext.Msg.alert('发生错误','处理文件发生错误!');
					}

				}
			}
		}]
	});


function addAMTPanel(){ //添加一个组件面板

  var mpl = new Ext.form.FormPanel ({
                   frame:true,
                   height:60,
                   id:'mpl',
                   items:{xtype:'displayfield',id:'mpl',value:'退费金额:300元'}
               });
 try{

   Ext.getCmp('uploader_panel').insert(1,mpl);
   Ext.getCmp('uploader_panel').doLayout();
     
	}catch(e){
          alert(e.name + " -- " + e.message);
 }
    
}

 
 function readyProgress(result) {
         var msgbox=Ext.Msg.show({
             title:"进度条应用",
             msg:"提示内容",
             closable:true,
             width:300,
             modal:true,
             progress:true
         });
         var count=0;
         var curnum=0;
         var msgtext="";
         Ext.TaskMgr.start({
             run:function () {
                 count++;
                 if (count>10) {
                     msgbox.hide();
                 }else{
					 snifTaskStatus(result.id);
				 }
                 curnum=count/10;
                 msgtext="当前加载:"+curnum*100+"%";
                 msgbox.updateProgress(curnum,msgtext,'当前时间:'+new Date().format('Y-m-d g:i:s A'));
				
             },
             interval:1000
             
         })
         
     }












function snifTaskStatus(taskID){

   var urls = "${importMeta.teskExcuteMeta.statusURI}?transName=Trans_Delay_Test&id="+taskID;

	$.ajax({
            type : "GET",
            url : urls,
            dataType : "jsonp",
           jsonp: 'callback',
            success : function(json){
                alert(json.name);
                //return true;
            }
        });


}
 
});





 
  


</script>


