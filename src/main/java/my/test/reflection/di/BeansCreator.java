package my.test.reflection.di;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeansCreator {
    private static final String TAG_BEAN = "bean";
    private static final String TAG_PROPERTY = "property";

    private List<Bean> beans = new ArrayList<>();

    public BeansCreator() {}

    public List<Bean> getBeansFromXml(String xmlPath) throws ParserConfigurationException, IOException, SAXException, InvalidConfigurationException, ClassNotFoundException {
        Document document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new File(xmlPath));

        Element root = document.getDocumentElement();
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node bean = nodes.item(i);
            if (TAG_BEAN.equals(bean.getNodeName()))
                parseBean(bean);
        }
        return (beans);
    }

    private void parseBean(Node bean) throws InvalidConfigurationException, ClassNotFoundException {
        NamedNodeMap attributes = bean.getAttributes();

        String idValue = attributes.getNamedItem("id").getNodeValue();
        String classValue = attributes.getNamedItem("class").getNodeValue();

        Map<String, Property> properties = new HashMap<>();
        NodeList nodes = bean.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (TAG_PROPERTY.equals(node.getNodeName())) {
                Property property = parseProperty(node);
                properties.put(property.getName(), property);
            }
        }
        beans.add(new Bean(idValue, classValue, properties));
    }

    private Property parseProperty(Node node) throws InvalidConfigurationException {
        NamedNodeMap attributes = node.getAttributes();

        String name = attributes.getNamedItem("name").getNodeValue();
        Node val = attributes.getNamedItem("val");
        if (val != null) {
            return (new Property(name, val.getNodeValue(), ValueType.VALUE));
        }
        Node ref = attributes.getNamedItem("ref");
        if (ref != null){
            return (new Property(name, ref.getNodeValue(), ValueType.REF));
        }
        throw new InvalidConfigurationException("Failed to find attribute val or ref");
    }
}
