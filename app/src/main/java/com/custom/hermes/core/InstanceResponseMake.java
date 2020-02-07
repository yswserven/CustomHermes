package com.custom.hermes.core;


import com.custom.hermes.mode.RequestBean;
import com.custom.hermes.mode.RequestParameter;
import com.custom.hermes.util.TypeUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by: Ysw on 2020/2/7.
 */
public class InstanceResponseMake extends ResponseMake {
    private Method method;

    @Override
    protected Object invokeMethod() {
        Object object;
        try {
            object = method.invoke(null, parameters);
            OBJECT_CENTER.putObject(object.getClass().getName(), object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void setMethod(RequestBean requestBean) {
        RequestParameter[] requestParameters = requestBean.getRequestParameters();
        Class<?>[] parameterTypes = null;
        if (requestParameters != null && requestParameters.length > 0) {
            parameterTypes = new Class[requestParameters.length];
            for (int i = 0; i < requestParameters.length; i++) {
                parameterTypes[i] = typeCenter.getClassType(requestParameters[i].getParameterClassName());
            }
        }
        String methodName = requestBean.getMethodName();
        this.method = TypeUtils.getMethodForGettingInstance(resultClass, methodName, parameterTypes);
    }
}
