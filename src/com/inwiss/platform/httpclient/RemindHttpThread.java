package com.inwiss.platform.httpclient;

import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class RemindHttpThread extends Thread {
	private volatile boolean shutdown;
	private final HttpClient httpClient;
    private final HttpContext context;
    private final HttpGet httpget;
    
    public RemindHttpThread(HttpClient httpClient, HttpGet httpget) {
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
    
  
    public void run1() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5*1000);
                    // Close expired connections

                }
            }
        } catch (InterruptedException ex) {
            // terminate
        }
    }
    
   
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}

