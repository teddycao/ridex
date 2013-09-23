/*
 *  Copyright 2004 Blandware (http://www.blandware.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.inwiss.platform.common;


/**
 * <p>Constant values used throughout the application.
 * </p>
 * <p><a href="Constants.java.html"><i>View Source</i></a>
 * </p>
 *
 */
public class Constants {
	//~ Static fields/initializers =============================================

	/*
	 * ============================ C O M M O N =========================
	 */

	/**
	 * The name of the configuration hashmap stored in application scope.
	 */
	public static final String CONFIG = "appConfig";

	/**
	 * The global default encoding
	 */
	public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Current system end-of-line setting
     */
    public static final String EOL = System.getProperty("line.separator", "\n");

	/**
	 * The application scoped attribute for persistence engine and class that
	 * implements it
	 */
	public static final String DAO_TYPE = "daoType";
    /**
     * 'Hibernate' DAO type
     */
	public static final String DAO_TYPE_HIBERNATE = "hibernate";

    /**
     * Name of admin console CSS theme
     */
    public static final String ADMIN_CSS_THEME = "adminCssTheme";
    /**
     * Name of front-end CSS theme
     */
    public static final String FRONTEND_CSS_THEME = "frontendCssTheme";

    /**
	 * JNDI Name of Mail Session.  As configured in the appserver.
	 */
	public static final String JNDI_MAIL = "mail/Session";

	/**
	 * Default from address for e-mail messages. You should change
	 * this to an e-mail address that your users can reply to.
	 */
	public static final String DEFAULT_FROM = "atleap@blandware.com";

	/**
	 * Name of property holding contact address
	 */
	public static final String CONTACT_ADDRESS_PROPERTY = "contact.address";

	/**
	 * Name of property holding contact language
	 */
	public static final String CONTACT_LANGUAGE_PROPERTY = "contact.language";


	/*
	 * ============================ S E C U R I T Y =========================
	 */

	/**
	 * Application scoped attribute for authentication url
	 */
	public static final String AUTH_URL = "authURL";

	/**
	 * Application scoped attributes for SSL Switching
	 */
	public static final String HTTP_PORT = "httpPort";
	public static final String HTTPS_PORT = "httpsPort";

    /**
     * Name of database datasource in JNDI
     */
    public static final String DATASOURCE_NAME = "dataSourceName";

	/**
	 * Application context path init parameter
	 */
	public static final String CONTEXT_PATH = "contextPath";

	/**
	 * Application development mode init parameter
	 */
	public static final String DEVELOPMENT_MODE = "developmentMode";


	/**
	 * The application scoped attribute for indicating a secure login
	 */
	public static final String SECURE_LOGIN = "secureLogin";

	/**
	 * The encryption algorithm key to be used for passwords
	 */
	public static final String ENC_ALGORITHM = "algorithm";

	/**
	 * A flag to indicate if passwords should be encrypted
	 */
	public static final String ENCRYPT_PASSWORD = "encryptPassword";

	/**
	 * The session scope attribute under which the User object for the
	 * currently logged in user is stored.
	 */
	public static final String USER_KEY = "com.blandware.atleap.USER_KEY";

	/**
	 * Name of cookie for "Remember Me" functionality.
	 */
	public static final String LOGIN_COOKIE = "sessionId";

    /**
     * Bean name of Acegi userCache from applicationContext-security.xml
     */
    public static final String ACEGI_USER_CACHE_BEAN = "userCache";
    
    /**
     * Bean name of Acegi userDetailsService from applicationContext-security.xml
     */
    public static final String USER_DETAILS_SERVICE_BEAN = "userDetailsService";

    /**
     * Separator which separates definition name and list name
     */
    public static final char TILE_CONTAINER_IDENTIFIER_SEPARATOR = '/';
    
    /*
    * ============================ G R O U P S =========================
    */

	/**
	 * The name of the Administrators group
	 */
	public static final String ADMINS_GROUP = "group_admins";

	/**
	 * The name of the Managers group
	 */
	public static final String MANAGERS_GROUP = "group_managers";

	/**
	 * The name of the Users group
	 */
	public static final String USERS_GROUP = "group_users";

	/**
	 * The name of role manager bean.
	 */
	public static final String ROLE_MANAGER_BEAN = "roleManager";

	/**
	 * The name of group manager bean.
	 */
	public static final String GROUP_MANAGER_BEAN = "groupManager";    

	/**
	 * The name of user manager bean.
	 */
	public static final String USER_MANAGER_BEAN = "userManager";

	/**
	 * The name of lookup manager bean.
	 */
	public static final String LOOKUP_MANAGER_BEAN = "lookupManager";

	/**
	 * The name of menu manager bean.
	 */
	public static final String MENU_MANAGER_BEAN = "menuManager";


	/*
	 * ============================ R E S O U R C E   T Y P E S =========================
	 */

    /**
     * Image (content resource of known image type)
     */
	public static final String RESOURCE_TYPE_IMAGE = "image";

    /**
     * Document (content resource of known document type)
     */
    public static final String RESOURCE_TYPE_DOCUMENT = "document";

    /**
     * File (all the rest content resources)
     */
	public static final String RESOURCE_TYPE_FILE = "file";

	/**
	 * Prefix for resources URIs
	 */
	public static final String RESOURCES_URI_PREFIX = "/rw/resource";

    /**
	 * Prefix of any localized URI (URI with locale suffix)
	 */
	public static final String LOCALIZED_URI_PREFIX = "/rw";

    /**
     * Action mapping for our actions
     */
    public static final String ACTION_MAPPING = "*.do";


	/*
	* ============================ M I M E   T Y P E S =========================
	*/

    /**
     * Plain text
     */
	public static final String MIME_TYPE_PLAIN = "text/plain";
    /**
     * HTML text
     */
	public static final String MIME_TYPE_HTML = "text/html";


    /**
     * =========================== S T A T I S T I C S =========================
     */


}
