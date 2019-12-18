package cn.xdl.util;

import cn.xdl.bean.Bean;
import netscape.javascript.JSUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parse {

    //private Map<String,Object> map = null;
    public List<Bean> parseXML() {
        InputStream is = null;
        try {
            is = new FileInputStream("F:/Java34/0- Java-tools/IDEA/idea-work/reflect/src/main/resources/bean.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SAXReader sr = new SAXReader();
        Document doc = null;
        try {
            doc = sr.read(is);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = doc.getRootElement();            //获取到根标签
        List<Element> es = root.elements();             //获取到跟标签下的所有子标签
        List<Bean> beanList = new ArrayList<>();        //创建一个List集合准备存储要创建的对象信息
        for(Element bean : es){                                 //循环遍历 得到所有bean标签
            String id = bean.attributeValue("id");           //获取到id属性值
            String clazz = bean.attributeValue("class");     //获取到class属性值
            List<Element> pros = bean.elements();               //获取到bean标签下面的参数标签property
            if(pros != null){                                   //当参数标签不等于空时查找出参数信息添加到List集合中
                Map<String,String> property = new HashMap<>();
                for(Element pro : pros){
                    String name = pro.attributeValue("name");
                    String value = pro.attributeValue("value");   //存储到Map集合中时的存储类型
                    property.put(name,value);
                }
                beanList.add(new Bean(id,clazz,property));
            }else{
                beanList.add(new Bean(id,clazz,null));
            }
        }
        return beanList;
    }

    /* 创建对象 */
    public Map<String,Object> createBean(){
        List<Bean> beanList = parseXML();                   //得到解析后的参数集合
        Map<String,Object> map = new HashMap<>();           //创建一个Map集合将创建好的对象都存在该集合中
        for(Bean bean : beanList){                          //遍历List集合中的参数对象
            Map pros = bean.getProperties();
            if(pros.isEmpty()){                             //如果参数bean对象中的参数成员变量为空，则直接反射创建无参对象
                String id = bean.getId();                   //得到id属性值
                String clazz = bean.getClazz();             //得到class属性值
                try {
                    Class c = Class.forName(clazz);         //得到Class类对象
                    Object obj = c.newInstance();           //通过Class对象来创建实例对象
                    map.put(id,obj);                        //将创建好的对象存储到Map集合中
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }else{                                          //当集合不为空时
                String id = bean.getId();                   //得到id属性值
                String clazz = bean.getClazz();             //得到class属性值
                try {
                    Class c = Class.forName(clazz);         //通过class的属性值得到Class对象
                    Object obj = c.newInstance();           //再通过Class对象创建class属性值所对应的实例对象
                    for(Object key : pros.keySet()){        //遍历参数
                        Object value = pros.get(key);       //得到key对应的value值 key对应就是方法名 value对应的是参数值
                        /* 字符串的转换开始 */
                        String str = (String) key;
                        String first = str.substring(0,1);
                        String firstUpper = first.toUpperCase();
                        StringBuilder sb = new StringBuilder(str);
                        sb.replace(0,1,firstUpper);
                        String methodName = "set"+sb;
                        /* 字符串的转换结束 */
                        String className = value.getClass().getName();
                        Field field = c.getDeclaredField((String) key);
                        //通过key拿到Class类中的成员属性名
                        Method method = c.getMethod(methodName,field.getType());
                        Class<?> type = field.getType();
                        //field.setAccessible(true);
                        if(value.getClass().getName().equals(type.getName())) {
                            method.invoke(obj,value);
                        }else {
                            Integer integer = Integer.valueOf((String)value);
                            method.invoke(obj,integer);
                        }
                    }
                    map.put(id,obj);
                } catch (ClassNotFoundException | NoSuchFieldException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
        return map;
    }

    public Object getBean(String id){
        Map<String, Object> beans = createBean();
        for(Object beanId : beans.keySet()){
            if(id.equals(beanId)){
                return beans.get(id);
            }
        }
        return null;
    }


}
