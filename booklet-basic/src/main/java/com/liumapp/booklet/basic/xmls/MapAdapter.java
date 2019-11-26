package com.liumapp.booklet.basic.xmls;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

/**
 * file MapAdapter.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/26
 */
public class MapAdapter extends XmlAdapter<MapElements[], Map<String, AccountBean>> {
    public MapElements[] marshal(Map<String, AccountBean> arg0) throws Exception {
        MapElements[] mapElements = new MapElements[arg0.size()];

        int i = 0;
        for (Map.Entry<String, AccountBean> entry : arg0.entrySet())
            mapElements[i++] = new MapElements(entry.getKey(), entry.getValue());

        return mapElements;
    }

    public Map<String, AccountBean> unmarshal(MapElements[] arg0) throws Exception {
        Map<String, AccountBean> r = new HashMap<>();
        for (MapElements mapelement : arg0)
            r.put(mapelement.key, mapelement.value);
        return r;
    }
}