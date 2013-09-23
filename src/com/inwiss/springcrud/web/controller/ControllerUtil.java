/*
 * Created on 2005-9-21
 */
package com.inwiss.springcrud.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.inwiss.springcrud.metadata.*;
import com.inwiss.springcrud.context.MetaBeanRetriever;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.metadata.HandCraftMeta;
import com.inwiss.springcrud.metadata.ImportMeta;
import com.inwiss.springcrud.support.excelinput.ExcelImportMeta;

/**
 * 给Controller使用的工具类。
 * 
 * <p>目前提供下面三项功能：
 * <li>根据请求参数获取相应的CrudMeta对象；
 * <li>根据请求参数获取相应的ImportMeta对象；
 * <li>判断当前请求是否为表单提交请求。
 * 
 * @author 
 */
@Component("controllerUtil")
public class ControllerUtil
{
    /**获取元数据定义的组件*/
	@Autowired
    private MetaBeanRetriever metaBeanRetriever;
    
    /**
     * 根据请求参数获取相应的CrudMeta对象。
     * @param request HTTP请求
     * @return 相应的CrudMeta对象
     */
    public CrudMeta retrieveCrudMetaByRequestParameter(HttpServletRequest request)
    {
        String name = request.getParameter("name");
        Assert.hasLength(name, "缺少指定Crud元数据定义对象的参数");
        
        CrudMeta crudMeta = metaBeanRetriever.getCrudMetaBean(name);
        Assert.notNull(crudMeta, "根据名称：["+name+"]获取的Crud元数据定义对象为空");
        
        return crudMeta;
    }
    
    /**
     * 根据请求参数获取相应的ImportMeta对象。
     * @param request HTTP请求
     * @return 相应的ImportMeta对象
     */
    public ImportMeta retrieveImportMetaByRequestParameter(HttpServletRequest request)
    {
        String name = request.getParameter("name");
        Assert.hasLength(name, "缺少指定Crud元数据定义对象的参数");
        
        ImportMeta importMeta = metaBeanRetriever.getImportMetaBean(name);
        Assert.notNull(importMeta, "根据名称：["+name+"]获取的File元数据定义对象为空");
        
        return importMeta;
    }
    
    
 
    
    /**
     * 根据请求参数获取相应的HandCraftMeta对象。
     * @param request HTTP请求
     * @return 相应的HandCraftMeta对象
     */
    public HandCraftMeta retrieveHandCraftMetaByRequestParameter(HttpServletRequest request)
    {
        String name = request.getParameter("name");
        Assert.hasLength(name, "缺少指定Crud元数据定义对象的参数");
        
        HandCraftMeta handCraftMeta = metaBeanRetriever.getHandCraftMetaBean(name);
        Assert.notNull(handCraftMeta, "根据名称：["+name+"]获取的手工补录元数据定义对象为空");
        
        return handCraftMeta;
    }
    
    /**
     * 确定当前是否是表单提交动作（而不是请求表单动作）。
     * @param request HTTP请求
     * @return 是否为表单提交动作的布尔值
     */
    public boolean isFormSubmission(HttpServletRequest request)
    {
        //如果是GET请求，必定不是表单提交
        if ( "GET".equals(request.getMethod()) )
            return false;
        
        //如果是POST请求，且没有包含requestForm参数的时候才被认为是表单提交动作
        String requestForm = request.getParameter("requestForm");
        return !"true".equals(requestForm);
    }
    
    
    /**
     * @param theMetaBeanRetriever The metaBeanRetriever to set.
     */
    public void setMetaBeanRetriever(MetaBeanRetriever theMetaBeanRetriever)
    {
        this.metaBeanRetriever = theMetaBeanRetriever;
    }
}
