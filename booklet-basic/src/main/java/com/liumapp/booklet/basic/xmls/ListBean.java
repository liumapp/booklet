package com.liumapp.booklet.basic.xmls;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * file ListBean.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/26
 */
@SuppressWarnings("unchecked")
@XmlRootElement(name = "list-bean")
public class ListBean {
    private String name;
    private List list;

    @XmlElements({
            @XmlElement(name = "account", type = Account.class),
            @XmlElement(name = "bean", type = AccountBean.class)
    })
    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}