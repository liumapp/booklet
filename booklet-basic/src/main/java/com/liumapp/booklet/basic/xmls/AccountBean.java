package com.liumapp.booklet.basic.xmls;

import lombok.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * file AccountBean.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/26
 */
@XmlRootElement(name = "account")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountBean {
    private int id;
    private String name;
    private String email;
    private String address;
    private Birthday birthday;

    @XmlElement
    public Birthday getBirthday() {
        return birthday;
    }

    @XmlAttribute(name = "number")
    public int getId() {
        return id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    @XmlElement
    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return this.name + "#" + this.id + "#" + this.address + "#" + this.birthday + "#" + this.email;
    }
}