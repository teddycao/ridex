<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<%@ include file="/commons/meta.jsp" %>

<script type="text/javascript">Ext.ns("App");</script>
<script src="${ctx}/scripts/ux/Ext.ux.PageSizePlugin.js"></script>
<script src="${ctx}/scripts/ux/Ext.lingo.TreeField.js"></script>

<title>用户管理</title>
</head>
<body>
<div id="contentview" align="center">
</div>
<%
String userName = request.getParameter("username");
%>

<script type="text/javascript">
<!--


Ext.onReady(function(){
       Ext.BLANK_IMAGE_URL = '../resources/images/default/s.gif';
        var MIF = new Ext.ux.ManagedIFramePanel({
                    border: false,
                    bodyBorder: false,
                    defaultSrc:'index.html',
                    listeners:{
                        domready : function(frame){
                              var fbody = frame.getBody();
                              var w = Ext.getCmp('myFrameWin');
                              if(w && fbody){
                                   //calc current offsets for Window body border and padding
                                  var bHeightAdj = w.body.getHeight() - w.body.getHeight(true);
                                  var bWidthAdj  = w.body.getWidth()  - w.body.getWidth(true);
                                  //Window is rendered (has size) but invisible
                                  w.setSize(Math.max(w.minWidth || 0, fbody.scrollWidth  +  w.getFrameWidth() + bWidthAdj) ,
                                            Math.max(w.minHeight || 0, fbody.scrollHeight +  w.getFrameHeight() + bHeightAdj) );
                                  //then show it sized to frame document
                                  w.show();
                              }
                        }
                    }
                });
        var windowFrame = new Ext.Window({
                    title: name,
                    width: 400,   //give it something to start with until the frame renders
                    height: 600,
                    hideMode:'visibility',
                    id:'myFrameWin',
                    hidden : true,   //wait till you know the size
                    title: 'Sized To Frame Document',
                    plain: true,
                    constrainHeader: true,
                    minimizable: true,
                    ddScroll: false,
                    border: false,
                    bodyBorder: false,
                    layout: 'fit',
                    plain:true,
                    maximizable: true,
                    buttonAlign:'center',
                    items:MIF
                });
        windowFrame.render(Ext.getBody());


		tab = this.add({
		    xtype       : 'iframepanel',
		    id          : menuInfo.id,
		    title		: menuInfo.text,
		    tabTip		: menuInfo.text,
		    closable	: true,
		    loadMask  	: true,
		    frame		: true,
		    frameConfig	: {autoCreate:{id: 'frame-' + menuInfo.id}},
		    defaultSrc 	: menuInfo.url,
		    layout		: 'fit',
		    margins		: '5 5 1 5'
});


    });

//-->
</script>
</body>
</html>