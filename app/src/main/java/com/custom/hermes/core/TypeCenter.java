package com.custom.hermes.core;

import android.text.TextUtils;

import com.custom.hermes.mode.RequestBean;
import com.custom.hermes.mode.RequestParameter;
import com.custom.hermes.util.TypeUtils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by: Ysw on 2020/2/6.
 */
public class TypeCenter {
    private static volatile TypeCenter singleton = null;

    private ConcurrentHashMap<String, Class<?>> mAnnotatedClasses;
    private ConcurrentHashMap<Class<?>, ConcurrentHashMap<String, Method>> mRawMethods;

    private TypeCenter() {
        mAnnotatedClasses = new ConcurrentHashMap<>();
        mRawMethods = new ConcurrentHashMap<>();
    }

    public static TypeCenter getInstance() {
        if (singleton == null) {
            synchronized (TypeCenter.class) {
                if (singleton == null) {
                    singleton = new TypeCenter();
                }
            }
        }
        return singleton;
    }

    public void register(Class<?> clazz) {
        registerClass(clazz);
        registerMethod(clazz);
    }

    private void registerClass(Class<?> clazz) {
        String name = clazz.getName();
        mAnnotatedClasses.putIfAbsent(name, clazz);
    }

    private void registerMethod(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            mRawMethods.putIfAbsent(clazz, new ConcurrentHashMap<String, Method>());
            ConcurrentHashMap<String, Method> map = mRawMethods.get(clazz);
            String key = TypeUtils.getMethodId(method);
            map.put(key, method);
        }
    }

    public Method getMethod(Class<?> clazz, RequestBean requestBean) {
        String name = requestBean.getMethodName();
        if (name != null) {
            mRawMethods.putIfAbsent(clazz, new ConcurrentHashMap<String, Method>());
            ConcurrentHashMap<String, Method> map = mRawMethods.get(clazz);
            Method method = map.get(name);
            if (method != null) {
                return method;
            }
            int pos = name.indexOf('(');
            Class[] parameters = null;
            RequestParameter[] requestParameters = requestBean.getRequestParameters();
            if (requestParameters != null && requestParameters.length > 0) {
                parameters = new Class[requestParameters.length];
                for (int i = 0; i < requestParameters.length; i++) {
                    parameters[i] = getClassType(requestParameters[i].getParameterClassName());
                }
            }
            method = TypeUtils.getMethod(clazz, name.substring(0, pos), parameters);
            map.put(name, method);
            return method;
        }
        return null;
    }

    public Class getClassType(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        Class<?> clazz = mAnnotatedClasses.get(name);
        if (clazz == null) {
            try {
                clazz = Class.forName(name);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return clazz;
    }

}
