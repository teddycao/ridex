/*
 * Created on 2005-11-27
 */
package com.inwiss.springcrud.metadata;

import com.inwiss.springcrud.datafilter.DataFilter;
import com.inwiss.springcrud.support.handcraft.*;
import com.inwiss.springcrud.support.handcraft.HandCraftFormObjectCreateStrategy;
import com.inwiss.springcrud.support.handcraft.HandCraftFormObjectCreateStrategyImpl;
/**
 * 用于配置数据补录的元数据类。
 * 
 * <p>数据补录本质上也是对单表的维护，即增、删、改、查（CRUD）。但是其CRUD操作有一些前提条件，如某些数据不可以修改但可以查看；
 * 当表中没有相应数据的时候，需要根据某个数据模板创建一批记录，修改后新增。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class HandCraftMeta
{
    /**对应的CRUD元数据信息*/
    private CrudMeta crudMeta;
    
    /**数据补录对应的View名称*/
    private String handCraftView;
    
    /**嵌套后的用于Create As的数据过滤器*/
    private DataFilter chainedFilterForCreateAs;
    
    /**用于类似Create As创建风格的数据过滤器*/
    private DataFilter[] dataFiltersForCreateAs;
    
    /**用于构造表单的数据过滤器*/
    private DataFilter chainedFilterForForm;
    
    /**用于构造表单的数据过滤器集合*/
    private DataFilter[] dataFiltersForForm;
    
    /**创建手工补录对应的表单对象的Strategy对象*/
    private HandCraftFormObjectCreateStrategy formObjectCreateStrategy = new HandCraftFormObjectCreateStrategyImpl();
    
    /**初始化方法，完成数据过滤器的嵌套逻辑*/
    public void init()
    {
        //应用用于Create As的数据过滤器逻辑
        if ( dataFiltersForCreateAs == null || dataFiltersForCreateAs.length == 0 )
            chainedFilterForCreateAs = null;
        else
        {
            DataFilter dataFilter = null;
            for ( int i = 0; i < dataFiltersForCreateAs.length; i++ )
            {
                if ( i == 0 )
                    dataFilter = dataFiltersForCreateAs[0];
                else
                    dataFilter = dataFilter.setChainedFilter(dataFiltersForCreateAs[i]);
            }
            chainedFilterForCreateAs = dataFiltersForCreateAs[0];
        }
        
        //应用于构造表单的数据过滤器逻辑
        if ( dataFiltersForForm == null || dataFiltersForForm.length == 0 )
            chainedFilterForForm = null;
        else
        {
            DataFilter dataFilter = null;
            for ( int i = 0; i < dataFiltersForForm.length; i++ )
            {
                if ( i == 0 )
                    dataFilter = dataFiltersForForm[0];
                else
                    dataFilter = dataFilter.setChainedFilter(dataFiltersForForm[i]);
            }
            chainedFilterForForm = dataFiltersForForm[0];
        }
        
    }
    /**
     * @return Returns the crudMeta.
     */
    public CrudMeta getCrudMeta()
    {
        return crudMeta;
    }
    /**
     * @param crudMeta The crudMeta to set.
     */
    public void setCrudMeta(CrudMeta crudMeta)
    {
        this.crudMeta = crudMeta;
    }
    /**
     * @return Returns the handCraftView.
     */
    public String getHandCraftView()
    {
        return handCraftView;
    }
    /**
     * @param handCraftView The handCraftView to set.
     */
    public void setHandCraftView(String handCraftView)
    {
        this.handCraftView = handCraftView;
    }
    
    /**
     * @return Returns the chainedFilterForCreateAs.
     */
    public DataFilter getChainedFilterForCreateAs()
    {
        return chainedFilterForCreateAs;
    }
    /**
     * @return Returns the chainedFilterForForm.
     */
    public DataFilter getChainedFilterForForm()
    {
        return chainedFilterForForm;
    }
    /**
     * @param dataFiltersForCreateAs The dataFiltersForCreateAs to set.
     */
    public void setDataFiltersForCreateAs(DataFilter[] dataFiltersForCreateAs)
    {
        this.dataFiltersForCreateAs = dataFiltersForCreateAs;
    }
    /**
     * @return Returns the dataFiltersForForm.
     */
    public DataFilter[] getDataFiltersForForm()
    {
        return dataFiltersForForm;
    }
    /**
     * @param dataFiltersForForm The dataFiltersForForm to set.
     */
    public void setDataFiltersForForm(DataFilter[] dataFiltersForForm)
    {
        this.dataFiltersForForm = dataFiltersForForm;
    }
    
    /**
     * @return Returns the formObjectCreateStrategy.
     */
    public HandCraftFormObjectCreateStrategy getFormObjectCreateStrategy()
    {
        return formObjectCreateStrategy;
    }
    /**
     * @param formObjectCreateStrategy The formObjectCreateStrategy to set.
     */
    public void setFormObjectCreateStrategy(
            HandCraftFormObjectCreateStrategy formObjectCreateStrategy)
    {
        this.formObjectCreateStrategy = formObjectCreateStrategy;
    }
}
