package com.custom.hermes.core;

import android.content.Context;

import com.custom.hermes.aidlbean.HermesRequest;
import com.custom.hermes.aidlbean.HermesResponse;
import com.custom.hermes.annotion.ClassId;
import com.custom.hermes.mode.RequestBean;
import com.custom.hermes.mode.RequestParameter;
import com.custom.hermes.service.HermesService;
import com.custom.hermes.util.TypeUtils;
import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Created by: Ysw on 2020/2/3.
 */
public class Hermes {
    private static volatile Hermes singleton = null;
    public static final int TYPE_NEW = 0;
    public static final int TYPE_GET = 1;
    private ServiceConnectionManager serviceConnectionManager;
    private TypeCenter typeCenter;
    private Gson GSON = new Gson();

    private Context mContext;

    private Hermes() {
        serviceConnectionManager = ServiceConnectionManager.getInstance();
        typeCenter = TypeCenter.getInstance();
    }

    public static Hermes getInstance() {
        if (singleton == null) {
            synchronized (Hermes.class) {
                if (singleton == null) {
                    singleton = new Hermes();
                }
            }
        }
        return singleton;
    }

    public void connect(Context context, Class<? extends HermesService> service) {
        connectApp(context, null, service);
    }

    private void connectApp(Context context, String packageName, Class<? extends HermesService> service) {
        init(context);
        serviceConnectionManager.bind(context.getApplicationContext(), packageName, service);
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public void register(Class<?> clazz) {
        typeCenter.register(clazz);
    }

    // TODO: Create by Ysw 2020/2/8 这个方法还需要再深入理解
    public <T> T getInstance(Class<T> clazz, Object... parameters) {
        HermesResponse response = sendRequest(HermesService.class, clazz, null, parameters);
        return getProxy(HermesService.class, clazz);
    }

    // TODO: Create by Ysw 2020/2/8 这个动态代理也需要再深入理解
    private <T> T getProxy(Class<? extends HermesService> service, Class<T> clazz) {
        ClassLoader classLoader = service.getClassLoader();
        T proxy = (T) Proxy.newProxyInstance(classLoader, new Class[]{clazz}, new HermesInvocationHandler(service, clazz));
        return proxy;
    }

    private <T> HermesResponse sendRequest(Class<HermesService> HermesServiceClass,
                                           Class<T> clazz, Method method, Object[] parameters) {
        RequestBean requestBean = new RequestBean();
        if (clazz.getAnnotation(ClassId.class) == null) {
            requestBean.setClassName(clazz.getName());
            requestBean.setResultClassName(clazz.getName());
        } else {
            requestBean.setClassName(clazz.getAnnotation(ClassId.class).value());
            requestBean.setResultClassName(clazz.getAnnotation(ClassId.class).value());
        }
        if (method != null) {
            requestBean.setMethodName(TypeUtils.getMethodId(method));
        }
        RequestParameter[] requestParameters = null;
        if (parameters != null && parameters.length > 0) {
            requestParameters = new RequestParameter[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Object parameter = parameters[i];
                String parameterClassName = parameter.getClass().getName();
                String parameterValue = GSON.toJson(parameterClassName);
                RequestParameter requestParameter = new RequestParameter(parameterClassName, parameterValue);
                requestParameters[i] = requestParameter;
            }
        }
        if (requestParameters != null) {
            requestBean.setRequestParameters(requestParameters);
        }
        HermesRequest request = new HermesRequest(GSON.toJson(requestBean), TYPE_GET);
        return serviceConnectionManager.request(HermesServiceClass, request);
    }

    public <T> HermesResponse sendObjectRequest(Class<HermesService> hermesServiceClass,
                                                Class<T> clazz, Method method, Object[] parameters) {
        RequestBean requestBean = new RequestBean();
        if (clazz.getAnnotation(ClassId.class) == null) {
            requestBean.setClassName(clazz.getName());
            requestBean.setResultClassName(clazz.getName());
        } else {
            requestBean.setClassName(clazz.getAnnotation(ClassId.class).value());
            requestBean.setResultClassName(clazz.getAnnotation(ClassId.class).value());
        }
        if (method != null) {
            requestBean.setMethodName(TypeUtils.getMethodId(method));
        }
        RequestParameter[] requestParameters = null;
        if (parameters != null && parameters.length > 0) {
            requestParameters = new RequestParameter[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Object parameter = parameters[i];
                String parameterClassName = parameter.getClass().getName();
                String parameterValue = GSON.toJson(parameterClassName);
                RequestParameter requestParameter = new RequestParameter(parameterClassName, parameterValue);
                requestParameters[i] = requestParameter;
            }
        }
        if (requestParameters != null) {
            requestBean.setRequestParameters(requestParameters);
        }
        HermesRequest request = new HermesRequest(GSON.toJson(requestBean), TYPE_NEW);
        return serviceConnectionManager.request(hermesServiceClass, request);
    }
}
