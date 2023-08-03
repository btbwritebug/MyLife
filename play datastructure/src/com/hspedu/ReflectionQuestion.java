package com.hspedu;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@SuppressWarnings({"all"})
public class ReflectionQuestion {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        //  传统方式 new 对象--> 调用方法
//        Cat cat = new Cat();
//        cat.hi();

        Properties properties = new Properties();
        properties.load(new FileInputStream("src/re.properties"));
        String classfullpath = properties.getProperty("classfullpath").toString();
        String methodName = properties.getProperty("method").toString();
        System.out.println(classfullpath + " " + methodName);

        //使用反射机制解决
        //（1）加载类，返回Class类型的对象
        Class cls = Class.forName(classfullpath);
        //（2）通过cls得到你加载的这个类，com.hspedu.Cat的对象实例
        Object o = cls.newInstance();
        System.out.println("o的运行类型" + o.getClass());
        //（3）通过cls得到你加载的类的方法
    }
}
