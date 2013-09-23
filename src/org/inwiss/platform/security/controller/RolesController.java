/**
 * 
 */
package org.inwiss.platform.security.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.MenuItem;
import org.inwiss.platform.model.core.Role;
import org.inwiss.platform.model.ext.CheckTreeItem;
import org.inwiss.platform.security.service.MenuManager;
import org.inwiss.platform.security.service.RoleManager;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Menu manager
 *
 */
@Controller
@RequestMapping(value="/s")
public class RolesController {
    /** logger. */
    private static Logger logger = LoggerFactory.getLogger(RolesController.class);
    
	@Autowired
	private RoleManager roleManager;

	public RoleManager getRoleManager() {
		return roleManager;
	}

	@Autowired
	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}
	
	@Autowired
	private MenuManager menuManager;

	public MenuManager getMenuManager() {
		return menuManager;
	}

	@Autowired
	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}
	
	
	@RequestMapping(value="/listRoles",method = RequestMethod.GET)
	public String listRoles(){
		return "security/listRoles";
	}
	
	@RequestMapping(value="/showRoles")
	public @ResponseBody Map<String, ? extends Object> 
						showRoles(HttpServletRequest request){
		
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int limit = ServletRequestUtils.getIntParameter(request, "limit", 30);
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		QueryInfo queryInfo = new QueryInfo();
		try {
		
		queryInfo.setOffset(start);
		queryInfo.setLimit(limit);
		 
		PartialCollection partialCollection = roleManager.listRoles(queryInfo);
		modelMap.put("result", partialCollection.asList());
		modelMap.put("totalCount", partialCollection.getTotal());
		modelMap.put("success", true);
		} catch (Exception ex) {
			modelMap.put("success", false);
		}
		return modelMap;

	}
	/**
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/retrieveRole",method = RequestMethod.GET)
	protected @ResponseBody Map<String, ? extends Object> 
		retrieveRole(String name){
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		try {
			Role role = roleManager.retrieveRole(name);

			modelMap.put("result", role);
			modelMap.put("totalCount", 1);
			modelMap.put("success", true);
			
		} catch (Exception ex) {
			// TODO: handle exception
		}
		

		return modelMap;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveRole",method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> saveRole(HttpServletRequest request){
		Role role = new Role();
		BeanUtilsBean beanUtils = new BeanUtilsBean();
		Map params = WebUtils.getParametersStartingWith(request, "");
		
		try {
			beanUtils.copyProperties(role, params);
			roleManager.createRole(role);
			
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (InvocationTargetException e) {

			e.printStackTrace();
		} catch (BeanAlreadyExistsException e) {
			e.printStackTrace();
		}
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		modelMap.put("success", true);
		
		return modelMap;
	}
	
	@RequestMapping(value="/updateRole",method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> updateRole(HttpServletRequest request){

		BeanUtilsBean beanUtils = new BeanUtilsBean();
		Map params = WebUtils.getParametersStartingWith(request, "");
		
		try {
			String roleName = (String)params.get("name");
			Role editRole = roleManager.retrieveRole(roleName);
			beanUtils.copyProperties(editRole, params);
			roleManager.updateRole(editRole);
			
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
		} catch (BeanAlreadyExistsException ex) {
			ex.printStackTrace();
		}
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		modelMap.put("success", true);
		
		return modelMap;
	}
	
	/**
	 * 使用递归方法遍历整个树状菜单
	 * @param allMenu
	 */
	public void buildAllMenuTree(List<MenuItem> allMenu,List<CheckTreeItem> nodeList,Role role) {
		if (allMenu == null || allMenu.size() == 0) {
			return;
		} else {
			Iterator<MenuItem> iter = allMenu.iterator();
			while (iter.hasNext()) {
				MenuItem menuItem = (MenuItem) iter.next();
				CheckTreeItem topNode = new CheckTreeItem();
				BeanUtils.copyProperties(menuItem, topNode);
				
				if (role.getMenuItems().contains(menuItem)) {
					 topNode.setChecked(true);
			
				 } 
				 boolean isleaf = menuItem.getChildItems().size()==0?true:false;
			     topNode.setLeaf(isleaf);
				String sing = (isleaf)?"--":"++";
			    nodeList.add(topNode);
			    logger.debug(sing + " " +menuItem.getName());
				buildAllMenuTree(menuItem.getChildItems(),topNode.getChildren(),role);
			}
		}
	}
	
	@RequestMapping(value="/getRoleMenus")
	public @ResponseBody List<CheckTreeItem> getRoleMenus(String ids,String roleId) {
		QueryInfo queryInfo = new QueryInfo();
		Role role = roleManager.retrieveRole(roleId);
		
		//List<Menu> list = menuManager.listMenuItems( "from Menu where parent is null order by theSort asc,id desc");
		PartialCollection<MenuItem> partials = menuManager.listMenuItems(queryInfo);
		//现支持2层树状菜单
		List<CheckTreeItem> nodeList = new ArrayList<CheckTreeItem>();
		
		buildAllMenuTree(partials.asList(),nodeList,role);
		
		CheckTreeItem root = new CheckTreeItem();
		root.setId(0L);
		root.setName("选择菜单");
		root.setChildren(new  ArrayList<CheckTreeItem>(nodeList));
		root.setLeaf(false);
		root.setChecked(true);
		
		ArrayList<CheckTreeItem> menus = new ArrayList<CheckTreeItem>();
		menus.add(root);
		return menus;
	}
	
	

	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/asignMenuRole",method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> selectRoleMenu(HttpServletRequest request,String ids,String roleId){
		Role role = roleManager.retrieveRole(roleId);
		role.getMenuItems().clear();
		
		 
		//BeanUtilsBean beanUtils = new BeanUtilsBean();
		Map params = WebUtils.getParametersStartingWith(request, "");
		 long mid = 0L;
		try {
			for (String str : ids.split(",")) {

				if(str.startsWith("xnode-")) continue;
				mid = Long.parseLong(str);
				logger.info("{}", mid);
				MenuItem menu = menuManager.retrieveMenuItem(mid);
				if (menu != null) {
					role.getMenuItems().add(menu);
				}

			}
			roleManager.updateRole(role);
		}catch (NumberFormatException nex) {
			logger.info("ID:" + mid + "NOT number exception");
		} catch (BeanAlreadyExistsException ex) {
			ex.printStackTrace();
		}
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		modelMap.put("success", true);
		
		return modelMap;
	}
	
	
	
	/**
     * 显示指定角色下可显示的菜单.
     *
     * @throws Exception 写入writer的时候，抛出异常
     
    public void getMenus() throws Exception {
        Role role = roleManager.get(roleId);
        List<Menu> list = menuManager.find(
                "from Menu where parent is null order by theSort asc,id desc");

        // 因为只有两级菜单，所以这里只需要写两个循环就可以判断哪些菜单被选中了
        // 不考虑多级情况，只从最直接的角度考虑
        for (Menu menu : list) {
            if (role.getMenus().contains(menu)) {
                menu.setChecked(true);

                for (Menu subMenu : menu.getChildren()) {
                    if (role.getMenus().contains(subMenu)) {
                        subMenu.setChecked(true);
                    }
                }
            }
        }

        // 现在checkbox tree的问题是无法在js里设置根节点，必须在json里做一个根节点
        // 如果不设置根节点，getChecked()方法返回的只有第一棵树的数据，疑惑中。
        // 为了他的限制，多写了下面这么多代码，真郁闷
        Menu root = new Menu();
        root.setId(0L);
        root.setName("选择菜单");
        root.setChildren(new LinkedHashSet<Menu>(list));
        root.setChecked(true);

        Menu[] menus = new Menu[] {root};

        JsonUtils.write(menus,
            ServletActionContext.getResponse().getWriter(), getExcludes(),
            getDatePattern());
    }
    */
	
    /**
     * 选择角色对应的菜单.
     *
     * @throws Exception 写入response可能抛出异常吧？
     
    public void selectMenu() throws Exception {
        Role role = roleManager.get(roleId);

        if (role == null) {
            ServletActionContext.getResponse().getWriter()
                                .print("{success:false}");

            return;
        }

        role.getMenus().clear();

        for (String str : ids.split(",")) {
            try {
                long id = Long.parseLong(str);
                logger.info("{}", id);

                Menu menu = menuManager.get(id);
                logger.info("{}", menu);

                if (menu != null) {
                    role.getMenus().add(menu);
                }
            } catch (Exception ex) {
                logger.info("", ex);
            }
        }

        roleManager.save(role);

        ServletActionContext.getResponse().getWriter()
                            .print("{success:true}");
    }
    */
	
}
