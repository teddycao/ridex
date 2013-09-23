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
 *		<li>边漫基站信息</li>
 *	</p>
 */
public class CellStation extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7332628446397309612L;

	private String ciDec;
	
	private String city;
	
	private String cellName;
	
	private String cellLacDec;
	
	private String bcch;
	
	private String bsic;
	
	private String bscName;
	
	private String siteType;
	
	private String stLevel;
	
	private String fdate;
	
	private String diType;
	
	private String ddStat;

	public CellStation(){
		
	}
	
	public String getCiDec() {
		return ciDec;
	}

	public void setCiDec(String ciDec) {
		this.ciDec = ciDec;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getCellLacDec() {
		return cellLacDec;
	}

	public void setCellLacDec(String cellLacDec) {
		this.cellLacDec = cellLacDec;
	}

	public String getBcch() {
		return bcch;
	}

	public void setBcch(String bcch) {
		this.bcch = bcch;
	}

	public String getBsic() {
		return bsic;
	}

	public void setBsic(String bsic) {
		this.bsic = bsic;
	}

	public String getBscName() {
		return bscName;
	}

	public void setBscName(String bscName) {
		this.bscName = bscName;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public String getStLevel() {
		return stLevel;
	}

	public void setStLevel(String stLevel) {
		this.stLevel = stLevel;
	}

	public String getFdate() {
		return fdate;
	}

	public void setFdate(String fdate) {
		this.fdate = fdate;
	}

	public String getDiType() {
		return diType;
	}

	public void setDiType(String diType) {
		this.diType = diType;
	}

	public String getDdStat() {
		return ddStat;
	}

	public void setDdStat(String ddStat) {
		this.ddStat = ddStat;
	}
	
}