/**
 * 
 */
package com.inwiss.springcrud.web.controller;

import java.util.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.inwiss.springcrud.CrudService;
import com.inwiss.springcrud.metadata.ColumnMeta;
import com.inwiss.springcrud.metadata.CrudMeta;
import com.inwiss.springcrud.metadata.DynamicCrudViewMeta;
import com.inwiss.springcrud.metadata.RequestContextCrudViewMetaCreator;
import com.inwiss.springcrud.support.paging.OnDemandPagingSourceProvider;
import com.inwiss.springcrud.support.paging.PagedListOnDemandHolder;



/**
 * @author raidery
 * <p>
 *    <li>Common List Controller</li>
 * </p>
 */
@Controller
@RequestMapping(value = "/s/hut")
public class ListController {

	Logger logger = LoggerFactory.getLogger(ListController.class);

	@Autowired
	private CrudService crudService;

    /**给Controller使用的工具对象*/
	@Autowired
    private ControllerUtil controllerUtil;
    
	public CrudService getCrudService() {
		return crudService;
	}

	@Autowired
	public void setCrudService(CrudService crudService) {
		this.crudService = crudService;
	}
	/**
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
	     //根据CrudMeta中持有的crudViewMeta和request生成DynamicCrudViewMeta，供前端使用。
	     DynamicCrudViewMeta crudViewMeta = RequestContextCrudViewMetaCreator.createRequestContextCrudViewMeta(crudMeta.getCrudViewMeta(), request);
	     model.addAttribute("crudViewMeta",crudViewMeta);
	     model.addAttribute("meta",crudMeta);
	      String listView = (crudMeta.getListView())==null?"hut/listView":crudMeta.getListView();
		return listView;
	}
	
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listDebug", method = RequestMethod.GET)
	public String listDebug(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
	     //根据CrudMeta中持有的crudViewMeta和request生成DynamicCrudViewMeta，供前端使用。
	     DynamicCrudViewMeta crudViewMeta = RequestContextCrudViewMetaCreator.createRequestContextCrudViewMeta(crudMeta.getCrudViewMeta(), request);
	     model.addAttribute("crudViewMeta",crudViewMeta);
	     model.addAttribute("meta",crudMeta);
	      String listView = "hut/listView_debug";
		return listView;
	}
	/**
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/jsapi", method = RequestMethod.GET )
	public String jsapi(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
	     //根据CrudMeta中持有的crudViewMeta和request生成DynamicCrudViewMeta，供前端使用。
	     DynamicCrudViewMeta crudViewMeta = RequestContextCrudViewMetaCreator.createRequestContextCrudViewMeta(crudMeta.getCrudViewMeta(), request);
	     model.addAttribute("crudViewMeta",crudViewMeta);
	     model.addAttribute("meta",crudMeta);
	      String jsapiView = "hut/listJsapi";
		return  jsapiView;
	}
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loadPageData", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> loadPageData
					(Model model,HttpServletRequest request) {
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int limit = ServletRequestUtils.getIntParameter(request, "limit", 30);
		Map<String,Object> paramsMap = WebUtils.getParametersStartingWith(request, "");
		//处理页面右边的条件过滤
		String filterTxt = (String)paramsMap.get("filterTxt");
		String filterValue = (String)paramsMap.get("filterValue");
		
		logger.debug(filterTxt+"--->"+filterValue);
		CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
		ListExposedModel listResult = new ListExposedModel();
		listResult.setCrudMeta(crudMeta);
		listResult.setDataHolder(new PagedListOnDemandHolder());
		
		
		Map  tpTreeMap =  new TreeMap<String, Object>(paramsMap);

		//取出不在定义中的参数
		 Iterator it = paramsMap.entrySet().iterator();   
		 while (it.hasNext()){   
		     Map.Entry pairs = (Map.Entry)it.next(); 
		     String key = (String)pairs.getKey();
		     ColumnMeta columnMeta = crudMeta.getColumnMetaByColName(key);
		     if(columnMeta == null || columnMeta.equals("")){
		    	 tpTreeMap.remove(key);
		     }
		    // System.out.println(pairs.getKey() + " = " + pairs.getValue());
		         
		  } 
		 //添加过滤条件
	     if(filterTxt != null && !filterTxt.equals("") 
	    		 && filterValue != null && !filterValue.equals("")){
	    	 tpTreeMap.put(filterTxt, filterValue);
	     }
	     
		if(tpTreeMap.size() > 0)
			listResult.setMapParam(tpTreeMap);
		try {
			
			String defaultFilterWhereClause = ( crudMeta.getChainedDefaultFilter() != null )?
                crudMeta.getChainedDefaultFilter().getFitlerWhereClause(request)
                :null;
	        
	      listResult.getDataHolder().setSourceProvider(new CrudSourceProvider(crudMeta,listResult.getMapParam(), defaultFilterWhereClause));
          //model.addAttribute("result",listResult);
		  modelMap.put("result", listResult.getDataHolder().getPageList(start,limit));
		  modelMap.put("totalCount", listResult.getDataHolder().getNrOfElements());
		  
		} catch (Exception ex) {
			ex.printStackTrace();
			modelMap.put("success", false);
		}
		return modelMap;
	}
	/**
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loadOneData", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> loadOneData
					(Model model,HttpServletRequest request) {
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map params = WebUtils.getParametersStartingWith(request, "");
			String jsonSetMap = ServletRequestUtils.getStringParameter(request, "keysMap");
        	jsonSetMap = java.net.URLDecoder.decode(jsonSetMap, "UTF-8");
        	Map<String, String> pkFilter = mapper.readValue(jsonSetMap, Map.class);
	       logger.debug("Query post data:" + jsonSetMap);
	       
		 String defaultFilterWhereClause = ( crudMeta.getChainedDefaultFilter() != null )?
	                crudMeta.getChainedDefaultFilter().getFitlerWhereClause(request)
	                :null; 
	      Map oneRecord = (Map)crudService.getItemList(crudMeta, pkFilter,defaultFilterWhereClause).get(0);
		  //Extjs form 要求返回必须为List
	      List records = new ArrayList(1);
	      records.add(oneRecord);
	      modelMap.put("result", records);
		  modelMap.put("totalCount", 1);
		} catch (Exception ex) {
			ex.printStackTrace();
			modelMap.put("success", false);
		}
		return modelMap;
	}
	
	/**
	 * 查看最原始的页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listold", method = RequestMethod.GET)
	public String listorig(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		CrudMeta crudMeta = controllerUtil.retrieveCrudMetaByRequestParameter(request);
		ListExposedModel listResult = new ListExposedModel();
		listResult.setCrudMeta(crudMeta);
		
	    String defaultFilterWhereClause = ( crudMeta.getChainedDefaultFilter() != null )?
                crudMeta.getChainedDefaultFilter().getFitlerWhereClause(request)
                :null;
	        
	    listResult.getDataHolder().setSourceProvider(new CrudSourceProvider(crudMeta,listResult.getMapParam(), defaultFilterWhereClause));
	        
	     
	        //根据CrudMeta中持有的crudViewMeta和request生成DynamicCrudViewMeta，供前端使用。
	        DynamicCrudViewMeta crudViewMeta = RequestContextCrudViewMetaCreator.createRequestContextCrudViewMeta(crudMeta.getCrudViewMeta(), request);
	        model.addAttribute("crudViewMeta",crudViewMeta);
	        
	        model.addAttribute("result",listResult);
	        
	        return "hut/list_old";
	}
	
	  
	
	
	/**
	 * 
	 * @author Raidery
	 *
	 */
	class CrudSourceProvider implements OnDemandPagingSourceProvider {
		private Map mapParam;

		private String defaultFilterWhereClause;

		private CrudMeta crudMeta;

		public CrudSourceProvider(CrudMeta crudMeta, Map mapParam,
				String defaultFilterWhereClause) {
			this.crudMeta = crudMeta;
			this.mapParam = mapParam;
			this.defaultFilterWhereClause = defaultFilterWhereClause;
		}

		/**
		 * @see com.inwiss.springcrud.support.paging.OnDemandPagingSourceProvider#getNrOfElements()
		 */
		public int getNrOfElements() {
			return crudService.getNrOfElements(crudMeta, mapParam,defaultFilterWhereClause);
		}

		/**
		 * @see com.inwiss.springcrud.support.paging.OnDemandPagingSourceProvider#getItemListWithOffset(int,
		 *      int)
		 */
		public List getItemListWithOffset(int nOffset, int nSize) {
			return crudService.getItemListWithOffset(crudMeta, mapParam,nOffset, nSize, defaultFilterWhereClause);
		}

		
		public Map getOneRecord() {
			return (Map)crudService.getItemList(crudMeta, mapParam,defaultFilterWhereClause).get(0);
		}

	}
}



