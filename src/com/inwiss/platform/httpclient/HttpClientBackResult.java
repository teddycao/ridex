package com.inwiss.platform.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpClientBackResult {
	
	public HttpClientBackResult(){
		
	}
	/**
	 * HttpClient执行结果,OK or ERROR
	 */
	private String result;
	/**
	 * HttpClient对应执行结果的消息
	 */
	private String message;
	/**
	 * id
	 */
	private String id;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	static class GetThread extends Thread {
	    
	    private final HttpClient httpClient;
	    private final HttpContext context;
	    private final HttpGet httpget;
	    
	    public GetThread(HttpClient httpClient, HttpGet httpget) {
	        this.httpClient = httpClient;
	        this.context = new BasicHttpContext();
	        this.httpget = httpget;
	    }
	    
	    @Override
	    public void run() {
	        try {
	            HttpResponse response = this.httpClient.execute(this.httpget, this.context);
	            HttpEntity entity = response.getEntity();
	            if (entity != null) {
	                // do something useful with the entity
	            }
	            // ensure the connection gets released to the manager
	            EntityUtils.consume(entity);
	        } catch (Exception ex) {
	            this.httpget.abort();
	        }
	    }
	   
	}

}
