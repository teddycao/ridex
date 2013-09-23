/*
 * Created on 2005-9-21
 */
package com.inwiss.springcrud.context;

import com.inwiss.springcrud.metadata.*;
import com.inwiss.springcrud.support.excelinput.ExcelImportMeta;

/**
 * 获取系统定义的Meta信息（包括CrudMeta和ImportMeta）的组件接口。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public interface MetaBeanRetriever
{
    /**
     * 根据名称获取相应的CrudMeta组件。
     * @param crudMetaName
     * @return CrudMeta
     */
    public abstract CrudMeta getCrudMetaBean(String crudMetaName);

    /**
     * 根据名称获取相应的ImportMeta组件。
     * @param corrCrudMetaName ImportMeta组件所引用的CrudMeta组件的名称
     * @return ImportMeta 相应的ImportMeta组件
     */
    public abstract ImportMeta getImportMetaBean(String corrCrudMetaName);
    
     
    /**
     * 根据名称获取相应的HandCraftMeta组件。
     * @param corrCrudMetaName HandCraft组件所引用的CrudMeta组件的名称
     * @return 相应的HandCraftMeta组件
     */
    public abstract HandCraftMeta getHandCraftMetaBean(String corrCrudMetaName);
}