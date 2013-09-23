/*
 * Created on 2005-8-2
 */
package com.inwiss.springcrud.web.depose;

import java.util.HashMap;
import java.util.Map;

import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.support.paging.PagedListOnDemandHolder;

/**
 * 提供给展现列表数据View使用的Model类，其同义词有“QueryResult”等等。
 * 
 * <P>它包括三个部分的内容：
 * 
 * <li>CRUD的元数据信息：提供给View以便根据其迭代数据，并提供Customerized的展现界面；
 * <li>用户输入的参数：用于构造根据用户上次输入的参数值所得的链接；
 * <li>数据容器：列表数据的容器。
 * 
 * @author 
 */
public class ListExposedModel
{
    /**CRUD元数据信息*/
    private CrudMeta crudMeta;
    
    /**保存用户输入的用于过滤数据的参数值*/
    private Map mapParam = new HashMap();
    
    /**列表数据的容器*/
    private PagedListOnDemandHolder dataHolder;
    
    /**
     * @return Returns the dataHolder.
     */
    public PagedListOnDemandHolder getDataHolder()
    {
        return dataHolder;
    }
    /**
     * @param dataHolder The dataHolder to set.
     */
    public void setDataHolder(PagedListOnDemandHolder dataHolder)
    {
        this.dataHolder = dataHolder;
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
     * @return Returns the mapParam.
     */
    public Map getMapParam()
    {
        return mapParam;
    }
    /**
     * @param mapParam The mapParam to set.
     */
    public void setMapParam(Map mapParam)
    {
        this.mapParam = mapParam;
    }
}
