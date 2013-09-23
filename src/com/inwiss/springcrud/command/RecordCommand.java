/*
 * Created on 2005-8-4
 */
package com.inwiss.springcrud.command;

import java.util.HashMap;
import java.util.Map;

/**
 * 标识CRUD操作中一条记录的Command对象。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class RecordCommand
{
	/**CrudMeta名字，以便在某些场合可以根据这个名字获取CrudMeta的定义*/
	private String name;
	
    /**保存Command对象在被修改之前的信息*/
    private Map mapContentBefore = new HashMap();
    
    /**保存Command对象被编辑后的信息*/
    private Map mapContent = new HashMap();
    
    /**该记录是否被选中*/
    private boolean selected;
    
    /**
     * @return Returns the mapContent.
     */
    public Map getMapContent()
    {
        return mapContent;
    }
    /**
     * @param mapContent The mapContent to set.
     */
    public void setMapContent(Map mapContent)
    {
        this.mapContent = mapContent;
    }
    
    /**
     * @return Returns the mapContentBefore.
     */
    public Map getMapContentBefore()
    {
        return mapContentBefore;
    }
    /**
     * @param mapContentBefore The mapContentBefore to set.
     */
    public void setMapContentBefore(Map mapContentBefore)
    {
        this.mapContentBefore = mapContentBefore;
    }
    /**
     * @return Returns the selected.
     */
    public boolean isSelected()
    {
        return selected;
    }
    /**
     * @param selected The selected to set.
     */
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }
    
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
