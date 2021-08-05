package com.hw.tobcore.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouyu on 24/7/2019.
 */
public class Cache {

    private static Cache instance       = null;
    List<Object> cacheList = null;
    Map<String, Object> cacheMap = null;

    private Cache() {
        cacheList = new ArrayList<Object>();
        cacheMap = new HashMap<String, Object>();
    }

    public static Cache getInstance() {
        if(instance == null){
            instance = new Cache();
        }
        return instance;
    }

    public void addList(Object o){
        cacheList.add(o);
    }

    public void removeFromList(Object o){
        cacheList.remove(o);
    }

    public void addMap(String k, Object v){
        cacheMap.put(k,v);
    }

    public void removeFromMap(String k){
        cacheMap.remove(k);
    }

    public List<Object> getCacheList(){
        return this.cacheList;
    }

    public Map<String, Object> getCacheMap(){
        return this.cacheMap;
    }

}
