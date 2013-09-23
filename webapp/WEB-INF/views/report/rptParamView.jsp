<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>报表展示</title>
<%@ include file="/commons/meta-ext4.jsp" %>
<link rel="stylesheet" type="text/css"  href="${ctx}/styles/ext-patch.css" />
<script src="${ctx}/js/ux/Ext.ux.MonthYearDatePicket.js"></script>
<style>
.formstyle {

	width:100%;  
    text-align:center;  
    vertical-align:center; 

	border-top:1px solid buttonhighlight;
	border-right:1px solid buttonshadow;
	border-bottom:1px solid buttonshadow;
	border-left:1px solid buttonhighlight;

	border-right-color:#CEAEA5 ;
	border-bottom-color:#CEAEA5 ;
	background: '#E9F2FC';


}


.rptBtn {
        background: url('${ctx}/images/icons/kchart-icon24.png') left top no-repeat !important;
}

.my-panel-no-border {border-style:none}
</style>
</head>

<!--div id="load" align="center">
	<img src="${ctx}/images/loading.gif" /> loading...
</div-->
<script type="text/javascript">
<!--

//-->
</script>
<script type="text/javascript">
  Ext.Loader.setConfig({enabled: true});
  Ext.Loader.setPath('sparks', '${ctx}/js/sparks');
  Ext.require([
    'Ext.form.Panel',
	'Ext.tab.*',
    'Ext.layout.container.Anchor'
  ]);

 var RPT_ID = '${param.rptid}';
 var rptView_URL = '/bc/ReportViewer?type=${rptid}.xls';
 var tabsCnt = 0;
 var currentName,viewport ;
 var preFieldLength = 1;
 var capMap = new Ext.util.MixedCollection();
 capMap.getKey = function(el){
   return el.pname;
};

//{'dft':'ym',datefield:'day','items':[{'value':'ymd','text':'日报'},{'value':'ym','text':'月报'},{'text':'季报','value':'yq'},{'text':'年报','value':'y'},{'text':'半年报','value':'ys'}]}
<c:forEach items="${rptParams}" var="par" varStatus="status">  
 <c:if test="${par.PARAM_NAME eq 'freq'}">
	var freqDispData = ${par.RPT_FREQ};
	var datefield = freqDispData.datefield;
	var freq = freqDispData.dft;
 </c:if>

 <c:if test="${par.PARAM_TIP != null && par.PARAM_TIP ne ''}">
	capMap.add('${par.PARAM_NAME}', {pname:"${par.PARAM_NAME}",pcap:"${par.PARAM_TIP}"});
 </c:if>


 <c:if test="${par.PARAM_TYPE eq 'combo' && par.PARAM_NAME ne 'freq'}">
 Ext.define("model_${par.PARAM_NAME}", {
        extend: 'Ext.data.Model',
        proxy: {
            type: 'ajax',
            url : '${ctx}/s/rpt/paramSelectVal.do?code=${par.PARAM_REF}',
            reader: {
                type: 'json',
				 autoLoad:false,
                root: 'result',
                totalProperty: 'totalCount'
            }
        },

        fields: [
            {type: 'string', name: 'key'},
			{type: 'string', name: 'value'}
        ]
});


// ComboBox with a custom item template
var field_${par.PARAM_NAME} = Ext.create('Ext.form.field.ComboBox', {
	emptyText:"请选择...",
    id:'${par.PARAM_NAME}',
	name:'${par.PARAM_NAME}',
	fieldLabel: '${par.PARAM_TITLE}',
    displayField: 'key',
	valueField:'value',
	allowBlank:false,
    store: Ext.create('Ext.data.Store', {
		model: 'model_${par.PARAM_NAME}'
	}),
	anchor: '100%',
    getInnerTpl: function() {
        return '<div data-qtip="{key}. {value}">{key} ({value})</div>';
    }
});

</c:if>

</c:forEach> 



var storeDispFreq = Ext.create('Ext.data.Store', {
    fields: ['value', 'text'],
    data:freqDispData.items,
	autoLoad : true
 });


 var comboDispFreq =Ext.create('Ext.form.ComboBox', {
	fieldLabel: '频率',
	id:'comboDispFreq',
    emptyText:"请选择",
    store : storeDispFreq,
    queryMode: 'local',
	anchor: '100%',
    triggerAction: 'all',
	forceSelection: true,
    valueField: 'value',
	value:'ym',
    displayField: 'text',
    listeners: { // 监听选择事件
    select: function() {
		var ivalue = this.getValue();
       if (ivalue != null) {
			var fields = getFreqFieldBy(ivalue);
			freq = ivalue;
			replace(fields,preFieldLength);
         } 
       }
    }
  });
       
//---------------------------------------------------------
  var store_y = Ext.create('Ext.data.Store', {
	  fields: [
          {type: 'string', name: 'value'},
          {type: 'string', name: 'text'}
      ],
      data:[
		  {'value':'2011','text':'2011年'},
		  {'value':'2012','text':'2012年'}
	  ],
	  autoLoad : true
  });




var field_y =  {  
	triggerAction: 'all',
    id: 'field_y',
    fieldLabel: '年',
    queryMode: 'local',
    editable: false,
    xtype: 'combo',
    store : store_y,
    displayField:'text',
    valueField:'value',
    value: '2011',
    anchor: '100%',
    forceSelection:true
};

//---------------------季报--------------------------
  var store_q = Ext.create('Ext.data.Store', {
		fields: [
          {type: 'string', name: 'value'},
          {type: 'string', name: 'text'}
      ],
       data:[
		  {'text':'一季度','value':'1'},
		  {'text':'二季度','value':'2'},
		  {'text':'三季度','value':'3'},
		  {'text':'四季度','value':'4'}
	     ],
		autoLoad : true
  });

//Ext.getCmp(id值).setValue(value)

var field_yq =  {  
	triggerAction: 'all',
    id: 'field_yq',
    fieldLabel: '季',
    queryMode: 'local',
    editable: false,
    xtype: 'combo',
    store : store_q,
    displayField:'text',
    valueField:'value',
    value: '1',
    anchor: '100%',
    forceSelection:true,
	listeners: { // 监听选择事件
      select: function() {
        if (this.getValue() != null){
		  //alert(datefield+"--"+this.getRawValue());
		  //Ext.getCmp(datefield).setValue(this.getValue());
         } 
      }//end select
  } //end listeners
};

//---------------------半年报--------------------------
  var store_ys = Ext.create('Ext.data.Store', {
		fields: [
          {type: 'string', name: 'value'},
          {type: 'string', name: 'text'}
      ],
       data:[
		  {'text':'上半年','value':'5'},
		  {'text':'下半年','value':'6'}
	     ],
		autoLoad : true
  });



var field_ys =  {  
	triggerAction: 'all',
    id: 'field_ys',
    fieldLabel: '半年',
    queryMode: 'local',
    editable: false,
    xtype: 'combo',
    store : store_ys,
    displayField:'text',
    valueField:'value',
    value: '5',
    anchor: '100%',
    forceSelection:true
};


//---------------月报--------------------------------

var field_ym = { 
	xtype : 'monthfield', 
	id: 'field_ym',
	fieldLabel : '月 份', 
	value: new Date(),
	format : 'Y年m月',
    listeners: { // 监听选择事件
      select: function() {
        if (this.getValue() != null){
			// Ext.Msg.alert('调试信息',this.getValue());
         } 
      }//end select
  } //end listeners

};
//---------------日报--------------------------------
var field_ymd = { 
	xtype : 'datefield',
	id: 'field_ymd',
	fieldLabel : '日报', 
	value: new Date(),
	format : 'Y年m月d日',
	renderer: Ext.util.Format.dateRenderer('Y年m月d日'),
    listeners: { // 监听选择事件
      select: function() {
        if (this.getValue() != null){
			// Ext.Msg.alert('调试信息',this.getValue());
         } 
      }//end select
  } //end listeners
}
/**
 *
 * d-天 t-旬 m-月  q-季  s-半年  y-年
 * 旬报(ymt),月报(ym),季报(yq),日报(ymd),年报(y),半年报(ys)
 */
var getFreqFieldBy = function(grep){	
	var innerFreqField = null;
	switch (grep)
    {
      case "ymd": //日报
       innerFreqField = field_ymd;
        break;
      case "ym": //月报
       innerFreqField = field_ym;
        break;
      case "yq": //季报
        innerFreqField = [field_y,field_yq];
        break;
	  case "ys": //半年报
        innerFreqField = [field_y,field_ys];
        break; 
	  case "y": //年报
        innerFreqField = [field_y];
        break; 
      default:  //年报
       innerFreqField = [field_y];
    }
    
	return innerFreqField;
}

var replace = function(config,flength) {
    var tfield = Ext.getCmp('rptDateFeield');
    if(flength == 1 || flength == undefined){
		tfield.remove(0);
	}else if(flength == 2){

		tfield.remove(0);
		tfield.remove(0);

	}
    tfield.add(Ext.apply(config));

	if(config.length == 1 || config.length == undefined){
		preFieldLength = 1;
	}else if(config.length == 2){
		preFieldLength = 2;
	}

};


var freqField;
Ext.onReady(function(){   
    Ext.QuickTips.init();
	var grep = freqDispData.dft;
	
   freqField = getFreqFieldBy(grep);
   preFieldLength = freqField.length;
   if(preFieldLength == undefined){
	  //alert(preFieldLength);
   }
});	

/**
 *
 *
 */
Ext.define('Ext.ux.spark.paramView', {
    extend: 'Ext.Component', // subclass Ext.Component
    alias: 'widget.paramView', // this component will have an xtype of 'managedimage'
    freq:  'ymd',
    initComponent: function() {
		var me = this;
		this.tabList = new Array();
		this.tabsCnt = 0;
        Ext.applyIf(this, {
            freq: freq
        });
		this.destroyAllTabs();
		this.createParamPanel();
		this.createRptViewTab();

        this.callParent(arguments);

		//Ext.getCmp('comboDispFreq').setValue('ym');
    },
	createViewport: function(){
		this.viewport = Ext.create('Ext.Viewport', {
                layout:'border',
				baseCls:'my-panel-no-border',
                items: [{
                    id:'rptfields',
                    region:'north',
					frame:false,
					collapsible: true,
					title: '参数页面',
                    split: true,
                    height: 100,
                    minHeight: 75,
                    maxHeight: 150,
                    layout:'fit',
                    margins: '0 0 0 0',
                    items: [this.formPanelParam]
               }, {
                    region:'center',
                    margins: '0 0 0 0',
                    layout:'anchor',

                    items:[{
                        anchor:'100% 100%',
                        baseCls:'x-plain',
                        layout: {
                            type: 'fit',
                            padding: 0
                        },
                        items: [this.rptViewTab]
                    }]
                }]
            });
	
	},
	createRptViewTab: function(){
		 this.rptViewTab = Ext.create('Ext.tab.Panel', {
			 id:'displayRptPanel',
			 name:'displayRptPanel',
			 region:'center',
			 activeTab:0,
			 //defaults: {autoScroll:true},
			 border:'0,0,0,0',
			 frame:false,
			 split:true,
			//anchor:'80%',
			 bodyPadding: 0,
		     items: []    
		 });
	
	},
	createParamPanel: function(){
		var me = this;
	   this.formPanelParam =  Ext.create('Ext.form.Panel', {
        bodyStyle: 'padding:0  0  0 0',
        width: 900,
		baseCls:'my-panel-no-border',
        fieldDefaults: {
            labelAlign: 'right',
            msgTarget: 'side'
        },
        defaults: {
            border: false,
            xtype: 'panel',
            flex: 1,
			msgTarget: 'side',
            layout: 'anchor'
        },

       layout: {
                type: 'hbox',
                padding:'0',
				 pack:'start',
                align:'middle'
                           },
       items: [
				<jsp:include page="/WEB-INF/views/report/paramInc.jsp" flush="true">
					<jsp:param name="parBegin" value="0"/>
			    </jsp:include>
	   {
			padding:'0 5 0 30',
           items: [{
				    text: '查看报表',
					anchor: '35%',
					scale   : 'medium', //large
					xtype: 'button',
					defaultType: 'splitbutton',
					iconAlign: 'left',
					minWidth: 80,
					iconCls: 'rptBtn',
					handler: function() {
						me.setFormRptParams(freq);
						 var form = this.up('form').getForm();
						 var rptParams = RPT_ID+"-"+form.getValues(true);
						 var tbURL = rptView_URL+'&'+form.getValues(true);
						 var freqDisp = comboDispFreq.getRawValue();
						 //if(form.isValid()){showrRptView(RPT_ID,'查看报表',tbURL)}
						 me.showrRptView('view',rptParams,'报表'+RPT_ID+freqDisp,tbURL);
						
						 //Ext.get('displayRptPanel').mask('正在报表查询,请稍候...', 'x-mask-loading');
						 //Ext.Msg.alert('Submitted Values', form.getValues(true));
					}
                
                }]
			}]
		});
	
 },
setFormRptParams : function(freq){
 var fianlValue,fianlCapValue;
  var freqCap = capMap.getByKey(datefield).pcap;

 //{pname:"day",pcap:"dayCap"}
  for (index = 0; index < capMap.getCount(); index++) {
		try{
		var fitem = capMap.getAt(index);
		var fkey = fitem.pname;
		//alert('name:'+fitem.pname+'---Cap :'+fitem.pcap +'--->>value::'+Ext.getCmp(fitem.pname).getRawValue());
		var fieldValue = Ext.getCmp(fitem.pname).getRawValue();
		Ext.getCmp(fitem.pcap).setValue(fieldValue);
		}catch(err){err.description}
   }



	switch (freq)
    {
      case "ymd": //日报
        var iday = Ext.getCmp('field_ymd').getValue();
		//var gedt = dateFormat(iday, 'yyyymmdd');
		var gedt = Ext.Date.format(iday, 'Ymd');
		Ext.getCmp(datefield).setValue(gedt);
		fianlValue = gedt;
		fianlCapValue = Ext.getCmp('field_ymd').getRawValue();
        break;
      case "ym": //月报

		var imonth = Ext.getCmp('field_ym').getValue();
		var gemdt = Ext.Date.format(imonth, 'Ym');
		fianlCapValue = Ext.getCmp('field_ym').getRawValue();
		fianlValue = gemdt;

		break;
      case "yq": //季报
		var iyear = Ext.getCmp('field_y').getValue();
		var iyearCap = Ext.getCmp('field_y').getRawValue();
		var iq = Ext.getCmp('field_yq').getValue();
		var iqCap = Ext.getCmp('field_yq').getRawValue();
		fianlValue = iyear + iq;
		fianlCapValue = iyearCap + iqCap;
        break;
	  case "ys": //半年报
       	var iyear = Ext.getCmp('field_y').getValue();
		var iyearCap = Ext.getCmp('field_y').getRawValue();
		var is = Ext.getCmp('field_ys').getValue();
		var isCap = Ext.getCmp('field_ys').getRawValue();
		fianlValue = iyear + is;
		fianlCapValue = iyearCap + isCap;
        break; 
	  case "y": //年报
        fianlValue = Ext.getCmp('field_y').getValue();
		fianlCapValue = Ext.getCmp('field_y').getRawValue();
        break; 
      default:  //年报
        fianlValue = Ext.getCmp('field_y').getValue();
		fianlCapValue = Ext.getCmp('field_y').getRawValue();
    }
	Ext.getCmp(datefield).setValue(fianlValue);
	Ext.getCmp(freqCap).setValue(fianlCapValue);



},
	
 showrRptView : function(op,tab_id,title,ifmurl){
	var me = this;
	var tabURL = ifmurl+"&op="+op;
 	var uid = tab_id;
 	var currentTab = this.rptViewTab.getComponent(uid);
 	if(!currentTab)
 	{
		if (me.tabsCnt>=6)
		{
			Ext.MessageBox.buttonText.ok="确定";
			Ext.MessageBox.alert('--提示信息--',"您打开的页签超过出系统限制,请关闭其他页签!");
			return;
		}
	
	    var currentTab = this.rptViewTab.add({'id' : uid,'title' : title,
			  listeners: {beforedestroy : me.handleDestroy},
					tbar:[{
						text:'补录',
						disabled:false,
						handler: function() {
							var ct = me.rptViewTab.getActiveTab();
							ct.body.dom.all[0].contentWindow.fillInData();

						}
					}],
					closable:true,
					html : '<iframe id="rptviewer_${rptid}" scrolling="auto" frameborder="0" width="100%" height="100%" src="'+tabURL+'"></iframe>'
			});
			me.tabList[me.tabList.length] = currentTab;
			me.tabsCnt++;
 	}
 	this.rptViewTab.setActiveTab(currentTab);
	
  },
   
  handleDestroy: function(tabx){
	  var me = this;
	try
	{
		me.tabsCnt--;
		if( typeof(tabx.body.dom.all[0].contentWindow.content.fillInData) == 'function' )
		{				
			//sentKill( tabx.body.dom.all[0].contentWindow.content.getEngineID() )
		}
		for( i=0 ; i<me.tabList.length ; i++ )
		{
			var tn = me.tabList[i];
			if(tn.id == tabx.id )
			{
				me.tabList[i] = 0;
				break;
			}
		}
	}
	catch(e){}
},
destroyAllTabs :function(){
	 var me = this;
	for( i=0 ; i < me.tabList.length ; i++ ){
		var tn = me.tabList[i];
		if( tn != 0 ){
			handleDestroy(tn);
		}
	}
},

replace : function(config, name) {
        var tfield = Ext.getCmp('rptDateFeield');
        if (name && name != currentName) {
            currentName = name;
            tfield.remove(0);
            tfield.add(Ext.apply(config));
        }
},
test : function(tab_id){
		alert(tab_id);
  }



});



Ext.application({
    name: 'rptviewer',
    appFolder: 'app',
    launch: function() {
		var viewport = Ext.create('Ext.ux.spark.paramView',{freq:freq});
		viewport.createViewport();
    }
});





Ext.define('SMS.view.Header', {
    extend: 'Ext.Component',
    initComponent: function() {
        Ext.applyIf(this, {
            xtype: 'box',
            cls: 'header',
            region: 'north',
            html: '<h1>111</h1>',
            height: 30
        });
        this.callParent(arguments);
    }
});

</script>




<body></body>