package com.inwiss.apps.fee.controller;

public class JsonResult {  
	private String message;   


	private boolean success;  
	private String msisdn;
	private String billdate;
   
    /**
	 * @return the billdate
	 */
	public String getBilldate() {
		return billdate;
	}
	/**
	 * @param billdate the billdate to set
	 */
	public void setBilldate(String billdate) {
		this.billdate = billdate;
	}
	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}
	/**
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public boolean isSuccess() {  
        return success;  
    }  
    public void setSuccess(boolean success) {  
        this.success = success;  
    }  
   
    /**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
    public String toString(){  
        return "{success:"+this.success+",message:"+this.message+
        		",msisdn:"+ msisdn+",billdate:"+ billdate+"}";  
    }  
}  