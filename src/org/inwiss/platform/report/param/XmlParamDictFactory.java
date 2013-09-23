package org.inwiss.platform.report.param;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.inwiss.platform.xml.XMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



@Component("XmlParamDictFactory")      
public class XmlParamDictFactory implements InitializingBean  {

	private Logger logger = LoggerFactory.getLogger(XmlParamDictFactory.class);
	/**
	 * 
	 */
	
	protected Collection<ParamDictBean>  paramsContent = new LinkedHashSet<ParamDictBean>();
	protected boolean reload = false;
	protected String resource = "param_dict.xml";
	//~ Methods ////////////////////////////////////////////////////////////////

	public void afterPropertiesSet() throws XmlFactoryException{
		try {
			initDone();
		} catch (XmlFactoryException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @throws XmlFactoryException
	 */
	public void initDone() throws XmlFactoryException{

		InputStream is = getInputStream(this.resource);
        if (is == null) {
            throw new XmlFactoryException("Unable to find workflows file '" + this.resource + "' in classpath");
        }
        
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db;
            try {
                db = dbf.newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                throw new XmlFactoryException("Error creating document builder", ex);
            }

            Document doc = db.parse(is);
            //Element root = (Element) doc.getElementsByTagName("paramdef").item(0);
            Element root = doc.getDocumentElement();  
            //String basedir = getBaseDir(root);

            List list = XMLUtil.getChildElements(root, "param");
            
            parseXmlContent(list);
           System.out.println(list.size());
        } catch (Exception ex) {
            throw new XmlFactoryException("Error in workflow config", ex);
        }
		
	}
	
	/**
	 * 
	 * @param params
	 */
	protected void parseXmlContent(List params){
		
		for (int i = 0; i < params.size(); i++) {
			Element e = (Element) params.get(i);
			ParamDictBean pdb =  new ParamDictBean(e.getAttribute("code"),
					  e.getAttribute("type"), e.getAttribute("default"),e.getAttribute("datasource"));
			
			if (!e.getAttribute(Param.TYPE).equals(Param.ENUM)) {
				Element t = XMLUtil.getChildElement(e, "entry");
				pdb.setParamSql(t.getTextContent());
				
			}else{
				 List parVals = XMLUtil.getChildElements(e, "entry");
				 
				 pdb.setEntryList(this.parseParamEntry(parVals));
			}
			
			
			//logger.info(pdb.toString());
			this.paramsContent.add(pdb);
		}
        
		
	}
	
	/**
	 * 
	 * @param params
	 */
	protected Collection<EntryHashMap> parseParamEntry(List params){
		Collection<EntryHashMap> entrys = new LinkedHashSet <EntryHashMap>(1);
		for (int i = 0; i < params.size(); i++) {
			Element e = (Element) params.get(i);
			entrys.add(new EntryHashMap(e.getAttribute("key"),e.getTextContent()));
		}
		
		return entrys;
	}
    /**
     * Get where to find workflow XML files.
     * @param root The root element of the XML file.
     * @return The absolute base dir used for finding these files or null.
     */
    protected String getBaseDir(Element root) {
        String basedir = root.getAttribute("basedir");

        if (basedir.length() == 0) {
            // No base dir defined
            return null;
        }

        if (new File(basedir).isAbsolute()) {
            // An absolute base dir defined
            return basedir;
        } else {
            // Append the current working directory to the relative base dir
            return new File(System.getProperty("user.dir"), basedir).getAbsolutePath();
        }
    }
	/**
     * Load the param config file from the current context classloader.
     * The search order is:
     * <li>Specified URL</li>
     * <li>&lt;name&gt;</li>
     * <li>/&lt;name&gt;</li>
     * <li>META-INF/&lt;name&gt;</li>
     * <li>/META-INF/&lt;name&gt;</li>
     */
    protected InputStream getInputStream(String name) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = null;

		if ((name != null) && (name.indexOf(":/") > -1)) {
			try {
				is = new URL(name).openStream();
			} catch (Exception e) {
			}
		}

		if (is == null) {
			try {
				is = classLoader.getResourceAsStream(name);
			} catch (Exception e) {
			}
		}

        if (is == null) {
            try {
                is = classLoader.getResourceAsStream('/' + name);
            } catch (Exception e) {
            }
        }

        if (is == null) {
            try {
                is = classLoader.getResourceAsStream("META-INF/" + name);
            } catch (Exception e) {
            }
        }

        if (is == null) {
            try {
                is = classLoader.getResourceAsStream("/META-INF/" + name);
            } catch (Exception e) {
            }
        }

        return is;
    }
    
    
	public boolean isReload() {
		return reload;
	}

	public void setReload(boolean reload) {
		this.reload = reload;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public Collection<ParamDictBean> getParamsContent() {
		return paramsContent;
	}

	
}
