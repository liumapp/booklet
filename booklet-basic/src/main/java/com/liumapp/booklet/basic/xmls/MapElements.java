package com.liumapp.booklet.basic.xmls;

import javax.xml.bind.annotation.XmlElement;

/**
 * file MapElements.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/26
 */
public class MapElements {
    @XmlElement
    public String key;

    @XmlElement
    public AccountBean value;

    @SuppressWarnings("unused")
    private MapElements() {
    } // Required by JAXB

    public MapElements(String key, AccountBean value) {
        this.key = key;
        this.value = value;
    }
}