<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!--DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"-->
<!DOCTYPE html>
<head>
<meta http-equiv=”X-UA-Compatible” content=”IE=Edge”>
<%@ include file="/commons/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="${ctx}/scripts/fileuploadfield/css/fileuploadfield.css"/>

<script type="text/javascript">Ext.namespace("App");</script>
<script src="${ctx}/scripts/ux/Ext.ux.Spotlight.js"></script>
<script type="text/javascript" src="${ctx}/scripts/fileuploadfield/FileUploadField.js"></script>
<script src="${ctx}/scripts/Sparks/Ext.sparks.ListJsonGrid.js"></script>
<script src="${ctx}/scripts/ux/Ext.ux.PageSizePlugin.js"></script>

<script src="${ctx}/scripts/models/fee/comobox-search.js"></script>



<style type="text/css">
<!--
@import url("${ctx}/scripts/express-css-table/style.css");
-->
</style>

<style type="text/css">

.fee_ok {
	background: url('${ctx}/images/icons/sys/success-icon24.png') no-repeat;;
	width: 85px;
	height: 35px;
	text-align:right;
}
.fee_detail {
	background: url('${ctx}/images/icons/sys/search-icon24.png') no-repeat;;
	width: 85px;
	height: 35px;
	text-align:right;
}
.upload-icon {
   background: url('${ctx}/images/icons/excel-icon.png') no-repeat 0 0 !important;
 }

.upload-database {
   background: url('${ctx}/images/icons/sys/Misc-Upload-Database-icon32.png') no-repeat 0 0 !important;
 }
 
 .button-reset {
   background: url('${ctx}/images/icons/sys/update-icon32.png') no-repeat 0 0 !important;
 }
 #fi-button-msg {
     border: 2px solid #ccc;
     padding: 5px 10px;
     background: #eee;
     margin: 5px;
     float: left;
 }
 .prucect_select {
     width:650px;
	 padding-top:20px;
    padding-left:180px;
     float: left;
 }



.search-item {
    font:normal 12px tahoma, arial, helvetica, sans-serif;
    padding:3px 10px 3px 11px;
    border:1px solid #fff;
    border-bottom:1px solid #eeeeee;
    white-space:normal;
    color:#555;
}
.search-item h3 {
    display:block;
    font:inherit;
    font-weight:bold;
    color:#222;
}

.search-item h3 span {
    float: right;
    font-weight:normal;
    margin:0 0 5px 5px;
    width:100px;
    display:block;
    clear:none;
}

.x-grid-font-red{  
  color: #FF0000;
}

</style>
</head>
<body>
<!-- The box wrap markup embedded instead of using Element.boxWrap() 
<div  class="prucect_select">
    <div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>
    <div class="x-box-ml"><div class="x-box-mr">
	
	<div class="x-box-mc">
        <h3 style="margin-bottom:5px;">第一步：请选择客户使用的产品类型</h3>
        <input type="text" size="40" name="search" id="search" />
        <div style="padding-top:4px;">
            Live search requires a minimum of 4 characters.

        </div>
    </div>
	</div></div>

    <div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>
</div>
-->

 <div id="fi-form">
 </div>
 <br>
 <div id="show-panel"></div>
<script type="text/javascript">
<!--
/*!
 * Ext JS Library 3.4.0
 * Copyright(c) 2006-2011 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */


 var panelWidth = 680;
 var comp_margins = '50 3 3 10';
 var imortRestData = new Object;
 //var limitFeeGrid;
  Ext.QuickTips.init();
	  Ext.form.Field.prototype.msgTarget = 'side';

    //呈现组件
 var QPanel=new Ext.Panel({
	 renderTo:'show-panel',
      width: panelWidth,
	  flex:1,
	  height:200,
	  //bodyStyle: 'padding: 10px 10px 0 10px;',
	margins:'3 3 3 10',
      //title:"边漫检查结果",
	  id:"QPanel"
      //renderTo:Ext.getBody()
    });
var vtype_store_data = [<c:forEach items="${vproducts}" var="col">
			['${col.VID}','${col.VNAME}'],
			</c:forEach>['', '--请选择V网类型--']
			  ];

//new Ext.data.SimpleStore({ 亦可

 var vtype_store = new Ext.data.ArrayStore({
       fields: ['VID', 'VNAME'], 
        data : vtype_store_data // from states.js
    });

/**

var procuct_Combo111 = new Ext.data.JsonStore({
             url: '${ctx}/s/fee/loadGridVProduct.do',
             remoteSort: false,
             autoLoad:false,
             idProperty: 'VID',
             root: 'result',
             totalProperty: 'totalCount',
             fields: ['VID', 'VNAME']
         });

 var procuct_Combo = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
           url: '${ctx}/s/fee/loadGridVProduct.do',
			method:'POST'
        }),
        reader: new Ext.data.JsonReader({
           root: 'result',
            idProperty: 'VID',
            id: 'product_id'
        },
		[
            {name: 'VID', mapping: 'VID'},
            {name: 'VNAME', mapping: 'VNAME'}
        ])//,baseParams:{prd_name:''}
    });


var vprocuctCb = new Ext.form.ComboBox({ 
	fieldLabel: 'V网类型',hiddenName: 'product_vid',
	store: new Ext.data.SimpleStore({
		fields: ['VID', 'VNAME'], 
		data :vtype_store  }),
	displayField:'VNAME',valueField:'VID',
	id:'product_vid_id',
	name:'name_product_vid',
	 typeAhead: true, mode: 'local',
	 triggerAction: 'all', 
	 emptyText:'-- 请选择V网类型 --', 
	 allowBlank: false, labelStyle: 'font-weight:bold;',
//			tpl:'<tpl for="."><div class="x-combo-list-item">{[Ext.util.Format.htmlEncode(values.name)]}</div></tpl>'
	 selectOnFocus:true }
);*/



  var msisdn = new Ext.form.TextField({
	   fieldLabel: '手机号码',
		name:'msisdn',
		emptyText:"请输入手机号码",
		id:'id_msisdn',
		maxLength:11,
		allowBlank:false,
		 regex: /^1[\d]{10}$/,
        regexText:"请输入正确的手机号码", //定义不符合正则表达式的提示信息
        labelStyle: 'font-weight:bold;'
    });

	  var prd_price = new Ext.form.TextField({
	   fieldLabel: '产品单价(元/分钟)',
		name:'prd_price',
		emptyText:"产品单价,如:0.12",
		id:'id_prd_price',
		 labelWidth: 150,
		maxLength:4,
		allowBlank:false,
		 //vtype: 'alphanum',
		//regex:/^\d*$/, //正则表达式　这里假设只允许输入数字　如果输入的不是数字　就会出现下面定义的提示信息
        //regexText:"请输入产品单价", //定义不符合正则表达式的提示信息
        labelStyle: 'font-weight:bold;'
    });



var bill_date = new Ext.form.DateField({
	id: 'id_bill_date',
	name:'bill_date',
	fieldLabel : '退费月份', 
	value: new Date(),
	format : 'Ym',
	labelStyle: 'font-weight:bold;',
	//editable: true,
    allowBlank:false,
    listeners: { // 监听选择事件
      select: function() {
        if (this.getValue() != null){
			var imonth = this.getValue();
			//Ext.getCmp('bill_date').setValue(this.formatDate(this.getValue()));
			var gedt = Ext.util.Format.date(imonth, 'Ym');
			Ext.getCmp('ibilldate').setValue(gedt);
			//Ext.Msg.alert('调试信息',gedt);

         } 
      }//end select
  } //end listeners

});



  var vp_combo = new Ext.form.ComboBox({
	   fieldLabel: 'V网类型',hiddenName: 'product_vid',
		displayField:'VNAME',valueField:'VID',
		id:'product_vid_id',
        store: vtype_store,
        typeAhead: true,
        mode: 'local',
        forceSelection: true,
        triggerAction: 'all',
        emptyText:'-- 请选择V网类型 --', 
		labelStyle: 'font-weight:bold;',
        selectOnFocus:true
    });



	
 //行1  
 
    var row_price = {  
        layout:'column',    //从左往右布局  
        items:[{  
            columnWidth:.3, //该列有整行中所占百分比  
            layout:'form',  //从上往下布局  
            items:[{ 
				name: 'vnet',
                xtype:'radio',  
                fieldLabel:'无V网',
				value: 'N',
				labelWidth:10,
				labelStyle: 'font-weight:bold;',
                width:50,
				 checked:true,
				listeners : {
                    check : function(checkbox, checked) {        //选中时,调用的事件
                            if (checked) {
                                //Ext.getCmp('vprice').setVisible(true);
								Ext.getCmp('id_vprice').hide();
                            }
                   }
                }
            }]  
        },{  
            columnWidth:.3,  
            layout:'form',  
            items:[{  
				name: 'vnet',
                xtype:'radio',  
                fieldLabel:'有V网', 
				value: 'Y',
				labelWidth:10, 
				labelStyle: 'font-weight:bold;',
                width:50, 
				listeners : {
                    check : function(checkbox, checked) {        //选中时,调用的事件
                            if (checked) {
                                //Ext.getCmp('vprice').setVisible(true);
								Ext.getCmp('id_vprice').show();
                            }
                   }
                } 
            }]  
        },{  
            columnWidth:.4,  
            layout:'form',
            items:[{  
                xtype:'textfield',  
				name:'vprice',
					id:'id_vprice',
				emptyText:"如:0.05",
				labelAlign:"right",
				enableAlignments:true,
                fieldLabel:'V网单价(元/分钟)', 
				labelStyle: 'font-weight:bold;',
				hidden: true,
				labelWidth:30, 
                width:80  
            }]  
        }]  
    }; 


 var row_template = {  
        layout:'column',    //从左往右布局  
        items:[{  
            columnWidth:.7, //该列有整行中所占百分比  
            layout:'form',  //从上往下布局  
            items:[{
            xtype: 'fileuploadfield',
            id: 'form-file',
            emptyText: 'Excel文件,例如:语音_清单.xls',
            fieldLabel: '导入文件',
            name: 'file',
			labelStyle: 'font-weight:bold;',
			anchor: '90%',
            buttonCfg: {
                text: '',
                iconCls: 'upload-icon'
            }
        }]  
        },{  
            columnWidth:.3,  
            layout:'form',  
            items:[{  
    //fieldLabel : '采购单编号',  
    name : 'pruappcode',  
    html: "<a href='${ctx}/语音_清单.xls'><b>清单模板下载(右键另存为)</b></a>"  
	}]  
        }]  
    }; 

 var billdate = new Ext.form.Hidden({   //hidden
                name:'billdate',
				id:'ibilldate'
            });

 var fp = new Ext.FormPanel({
        renderTo: 'fi-form',
		layout:'form',
        fileUpload: true,
        width: panelWidth,
        frame: true,
        title: '第一步：请上传语音清单',
        autoHeight: true,
        bodyStyle: 'padding: 10px 10px 0 10px;',
		margins:comp_margins,
        labelWidth: 120,
		labelAlign : 'right',
        /**defaults: {
            anchor: '60%',
			xtype: 'textfield',
            allowBlank: false,
            msgTarget: 'side'
        }*/
        items: [
			   msisdn,bill_date,prd_price,row_price,billdate,
			row_template,{ boxLabel: '勾选', labelStyle: 'font-weight:bold;',name: 'isdouble', id: 'isdouble', inputValue: 'true', xtype: 'checkbox', checked: false,labelStyle: 'font-weight:bold;' }
		],
        buttons: [{
            text: ' &nbsp;导入并试算 ',
			 scale: 'large',
			iconCls: 'upload-database',
            handler: function(){

			var imonth = Ext.getCmp('id_bill_date').getValue();
			var gedt = Ext.util.Format.date(imonth, 'Ym');
			Ext.getCmp('ibilldate').setValue(gedt);

                if(fp.getForm().isValid()){
	                fp.getForm().submit({
	                    url: '${ctx}/s/fee/feeGsmImport.do?name=gsmdetail',
	                    waitMsg: '正在导入文件,请稍等...',
	                    success: function(form, action){
							
							imortRestData = Ext.util.JSON.decode(action.response.responseText);
							if(!imortRestData.success){

							}
							 //Ext.MessageBox.alert('操作成功','手机号码:' + imortRestData.msisdn +'  通话日期:'+imortRestData.billdate
								//+ '<br>的语音清单已经成功导入!');
							 keepOptBtnLive();
							 confirmSendback(Ext.getCmp("isdouble").getValue(),Ext.getCmp("id_prd_price").getValue(),Ext.getCmp("id_vprice").getValue());
	                    },
                          failure: function(form, action) {
								Ext.MessageBox.alert("异常","系统发生异常,请联系管理员!<br>"+action.response.responseText);
								//alert(action.response.responseText);
                            }
	                });
                }
            }
        },{
            text: ' &nbsp;重  置 ',
			 scale: 'large',
			iconCls: 'button-reset',
            handler: function(){
				 //Ext.App.ghost('Button Click','You clicked the "{0}" button.', '123');
                fp.getForm().reset();
            }
        }]

    });

function confirmSendback(isdouble,product,product_vid){
	//Ext.MessageBox.alert(isdouble+"--"+product+"--"+product_vid);
	var butchUrl = "${ctx}/s/fee/feeSendbackMain.do";
  					
					//alert(Ext.util.JSON.encode(postParams));
                    Ext.getBody().mask('提交数据,请稍候...', 'x-mask-loading');
                    Ext.Ajax.request({
                        url     : butchUrl, //+ '?ids=' + ids,
						method: 'POST',
						params: {msisdn:imortRestData.msisdn,
							billdate:imortRestData.billdate,
							product:product,
							product_vid:product_vid,
							isdouble:isdouble
						},
                       success: function(resp,opts) {
							var respObj = Ext.util.JSON.decode(resp.responseText); 
							//respObj.allowSendbackFee
							if(respObj.status == 1){
								Ext.getBody().unmask();
								Ext.MessageBox.alert('警  告', '该用户已经退费,不能重复退费');
								return;
							}
							
							if(respObj.status == -1){
								Ext.getBody().unmask();
								Ext.MessageBox.alert('警  告', respObj.msg+',该用户无需退费');
								return;
							}

							tpl.overwrite(QPanel.body,respObj);
							Ext.getBody().unmask();
                            //Ext.MessageBox.alert('提 示', '已计算出该用户退费金额：<red>'+respObj.AMT+'</red>');
							//处灰退费按钮
							var deailCalls = Ext.getDom("feeSendback");
							//feeSendback.disabled = (feeSendback.disabled == "" ? "disabled" : "");
                            
                        }.createDelegate(this),
                       failure: function(resp,opts) {   
							Ext.getBody().unmask();
                            Ext.MessageBox.alert('提 示', '该用户在该通话期已经退费！');
							
                        }
                    });
               
           

}
    //创建模板
   var tpl = new Ext.XTemplate(
            '<table id="rounded-corner" summary="2011">',
    '<thead>',
    	'<tr>',
        	'<th scope="col" class="rounded-company"><b>手机号码</b></th>',
            '<th scope="col" class="rounded-q1"><b>通话日期</b></th>',
            '<th scope="col" class="rounded-q2"><b>通话记录数</b></th>',
            '<th scope="col" class="rounded-q3"><b>边漫通话记录数</b></th>',
            '<th scope="col" class="rounded-q4"><b>边漫基站应退话费</b></th>',
        '</tr>',
    '</thead>',
        '<tfoot>',
    	'<tr>',
        	'<td colspan="4" class="rounded-foot-left"><em><input type="button" name="deailCalls" id="deailCalls" value="边漫详单" onClick="fnFileImportHandler()" class="fee_detail"></em></td>',
        	'<td class="rounded-foot-right">&nbsp;<input type="button" name="feeSendback" id="feeSendback" value="确认退费" onClick="changeFeehisStatus()" class="fee_ok"></td>',
        '</tr>',
    '</tfoot>',
    '<tbody>',
	'<tpl for=".">',
    	'<tr style="color:red;font-weight: bold;font-size:16px">',
        	'<td>{MSISDN}</td>',
            '<td>{BILL_DATE}</td>',
            '<td>{CALLS_CNT}</td>',
            '<td>{LIMIT_CNT}</td>',
            '<td style="color:red;font-size:28px">{AMT}(元)</td>',
       '</tr>',
	   '</tpl>',
    '</tbody>',
'</table>'
        );

  var resp_data = [
            {MSISDN:'',BILL_DATE:'',AMT:'0.00',CALLS_CNT:0,LIMIT_CNT:0}
        ];

var sFixupFeeGrid;
var qSPannel;
var dlgWidth = panelWidth+150;
var dlgHeight = 400;

var renderColAction = function(value,meta){
	  meta.css='x-grid-font-red';  
	var detail = "此条退费："+value+" (元)"
	return detail;
}
var renderColMimi = function(value,meta){
	  meta.css='x-grid-font-red';  
	 //CEIL (v_cur.call_duration / 60)
	var duing = Math.ceil(value/60); //取整 +1
	var detail = duing+"(分钟)";
	return detail;
}

var renderColFee = function(value,meta){
	 meta.css='x-grid-font-red';  
	var detail = value+"(元)";
	return detail;
}

App.FixupFeeGrid = Ext.extend(Ext.sparks.ListJsonGrid, {
    id: 'fixupfee_grid',
    rootName: '固定用户退费',
    enableDD: false,
	flex:1,
    urlPagedQuery: "${ctx}/s/fee/showGsmDetailList.do",
    //urlLoadData: "${ctx}/s/fee/searchFixupFees.do",
    //urlSave: "${ctx}/s/saveRptInfo.do",
    //urlRemove: "${ctx}/s/removeRptInfo.do",
    pageSize: 100,
    dlgWidth:dlgWidth,
    dlgHeight:dlgHeight,
    autoScroll: true,
    
    formConfig: [
		{name : 'tuifee', fieldLabel : "通话退费明细",mapping:'TUI_FEE',renderer:renderColAction,hideGrid:false,width:150},
        {name : 'callType', fieldLabel : "呼叫类型", mapping:'CALL_TYPE', vType : "chn",width:50},
		 {name : 'cfee', fieldLabel : "基本费",renderer:renderColFee,mapping:'CFEE', vType : "number",width:70},
		 {name : 'cellId', fieldLabel : "基站号",mapping:'CELL_ID', vType : "chn",width:50},
		{name : 'msisdn', fieldLabel : "手机号码",mapping:'MSISDN', vType : "chn",hideGrid:false},
		 {name : 'otherParty', fieldLabel : "对方号码",mapping:'OTHER_PARTY', vType : "chn"},
		{name : 'callDuration', fieldLabel : "通话时长",renderer:renderColMimi,mapping:'CALL_DURATION', vType : "integer",width:70},
        {name : 'startDate',fieldLabel : "通话日期",mapping:'START_DATE', vType : "chn"},
        {name : 'startTime', fieldLabel : "通话时间",mapping:'START_TIME', vType : "chn"},

	    {name : 'lfee', fieldLabel : "长途费",mapping:'LFEE', vType : "number"},
        {name : 'totalFee', fieldLabel : "总费用",mapping:'TOTAL_FEE', vType : "number"},
        {name : 'officeCode', fieldLabel : "话单位置",mapping:'OFFICE_CODE', vType : "chn"},
        {name : 'bizType', fieldLabel : "长途类型",mapping:'BIZ_TYPE', vType : "chn"},
        {name : 'roamType', fieldLabel : "漫游类型",mapping:'ROAM_TYPE', vType : "chn"},
		{name : 'originNo', fieldLabel : "前转号码",mapping:'ORIGIN_NO', vType : "chn"}

        //{name : 'vpnId', fieldLabel : "网络标识",mapping:'VPN_ID', vType : "chn"}
        //,{name : 'sourceType', fieldLabel : "数据源类型",mapping:'CALL_TYPE', vType : "chn"}
		//,{name : 'sourceType', fieldLabel : "省市县", vType : "chn"},
    ]
    
});
 
                
function changeFeehisStatus(){
	var updateStatusUrl = "${ctx}/s/fee/updateFeeSendbackStatus.do";
    Ext.getBody().mask('提交数据,请稍候...', 'x-mask-loading');
    Ext.Ajax.request({
      url     : updateStatusUrl, //+ '?ids=' + ids,
	  method: 'POST',
	  params: {
		   msisdn:imortRestData.msisdn,
		   billdate:imortRestData.billdate,
		   status:1,
		   cmt:'退费成功'
		},
      success: function(resp,opts) {
			Ext.getBody().unmask();
			tpl.overwrite(QPanel.body,resp_data);
			Ext.App.ghost('系统提示','手机号码为：{0},在通话帐期：{1} &nbsp;已经退费成功.', imortRestData.msisdn,imortRestData.billdate);
			fp.getForm().reset();
			keepOptBtnLive();
      },
     failure: function(resp,opts) {   
			Ext.getBody().unmask();
         Ext.MessageBox.alert("异常","系统发生异常,请联系管理员!<br>");
			
      }
  });
}


function fnFileImportHandler(){
   var win = null;
		
		if (!win) {
			var limitFeeGrid = new App.FixupFeeGrid();
			win = new Ext.Window({
		         layout: 'fit',
		         width: this.dlgWidth ? this.dlgWidth : panelWidth,
		         height: this.dlgHeight ? this.dlgHeight : 148,
		         closeAction: 'hide',
				  maximizable: false, 
                   modal : true,
		         items: [limitFeeGrid],
				buttons: [{
                text: '确   定',
                handler: function() {
                    win.close();
					win = null;
                }.createDelegate(this)
            }]
		    });
				//alert(imortRestData.msisdn+"-->"+imortRestData.billdate);
			limitFeeGrid.queryGridByParams(imortRestData);
			win.show();
		}	
	var limitParams = {msisdn :imortRestData.msisdn,billdate:imortRestData.billdate};
	
}

var replace = function(config){
         var btns = Ext.getCmp('btns');
         btns.remove(0);
         btns.add(config);

         btns.doLayout();
 }

 var msg = function(title, msg){
        Ext.Msg.show({
            title: title,
            msg: msg,
            minWidth: 200,
            modal: true,
            icon: Ext.Msg.INFO,
            buttons: Ext.Msg.OK
        });
    };

function keepOptBtnLive(){
		var deailCalls = Ext.getDom("deailCalls");
	deailCalls.disabled = (deailCalls.disabled == "" ? "disabled" : "");

	var deailCalls = Ext.getDom("feeSendback");
	feeSendback.disabled = (feeSendback.disabled == "" ? "disabled" : "");
 }



Ext.onReady(function(){
  Ext.form.TriggerField.override({
	    afterRender : function(){
	        Ext.form.TriggerField.superclass.afterRender.call(this);
	        var y;
	        if(Ext.isIE && !this.hideTrigger && this.el.getY() != (y = this.trigger.getY())){
	            this.el.position();
	            this.el.setY(y);
	        }
	    }
	});

    Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
  //sFixupFeeGrid = new App.FixupFeeGrid();

   var viewport = new Ext.Viewport({
           layout: 'vbox',
			align : 'stretch',
			pack  : 'start',
			items: [fp,QPanel]
    });

tpl.overwrite(QPanel.body,resp_data);

keepOptBtnLive();

//tpl1.append('tpl-1',data1);
});




	var confirm_btn = new Ext.Button({ 
				 text: '确认退费',
				 id: 'confirm',
				 //applyTo: 'confirm',
				 handler: function(){
				 
				 }
	 
	  });
  
//Ext.fly("product_id").up('.x-form-item').setDisplayed(true);

//-->
</script>




</body>
</html>