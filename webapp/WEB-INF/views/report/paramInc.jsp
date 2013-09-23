<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   {items: [comboDispFreq] },
   {
        id:'rptDateFeield',
		xtype: 'fieldcontainer',
        fieldLabel: '报表日期',
        combineErrors: true,
        msgTarget : 'side',
        layout: 'hbox',
        defaults: {
            flex: 1,
            hideLabel: true
        },
        items: [
            freqField
        ]
   },

<c:forEach items="${rptParams}" var="par" varStatus="status" begin="${param.parBegin}" step="1"> 

<c:choose>
  <c:when test="${ (par.PARAM_TYPE eq 'textfield') or (par.PARAM_TYPE eq 'datefield')}">
 	{
     items: [{
	 id:'${par.PARAM_NAME}',
     name:'${par.PARAM_NAME}',
	 labelAlign: 'right',
	 xtype:'${par.PARAM_TYPE}',
     fieldLabel: '${par.PARAM_TITLE}',
	 allowBlank:false,
     //anchor: '100%',
	 maxLength: 100}]
    },


 </c:when>

 <c:when test="${par.PARAM_TYPE eq 'combo' && par.PARAM_NAME ne 'freq'}">
     	 { items: [field_${par.PARAM_NAME}] },
 </c:when>

 <c:when test="${par.PARAM_TYPE eq 'hidden' || par.PARAM_TYPE eq 'datepicker'}">
   { xtype:'textfield',id:'${par.PARAM_NAME}', name:'${par.PARAM_NAME}',value:'', hidden:true, hideLabel:true},
 </c:when>

</c:choose>

 <c:if test="${par.PARAM_TIP != null && par.PARAM_TIP ne ''}">
	{ xtype:'textfield',id:'${par.PARAM_TIP}', name:'${par.PARAM_TIP}',value:'', hidden:true, hideLabel:true},
 </c:if>

	<%--<c:if test="${not status.last}">,</c:if>--%>
</c:forEach>

