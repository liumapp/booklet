package com.liumapp.booklet.basic.xmls;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * file Jaxb2Test.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/26
 */
public class Jaxb2Test {
    private JAXBContext context = null;

    private StringWriter writer = null;
    private StringReader reader = null;

    private AccountBean bean = null;

    @Before
    public void init() {
        bean = new AccountBean();
        bean.setAddress("北京");
        bean.setEmail("email");
        bean.setId(1);
        bean.setName("jack");
        Birthday day = new Birthday();
        day.setDate("2010-11-22");
        bean.setBirthday(day);

        try {
            context = JAXBContext.newInstance(AccountBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBean2XML() {
        try {
            //下面代码演示将对象转变为xml
            Marshaller mar = context.createMarshaller();
            writer = new StringWriter();
            mar.marshal(bean, writer);
            fail(writer);

            //下面代码演示将上面生成的xml转换为对象
            reader = new StringReader(writer.toString());
            Unmarshaller unmar = context.createUnmarshaller();
            bean = (AccountBean)unmar.unmarshal(reader);
            fail(bean);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @After
    public void destory() {
        context = null;
        bean = null;
        try {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.gc();
    }

    public void fail(Object o) {
        System.out.println(o);
    }

    public void failRed(Object o) {
        System.err.println(o);
    }
}