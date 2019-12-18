package cn.xdl.test;

import cn.xdl.bean.Bean;
import cn.xdl.bean.Person;
import cn.xdl.util.Parse;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Exception {

        System.out.println("-------反射机制中的方法的测试--------");
        Class c = Class.forName("cn.xdl.bean.Person");
        System.out.println(c);
        Object obj = c.newInstance();
        System.out.println("类完整路径："+c.getName());
        System.out.println("包路径："+c.getPackage());
        System.out.println("类名："+c.getSimpleName());
        Field name = c.getDeclaredField("age");
        System.out.println("成员变量名(公共/私有)："+name);
        Field name2 = c.getField("test");
        System.out.println("成员变量(公共)："+name2);
        name.setAccessible(true);
        name.set(obj,18);
        Method method = c.getMethod("getAge");
        Object zhi = method.invoke(obj);
        System.out.println("值："+zhi);
        System.out.println("-------反射机制中的方法的测试--------\n");

        System.out.println("----------------------------------");
        Parse p = new Parse();
        Person person2 = (Person) p.getBean("person2");
        System.out.println(person2);

    }
}
