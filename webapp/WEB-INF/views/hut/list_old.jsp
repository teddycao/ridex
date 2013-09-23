<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType = "text/html; charset=UTF-8" %>
<%@ include file="/commons/meta.jsp" %>
<html>
<head>

<c:set var="cssFileName">
	<c:out value="${crudViewMeta.cssFileName}" default="default.css"/>
</c:set>
<link rel="stylesheet" href="<c:url value='/css/${cssFileName}'/>" type="text/css">

<title><c:out value="${result.crudMeta.title}"/>数据列表</title>

<script type="text/javascript">

<!--页面跳转处理-->
function turnToPage()
{
	var inputPage=document.all.item("inputPage").value;
	var theNrOfPages=document.all.item("theNrOfPages").value;
	if(isNaN(inputPage))
	{
		alert("请输入合法的页码数字！");
		return;
	}
	if( Number(inputPage) > Number(theNrOfPages) || Number(inputPage) < 1 )
	{
		alert("请输入 1--" + theNrOfPages + " 的页码！");
		return;
	}
	document.all.item("dataHolder.page").value = Number(inputPage) -1 ;
	listForm.submit();
}

<!--选中所有记录-->
function selectAllRow(){
	var bselect=document.all.item("selectAllRecord").checked;  
	var formObj = listForm;
	for(var i=0; i< formObj.length; i++) 
	{ 
		var e = formObj.elements[i]; 
		if ((e.name != 'selectAllRecord') && (e.type=='checkbox'))
		{
			e.checked=bselect;
		}
	}
}
 
<!--反选中一条记录处理-->
function deselectAllRowCheckbox(name)
{
	if(document.all.item(name).checked==false)
		document.all.item("selectAllRecord").checked=false;
}

<!--根据列表Item的序号进行数据表单的填充-->
function fillMapContentWithSelected(selectedIndex)
{
	<c:forEach items="${result.crudMeta.columnMetas}" var="col">
		var formItem4Submit = document.all.item("mapContent[<c:out value='${col.colName}'/>]");
		var formItemValueSource = document.all.item("records["+selectedIndex+"].mapContent[<c:out value='${col.colName}'/>]");
		formItem4Submit.value = formItemValueSource.value;
	</c:forEach>	
}

<!--提交删除一条记录-->
function submitDeleteSingle(selectedIndex)
{    
	fillMapContentWithSelected(selectedIndex);	
	listForm.action = "<c:url value='/deleteSingle.do'/>";
	listForm.submit();
}

<!--提交编辑一条记录-->
function submitEditSingle(selectedIndex)
{
	fillMapContentWithSelected(selectedIndex);
	listForm.action="<c:url value='/editSingle.do'/>";
	listForm.submit();
}

<!--提交查看一条记录-->
function submitViewSingle(selectedIndex) 
{    
	fillMapContentWithSelected(selectedIndex);
	listForm.action="<c:url value='/viewSingle.do'/>";
	listForm.submit();
}

<!--提交删除一批记录-->
function submitDeleteBatch()
{    
	var isAnySelected = false;
	for(var i=0; i< listForm.length; i++) { 
		var e = listForm.elements[i]; 
		if ((e.name != 'selectAllRecord') && (e.type=='checkbox'))
		{
			if ( e.checked ){
				if(!isAnySelected)
					isAnySelected = true;			 
				e.value = "true";
			}else	
				e.value = "false";
		}
	}
	
	if(isAnySelected==false)
	{
		alert("请选择要删除的条目!");
		return false;
	}
		
	listForm.action = "<c:url value='/delete.do'/>";
	listForm.submit();
}

<!--提交编辑当前页所有数据-->
function submitEditPage()
{
	listForm.action="<c:url value='/editPage.do'/>";
	listForm.submit();
}

<!--提交新增记录-->
function submitAdd()
{
	listForm.action="<c:url value='/add.do'/>";
	listForm.submit();
}

<!--提交新增记录-->
function submitDataImport()
{
	listForm.action="<c:url value='/dataImportForm.do'/>";
	listForm.submit();
}

<!--批量更新-->
function submitUpdateBatch(token)
{
	listForm.updateBatchToken.value = token;
	listForm.action="<c:url value='/updateBatch.do'/>";
	listForm.submit();
}
</script>

</head>

<body>

<form name="listForm" action="<c:url value='/list.do'/>" method="POST"/>

<input type="hidden" name="name" value="<c:out value='${result.crudMeta.beanName}'/>"/>
<input type="hidden" name="requestForm" value="true">
<input type="hidden" name="updateBatchToken">
<c:forEach items="${result.crudMeta.otherWantedParameters}" var="wantedParam">
	<input type="hidden" name="<c:out value='${wantedParam}'/>" value="<c:out value='${param[wantedParam]}'/>">
</c:forEach>

<!--过滤条件处理-->
<c:if test="${crudViewMeta.filterWindowEnabled}">
<table>
	<tr>
	<c:forEach items="${result.crudMeta.columnMetas}" var="col">
		<c:if test="${col.useAsDataFilter}">
			<td align="right" nowrap>
				<c:out value="${col.title}"/>
			</td>
			<td align="left">
				<c:if test="${empty col.dictName}">
				<input type="text" name="mapParam[<c:out value='${col.colName}'/>]" value="<c:out value='${result.mapParam[col.colName]}'/>">
				</c:if>
				<c:if test="${not empty col.dictName}">
					<select name="mapParam[<c:out value='${col.colName}'/>]">
						<option value="">(未选取)</option>
						<c:forEach var="map" items="${col.dictMap}">										
							<option value='<c:out value="${map.key}"/>' <c:if test="${map.key == result.mapParam[col.colName]}">selected="selected"</c:if>><c:out value="${map.value}"/></option>
						</c:forEach>
					</select>
					
				</c:if>
			</td>
		</c:if>		
	</c:forEach>
	</tr>
</table>
&nbsp;<input type="submit" name="Apply" value="应用过滤条件">
</c:if>
<!--END:过滤条件处理-->

<input type="hidden" name="nrOfElements" value="<c:out value='${result.dataHolder.currentPageNrOfElements}'/>">

<hr size="1" noshade align="center">
<!--处理分页逻辑-->
<c:if test="${result.dataHolder.nrOfPages > 1}">
  	
  	<c:set var="pageUrl">
  		<c:url value='list.do'>
	         <c:param name='dataHolder.pageSize' value='${result.dataHolder.pageSize}'/>
	         <c:param name='name' value='${result.crudMeta.beanName}'/> 
	         <c:forEach items='${result.mapParam}' var='theParam'>
	         	<c:param name='mapParam[${theParam.key}]' value='${theParam.value}'/>
	         </c:forEach>
	         <c:forEach items="${result.crudMeta.otherWantedParameters}" var="wantedParam">
         		<c:param name='${wantedParam}' value='${param[wantedParam]}'/>
         	 </c:forEach>
	    </c:url>
  	</c:set>
	<table width="100%">
	<tr><td>
  	<div class="naviBar">共<c:out value="${result.dataHolder.nrOfElements}"/>条&nbsp; 第<c:out value="${result.dataHolder.page+1}"/>/<c:out value="${result.dataHolder.nrOfPages}"/>页&nbsp; 
	
	<c:if test="${result.dataHolder.page ==0}"><span class="naviDisabled">【首　页】</span></c:if>
	<c:if test="${result.dataHolder.page !=0}">
		<span class="naviEnabled"><a href="<c:url value='${pageUrl}'>
	         <c:param name='dataHolder.page' value='0'/>
	        </c:url>">【首　页】</a></span>
    </c:if>
    
	<c:if test="${result.dataHolder.page ==0}"><span class="naviDisabled">【上一页】</span></c:if>
	<c:if test="${result.dataHolder.page !=0}">
		<span class="naviEnabled"><a href="<c:url value='${pageUrl}'>
         	<c:param name='dataHolder.page' value='${result.dataHolder.page - 1}'/>
        	</c:url>">【上一页】</a></span>
    </c:if>
    
	<c:if test="${result.dataHolder.page == result.dataHolder.nrOfPages-1}"><span class="naviDisabled">【下一页】</span></c:if>
	<c:if test="${result.dataHolder.page != result.dataHolder.nrOfPages-1}">
    	<span class="naviEnabled"><a href="<c:url value='${pageUrl}'>
         	<c:param name='dataHolder.page' value='${result.dataHolder.page + 1}'/> 
        	</c:url>">【下一页】</a></span>
    </c:if>
 
	<c:if test="${result.dataHolder.page == result.dataHolder.nrOfPages-1}"><span class="naviDisabled">【尾　页】</span></c:if>
	<c:if test="${result.dataHolder.page != result.dataHolder.nrOfPages-1}">
    	<span class="naviEnabled"><a href="<c:url value='${pageUrl}'>
         	<c:param name='dataHolder.page' value='${result.dataHolder.nrOfPages-1}'/> 
        	</c:url>">【尾　页】</a></span>
    </c:if>
   
    转到<input type="text" size="3" name="inputPage" value="<c:out value='${result.dataHolder.page + 1}'/>"/>页 
    	<input type="button" name="thebutton" onclick="javascript:turnToPage();" value="GO">
    	<input type="hidden" name="dataHolder.page" value="<c:out value='${result.dataHolder.page}'/>">
    	<input type="hidden" name="theNrOfPages" value="<c:out value='${result.dataHolder.nrOfPages}'/>">
    	<input type="hidden" name="dataHolder.pageSize" value="<c:out value='${result.dataHolder.pageSize}'/>">
  	</div>
	</td>
	<td>
		<table align=right>
			<tr>
			<c:if test="${crudViewMeta.exportAsTxtEnabled}">
				<td><img border=0 src="<c:url value='/images/txt.gif'/>" alt="导出为TXT"/></td>
			</c:if>
			<c:if test="${crudViewMeta.exportAsCsvEnabled}">
				<td><img border=0 src="<c:url value='/images/csv.gif'/>" alt="导出为CSV"/></td>
			</c:if>
			<c:if test="${crudViewMeta.exportAsExcelEnabled}">
				<td><img border=0 src="<c:url value='/images/xls.gif'/>" alt="导出为EXCEL"/></td>
			</c:if>
			</tr>
		</table>
	</td>
	</tr>
	</table>
</c:if> 
<!--END:处理分页逻辑-->  

<c:if test="${result.dataHolder.nrOfPages <= 1}">
	<table width="100%"><tr><td></td>
	<td>
		<table align=right>
			<tr>
			<c:if test="${crudViewMeta.exportAsTxtEnabled}">
				<td><img border=0 src="<c:url value='/images/txt.gif'/>" alt="导出为TXT"/></td>
			</c:if>
			<c:if test="${crudViewMeta.exportAsCsvEnabled}">
				<td><img border=0 src="<c:url value='/images/csv.gif'/>" alt="导出为CSV"/></td>
			</c:if>
			<c:if test="${crudViewMeta.exportAsExcelEnabled}">
				<td><img border=0 src="<c:url value='/images/xls.gif'/>" alt="导出为EXCEL"/></td>
			</c:if>
			</tr>
		</table>
	</td>
	</tr>
	</table>
</c:if>
  
<br>

<table class="content" width="<c:out value='${crudViewMeta.listTableWidth}'/>">
    
	<!--列头处理-->
	<tr>
		<c:set var="buttonForUpdateBatchCount" value="0"/>
		<c:forEach items="${result.crudMeta.buttonForUpdateBatchs}">
			<c:set var="buttonForUpdateBatchCount"><c:out value="${buttonForUpdateBatchCount + 1 }"/></c:set>
		</c:forEach>

		<!--最前面的用于全选的checkbox-->
		<c:if test="${crudViewMeta.deleteBatchEnabled || buttonForUpdateBatchCount > 0}">
			<td class="headtitle" nowrap>
				<input type="checkbox" name="selectAllRecord" onClick="javascript:selectAllRow()" value="">&nbsp;
			</td>
	    </c:if>
	    
	    <!--列名称-->
		<c:forEach items="${result.crudMeta.columnMetas}" var="col">
			<!--将列表中的一条数据填充到一个Command对象时使用的表单元素-->
			<input type="hidden" name="mapContent[<c:out value='${col.colName}'/>]" value="">
	  		
	  		<c:if test="${col.showInList}">
	  			<td class="headtitle" nowrap><c:out value="${col.title}"/></td>
	  		</c:if>
	  	</c:forEach>
	  	
	  	<!--给每条记录后面的控制按钮留出的空位置-->
	  	<c:if test="${crudViewMeta.editSingleEnabled || crudViewMeta.deleteSingleEnabled || crudViewMeta.viewSingleEnabled}">
	  		<td class="headtitle" nowrap>&nbsp;</td>
	  	</c:if>
	  	
	</tr>
	<!--END:列头处理-->
	
	<!--迭代查询结果数据-->
	<c:forEach items="${result.dataHolder.pageList}" var="item" varStatus="s">
		<tr class="body">
			<!--每条记录前面的CheckBox-->
			<c:if test="${crudViewMeta.deleteBatchEnabled || buttonForUpdateBatchCount > 0}">
				<td class="listhead">
					<input type="checkbox" name="records[<c:out value='${s.index}'/>].selected" 
						onClick="javascript:deselectAllRowCheckbox(name)">
				</td>
			</c:if>
			
			<!--展现数据-->
			<c:forEach items="${result.crudMeta.columnMetas}" var="col">
				<!--将所有的数据应用到表单上，以便于当编辑整页数据或者批量删除时的提交和绑定-->
				<input type="hidden" 
					name="records[<c:out value='${s.index}'/>].mapContent[<c:out value='${col.colName}'/>]" 
					value="<c:out value="${item[col.colName]}"/>">
				
				<c:if test="${col.showInList}">
			      	<td class="listhead">
			      		<c:if test="${empty col.dictName || !col.needDictReplace}"><c:out value="${item[col.colName]}"/></c:if>
			      		<c:if test="${col.dictName != null && col.needDictReplace}"><c:out value="${col.dictMap[item[col.colName]]}"/></c:if>
			      	</td>
		      	</c:if>
			</c:forEach>
			
			<!--每条数据后面的控制按钮-->
			<td class="listhead">
				<c:if test="${crudViewMeta.editSingleEnabled}">
					<span class="button4Row" onclick="javascript:submitEditSingle(<c:out value='${s.index}'/>)">编辑</span>
				</c:if>
				<c:if test="${crudViewMeta.viewSingleEnabled}">
					<span class="button4Row" onclick="javascript:submitViewSingle(<c:out value='${s.index}'/>)">查看</span>
				</c:if>			
				<c:if test="${crudViewMeta.deleteSingleEnabled}">
					<span class="button4Row" onclick="javascript:submitDeleteSingle(<c:out value='${s.index}'/>)">删除</span>
				</c:if>
			</td>
		</tr>
	</c:forEach>
	<!--END:迭代查询结果数据-->	
</table>

<br>

<!--功能按钮区，包括新增、删除、编辑当前页等-->
<table>
	<tr align="center" valign="center">
		<td align="left" width="50%">
			<c:if test="${crudViewMeta.addEnabled}">
	 			<input type="button" name="add" value="新增" onclick="javascript:submitAdd()">
	 		</c:if>
	 		
			<c:if test="${crudViewMeta.deleteBatchEnabled}">
	 			<input type="button" name="delSelected" value="删除选中记录" onclick="javascript:submitDeleteBatch()">
	 		</c:if>
	 		
	 		<c:if test="${crudViewMeta.editPageEnabled}">
	 			<input type="button" name="editPage" value="编辑当前页" onclick="javascript:submitEditPage()">
			</c:if>	 		
				
	 		<c:if test="${crudViewMeta.dataImportEnabled}">
	 			<input type="button" name="editPage" value="数据导入" onclick="javascript:submitDataImport()">
	        </c:if>
			
			<c:forEach items="${result.crudMeta.buttonForUpdateBatchs}" var="buttonForUpdateBatch" varStatus="s">
				<input type="button" name="buttonForUpdateBatch_<c:out value='${s.index}'/>" value="<c:out value='${buttonForUpdateBatch.label}'/>" onclick="javascript:submitUpdateBatch(<c:out value='${buttonForUpdateBatch.token}'/>)">
			</c:forEach>

        
        </td>
        <td width="50%">&nbsp;</td>
	</tr>
</table>
<!--END:功能按钮区，包括新增、删除、编辑当前页等--> 	

</form>

</body>
</html>