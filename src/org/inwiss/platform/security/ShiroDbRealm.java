/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.inwiss.platform.security;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.inwiss.platform.model.core.Role;
import org.inwiss.platform.model.core.User;
import org.inwiss.platform.security.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The Spring/Hibernate sample application's one and only configured Apache Shiro Realm.
 *
 * <p>Because a Realm is really just a security-specific DAO, we could have just made Hibernate calls directly
 * in the implementation and named it a 'HibernateRealm' or something similar.</p>
 *
 * <p>But we've decided to make the calls to the database using a UserDAO, since a DAO would be used in other areas
 * of a 'real' application in addition to here. We felt it better to use that same DAO to show code re-use.</p>
 */
@Component
public class ShiroDbRealm extends AuthorizingRealm {

    protected UserManager userManager = null;
    String roleAndGroupAuthoritiesByUsernameQuery = "  SELECT user_role.username, " +
	"     user_role.rolename , " +
	" user_role.groupname,  " +
	"role1.title rtitle, " +
	" role1.description , " +
	" group1.groupname gtitle, " +
	" group1.title   " +
	"FROM al_core_user_role user_role INNER JOIN al_core_role role1 " +
	" ON user_role.rolename = role1.rolename  " +
	" LEFT OUTER JOIN al_core_group group1 " +
	" ON user_role.groupname = group1.groupname " +
	"WHERE user_role.username = ?";
    
    public ShiroDbRealm() {
        setName("shiroDbRealm"); //This name must match the name in the User class's getPrincipals() method
        setCredentialsMatcher(new HashedCredentialsMatcher("MD5"));
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        User user = userManager.retrieveUser(token.getUsername());
        if( user != null ) {
            return new SimpleAuthenticationInfo(user.getUserId(), user.getPassword(), getName());
        } else {
            return null;
        }
    }


    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String loginName = (String) principals.fromRealm(getName()).iterator().next();
        User user = userManager.retrieveUser(loginName);
        if( user != null ) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            for( Role role :  user.getRoles()) {
            	
                info.addRole(role.getName());
                //info.addStringPermissions( role.getPermissions() );
            }
            return info;
        } else {
            return null;
        }
    }
    

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
}

