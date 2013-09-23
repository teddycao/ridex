package com.inwiss.platform.httpclient;

import java.util.concurrent.TimeUnit;

import org.apache.http.conn.ClientConnectionManager;

public  class MonitorThread extends Thread {
    

    private volatile boolean shutdown;
    
    public MonitorThread() {
        super();
       
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
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