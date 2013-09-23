package org.inwiss.platform.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XMLHelper {

        public static List getNodeList(Node node, String xPath) {
                return node.selectNodes(xPath);
        }
        
        public static Node getSingleNode(Node node, String xPath){
                return node.selectSingleNode(xPath);
        }

        public static String getNodeText(Node node, String xPath) {
                return node.selectSingleNode(xPath).getText();
        }
        
        public static String getNodeText(Node node, String xPath, String defaultText) {
                try{
                        return node.selectSingleNode(xPath).getText();
                } catch (Exception e){
                        return defaultText;
                }
        }
        
        public static String getAttributeValue(Node node, String xPath, String attrName ) 
                throws Exception {
                return getAttributeValue((Element)getSingleNode(node, xPath), attrName);
        }
        
        public static String getAttributeValue(Node node, String xPath, String attrName,
                        String defaultValue) throws Exception {
                try {
                        return getAttributeValue(node, xPath, attrName);
                } catch (Exception e) {
                        return defaultValue;
                }
        }

        public static String getAttributeValue(Element element, String attrName)
                        throws Exception {
                if (element != null && element.attributeCount() >= 1) {
                        Iterator iter = element.attributeIterator();
                        while (iter.hasNext()) {
                                Attribute attr = (Attribute) iter.next();
                                if (attr.getName().equals(attrName)) {
                                        return attr.getValue();
                                }
                        }
                }
                throw new Exception("Attribute not found!");
        }

        public static String getAttributeValue(Element element, String attrName,
                        String defaultValue) {
                try {
                        return getAttributeValue(element, attrName);
                } catch (Exception e) {
                        return defaultValue;
                }
        }

        public static Document createXmlDocument(String text, String encoding)
                        throws IOException, DocumentException {
                SAXReader saxReader = new SAXReader();
                Document document;
                saxReader.setEncoding(encoding);
                document = saxReader.read(new ByteArrayInputStream(text.getBytes(encoding)));
                return document;
        }

        public static String formatXml(Document document, String encoding) throws IOException {
                StringWriter sw = new StringWriter();
                OutputFormat format = OutputFormat.createPrettyPrint();
                format.setEncoding(encoding);
                XMLWriter writer = new XMLWriter(sw, format);
                writer.write(document);
                writer.close();
                return sw.toString();
        }
}
