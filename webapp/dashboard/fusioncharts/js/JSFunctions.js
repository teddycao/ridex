//FC_ChartUpdated method is called when user has changed pointer value.
function FC_ChartUpdated(DOMId){
  //Check if DOMId is that of the chart we want i.e. year selection linear gadget
  if (DOMId.toLowerCase()=="fg_yearselector"){
	 //Get reference to the chart

	var FGRef = getChartFromId(DOMId);
	//Get the selected value/year
	var year = Math.round(FGRef.getData(1)); 
	
	//updating YearSelectionChart to go to the centre of year's value
	FGRef = getChartFromId("FG_YearSelector");
	FGRef.setData(1, year);

	//Update display
	if(year!=globalYear){
		globalYear=year;
		submitYear(year);
	}
  }
} 


function FC_WinOpen(url){
   window.open(url,'ttt','width=200,height=100');
}



//this function live-updates all charts on the page using setDataURL/setDataXML method
function submitYear(year){
	var dataURL, FGRef
	var arr_setObj=new Array();
	arr_setObj.push({id:"FG_AvgRev",QS:"?op=buildXMLAvgRev&year="+year});
	arr_setObj.push({id:"FG_AvgQuan",QS:"?op=buildXMLAvgQuan&year="+year});
	arr_setObj.push({id:"FG_MonthlyRev",QS:"?op=buildXMLMonthlyRev&year="+year});
	arr_setObj.push({id:"FG_MonthlyQuan",QS:"?op=buildXMLMonthlyQuan&year="+year});
	arr_setObj.push({id:"FG_TopCountries",QS:"?op=buildXMLTopCountries&year="+year});
	arr_setObj.push({id:"FG_TopEmployees",QS:"?op=buildXMLTopEmployees&year="+year});
	arr_setObj.push({id:"FC_TopCustomers",QS:"?op=buildXMLTopCustomers&year="+year});
	arr_setObj.push({id:"FG_PI",QS:"?op=buildXMLEachInventories&year="+year+"&rank="});
	
	
	for(var i in arr_setObj){
		if(arr_setObj[i].id!="FG_PI"){
			FGRef = getChartFromId(arr_setObj[i].id);
			dataURL = escape("DataGen."+extn+arr_setObj[i].QS);
			FGRef.setDataURL(dataURL);
		}
		else{
			for(var j=1;j<=5;j++){
				FGRef = getChartFromId(arr_setObj[i].id+j);
    		    dataURL = escape("DataGen."+extn+arr_setObj[i].QS+j);
 				FGRef.setDataURL(dataURL);
			}
		}
	}
	
}
