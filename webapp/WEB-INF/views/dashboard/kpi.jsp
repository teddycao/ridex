<html>
<%@ page language="java" pageEncoding="gb2312" autoFlush="true"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/extcss/main.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/extscrpit3/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/extscrpit3/ux/css/Portal.css" />
<script type="text/javascript" src="<%=basePath%>/extscrpit3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=basePath%>/extscrpit3/ext-all.js"></script>
<script type="text/javascript" src="<%=basePath%>/extscrpit3/ux/Portal.js"></script>
<script type="text/javascript" src="<%=basePath%>/extscrpit3/ux/PortalColumn.js"></script>
<script type="text/javascript" src="<%=basePath%>/extscrpit3/ux/Portlet.js"></script>
<script type="text/javascript" src="<%=basePath%>/extscrpit3/RowExpander.js"></script>
<script type="text/javascript">Ext.BLANK_IMAGE_URL = '<%=basePath%>/extscrpit3/resources/images/default/s.gif'</script>
<div id="loading-mask" style=""></div>
		<div id="loading">
			<div class="loading-indicator">
				<img src="<%=basePath%>/app/img/extanim32.gif" width="32" height="32" style="margin-right:8px;" align="absmiddle" />
				Loading...
			</div>
		</div>
<SCRIPT LANGUAGE="JavaScript">
<!--
setTimeout(function() {
					Ext.get('loading').remove();
					Ext.get('loading-mask').fadeOut({remove:true});
				}, 250
			  );
//-->
</SCRIPT>

<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<head>
    <title>Custom Layouts and Containers - Portal Example</title>
</head>

<body>
<SCRIPT LANGUAGE="JavaScript">	
<!--
Ext.onReady(function(){

    // NOTE: This is an example showing simple state management. During development,
    // it is generally best to disable state management as dynamically-generated ids
    // can change across page loads, leading to unpredictable results.  The developer
    // should ensure that stable state ids are set for stateful components in real apps.
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    // create some portlet tools using built in Ext tool ids
    var tools = [{
        id:'gear',
        handler: function(){
            Ext.Msg.alert('Message', 'The Settings tool was clicked.');
        }
    },{
        id:'close',
        handler: function(e, target, panel){
            panel.ownerCt.remove(panel, true);
        }
    }];

    var viewport = new Ext.Viewport({
        layout:'border',
        items:[{
            xtype:'portal',
            region:'center',
            margins:'35 5 5 0',
            items:[{
                columnWidth:.50,
                style:'padding:10px 0 10px 10px',
                items:[{
                    title: 'ȫ���ܴ��',
                    tools: tools,
                    height: 380,
                    html:'<iframe id ="hp" scrolling="auto" frameborder="0" width="100%" height="100%" src="<c:url value='/app/dashboard.jsp?reportCode=nc_dsboard_01&height=300&width=450'/>"></iframe>'
                },{
                    title: '���������������',
                    tools: tools,
                    height: 380,
                   html: '<iframe id ="hp" scrolling="auto" frameborder="0" width="100%" height="100%" src="<c:url value='/app/chart.jsp?reportCode=nc_dsboard_21&height=300&width=450&reportType=2&chartType=Line'/>"></iframe>'
                }] 
            },{
                columnWidth:.50,
                style:'padding:10px',
                items:[{
                    title: '�ܴ�����������',
                    tools: tools,
                    height: 380,
                    html: '<iframe id ="hp" scrolling="auto" frameborder="0" width="100%" height="100%" src="<c:url value='/app/chart.jsp?reportCode=nc_dsboard_321&height=300&width=450&chartType=Column2D&reportType=2'/>"></iframe>'
                },{
                    title: '���˴�����;',
                    tools: tools,
                    height: 380,   
                    html: '<iframe id ="hp" scrolling="auto" frameborder="0" width="100%" height="100%" src="<c:url value='/app/chart.jsp?reportCode=M_CL013&height=300&width=550&chartType=Pie3D&reportType=2'/>"></iframe>'
                }]
            }]
            
            /*
             * Uncomment this block to test handling of the drop event. You could use this
             * to save portlet position state for example. The event arg e is the custom 
             * event defined in Ext.ux.Portal.DropZone.
             */
//            ,listeners: {
//                'drop': function(e){
//                    Ext.Msg.alert('Portlet Dropped', e.panel.title + '<br />Column: ' + 
//                        e.columnIndex + '<br />Position: ' + e.position);
//                }
//            }
        }]
    });
});
//-->
</SCRIPT>
</body>
</html>