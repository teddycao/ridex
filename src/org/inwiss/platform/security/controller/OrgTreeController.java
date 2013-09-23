/**
 * 
 */
package org.inwiss.platform.security.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.inwiss.platform.common.util.JsonUtils;
import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.Org;
import org.inwiss.platform.model.ext.TreeItemOrg;
import org.inwiss.platform.security.service.OrgManager;
import org.inwiss.platform.service.exception.ParentItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

/**
 * Org manager
 *@author Lvhq
 */
@Controller
@RequestMapping(value="/s")
public class OrgTreeController {

	Logger logger = LoggerFactory.getLogger(OrgTreeController.class);
	@Autowired
	private OrgManager orgManager;

	public OrgManager getOrgManager() {
		return orgManager;
	}

	@Autowired
	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}
	
	@RequestMapping(value="/listOrgtrees",method = RequestMethod.GET)
	public String listOrgtrees(){
		return "security/listOrgtree";
	}
	
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	@RequestMapping(value="/showOrgtrees")
	public @ResponseBody Map<String, ? extends Object> showOrgtrees(String node){
		QueryInfo queryInfo = new QueryInfo();
		PartialCollection partials = orgManager.listOrgItems(queryInfo);
		
		Map<String,Object> child = new HashMap<String,Object>(3);
		List treeList = new ArrayList<Map<String, Object>>();

		for (Iterator iterator = partials.iterator(); iterator.hasNext();) {
			Org org = (Org) iterator.next();
			 child.put("id", org.getId());
		     child.put("text", org.getName());
		     child.put("checked", Boolean.FALSE);
		     child.put("leaf", Boolean.TRUE);
		     treeList.add(child);
		}
		
	    Map modelMap = new HashMap();
	    modelMap.put("JSON_OBJECT", treeList);
		return modelMap;
	}

	
	/**
	 * loadTreeItems
	 * @param node id
	 * @return json tree items
	 */
	@RequestMapping(value="/loadOrgTreeItems")
	public @ResponseBody List<TreeItemOrg> loadTreeItems(String node) {
		
		//bug in ExtJs 3.2.1 and prior requires treeload method to accept
        // at least one parameter or UI node which was expanded keeps
        //spinning with loading animation -1
		List<TreeItemOrg> nodeList = new ArrayList<TreeItemOrg>();
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		QueryInfo queryInfo = new QueryInfo();
		if(node != null && !node.equals("-1")) {
			paraMap.put("parentItemId", new Long(node));
		}
			
		queryInfo.setQueryParameters(paraMap);
		PartialCollection<Org> partials = orgManager.listOrgItems(queryInfo);

		for (Iterator iterator = partials.iterator(); iterator.hasNext();) {
			Org org = (Org) iterator.next();
			TreeItemOrg extNode = new TreeItemOrg();
			BeanUtils.copyProperties(org, extNode);
			boolean isleaf = org.getChildItems().size()==0?true:false;
			extNode.setLeaf(isleaf);
			nodeList.add(extNode);
		}

		return nodeList;
	}
	
     
	 /**
	  * loadTreeItems
	  * @param node id
	  * @return json tree items
	  */
	@RequestMapping(value="/insertOrgTreeItem")
	public @ResponseBody Map<String, ? extends Object> insertTreeItem(HttpServletRequest request,String data) {
		
		Map<String,Object> modelMap = new HashMap<String,Object>();
		try {
			
			Org node = JsonUtils.json2Bean(data, Org.class,
	                getExcludesForChildren(), getDatePattern());
			// 更新上级分类
	        Long parentId = node.getParentId();
	        if (parentId != null && parentId != -1L ) {
	        	orgManager.createOrgItem(node, parentId, null);
	        }else{
	        	orgManager.createOrgItem(node, null, null);
	        	
	        }
	        
		} catch (Exception ex) {
			ex.printStackTrace();
			modelMap.put("success",false);
		}
		
		logger.debug("Data:"+data);
		
		modelMap.put("success",true);
		// print("{success:true,id:" + entity.getId() + "}");
		 return modelMap;
	}
	
	/**
	 * removeTreeItem
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/removeOrgTreeItem")
	public @ResponseBody Map<String, ? extends Object>
					 removeTreeItem(HttpServletRequest request,Long id) {
		
		Map<String,Object> modelMap = new HashMap<String,Object>();
		try {
			//MenuItem node = mapper.readValue((String)data,MenuItem.class);
			if(id == null || id == -1L) throw new ParentItemNotFoundException("Can not remove.");
			orgManager.deleteOrgItem(id);
	     
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		 modelMap.put("success",true);
		 return modelMap;
	}
	
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/loadOrgItemData")
	public @ResponseBody Map<String, ? extends Object> 
						loadItemData(HttpServletRequest request,Long id) {
		
		 Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
	        if (id != null && id != -1L ) {
	        	Org treeItem = orgManager.retrieveOrgItem(id);
				modelMap.put("results", treeItem);
				//modelMap.put("total", partialCollection.getTotal());
	        }
	        
		} catch (Exception ex) {
			ex.printStackTrace();
			modelMap.put("success", false);
		}
 		modelMap.put("success", true);
		// print("{success:true,id:" + entity.getId() + "}");
		 return modelMap;
	}
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateOrgTreeItem")
	public @ResponseBody Map<String, ? extends Object> 
				updateTreeItem(Model model,HttpServletRequest request) {
		
		 Map<String,Object> modelMap = new HashMap<String,Object>();
		 Map<String,Object> params = WebUtils.getParametersStartingWith(request, "");
		 BeanUtilsBean beanUtils = new BeanUtilsBean();
		try {
			//MenuItem node = mapper.readValue((String)data,MenuItem.class);
			String itemId = (String)params.get("id");
			Org oraTreeItem = orgManager.retrieveOrgItem(new Long(itemId));
			//copyProperties(Object dest, Object orig) 
			beanUtils.copyProperties(oraTreeItem, params);
			//BeanUtils.copyProperties(params, oraTreeItem,getExcludesForChildren());
			orgManager.updateOrgItem(oraTreeItem, oraTreeItem.getParentId(), null);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		 modelMap.put("success",true);
		// print("{success:true,id:" + entity.getId() + "}");
		 return modelMap;
	}
	
	
	/**
     * 根据传入参数，返回对应id分类的子分类，用于显示分类的树形结构.
     * 如果没有指定id的值，则返回所有根节点
     *
     * @throws Exception 异常
     
    public void getChildren() throws Exception {
        long parentId = node;
        List<Menu> list = null;
        String hql = "from Menu";
        String orderBy = " order by theSort asc, id desc";

        if (parentId == -1L) {
            // 根节点
            list = menuManager.find(hql + " where parent is null"
                    + orderBy);
        } else {
            list = menuManager.find(hql + " where parent.id=?" + orderBy,
                    parentId);
        }
      }*/
	
    /**
     * 迭代取得所有节点时候，使用的exclude设置.
     *
     * @return 不需要转化成json的属性列表，默认是空的
     */
    public String[] getExcludesForAll() {
        return new String[] {"class", "root", "parent", "checked", "roles"};
    }

    /**
     * 只取得直接子节点时，使用的exclude设置.
     *
     * @return 不需要转换json的属性数组
     */
    public String[] getExcludesForChildren() {
        return new String[] {
            "class", "root", "parent", "children", "checked", "roles"
        };
    }

    public String getDatePattern() {
        return "yyyy-MM-dd";
    }
    
	
	/**
	 * Inner class for jstree node
	 * @author Riadery@gmail.com
	 *
	 */
	class ExtJsTreeNode {

	    private String id;
	    private String text;
	    private boolean leaf;


	    public ExtJsTreeNode(String id, String text, boolean leaf) {
	        this.text = text;
	        this.leaf = leaf;
	        this.id = id;
	    }

	    public boolean isLeaf() {
	        return leaf;
	    }

	    public void setLeaf(boolean leaf) {
	        this.leaf = leaf;
	    }

	    public String getText() {
	        return text;
	    }

	    public void setText(String text) {
	        this.text = text;
	    }


	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }
	}
}
