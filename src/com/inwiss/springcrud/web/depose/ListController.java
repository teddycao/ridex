/*
 * Created on 2005-8-1
 */
package com.inwiss.springcrud.web.depose;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.inwiss.springcrud.CrudService;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.metadata.DynamicCrudViewMeta;
import com.inwiss.springcrud.metadata.RequestContextCrudViewMetaCreator;
import com.inwiss.springcrud.support.paging.OnDemandPagingSourceProvider;
import com.inwiss.springcrud.support.paging.PagedListOnDemandHolder;
import com.inwiss.springcrud.web.controller.ControllerUtil;

/**
 * 显示数据列表的Controller类。
 * 
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class ListController extends AbstractController
{   
    /**CRUD业务逻辑接口对象*/
    private CrudService crudService;
    
    /**给Controller使用的工具对象*/
    private ControllerUtil controllerUtil;
    
    /**列表View名称*/
    private String viewName;
    
    /**
     * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
        
        ListExposedModel listResult = new ListExposedModel();
        listResult.setCrudMeta(crudMeta);
        listResult.setDataHolder(new PagedListOnDemandHolder());
        
        ServletRequestDataBinder binder = new ServletRequestDataBinder(listResult,"result");
        binder.bind(request);
        BindingResult br = binder.getBindingResult();
        //BindException ex = BindUtils.bind(request, listResult, "result");
        
        String defaultFilterWhereClause = ( crudMeta.getChainedDefaultFilter() != null )?
                crudMeta.getChainedDefaultFilter().getFitlerWhereClause(request)
                :null;
        
        listResult.getDataHolder().setSourceProvider(new CrudSourceProvider(crudMeta,listResult.getMapParam(), defaultFilterWhereClause));
        
        Map mapModel = br.getModel();
        //根据CrudMeta中持有的crudViewMeta和request生成DynamicCrudViewMeta，供前端使用。
        DynamicCrudViewMeta crudViewMeta = RequestContextCrudViewMetaCreator.createRequestContextCrudViewMeta(crudMeta.getCrudViewMeta(), request);
        mapModel.put("crudViewMeta",crudViewMeta);
        
        String viewName = ( crudMeta.getListView() == null )? this.viewName:crudMeta.getListView();
        
        return new ModelAndView(viewName, mapModel);
        
    }

    
    /**
     * @param crudService The crudService to set.
     */
    public void setCrudService(CrudService crudService)
    {
        this.crudService = crudService;
    }
	
    /**
     * @param controllerUtil The controllerUtil to set.
     */
    public void setControllerUtil(ControllerUtil controllerUtil)
    {
        this.controllerUtil = controllerUtil;
    }
    
    /**
     * @param viewName The viewName to set.
     */
    public void setViewName(String viewName)
    {
        this.viewName = viewName;
    }
    
    class CrudSourceProvider implements OnDemandPagingSourceProvider
    {   
        private Map mapParam;
        
        private String defaultFilterWhereClause;
        
        private CrudMeta crudMeta;
        
        public CrudSourceProvider(CrudMeta crudMeta, Map mapParam, String defaultFilterWhereClause)
        {
        	this.crudMeta = crudMeta;
            this.mapParam = mapParam;
            this.defaultFilterWhereClause = defaultFilterWhereClause;
        }
        
        /**
         * @see com.inwiss.springcrud.support.paging.OnDemandPagingSourceProvider#getNrOfElements()
         */
        public int getNrOfElements()
        {
            return crudService.getNrOfElements(crudMeta, mapParam, defaultFilterWhereClause);
        }

        /**
         * @see com.inwiss.springcrud.support.paging.OnDemandPagingSourceProvider#getItemListWithOffset(int, int)
         */
        public List getItemListWithOffset(int nOffset, int nSize)
        {
            return crudService.getItemListWithOffset(crudMeta, mapParam, nOffset, nSize, defaultFilterWhereClause);
        }
        
		public Map getOneRecord() {
			return (Map)crudService.getItemList(crudMeta, mapParam,defaultFilterWhereClause).get(0);
		}
    }
}
