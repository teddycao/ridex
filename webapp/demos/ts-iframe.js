xt.ux.TestIframePanel = Ext.extend(Ext.ux.ManagedIFramePanel, {  
      
    initComponent: function(){  
          
        this.tbar = [{  
            text: 'Send',  
            scope: this,  
            handler: function(){  
                this.fireEvent('send');               
            }  
        }];  
          
        Ext.ux.TestIframePanel.superclass.initComponent.call(this);  
          
          
        this.addEvents('send', 'receive');  
    },  
      
      
    initEvents: function(){  
        Ext.ux.TestIframePanel.superclass.initEvents.call(this);  
          
        this.on('send', function(){  
            alert('I want to send message to embedded page: test.html');  
        }, this);  
          
        this.on('receive', function(){  
            alert('I want to receive message from embedded page');  
        });  
    }  
});  
  
Ext.onReady(function(){  
    Ext.QuickTips.init();  
      
    var iframePanel = new Ext.ux.TestIframePanel({  
        defaultSrc: 'test.html',  
        disableMessaging: false,  
        renderTo: 'test-iframe',  
        width: 600,  
        height:400  
    });  
});  





<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">  
    <title>Test Iframe</title>  
    <link rel="stylesheet" type="text/css" href="../resources/css/ext-all.css" />  
    <!-- LIBS -->  
    <script type="text/javascript" src="../js/core/adapter/ext-base.js"></script>  
    <script type="text/javascript" src="../js/core/ext-all.js"></script>  
      
    <!-- Test extensions  -->  
    <script type="text/javascript" src="../js/extensions/Ext.ux.ManagedIFrame.js"></script>  
    <script type="text/javascript" src="../js/extensions/mifmsg.js"></script>  
    <script type="text/javascript" src="ts-iframe.js"></script>  
</head>  
<body>  
<div id="test-iframe"></div>  
</body>  
</html>  