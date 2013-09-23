package org.inwiss.platform.model.core;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.inwiss.platform.model.BaseObject;

@JsonIgnoreProperties( { "parentItem", "children", "Comparator" })
public class RptInfo extends BaseObject {

	protected Long interId;

	protected String rptId;

	protected String name;

	protected String rptUrl;

	protected String rptDesc;

	protected String rptOrg;

	protected String rptStatu;

	protected Long parentId;

	protected Boolean isLeaf = true;

	protected RptInfo parentItem = null;

	//protected Localizable owner;
	
	protected List childItems = new ArrayList();

	public List getChildItems() {
		return childItems;
	}

	public void setChildItems(List childItems) {
		this.childItems = childItems;
	}

	/*public Localizable getOwner() {
		return owner;
	}

	public void setOwner(Localizable owner) {
		this.owner = owner;
	}*/

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public RptInfo getParentItem() {
		return parentItem;
	}

	public void setParentItem(RptInfo parentItem) {
		this.parentItem = parentItem;
	}

	public Long getParentId() {
		if (this.getParentItem() != null)
			return parentItem.getId();
		else
			return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRptId() {
		return rptId;
	}

	public void setRptId(String rptId) {
		this.rptId = rptId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRptUrl() {
		return rptUrl;
	}

	public void setRptUrl(String rptUrl) {
		this.rptUrl = rptUrl;
	}

	public String getRptDesc() {
		return rptDesc;
	}

	public void setRptDesc(String rptDesc) {
		this.rptDesc = rptDesc;
	}

	public String getRptOrg() {
		return rptOrg;
	}

	public void setRptOrg(String rptOrg) {
		this.rptOrg = rptOrg;
	}

	public String getRptStatu() {
		return rptStatu;
	}

	public void setRptStatu(String rptStatu) {
		this.rptStatu = rptStatu;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof RptInfo)) {
			return false;
		}

		final RptInfo rptInfo = (RptInfo) o;

		if (String.valueOf(id) != null ? !String.valueOf(id).equals(
				String.valueOf(rptInfo.id)) : String
				.valueOf(rptInfo.id) != null) {
			return false;
		}
		/*if (owner != null ? !owner.equals(rptInfo.owner)
				: rptInfo.owner != null) {
			return false;
		}*/
		if (parentItem != null ? !parentItem.equals(rptInfo.parentItem)
				: rptInfo.parentItem != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result;
		result = (parentItem != null ? parentItem.hashCode() : 0);
		result = 29
				* result
				+ (String.valueOf(id) != null ? String.valueOf(id)
						.hashCode() : 0);
		//result = 29 * result + (owner != null ? owner.hashCode() : 0);
		return result;
	}

}
