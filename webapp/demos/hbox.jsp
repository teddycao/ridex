<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>报表展示</title>
<%@ include file="/commons/meta-ext4.jsp" %>
<link rel="stylesheet" type="text/css"  href="${ctx}/styles/ext-patch.css" />

    <style type="text/css">
        html, body {
            font: normal 12px verdana;
            margin: 0;
            padding: 0;
            border: 0 none;
        }
    </style>
    <script type="text/javascript">
	Ext.require([
    'Ext.form.Panel',
	'Ext.tab.*',
    'Ext.layout.container.Anchor'
]);

    var currentName,
        viewport;
    var replace = function(config, name) {
        var btns = Ext.getCmp('freq');
        if (name && name != currentName) {
            currentName = name;
            btns.remove(0);
            btns.add(Ext.apply(config));
        }
		btns.doLayout();
    };

var btns ={
                xtype:'textfield',
                fieldLabel: '机构选择',
				 anchor: '-1',
                name: 'first'
            };


var formPanel =     Ext.create('Ext.form.Panel', {
        //renderTo: Ext.getBody(),

        bodyStyle: 'padding:0px 0px 0',
        width: 900,
        fieldDefaults: {
            labelAlign: 'right',
            msgTarget: 'side'
        },
        defaults: {
            border: false,
            xtype: 'panel',
            flex: 1,
            layout: 'anchor'
        },

       layout: {
                type: 'hbox',
                padding:'0',
				 pack:'start',
                align:'middle'
                           },
       items: [

	   {
           items: [{
                xtype:'textfield',
                fieldLabel: '频率',
				 anchor: '-10',
                name: 'first'
            }]
        },{id:'freq',
            items: [{
				
                xtype:'textfield',
                fieldLabel: '频率',
               anchor: '100%',
                name: 'first'
            }]
        },{
            items: [{
                xtype:'textfield',
                fieldLabel: '机构',
                anchor: '100%',
                name: 'first'
            }, {
                xtype:'textfield',
                fieldLabel: '频率',
                  anchor: '100%',
                name: 'company'
            }]
        },{
            items: [{
                xtype:'textfield',
                fieldLabel: '频率',
               anchor: '100%',
                name: 'first'
            }]
        },{
            items: [{
                xtype:'textfield',
                fieldLabel: '频率',
               anchor: '100%',
                name: 'first'
            }]
        },{
            items: [{
                xtype:'textfield',
                fieldLabel: '频率',
               anchor: '100%',
                name: 'first'
            }]
        },{padding:'0 5 0 30',
           items: [{
            xtype:'button',
            scale: 'medium',
            text: '查看报表',
			align:'middle',
			handler: function(){
			replace(btns, 'rptview');
			}
			//anchor:'30% 100%'
              }]
        }]
    });


var rptViewTab = Ext.create('Ext.tab.Panel', {
	 id:'displayRptPanel',
	 name:'displayRptPanel',
	 region:'center',
	 activeTab:0,
	 //defaults: {autoScroll:true},
	 border:'0,0,0,0',
	 frame:false,
	//anchor:'80%',
	 bodyPadding: 0,
     items: [{
             title: '查看报表',
            closable: false
        }]
});
 
 Ext.onReady(function() {



            viewport = Ext.create('Ext.Viewport', {
                layout:'border',
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
                    margins: '5 5 0 5',
                    items: [formPanel]
               }, {
                    region:'center',
                    margins: '0 5 5 5',
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
        });
    </script>
</head>
<body>
</body>
</html>
