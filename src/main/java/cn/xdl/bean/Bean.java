package cn.xdl.bean;

import java.util.Map;

public class Bean {
    private String id;
    private String clazz;
    private Map<String,String> properties;

    public Bean() {
    }

    public Bean(String id, String clazz, Map<String, String> properties) {
        this.id = id;
        this.clazz = clazz;
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public String getClazz() {
        return clazz;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id='" + id + '\'' +
                ", clazz='" + clazz + '\'' +
                ", properties=" + properties +
                '}';
    }
}
