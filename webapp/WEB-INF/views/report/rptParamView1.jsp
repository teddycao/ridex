<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>报表展示</title>
<%@ include file="/commons/meta-ext4.jsp" %>
<link rel="stylesheet" type="text/css"  href="${ctx}/styles/ext-patch.css" />
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
  Ext.Loader.setConfig({enabled: true});
  Ext.Loader.setPath('Sparks', '${ctx}/js/Sparks');
  	Ext.require([
    'Ext.form.Panel',
	'Ext.tab.*',
    'Ext.layout.container.Anchor'
]);
  Ext.require('Sparks.views.UsersGridPanel');


 var RPT_ID='${rptid}';
 var rptView_URL = '${ctx}/s/rpt/reportviewer.do?rptid=${rptid}';
 var tabsCnt = 0;
 var currentName,viewport ;

<c:forEach items="${params}" var="par" varStatus="status">  
 <c:if test="${par.PARAM_TYPE eq 'combo'}">
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
var combo_${par.PARAM_NAME} = Ext.create('Ext.form.field.ComboBox', {
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

     
var formPanelParam =  Ext.create('Ext.form.Panel', {
        bodyStyle: 'padding:0px 0px 0',
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
					<jsp:param name="parBegin" value="1"/>
			    </jsp:include>
	   ,{
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
						 var form = this.up('form').getForm();
						 var tbURL = rptView_URL+'&'+form.getValues(true);
						 //if(form.isValid()){showrRptView(RPT_ID,'查看报表',tbURL)}
							//Ext.get('displayRptPanel').mask('正在报表查询,请稍候...', 'x-mask-loading');
						  Ext.Msg.alert('Submitted Values', form.getValues(true));
					}
                
                }]
        }]
    });


 



var showrRptView = function(tab_id,title,ifmurl){

 	var uid = tab_id;
 	var n = rptViewTab.getComponent(uid);
 	if(!n)
 	{
		if (tabsCnt>=10)
		{
			Ext.MessageBox.buttonText.ok="确定";
			Ext.MessageBox.alert('--提示信息--',"您打开的页签超过出系统限制,请关闭其他页签!");
			return;
		}
	
	    var n = rptViewTab.add({'id' : uid,'title' : title,
					//listeners: {beforedestroy : handleDestroy},
					
					closable:true,
					html : '<iframe id="rptviewer_${rptid}" scrolling="auto" frameborder="0" width="100%" height="100%" src="'+ifmurl+'"></iframe>'
					});
			//tabList[tabList.length] = n;
			tabsCnt++;
 	}
 	rptViewTab.setActiveTab(n);
	
};



var rptViewTab = Ext.create('Ext.tab.Panel', {
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


Ext.onReady(function() {
	 Ext.tip.QuickTipManager.init();
	 var app = new Sparks.views.UsersGridPanel();
            viewport = Ext.create('Ext.Viewport', {
                layout:'border',
				baseCls:'my-panel-no-border',
                items: [{
                    id:'btns',
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
                    items: [formPanelParam]
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
                        items: [rptViewTab]
                    }]
                }]
            });

})

    var replace = function(config, name) {
        var btns = Ext.getCmp('freq');
        if (name && name != currentName) {
            currentName = name;
            btns.remove(0);
            btns.add(Ext.apply(config));
        }
    };

//throw new Error('['+ Ext.getDisplayName() +'] Some message here');
</script>




<body></body>