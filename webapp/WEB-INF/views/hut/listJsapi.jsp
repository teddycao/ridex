<%@ page language="java" contentType="text/javascript; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>



var qSPannel;
var hutGrid;
var dlgWidth = 500;
var dlgHeight = 400;

<c:if test="${crudViewMeta.dlgWidth !=null}">
	dlgWidth = ${crudViewMeta.dlgWidth};
</c:if>

<c:if test="${crudViewMeta.dlgHeight !=null}">
	dlgHeight = ${crudViewMeta.dlgHeight};
</c:if>


var filterTxt = '${param.filterTxt}';
var filterValue = '${param.filterValue}';

//${meta.beanName}
	//相应列没有设置字典
//----------------------------------------------------
 <c:forEach items="${meta.columnMetas}" var="col">

  		<c:if test="${col.dictName!=null}">
		  var dataArray_${col.colName} = [<c:forEach var="map" items="${col.dictMap}" varStatus="sta">										
						['${map.value}','${map.key}']
				  <c:if test="${not sta.last}">,</c:if>
			  </c:forEach>];

		 var sel_${col.colName} = new Ext.data.SimpleStore({
          fields: ['name', 'val'],
          data : dataArray_${col.colName}
		});



var renderColAction_${col.colName} = function(value, cellmeta, record, rowIndex, columnIndex, store){
  var elems = dataArray_${col.colName};
  for ( var i = 0, length = elems.length; i < length; i++ ){
	  if(elems[i][1] == value){
		  return elems[i][0];
	  }
  }
  return value;
} 


	</c:if>
 </c:forEach>	





 var formNewConfig =[{
                xtype: "panel",
	            //html: "<img src=${ctx}/images/pics/Action-view-right-icon48.png><br>",
				inonCls: 'form_tip'
				},{
            xtype:'fieldset',
			layout:'form',
			useType:'new',
            title: '${meta.title}新增记录',
            collapsible: true,
            autoHeight:true,
            width:dlgWidth-50,
            defaults: {width: 130},
            defaultType: 'textfield', 
			items :[
				{ xtype:'textfield',id:'name', name:'name',value:'${param.name}', hidden:true, hideLabel:true},
		//根据定生成对应的类型
     	 <c:forEach items="${meta.columnMetas}" var="col" varStatus="sta">
	  		<c:if test="${col.showInAddForm}">
			<c:choose>

				<c:when test="${col.viewType eq 'textfield'}">
				{
					 fieldLabel: '${col.title}', name: '${col.colName}',
					  <c:if test="${col.config!=null}">${col.config},</c:if>
					 labelStyle: 'font-weight:bold;',
					  <c:if test="${col.readOnly}">readOnly:true,</c:if>
					 <c:if test="${col.value!=null}">value:'${col.value}',</c:if>
					 width: 180
					  
					
				},
				</c:when>

				<c:when test="${col.viewType eq 'combo'}">
				{
				 xtype: 'combo',fieldLabel: '${col.title}',hiddenName: '${col.colName}',
				 value: <c:if test="${map.key == status.value}">'${map.key}'</c:if>,
				 <c:if test="${col.config!=null}">${col.config},</c:if>
				 <c:if test="${not col.allowBlank}">allowBlank:false,</c:if>
			     typeAhead: true,triggerAction: 'all',displayField:'name',valueField:'val',
			     store: sel_${col.colName}, mode: 'local',editable: false,
					  <c:if test="${col.readOnly}">readOnly:true,</c:if>
					   labelStyle: 'font-weight:bold;'
				},

				</c:when>

			 <c:when test="${col.viewType eq 'datefield'}">
				{
				 xtype: 'datefield',fieldLabel: '${col.title}',
				 emptyText: '请选择日期',name:"${col.colName}", value:new Date(),
				  <c:if test="${col.config!=null}">${col.config},</c:if>
					 <c:if test="${col.primaryKey}">readOnly:true,</c:if>
					  labelStyle: 'font-weight:bold;'

				},
			 </c:when>

			 <c:when test="${col.viewType eq 'textarea'}">
				{
				 xtype: 'textarea',fieldLabel: '${col.title}',
				 emptyText: '请输入${col.title}信息',name:"${col.colName}",
				  <c:if test="${col.config!=null}">${col.config},</c:if>
					 <c:if test="${col.primaryKey}">readOnly:true,</c:if>
					  labelStyle: 'font-weight:bold;'

				},
			 </c:when>
				<c:otherwise>
				  			{ xtype:'textfield',id:'${col.colName}', name:'${col.colName}',value:'${col.value}', hidden:true, hideLabel:true},
				</c:otherwise>


			</c:choose>

	  		</c:if>
			
	  	</c:forEach>
		{xtype:'hidden', name:'${col.colName}_hidden_cover'}
    ]}];


var formEditConfig =[{
                xtype: "panel",
	            //html: "<img src=${ctx}/images/pics/Action-view-right-icon48.png><br>",
				inonCls: 'form_tip'
				},{
            xtype:'fieldset',
			layout:'form',
			useType:'edit',
             title: '${meta.title}修改记录',
			//iconCls:'findIcon',
            collapsible: true,
            autoHeight:true,
            width:dlgWidth-50,
            defaults: {width: 150,  msgTarget: 'side'},
			layoutConfig: {labelSeparator: ':'},
            defaultType: 'textfield',
			items :[
				{
                xtype: 'panel',
                html: ""
				},
				{ xtype:'textfield',id:'name', name:'name',value:'${param.name}', hidden:true, hideLabel:true},
		//根据定生成对应的类型
     	 <c:forEach items="${meta.columnMetas}" var="col" varStatus="sta">
	  		<c:if test="${col.showInEditForm}">
			<c:choose>

			 	<c:when test="${col.viewType eq 'textfield'}">
				{
					 fieldLabel: '${col.title}', name: '${col.colName}',width: 180,
					  <c:if test="${col.config!=null}">${col.config},</c:if>
					  <c:if test="${col.primaryKey}">readOnly:true,</c:if>
					   labelStyle: 'font-weight:bold;'
					
				},
				</c:when>

				<c:when test="${col.viewType eq 'combo'}">
				{
				 xtype: 'combo',fieldLabel: '${col.title}',hiddenName: '${col.colName}',
				 value: <c:if test="${map.key == status.value}">'${map.key}'</c:if>,
				 <c:if test="${col.config!=null}">${col.config},</c:if>
				 <c:if test="${not col.allowBlank}">allowBlank:false,</c:if>
				 <c:if test="${col.primaryKey}">readOnly:true,</c:if>
			     typeAhead: true,triggerAction: 'all',displayField:'name',valueField:'val',
			     store: sel_${col.colName}, mode: 'local',editable: false,
					 labelStyle: 'font-weight:bold;'
				},
				</c:when>
				<c:when test="${col.viewType eq 'textarea'}">
				{
				 xtype: 'textarea',fieldLabel: '${col.title}',
				 emptyText: '请输入${col.title}信息',name:"${col.colName}",
				  <c:if test="${col.config!=null}">${col.config},</c:if>
					 <c:if test="${col.primaryKey}">readOnly:true,</c:if>
					  labelStyle: 'font-weight:bold;'
				},
			 </c:when>
		
			 <c:when test="${col.viewType eq 'datefield'}">
				{
				 xtype: 'datefield', name:"${col.colName}",  
				 fieldLabel: '${col.title}',
				 emptyText: '请选择日期', value:new Date(),
				  <c:if test="${col.config!=null}">${col.config},</c:if>
					 <c:if test="${col.primaryKey}">readOnly:true,</c:if>
					  labelStyle: 'font-weight:bold;'
				},
			 </c:when>

				<c:otherwise>
					{ xtype:'textfield',id:'${col.colName}', name:'${col.colName}',value:'${col.value}', hidden:true, hideLabel:true},
				</c:otherwise>
			</c:choose>

	  		</c:if>
			
	  	</c:forEach>
		{xtype:"hidden", name:"${col.colName}_hidden_cover"}
     ]}];
 //---------------------------------------------------------
var formViewConfig = [
     	<c:forEach items="${meta.columnMetas}" var="col" varStatus="sta">
				 {fieldLabel: '${col.title}',hideGrid:${!col.showInList}, name: '${col.colName}',width: 120,mapping:'${col.colName}',sortable: ${col.sortable},
				 <c:if test="${col.renderer!=null}">renderer:renderColAction_${col.colName},</c:if>
				  cover:'balnk'
				 }
			<c:if test="${not sta.last}">,</c:if>
	  	</c:forEach>
    ];

var primaryKey = [
		<c:forEach items="${meta.columnMetas}" var="col" varStatus="sta">
	  		<c:if test="${col.primaryKey}">'${col.colName}'</c:if>
			<c:if test="${not sta.last && col.primaryKey}">,</c:if>
	  	</c:forEach>null];



var recordMapping =[
		<c:forEach items="${meta.columnMetas}" var="col" varStatus="sta">
	  		<c:if test="${col.showInEditForm}">
			{name: '${col.colName}', mapping: '${col.colName}'}</c:if>
			<c:if test="${not sta.last}">,</c:if>
	  	</c:forEach>];

 
 /**
  *生成查询界面
  */
var filterBar =   [
	<c:if test="${crudViewMeta.filterWindowEnabled}">
				{ xtype:'textfield',id:'filter_${col.colName}', name:'filter_${col.colName}',value:'${param.name}', hidden:true, hideLabel:true},
		//根据定生成对应的类型
     	 <c:forEach items="${meta.columnMetas}" var="col" varStatus="sta">
	  		<c:if test="${col.useAsDataFilter}">
			'&nbsp;&nbsp;<span style="font-size:12px;">${col.title}：</span>',
			<c:choose>
				<c:when test="${col.viewType eq 'combo'}">
				{xtype: 'combo',fieldLabel: '${col.title}',hiddenName: 'filter_${col.colName}',id:'filter_${col.colName}', value: <c:if test="${map.key == status.value}">'${map.key}'</c:if>,
			     typeAhead: true,triggerAction: 'all',displayField:'name',valueField:'val',
			     store: sel_${col.colName}, mode: 'local',editable: false,width: 100},
				</c:when>

			<c:when test="${col.viewType eq 'datefield'}">
						{
				 xtype: 'datefield',name:"filter_${col.colName}",id:'filter_${col.colName}',
				fieldLabel: '${col.title}',
				width: 100,allowBlank:true,
				  <c:if test="${col.config!=null}">${col.config},</c:if>
					  emptyText: '请选择日期', value:new Date()
				},
			</c:when>

			<c:when test="${col.viewType eq 'checkbox'}">
				{xtype: 'checkbox',fieldLabel: '${col.title}',
				 name: 'filter_${col.colName}',id:'filter_${col.colName}',
				 value:new Date(),
			      false,width: 100},
			</c:when>

				<c:otherwise>
					{xtype:'textfield',fieldLabel: '${col.title}',id:'filter_${col.colName}',id:'filter_${col.colName}', name: 'filter_${col.colName}',width: 100,cls: 'x-btn-text-icon'},
				</c:otherwise>

			</c:choose>

	  		</c:if>
			
	  	</c:forEach>
			'-',{text: '&nbsp;&nbsp;查询&nbsp;',
						scale: 'medium',
                        iconCls: 'findIcon',
                        cls: 'x-btn-text-icon',
						handler:fnQueryHandler
				},
				/**,{
                        text: '&nbsp;全部',
						scale: 'medium',
                        iconCls: "ref_all",
                        cls: "x-btn-text-icon",
                        handler: function(){
					
						  var allRecords = {name:'${meta.beanName}',filterTxt:'',filterValue:''};
                          hutGrid.queryGridByParams(allRecords);   
                        }
		                    }*/
			<c:if test="${crudViewMeta.dataImportEnabled}">{
				text:'&nbsp;文件上传&nbsp;',
				scale: 'medium',
				handler : function() {
		            fileUploadWin.showBatchWin();
		         },
				icon: "${ctx}/images/upload-icon24.png"
			},</c:if>
			'->',{xtype:'label',text:'${meta.title}'}
	</c:if>		
    ];

var cfgToolBarItems = [
	<c:if test="${crudViewMeta.addEnabled}">	
	  {
            id      : 'add',
            text    : '新增',
            iconCls : 'add',
            tooltip : '新增',
            handler : function() {
				hutGrid.add();
			}
        },'-',
	</c:if>
	<c:if test="${crudViewMeta.editSingleEnabled}">	
	  {
			id      : 'edit',
            text    : '修改',
            iconCls : 'edit',
            tooltip : '修改',
            handler : function() {
				hutGrid.edit();
			}
        },'-',
	</c:if>

	<c:if test="${crudViewMeta.deleteSingleEnabled}">	
	  {
            id      : 'del',
            text    : '删除',
            iconCls : 'delete',
            tooltip : '删除',
            handler : function() {
				hutGrid.del();
			}
        },'-',
	</c:if>

		'&nbsp;'];

 function fnQueryHandler(){
		//使用id来取值
     var queryParams = {filterTxt:'',filterValue:'',
		 <c:forEach items="${meta.columnMetas}" var="col" varStatus="sta">
			<c:if test="${col.useAsDataFilter}">
			  ${col.colName}: Ext.getCmp('filter_${col.colName}').getValue(),
			 </c:if>
		 </c:forEach>
	 name:'${meta.beanName}'};
			 
	   hutGrid.queryGridByParams(queryParams);
          
	}




// 扩展窗体
var fileUploadWin = function(){
    var curFormWin;
    return {
	 width : 600,
      height : 380,
	  showBatchWin : function() {
            // 显示添加子目录窗口
            var win_url =  "${ctx}/s/hut/batchUploadView.do?name=${meta.beanName}";
            var window = this.createWin("windirnew", "${meta.beanName}操作", win_url, function() {
                grid.reload();
            });
            window.show();
        },
	
		createWin : function(winId, winTitle, iframePage, closeFun) {
            // 供各类型窗口创建时调用
            var win = Ext.getCmp(winId);
            if (!win) {
                win = new Ext.Window({
                    id : winId,
                    title :  winTitle,
                    width : this.width,
                    height : this.height,
                    maximizable : true,
					layout:'fit',
					plain:true,
					maximizable: false, 
					resizable:false, 
                    modal : true,
                    html : "<iframe width='100%' height='100%' frameborder='0' src='"
                            + iframePage + "'></iframe>"
                });
                closeFun;
            }
            curFormWin = win;
            return win;
        },
		gridReload : function() {
			grid.store.reload();  
		},
		close : function() {
            if(curFormWin){
                curFormWin.close();
            }
        }
	}
}();
