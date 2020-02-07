package com.custom.hermes.core;

import com.custom.hermes.mode.RequestBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by: Ysw on 2020/2/7.
 */
public class ObjectResponseMake extends ResponseMake {
    private Method method;
    private Object object;

    @Override
    protected Object invokeMethod() {
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void setMethod(RequestBean requestBean) {
        object = OBJECT_CENTER.getObject(resultClass.getName());
        this.method = typeCenter.getMethod(object.getClass(), requestBean);
    }
}
