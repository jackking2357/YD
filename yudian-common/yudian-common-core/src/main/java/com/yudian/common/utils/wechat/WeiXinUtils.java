package com.yudian.common.utils.wechat;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class WeiXinUtils {

    
    private static Map<String, String> USER_OPENID = new HashMap<>();

    public static Map<String, String> getUserOpenid() {
        return USER_OPENID;
    }

    public static void setUserOpenid(Map<String, String> userOpenid) {
        USER_OPENID = userOpenid;
    }


    
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes(StandardCharsets.UTF_8));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {

            }
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }


    
    public static void responsePrint(HttpServletResponse response, String content) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        response.getWriter().print(content);
        response.getWriter().flush();
        response.getWriter().close();
    }

}
