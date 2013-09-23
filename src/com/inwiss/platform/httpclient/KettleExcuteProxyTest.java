package com.inwiss.platform.httpclient;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;


import org.junit.Test;

public class KettleExcuteProxyTest {

	@Test
	public void testFireExcuteTrans() {
		//fail("Not yet implemented");
		
		String serverURI = "http://localhost:8080/pdiserver/kettle/expert/";
		String folderName = "/";
		//String wfName = "fee_cell_stations";
		String wfName = "Trans_Delay_Test";
		String sysRunDate = "20110922";
		String logLevel = "Debug";

		try {
		KettleExcuteProxy ketf = new KettleExcuteProxy();
		//Map statusMap = ketf.fireExcuteTrans(serverURI, folderName,wfName, sysRunDate, logLevel);
		//statusMap.get("id");
		//System.out.println(statusMap);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}
	
	
	public void testXmlParse() {
		String xml = "<webresult>" +
				"<result>ERROR</result>" +
				"<message/><id/>" +
				"</webresult>";
		KettleExcuteProxy ketf = new KettleExcuteProxy();
		ketf.parseResult(xml);
	}

}
