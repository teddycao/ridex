package com.inwiss.platform.httpclient;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import java.io.UnsupportedEncodingException;
import java.util.*;

public class KettleExcuteProxy {
	Logger logger = LoggerFactory.getLogger(KettleExcuteProxy.class);

	String serverURI = "http://localhost:8080/pdiserver/kettle/expert/";
	protected List<Map<String,Object>> params = null;
	protected  List<Map<String,Object>> variables = null;


	/**
	 *   NOTHING(0, "Nothing"),
     ERROR(1, "Error"),
  	MINIMAL(2, "Minimal"),
  	BASIC(3, "Basic"),
  	DETAILED(4, "Detailed"),
  	DEBUG(5, "Debug"),
  	ROWLEVEL(6, "Rowlevel");
	 * @param serverURI
	 * @param folderName
	 * @param wfName
	 * @param sysRunDate
	 * @param logLevel
	 * @return
	 */
	public Map fireExcuteTrans(ReqParam reqParam) {
		ObjectMapper mapper = new ObjectMapper();
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(serverURI);
		HttpEntity entity = null;
		List<Map<String, Object>> paramsList = this.getParams();
		String taskJsonStr = null;

		/**
		 * 样例 {\"params\": [{\"param0\" : \"20101130\"}] ,\"logLevel\" :
		 * \"Basic\",\"transName\" : \"Kettle_Workflow\",\"dirName\" : \"/\"}
		 */
		if(this.params != null){
		
		}
	
		try {
			taskJsonStr = mapper.writeValueAsString(reqParam);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		logger.info("Try to construct params[" + taskJsonStr + "]");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		/**
		 * 请求参数
		 */
		nvps.add(new BasicNameValuePair(KettleConstant.DIRNAME, reqParam.getDirName()));
		nvps.add(new BasicNameValuePair(KettleConstant.TRANSNAME, reqParam.getTransName()));
		// nvps.add(new BasicNameValuePair(KettleConstant.JOBNAME,wfName));
		nvps.add(new BasicNameValuePair(KettleConstant.PARAMS, taskJsonStr));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpost);
			entity = response.getEntity();

			if (entity != null) {
				// entity = new BufferedHttpEntity(entity);
			}

			String feedback = EntityUtils.toString(entity);
			System.out.println(feedback);
			EntityUtils.consume(entity);
			
			int  code = response.getStatusLine().getStatusCode();
			if(code == 200){
				return parseResult(feedback);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return null;
	}
	/**
	 * 解析ETL执行结果
	 * @param xml
	 * @return
	 */
	public Map parseResult(String xml) {
		try {
			Map map = new HashMap();
			Document document = DocumentHelper.parseText(xml);
			Element nodeElement = document.getRootElement();
			List node = nodeElement.elements();
			for (Iterator it = node.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				map.put(elm.getName(), elm.getText());
				elm = null;
			}
			node = null;
			nodeElement = null;
			document = null;
			logger.debug("Reulst params[" + map + "]");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public boolean getTaskStatus(String type){
		///transStatusGetter/{id}/{transName}
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(serverURI);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		try {
			httpost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		
		
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity  = response.getEntity();

		if (entity != null) {
			// entity = new BufferedHttpEntity(entity);
		}

		String feedback = EntityUtils.toString(entity);
		System.out.println(feedback);
		EntityUtils.consume(entity);
		
		return false;
		
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return false;
		}
	}
	
	
	
	
	
	

	/**
	 * @return the params
	 */
	public List<Map<String, Object>> getParams() {
		return params;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(List<Map<String, Object>> params) {
		this.params = params;
	}
	/**
	 * @return the variables
	 */
	public List<Map<String, Object>> getVariables() {
		return variables;
	}
	/**
	 * @param variables the variables to set
	 */
	public void setVariables(List<Map<String, Object>> variables) {
		this.variables = variables;
	}
}
