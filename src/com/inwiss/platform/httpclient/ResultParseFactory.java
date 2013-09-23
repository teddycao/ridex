package com.inwiss.platform.httpclient;

import java.io.*;
import java.util.*;
import org.dom4j.Document;  
import org.dom4j.DocumentHelper;  
import org.dom4j.Element;  
import org.dom4j.io.OutputFormat;  
import org.dom4j.io.XMLWriter; 

public class ResultParseFactory {

	 public static Map xmltoMap(String xml) {  
	        try {  
	            Map map = new HashMap();  
	            Document document = DocumentHelper.parseText(xml);  
	            Element nodeElement = document.getRootElement();  
	            List node = nodeElement.elements();  
	            for (Iterator it = node.iterator(); it.hasNext();) {  
	                Element elm = (Element) it.next();  
	                map.put(elm.attributeValue("label"), elm.getText());  
	                elm = null;  
	            }  
	            node = null;  
	            nodeElement = null;  
	            document = null;  
	            return map;  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return null;  
	    }  
}
