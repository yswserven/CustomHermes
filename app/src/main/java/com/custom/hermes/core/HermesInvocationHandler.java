package com.custom.hermes.core;

import android.text.TextUtils;

import com.custom.hermes.aidlbean.HermesResponse;
import com.custom.hermes.mode.ResponseBean;
import com.custom.hermes.service.HermesService;
import com.google.gson.Gson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by: Ysw on 2020/2/6.
 */
public class HermesInvocationHandler implements InvocationHandler {
    private final String TAG = this.getClass().getSimpleName();
    private Class clazz;
    private static final Gson GSON = new Gson();
    private Class hermesService;

    public HermesInvocationHandler(Class<? extends HermesService> service, Class clazz) {
        this.hermesService = service;
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        HermesResponse rxBusResponse = Hermes.getInstance().sendObjectRequest(hermesService, clazz, method, args);
        if (!TextUtils.isEmpty(rxBusResponse.getData())) {
            ResponseBean responseBean = GSON.fromJson(rxBusResponse.getData(), ResponseBean.class);
            if (responseBean.getData() != null) {
                String data = GSON.toJson(responseBean.getData());
                Class<?> returnType = method.getReturnType();
                return GSON.fromJson(data, returnType);
            }
        }
        return null;
    }
}
