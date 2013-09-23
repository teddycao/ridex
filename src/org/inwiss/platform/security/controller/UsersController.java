/**
 * 
 */
package org.inwiss.platform.security.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.codehaus.jackson.map.ObjectMapper;
import org.inwiss.platform.common.util.AuthHelper;
import org.inwiss.platform.common.util.PartialCollection;
import org.inwiss.platform.common.util.QueryInfo;
import org.inwiss.platform.model.core.Role;
import org.inwiss.platform.model.core.User;
import org.inwiss.platform.model.vo.UserEditBean;
import org.inwiss.platform.security.service.GroupManager;
import org.inwiss.platform.security.service.RoleManager;
import org.inwiss.platform.security.service.UserManager;
import org.inwiss.platform.service.exception.BeanAlreadyExistsException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Raidery
 *
 */
@Controller
@RequestMapping(value="/s")
public class UsersController {
	
	 Logger logger = LoggerFactory.getLogger(UsersController.class);
	 
	  
	protected BeanUtilsBean beanUtils = null;
	
	
	public UsersController() {
		 beanUtils = new BeanUtilsBean();
	}

	
	@Autowired
	private UserManager userManager;

	public UserManager getUserManager() {
		return userManager;
	}
	
	
	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

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
	private GroupManager groupManager;

	public GroupManager getGroupManager() {
		return groupManager;
	}

	@Autowired
	public void setGroupManager(GroupManager groupManager) {
		this.groupManager = groupManager;
	}
	
	
	@RequestMapping(value="/listUsers",method = RequestMethod.GET)
	public String listUsers(){
		return "security/listUsers";
	}
	
	@RequestMapping(value="/viewUser",method = RequestMethod.GET)
	public String oneUserView(Model model,HttpServletRequest request,String username){
		model.addAttribute("username", username);
		model.addAttribute("userName", username);
		return "security/viewUser";
	}
	
	
	@RequestMapping(value="/retrieveUser",method = RequestMethod.GET)
	protected @ResponseBody Map<String, ? extends Object> 
			  retrieveUser(Model model,@RequestParam(value="username",  required=false) String userName){
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		
		User user = userManager.retrieveUser(userName);

		modelMap.put("data", user);
		
		modelMap.put("success", true);
		
		
		return modelMap;
	}
	
	@RequestMapping(value="/updateUser",method = RequestMethod.POST)
	protected @ResponseBody Map<String, ? extends Object> 
			  updateUser(@RequestParam Object jsonData,HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		
		String userName = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			UserEditBean formUser = mapper.readValue((String)jsonData,UserEditBean.class);
			
			userName = ServletRequestUtils.getStringParameter(request, "name");
			User saveUser = userManager.retrieveUser(userName);
			//User saveUser = new User();
			BeanUtils.copyProperties(formUser,saveUser);
			userManager.updateUser(saveUser);
		} catch (Exception ex) {
				ex.printStackTrace();
		}
				
		
		modelMap.put("success", true);

		return modelMap;
	}
	
	
	@RequestMapping(value="/showUsers")
	protected @ResponseBody Map<String, ? extends Object>
						showUsers(HttpServletRequest request){
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int limit = ServletRequestUtils.getIntParameter(request, "limit", 30);
		QueryInfo queryInfo = new QueryInfo();
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		try {
			queryInfo.setOffset(start);
			queryInfo.setLimit(limit);
			PartialCollection partialCollection = userManager.listUsers(queryInfo);
			
			modelMap.put("result", partialCollection.asList());
			modelMap.put("totalCount", partialCollection.getTotal());
		} catch (Exception ex) {
			modelMap.put("success", false);
		}
		
		return modelMap;
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveUsers",method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> saveUsers(HttpServletRequest request){
		User user = new User();
		BeanUtilsBean beanUtils = new BeanUtilsBean();
		Map params = WebUtils.getParametersStartingWith(request, "");
		
		try {
			beanUtils.copyProperties(user, params);
			//ByteSource salt = new SecureRandomNumberGenerator().nextBytes();
			//new Sha512Hash(password, salt).toBase64();
			String md5Base64 = new Md5Hash(user.getPassword()).toHex();
			
			user.setPassword(md5Base64);
			//
			//user.addFreeRole(new Role);
			userManager.createUser(user);
			
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
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getUserAllRoles",method = RequestMethod.POST)
	protected @ResponseBody Map<String, ? extends Object>
							getUserAllRoles(HttpServletRequest request){
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int limit = ServletRequestUtils.getIntParameter(request, "limit", 50);
		QueryInfo queryInfo = new QueryInfo();
		
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		try {
			String userName = ServletRequestUtils.getStringParameter(request, "userName");
			queryInfo.setOffset(start);
			queryInfo.setLimit(limit);
			PartialCollection partialCollection = roleManager.listRoles(queryInfo);
			List allRoles = partialCollection.asList();
			User currUser = userManager.retrieveUser(userName);
			
			AuthHelper.judgeAuthorized(allRoles,currUser.getFreeRoles());
			modelMap.put("result", allRoles);
			modelMap.put("totalCount", partialCollection.getTotal());
		} catch (Exception ex) {
			modelMap.put("success", false);
		}
		
		return modelMap;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/authRoleToUser")
	protected @ResponseBody Map<String, ? extends Object>
					authRoleToUser(HttpServletRequest request){
	
		
		QueryInfo queryInfo = new QueryInfo();
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		try {
			String userName = ServletRequestUtils.getStringParameter(request, "userId");
			String roleName = ServletRequestUtils.getStringParameter(request, "roleId");
			boolean auth = ServletRequestUtils.getBooleanParameter(request, "auth");
			User user = userManager.retrieveUser(userName);
			Role role = roleManager.retrieveRole(roleName);
			
			if (auth) {
	            if (!user.getFreeRoles().contains(role)) {
	                user.addFreeRole(role);
	            }
	        } else {
	            if (user.getFreeRoles().contains(role)) {
	                user.removeFreeRole(role);
	            }
	        }
			
			
			userManager.updateUser(user);
		} catch (Exception ex) {
			modelMap.put("success", false);
		}
		
		return modelMap;
	}
	
	@RequestMapping(value="/loadUsersByOrgId",method = RequestMethod.POST)
	protected @ResponseBody Map<String, ? extends Object>
						loadUsersByOrgId(HttpServletRequest request){
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int limit = ServletRequestUtils.getIntParameter(request, "limit", 30);
		QueryInfo queryInfo = new QueryInfo();
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		try {
			//get org id of request
			String dept = ServletRequestUtils.getStringParameter(request, "dept");
			Map<String,String> queryParameters = new HashMap<String,String>();
			queryParameters.put("dept", dept);
			queryInfo.setQueryParameters(queryParameters);
			
			queryInfo.setOffset(start);
			queryInfo.setLimit(limit);
			PartialCollection partialCollection = userManager.loadUsersByOrgId(queryInfo);
			
			modelMap.put("result", partialCollection.asList());
			modelMap.put("totalCount", partialCollection.getTotal());
		} catch (Exception ex) {
			modelMap.put("success", false);
		}
		
		return modelMap;
	}
	
	@RequestMapping(value="/removeUser",method = RequestMethod.POST)
	protected @ResponseBody Map<String, ? extends Object> 
			removeUser(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		
		String ids = null;
		try {
			//ObjectMapper mapper = new ObjectMapper();
			//UserEditBean formUser = mapper.readValue((String)jsonData,UserEditBean.class);
			
			ids = ServletRequestUtils.getStringParameter(request, "ids");
			String[] userNames = ids.split(",");
			//User saveUser = userManager.retrieveUser(userName);
			//BeanUtils.copyProperties(formUser,saveUser);
			int length = userNames.length;
			for (int i = 0; i < length; i++) {
				String userName = userNames[i];
				userManager.deleteUser(userName);
			}			
		} catch (Exception ex) {
				ex.printStackTrace();
		}
				
		
		modelMap.put("success", true);

		return modelMap;
	}
	
	/**
	 * 显示修改用户密码页面
	 * @return
	 */
	@RequestMapping(value="/modifyUserPwd",method = RequestMethod.GET)
	public String modifyUserPwd(){
		return "security/modifyUserPwd";
	}
	
	/**
	 * 保存修改后的密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveUserPwd",method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> saveUserPwd(HttpServletRequest request){
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		
		//User user = new User();
		//BeanUtilsBean beanUtils = new BeanUtilsBean();
		//Map<String,Object> params = WebUtils.getParametersStartingWith(request, "");		
		try {
			//beanUtils.copyProperties(user, params);
			String userName = ServletRequestUtils.getStringParameter(request, "name");
			String password = ServletRequestUtils.getStringParameter(request, "password");
			//MD5加密
			String md5Base64 = new Md5Hash(password).toHex();
			//根据用户名获得当前用户信息
			User saveUser = userManager.retrieveUser(userName);
			saveUser.setPassword(md5Base64);
			userManager.updateUser(saveUser);
			
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			e.printStackTrace();
		}
		
		return modelMap;
	}
	
	/**
	 * 检测当前密码输入是否正确
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/checkCurrentPwd",method = RequestMethod.POST)
	protected @ResponseBody
	Map<String, ? extends Object> checkCurrentPwd(HttpServletRequest request){
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
			
		try {
			String userName = ServletRequestUtils.getStringParameter(request, "username");
			String currentPwd = ServletRequestUtils.getStringParameter(request, "currentValue");
			
			//根据用户名获得当前用户信息
			User saveUser = userManager.retrieveUser(userName);
			String saveUserPassword = saveUser.getPassword();//用户之前已经保存过的密码
			//对用户输入的当前密码进行加密
			String currentPwdMd5Base64 = new Md5Hash(currentPwd).toHex();
			
			if(!currentPwdMd5Base64.equals(saveUserPassword)){
				//modelMap.put("message", "[当前密码]输入不正确,请核对后重新输入！");
				modelMap.put("success", false);
			}else{
				modelMap.put("success", true);
			}			
		} catch (Exception e) {
			modelMap.put("success", false);
			e.printStackTrace();
		}
		
		return modelMap;
	}
}
