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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @Test
    public void testList2XML() {
        ListBean listBean = new ListBean();
        listBean.setName("list to xml");
        List<Object> list = new ArrayList<>();
        list.add(bean);
        bean = new AccountBean();
        bean.setAddress("china");
        bean.setEmail("tom@125.com");
        bean.setId(2);
        bean.setName("tom");
        Birthday day = new Birthday("2010-11-22");
        bean.setBirthday(day);

        Account acc = new Account();
        acc.setAddress("china");
        acc.setEmail("tom@125.com");
        acc.setId(2);
        acc.setName("tom");
        day = new Birthday("2010-11-22");
        acc.setBirthday(day);
        list.add(bean);
        list.add(acc);
        listBean.setList(list);

        try {
            context = JAXBContext.newInstance(ListBean.class);
            //下面代码演示将对象转变为xml
            Marshaller mar = context.createMarshaller();
            writer = new StringWriter();
            mar.marshal(listBean, writer);
            fail(writer);

            //下面代码演示将上面生成的xml转换为对象
            reader = new StringReader(writer.toString());
            Unmarshaller unmar = context.createUnmarshaller();
            listBean = (ListBean)unmar.unmarshal(reader);
            fail(listBean.getList().get(0));
            fail(listBean.getList().get(1));
            fail(listBean.getList().get(2));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMap2XML() {
        MapBean mapBean = new MapBean();
        HashMap<String, AccountBean> map = new HashMap<String, AccountBean>();
        map.put("NO1", bean);
        bean = new AccountBean();
        bean.setAddress("china");
        bean.setEmail("tom@125.com");
        bean.setId(2);
        bean.setName("tom");
        Birthday day = new Birthday("2010-11-22");
        bean.setBirthday(day);
        map.put("NO2", bean);
        mapBean.setMap(map);

        try {
            context = JAXBContext.newInstance(MapBean.class);
            //下面代码演示将对象转变为xml
            Marshaller mar = context.createMarshaller();
            writer = new StringWriter();
            mar.marshal(mapBean, writer);
            fail(writer);

            //下面代码演示将上面生成的xml转换为对象
            reader = new StringReader(writer.toString());
            Unmarshaller unmar = context.createUnmarshaller();
            mapBean = (MapBean)unmar.unmarshal(reader);
            fail(mapBean.getMap());
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