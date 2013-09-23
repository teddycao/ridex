<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>

<title>FusionWidgets v3 - Demo Application</title>
<script type="text/javascript" src="${ctx}/dashboard/fusioncharts/js/metrics.js"></script>
<link href="style.css" rel="stylesheet" type="text/css" />
<SCRIPT type="text/javascript" LANGUAGE="Javascript" SRC="${ctx}/dashboard/fusioncharts/js/JSClass/FusionCharts.js"></SCRIPT>
<SCRIPT type="text/javascript" LANGUAGE="Javascript" SRC="${ctx}/dashboard/fusioncharts/js/JSFunctions.js"></SCRIPT>
<script type="text/javascript" LANGUAGE="Javascript" >
	var globalYear =1996;
	//setting the application extension to automatize JSFinction Drill Down
	var extn ="asp";
</script>

</head>

<body>
<table width="780" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="3" align="left" valign="top" class="left-shadow"><img src="images/left-shadow.gif" alt="" width="3" /></td>
    <td width="774" align="left" valign="top" class="body"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="24" align="center" valign="middle" class="stab-info">图表门户</td>
      </tr>
      <tr>
        <td align="center" valign="top" style="padding:25px 0px 0px 0px;"><table width="729" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="24" align="left" valign="top">&nbsp;</td>
          </tr>
          <tr>
            <td align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="349" align="center" valign="top" class="gauge-back" style="padding:6px 0px 0px 0px;"><table width="336" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="33" align="center" valign="middle" class="gauge-header">销售量前5名国家(Top 5)</td>
                  </tr>
                  <tr>
                    <td align="center" valign="middle">
	<!-- START Script Block for Chart FG_TopCountries -->
	<div id='FG_TopCountriesDiv' align='center'>
		Chart.
		
	</div>
		
	<script type="text/javascript" language="javascript">	
		//Instantiate the Chart	
		var chart_FG_TopCountries = new FusionCharts("charts/Funnel.swf", "FG_TopCountries", "335", "270", "0", "1");
		
		//Set the dataURL of the chart
		chart_FG_TopCountries.setDataXML("<chart decimals='0' numberPrefix='$' streamlinedData='0' bgColor='f7f2ea' borderColor='f7f2ea' borderthickness='0'  bgAlpha='100'><set label='USA' value='104254' link='n-DrillCountry%2Easp%3Fyear%3D1996%26Country%3DUSA' /><set label='Germany' value='96299' link='n-DrillCountry%2Easp%3Fyear%3D1996%26Country%3DGermany' /><set label='Austria' value='61577' link='n-DrillCountry%2Easp%3Fyear%3D1996%26Country%3DAustria' /><set label='Brazil' value='53019' link='n-DrillCountry%2Easp%3Fyear%3D1996%26Country%3DBrazil' /><set label='Sweden' value='29393' link='n-DrillCountry%2Easp%3Fyear%3D1996%26Country%3DSweden' /></chart>");
				
		//Finally, render the chart.
		chart_FG_TopCountries.render("FG_TopCountriesDiv");
	</script>	
	<!-- END Script Block for Chart FG_TopCountries -->
	<div class="msgText">Please click on a slice to drill-down to detailed charts</div></td>
                  </tr>
                </table></td>
                <td width="32" height="272" align="center" valign="middle">&nbsp;</td>
                <td width="349" align="center" valign="top" class="gauge-back" style="padding:6px 0px 0px 0px;"><table width="336" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="33" align="center" valign="middle" class="gauge-header">销售量前10名员工 (Top 10)</td>
                  </tr>
                  <tr>
                    <td align="center" valign="middle">
	<!-- START Script Block for Chart FG_TopEmployees -->
	<div id='FG_TopEmployeesDiv' align='center'>
		Chart.
		
	</div>
		
	<script type="text/javascript" language="javascript">	
		//Instantiate the Chart	
		var chart_FG_TopEmployees = new FusionCharts("charts/Pyramid.swf", "FG_TopEmployees", "335", "270", "0", "1");
		
		//Set the dataURL of the chart
		chart_FG_TopEmployees.setDataURL(encodeURI("/ridex/charts/view.do"));
				
		//Finally, render the chart.
		chart_FG_TopEmployees.render("FG_TopEmployeesDiv");
	</script>	
	<!-- END Script Block for Chart FG_TopEmployees -->
	<div class="msgText">Please click on a slice to drill-down to detailed charts</div></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td align="left" valign="top">&nbsp;</td>
          </tr>
          <tr>
            <td height="25" align="left" valign="top">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
    <td width="3" align="left" valign="top" class="right-shadow"><img src="images/right-shadow.gif" alt="" width="3"  /></td>
  </tr>
</table>

<!-- Google Analytics Tracker Code Ends --></body>
</html>
