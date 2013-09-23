/**
 * 
 */
package com.inwiss.apps.fee.model;

import java.io.Serializable;

import org.inwiss.platform.ibatis.entity.BaseEntity;

/**
 * @author lvhq
 * 
 *	<p>
 *		<li>边漫基站上报</li>
 *	</p>
 */
public class StationsReport extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 981324810947454809L;
	
	private String cidDec;
	
	private String reporter;
	
	private String msisdn;
	
	private String repReason;
	
	private String operDate;
	
	private String operTime;
	
	private String status;
	
	private String recType;
	
	private String passed;
	
	private String cmt;

	public StationsReport(){}

	public String getCidDec() {
		return cidDec;
	}

	public void setCidDec(String cidDec) {
		this.cidDec = cidDec;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getRepReason() {
		return repReason;
	}

	public void setRepReason(String repReason) {
		this.repReason = repReason;
	}

	public String getOperDate() {
		return operDate;
	}

	public void setOperDate(String operDate) {
		this.operDate = operDate;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecType() {
		return recType;
	}

	public void setRecType(String recType) {
		this.recType = recType;
	}

	public String getPassed() {
		return passed;
	}

	public void setPassed(String passed) {
		this.passed = passed;
	}

	public String getCmt() {
		return cmt;
	}

	public void setCmt(String cmt) {
		this.cmt = cmt;
	}
	
}
