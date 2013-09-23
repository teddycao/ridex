<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
items :[
		 { xtype:'textfield',id:'name', name:'name',value:'${param.name}', hidden:true, hideLabel:true},
		//根据定生成对应的类型
     	 <c:forEach items="${param.meta.columnMetas}" var="col" varStatus="sta">
	  		<c:if test="${col.showInAddForm}">
			<c:choose>
				<c:when test="${col.viewType eq 'combo'}">
				{xtype: 'combo',fieldLabel: '${col.title}',hiddenName: '${col.colName}', value: <c:if test="${map.key == status.value}">'${map.key}'</c:if>,
			     typeAhead: true,triggerAction: 'all',displayField:'name',valueField:'val',
			     store: sel_${col.colName}, mode: 'local',editable: false}
				</c:when>


				<c:otherwise>
					{fieldLabel: '${col.title}', name: '${col.colName}',width: 180}
				</c:otherwise>
			</c:choose>

	  		</c:if>
			<c:if test="${not sta.last}">,</c:if>
	  	</c:forEach>
		
    ]