/*
 * Created on 2005-9-12
 */
package com.inwiss.springcrud.web.depose;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.BindUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.inwiss.springcrud.command.RecordCommand;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.metadata.DynamicCrudViewMeta;
import com.inwiss.springcrud.metadata.RequestContextCrudViewMetaCreator;
import com.inwiss.springcrud.web.controller.ControllerUtil;

/**
 * 查看单条记录信息的Controller类。
 * 
 * @author wuzixiu
 * @author <a href="mailto:ljglory@126.com">Lujie</a>
 */
public class ViewSingleController extends AbstractController 
{
	
	/**给Controller使用的工具对象*/
    private ControllerUtil controllerUtil;
    
    /**查看单条记录信息的View名称*/
    private String viewName;
    
    /**
     * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
        //先将POST过来的数据绑定到Command对象中。
    	RecordCommand command = new RecordCommand();
    	ServletRequestDataBinder binder = new ServletRequestDataBinder(command,"result");
        binder.bind(request);
        BindingResult br = binder.getBindingResult();
        //BindException ex = binder.getErrors();
        //BindException ex = BindUtils.bind(request, command, "result");
        Map mapModel = br.getModel();
        
        //获取crudMeta，并作为model的一部分。
        CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
        mapModel.put("crudMeta",crudMeta);
        
        //根据CrudMeta中持有的crudViewMeta和request生成DynamicCrudViewMeta，供前端使用。
        DynamicCrudViewMeta crudViewMeta = RequestContextCrudViewMetaCreator.createRequestContextCrudViewMeta(crudMeta.getCrudViewMeta(), request);
        mapModel.put("crudViewMeta",crudViewMeta);
        
        //确定使用哪个View
        String viewName = (crudMeta.getViewSingleView()==null)?this.viewName:crudMeta.getViewSingleView();
        
        return new ModelAndView(viewName,mapModel);
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
}
