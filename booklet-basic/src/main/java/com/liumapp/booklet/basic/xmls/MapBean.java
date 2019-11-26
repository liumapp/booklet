package com.liumapp.booklet.basic.xmls;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;

/**
 * file MapBean.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/26
 */
@XmlRootElement
public class MapBean {
    private HashMap<String, AccountBean> map;

    @XmlJavaTypeAdapter(MapAdapter.class)
    public HashMap<String, AccountBean> getMap() {
        return map;
    }
    public void setMap(HashMap<String, AccountBean> map) {
        this.map = map;
    }
}