package cn.xdl.test;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

public class XPathTest {
    public static void main(String[] args) throws DocumentException {
        SAXReader sr = new SAXReader();
        Document doc = sr.read(new File("F:/Java34/0- Java-tools/IDEA/idea-work/reflect/src/main/resources/books.xml"));
        List<Element> es = doc.selectNodes("//name");
        for(Element e : es){
            System.out.println(e.getText());
        }
    }
}
