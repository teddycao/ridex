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
import org.apache.shiro.SecurityUtils;
import org.inwiss.platform.common.util.JsonUtils;
import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.MenuItem;
import org.inwiss.platform.model.core.Role;
import org.inwiss.platform.model.core.User;
import org.inwiss.platform.model.ext.TreeItemMenu;
import org.inwiss.platform.security.service.MenuManager;
import org.inwiss.platform.security.service.UserManager;
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
 * Menu manager
 *@author Raidery
 */
@Controller
@RequestMapping(value="/s")
public class MenuTreeController {

	Logger logger = LoggerFactory.getLogger(MenuTreeController.class);
	
	@Autowired
	private MenuManager menuManager;

	  private byte[] lock = new byte[0];  // 特殊的instance变量
	  
	@Autowired
	private UserManager userManager;
	
	@RequestMapping(value="/listMenutrees",method = RequestMethod.GET)
	public String listRoles(){
		return "security/listMenutree";
	}
	
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	@RequestMapping(value="/showMenutrees")
	public @ResponseBody Map<String, ? extends Object> showMenutrees(String node){
		QueryInfo queryInfo = new QueryInfo();
		PartialCollection partials = menuManager.listMenuItems(queryInfo);
		
		Map<String,Object> child = new HashMap<String,Object>(3);
		List treeList = new ArrayList<Map<String, Object>>();

		for (Iterator iterator = partials.iterator(); iterator.hasNext();) {
			MenuItem item = (MenuItem) iterator.next();
			 child.put("id", item.getId());
		     child.put("text", item.getName());
		     child.put("checked", Boolean.FALSE);
		     child.put("leaf", Boolean.TRUE);
		     treeList.add(child);
		}
		//the spring mvc framework takes a hashmap as the model..... :| so in order to get the json array to the View, we need to put it in a HashMap
	    Map modelMap = new HashMap();
	    modelMap.put("JSON_OBJECT", treeList);
		return modelMap;
	}

	
	/**
	 * loadTreeItems
	 * @param node id
	 * @return json tree items
	 */
	@RequestMapping(value="/loadTreeItems")
	public @ResponseBody List<TreeItemMenu> loadTreeItems(String node) {
		synchronized(lock) {  
		logger.debug("Tree ieam node ID:"+node);
		//bug in ExtJs 3.2.1 and prior requires treeload method to accept
        // at least one parameter or UI node which was expanded keeps
        //spinning with loading animation -1
		List<TreeItemMenu> nodeList = new ArrayList<TreeItemMenu>();
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		QueryInfo queryInfo = new QueryInfo();
		if(node != null && !node.equals("-1")) {
			paraMap.put("parentItemId", new Long(node));
		}
			
		queryInfo.setQueryParameters(paraMap);
		PartialCollection<MenuItem> partials = menuManager.listMenuItems(queryInfo);

		for (Iterator iterator = partials.iterator(); iterator.hasNext();) {
			MenuItem item = (MenuItem) iterator.next();
			//如果该菜单项不在用户使用列表中则循环下一个
			boolean isInclude = isIncludeAtRole(item);
			logger.debug("Tree ieam:"+item.getName() +":ISSHOW:"+isInclude);
		  if(isInclude){
			//nodeList.add(new ExtJsTreeNode(item.getId().toString(),item.getName(), false));
			TreeItemMenu extNode = new TreeItemMenu();
			BeanUtils.copyProperties(item, extNode);
			boolean isleaf = item.getChildItems().size()==0?true:false;
			extNode.setLeaf(isleaf);
			nodeList.add(extNode);
		  }
		}

		return nodeList;
		
		} //end synchronized
	}
	
	
	/**
	 * 判断该菜单项目是否在当前用户使用范围内，如果可以使用返回true 
	 * 不能使用返回false
	 * @param menu
	 * @return
	 */
	protected boolean isIncludeAtRole(MenuItem menu){
		String userName = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userManager.retrieveUser(userName);
		//List<Role> userRoles = user.getRoles();
		//List<Role> menuRoles = menu.getRoles();

		for(Role r1 : menu.getRoles()){
			for(Role r2 : user.getRoles()){
				if(r1.getName().equals(r2.getName())){
					return true;
				}
			}
		}
			
		return false;
	}
    /* loadTreeItems
	 * @param node id
	 * @return json tree items
	 */
	@RequestMapping(value="/insertTreeItem")
	public @ResponseBody Map<String, ? extends Object> insertTreeItem(HttpServletRequest request,String data) {
		
		Map<String,Object> modelMap = new HashMap<String,Object>();
		try {
			//MenuItem node = mapper.readValue((String)data,MenuItem.class);
			
			MenuItem node = JsonUtils.json2Bean(data, MenuItem.class,
	                getExcludesForChildren(), getDatePattern());
			// 更新上级分类
	        Long parentId = node.getParentId();
	        if (parentId != null && parentId != -1L ) {
	        	//MenuItem parent = menuManager.retrieveMenuItem(parentId);
	            //node.setParentItem(parent);
	        	menuManager.createMenuItem(node, parentId, null);
	        }else{
	        	menuManager.createMenuItem(node, null, null);
	        	
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
	@RequestMapping(value="/removeTreeItem")
	public @ResponseBody Map<String, ? extends Object>
					 removeTreeItem(HttpServletRequest request,Long id) {
		
		Map<String,Object> modelMap = new HashMap<String,Object>();
		try {
			//MenuItem node = mapper.readValue((String)data,MenuItem.class);
			if(id == null || id == -1L) throw new ParentItemNotFoundException("Can not remove.");
	        menuManager.deleteMenuItem(id);
	     
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
	@RequestMapping(value="/loadItemData")
	public @ResponseBody Map<String, ? extends Object> 
						loadItemData(HttpServletRequest request,Long id) {
		
		 Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
	        if (id != null && id != -1L ) {
	        	MenuItem treeItem = menuManager.retrieveMenuItem(id);
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
	@RequestMapping(value="/updateTreeItem")
	public @ResponseBody Map<String, ? extends Object> 
				updateTreeItem(Model model,HttpServletRequest request) {
		
		 Map<String,Object> modelMap = new HashMap<String,Object>();
		 Map<String,Object> params = WebUtils.getParametersStartingWith(request, "");
		 BeanUtilsBean beanUtils = new BeanUtilsBean();
		try {
			//MenuItem node = mapper.readValue((String)data,MenuItem.class);
			String itemId = (String)params.get("id");
			MenuItem oraTreeItem = menuManager.retrieveMenuItem(new Long(itemId));
			//copyProperties(Object dest, Object orig) 
			beanUtils.copyProperties(oraTreeItem, params);
			//BeanUtils.copyProperties(params, oraTreeItem,getExcludesForChildren());
			menuManager.updateMenuItem(oraTreeItem, oraTreeItem.getParentId(), null);
			
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
