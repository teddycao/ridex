package org.inwiss.platform.model.ext;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties({ "checked","children" })
public class TreeItemRptInfo extends LongTreeNode<TreeItemRptInfo> {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = -4721129509819246740L;

	/** * interId. */
    private Long interId;

    /** * parent. */
    private TreeItemRptInfo parent;

    /** * rptName. */
    private String rptName;

	/** * rptId. */
    private String rptId;

    /** * rptUrl. */
    private String rptUrl;

    /** * rptDesc. */
    private String rptDesc;

    /** * rptOrg. */
    private String rptOrg;
    
    /** * rptStatu. */
    private String rptStatu;

	private boolean isLeaf = true;

	
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	@Override
	public boolean isLeaf() {
		return isLeaf;
	}

	/** * checked. */
    private boolean checked = false;

    /** * 构造方法. */
    public TreeItemRptInfo() {
    }

    /** * @return parent. */
    public TreeItemRptInfo getParent() {
        return parent;
    }

    /** * @param parent Menu. */
    public void setParent(TreeItemRptInfo parent) {
        this.parent = parent;
    }
    
    public Long getInterId() {
		return interId;
	}
	public void setInterId(Long interId) {
		this.interId = interId;
	}
	public String getRptName() {
		return rptName;
	}
	public void setRptName(String rptName) {
		this.rptName = rptName;
	}
	public String getRptId() {
		return rptId;
	}
	public void setRptId(String rptId) {
		this.rptId = rptId;
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
	/** * @return isChecked. */

    public boolean isChecked() {
        return checked;
    }

    /** * @param checked boolean. */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
