package my.test.reflection.di;

import java.util.Map;
import java.util.Properties;

public class Bean {
    private String id;
    private String className;
    private Class<?> aClass;
    private Object object;
    private Map<String, Property> properties;

    public Bean(String id, String className, Map<String, Property> properties) throws ClassNotFoundException {
        this.id = id;
        this.className = className;
        this.properties = properties;
        aClass = Class.forName(className);
    }

    public Object createObject() throws IllegalAccessException, InstantiationException {
        object = aClass.newInstance();
        return object;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, Property> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Property> properties) {
        this.properties = properties;
    }

    public Class<?> getBeanClass() {
        return aClass;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id='" + id + '\'' +
                ", className='" + className + '\'' +
                ", properties=" + properties +
                '}';
    }
}
