package com.custom.hermes.core;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by: Ysw on 2020/2/6.
 */
public class ObjectCenter {
    private final String TAG = this.getClass().getSimpleName();
    private final ConcurrentHashMap<String, Object> objects;
    private static volatile ObjectCenter singleton = null;

    private ObjectCenter() {
        objects = new ConcurrentHashMap<>();
    }

    public static ObjectCenter getInstance() {
        if (singleton == null) {
            synchronized (ObjectCenter.class) {
                if (singleton == null) {
                    singleton = new ObjectCenter();
                }
            }
        }
        return singleton;
    }

    public Object getObject(String name) {
        return objects.get(name);
    }

    public void putObject(String name, Object object) {
        objects.put(name, object);

    }

}
