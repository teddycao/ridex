/*
 * Created on 2005-11-30
 */
package com.inwiss.springcrud.metadata;

/**
 * 用于批量更新的Button元数据。
 * 
 * <p>在OpenCrud中，可以通过在列表界面选中一批记录进行批量更新，从而实现数据审批类的需求。
 * 为了更好的实现配置的外部化，定义了该类。
 * 
 * <p>根据用户配置，列表界面会展现实现这种批量更新的按钮，其中显示的信息就是该类中label所保存的数据；
 * 而当用户点击该按钮时，系统保证将相应token的传入，从而可以根据token的值来完成不同的更新操作。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class ButtonForUpdateBatchMeta
{
    /**触发该按钮时传入的Token*/
    private String token;
    
    /**该按钮上显示的label*/
    private String label;
    
    
    /**
     * @return Returns the label.
     */
    public String getLabel()
    {
        return label;
    }
    /**
     * @param label The label to set.
     */
    public void setLabel(String label)
    {
        this.label = label;
    }
    /**
     * @return Returns the token.
     */
    public String getToken()
    {
        return token;
    }
    /**
     * @param token The token to set.
     */
    public void setToken(String token)
    {
        this.token = token;
    }
}
