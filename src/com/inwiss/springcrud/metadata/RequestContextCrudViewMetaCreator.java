/*
 * Created on 2005-8-10
 */
package com.inwiss.springcrud.metadata;

import javax.servlet.http.HttpServletRequest;

/**
 * 在OpenCrud中，支持根据上下文信息来构造不同的View界面。比如当用户权限比较高的时候，在列表界面上我们可以提供新增、删除等操作按钮；
 * 而当用户权限比较低时，我们可能仅仅提供数据察看的功能。
 * 
 * <p>这些功能的实现，有赖于OpenCrud中CrudViewMeta信息的动态构造，该类完成的工作就是根据用户定义的接口实现（其中完成相应的业务逻辑），
 * 进行CrudViewMeta信息的定制。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class RequestContextCrudViewMetaCreator
{
    public static DynamicCrudViewMeta createRequestContextCrudViewMeta(CrudViewMeta theMeta, HttpServletRequest request)
    {
        DynamicCrudViewMeta newCrudViewMeta = new DynamicCrudViewMeta(theMeta);
        
        if ( theMeta != null )
        {
	        if ( theMeta.getAddEnabledRuler() != null )
	            newCrudViewMeta.setAddEnabled(theMeta.getAddEnabledRuler().getRequestContextBoolean(request));
	        
	        if ( theMeta.getViewSingleEnabledRuler() != null )
	            newCrudViewMeta.setViewSingleEnabled(theMeta.getViewSingleEnabledRuler().getRequestContextBoolean(request));
	        
	        if ( theMeta.getDeleteEnabledRuler() != null )
	            newCrudViewMeta.setDeleteBatchEnabled(theMeta.getDeleteEnabledRuler().getRequestContextBoolean(request));
	        
	        if ( theMeta.getEditSingleEnabledRuler() != null )
	            newCrudViewMeta.setEditSingleEnabled(theMeta.getEditSingleEnabledRuler().getRequestContextBoolean(request));
	        
	        if ( theMeta.getFilterWindowEnabledRuler() != null )
	            newCrudViewMeta.setFilterWindowEnabled(theMeta.getFilterWindowEnabledRuler().getRequestContextBoolean(request));
	        
	        if ( theMeta.getDataImportEnabledRuler() != null )
	        	newCrudViewMeta.setDataImportEnabled(theMeta.getDataImportEnabledRuler().getRequestContextBoolean(request));
        }
        return newCrudViewMeta;
    }
}
