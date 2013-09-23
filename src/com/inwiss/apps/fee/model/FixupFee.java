/**
 * 
 */
package com.inwiss.apps.fee.model;

import java.io.Serializable;

import org.inwiss.platform.ibatis.entity.BaseEntity;

/**
 * @author lvhq
 * 
 */
public class FixupFee extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -665152183767026343L;

	/**
	 * 呼叫类型
	 */
	private String callType;
	/**
	 * 通话日期
	 */
	private String startDate;
	/**
	 * 通话开始时间
	 */
	private String startTime;
	/**
	 * 通话时长
	 */
	private int callDuration;
	/**
	 * 对方号码
	 */
	private String otherParty;
	/**
	 * 前转号码
	 */
	private String originNo;
	/**
	 * 归属市县(话单位置)
	 */
	private String officeCode;
	/**
	 * 话单业务类型
	 */
	private String bizType;
	/**
	 * 漫游类型
	 */
	private String roamType;
	/**
	 * 基本通话费
	 */
	private double cfee;
	/**
	 * 基本通话附加费
	 */
	private double cfeeAdd;
	/**
	 * 长途通话费
	 */
	private double lfee;
	/**
	 * 长途通话附加费
	 */
	private double lfeeAdd;
	/**
	 * 其他费用
	 */
	private double otherFee;
	/**
	 * 总费用
	 */
	private double totalFee;
	/**
	 * 基站号
	 */
	private String cellId;
	/**
	 * 网络标识
	 */
	private String vpnId;
	/**
	 * 数据源类型
	 */
	private String sourceType;
	
	/**
	 * 手机号码
	 */
	private String msisdn;
	
	public FixupFee(){
		
	}
	
	public FixupFee(int start,int limit){
		super(start,limit);
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public int getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(int callDuration) {
		this.callDuration = callDuration;
	}

	public String getOtherParty() {
		return otherParty;
	}

	public void setOtherParty(String otherParty) {
		this.otherParty = otherParty;
	}

	public String getOriginNo() {
		return originNo;
	}

	public void setOriginNo(String originNo) {
		this.originNo = originNo;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getRoamType() {
		return roamType;
	}

	public void setRoamType(String roamType) {
		this.roamType = roamType;
	}

	public double getCfee() {
		return cfee;
	}

	public void setCfee(double cfee) {
		this.cfee = cfee;
	}

	public double getCfeeAdd() {
		return cfeeAdd;
	}

	public void setCfeeAdd(double cfeeAdd) {
		this.cfeeAdd = cfeeAdd;
	}

	public double getLfee() {
		return lfee;
	}

	public void setLfee(double lfee) {
		this.lfee = lfee;
	}

	public double getLfeeAdd() {
		return lfeeAdd;
	}

	public void setLfeeAdd(double lfeeAdd) {
		this.lfeeAdd = lfeeAdd;
	}

	public double getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(double otherFee) {
		this.otherFee = otherFee;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public String getCellId() {
		return cellId;
	}

	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	public String getVpnId() {
		return vpnId;
	}

	public void setVpnId(String vpnId) {
		this.vpnId = vpnId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	@Override
	public String toString() {
		String fixupFeeString = "org.inwiss.fee.model.FixupFee [callType=" + this.callType
				+ ", startDate=" + this.startDate + ", startTime="
				+ this.startTime + ", callDuration=" + this.callDuration
				+ ", otherParty=" + this.otherParty + ", originNo="
				+ this.originNo + ", officeCode=" + this.officeCode
				+ ", bizType=" + this.bizType + ", roamType=" + this.roamType
				+ ", cfee=" + this.cfee + ", cfeeAdd=" + this.cfeeAdd
				+ ", lfee=" + this.lfee + ", lfeeAdd=" + this.lfeeAdd
				+ ", otherFee=" + this.otherFee + ", totalFee=" + this.totalFee
				+ ", cellId=" + this.cellId + ", vpnId=" + this.vpnId
				+ ", sourceType=" + this.sourceType + "]";
		return fixupFeeString;

	}
}