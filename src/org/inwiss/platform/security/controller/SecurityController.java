/**
 * 
 */
package org.inwiss.platform.security.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.MenuItem;
import org.inwiss.platform.model.core.Role;
import org.inwiss.platform.model.core.User;
import org.inwiss.platform.model.ext.CheckTreeItem;
import org.inwiss.platform.model.ext.TreeItemMenu;
import org.inwiss.platform.security.service.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Raidery
 *
 */
@Controller

public class SecurityController {
	
	final Logger logger = LoggerFactory.getLogger(SecurityController.class);
	
	@Autowired
	private UserManager userManager;

	
	
	@RequestMapping(value="/login",method= RequestMethod.GET)
    public String showLoginForm(Model model ) {
        return "login";
    }
	
	@RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(Model model ) {
        return "index";
    }
	
	/**
	 * Get current User info
	 * @return User
	 */
	public User getCurrentUser() {
		final String currentUserId = (String) SecurityUtils.getSubject().getPrincipal();
		if (currentUserId != null) {
			return getUser(currentUserId);
		} else {
			return null;
		}
	}


	@RequestMapping(value="/login",method= RequestMethod.POST)
	protected String userLogin(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String username = ServletRequestUtils.getStringParameter(request, "username");
		String password = ServletRequestUtils.getStringParameter(request, "password");
	      //Object securityContext = request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
	      response.setHeader("Cache-Control", "no-cache, must-revalidate");
	      response.setCharacterEncoding("UTF-8");

	      Subject currentUser = SecurityUtils.getSubject();
	      UsernamePasswordToken token = new UsernamePasswordToken(username, password);

	      try {
	    	    currentUser.login( token );
	    	    //if no exception, that's it, we're done!
	    	    response.getWriter().print("{success:true,msg:'succeed'}");
	    	    logger.info( "User [" + currentUser.getPrincipal() + "] logged in successfully." );
	    	} catch ( UnknownAccountException uae ) {
	    	    //username wasn't in the system, show them an error message?
	    	} catch ( IncorrectCredentialsException ice ) {
	    	    //password didn't match, try again?
	    	} catch ( LockedAccountException lae ) {
	    	    //account for that username is locked - can't login.  Show them a message?
	    		response.getWriter().print("{success:false,msg:'can not login!'}");
	    	   
	    	} catch ( AuthenticationException ae ) {
	    		logger.debug("Error authenticating.", ae);
	        	response.getWriter().print("{success:false,msg:'请先登录'}");
	    	}
	    	

	        return null;
	}
	/**
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/s/getUserMenuThree")
	protected @ResponseBody Map<String, ? extends Object>
							getUserMenuThree(HttpServletRequest request) {
			QueryInfo queryInfo = new QueryInfo();
		
			Subject currentUser = SecurityUtils.getSubject();
			if (currentUser.hasRole("ROLE_ADMIN")) {
			    System.out.println("ROLE_ADMIN PASSED");
			} else {
				System.out.println("REJECT");
			}
			
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		try {

			User user = getCurrentUser();
			List<Long>  menuIds = userManager.loadUserMenus(user.getName());
			//List<MenuItem> menus = userManager.loadTopTree("id", "asc");
			//按照position进行排序
			List<MenuItem> menus = userManager.loadTopTree("m.position", "asc");
			List<TreeItemMenu> menuList = filterMenu(menus, menuIds);
			
			modelMap.put("result", menuList);
			modelMap.put("totalCount", menuList.size());
			modelMap.put("success", true);
		} catch (Exception ex) {
			ex.printStackTrace();
			modelMap.put("success", false);
		}
		
		return modelMap;
	}
       
	

	 /**
	  * 
	  * @param menus
	  * @param menuIds
	  * @return
	  */
	  public List<TreeItemMenu> filterMenu(Collection<MenuItem> menus, List<Long> menuIds) {
	        List<TreeItemMenu> menuList = new ArrayList<TreeItemMenu>();

	        for (MenuItem m : menus) {
	            if (menuIds.contains(m.getId())) {
	               
	                TreeItemMenu extNode = new TreeItemMenu();
	    			BeanUtils.copyProperties(m, extNode);
	    			boolean isleaf = m.getChildItems().size()==0?true:false;
	    			extNode.setLeaf(isleaf);
	    			menuList.add(extNode);
	    			
	    			//------------------------------------
	                List<TreeItemMenu> children = filterMenu(m.getChildItems(), menuIds);
	                m.getChildItems().clear();
	                m.getChildItems().addAll(children);
	            }
	        }
	        return menuList;
	  }
	  
	  
	@RequestMapping("/logout")
    public String logout(Model model,HttpServletRequest request) {
        
      Subject subject = SecurityUtils.getSubject(); //get user
       if (subject != null) {                     //if is not already logged out,then ... log out basterd.
        	subject.logout();
       }


      HttpSession session = request.getSession(false); //clear session 
      if( session != null ) {
    	  session.invalidate();
      } 
      SecurityUtils.getSubject().logout();
      
       return "redirect:/index.do";
    }
	
    public User getUser(String userName) {
        return userManager.retrieveUser(userName);
    }
    
    public UserManager getUserManager() {
		return userManager;
	}
	
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	
}
