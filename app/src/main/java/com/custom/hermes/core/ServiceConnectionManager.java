package com.custom.hermes.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.custom.hermes.aidlbean.HermesRequest;
import com.custom.hermes.aidlbean.HermesResponse;
import com.custom.hermes.service.HermesService;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by: Ysw on 2020/2/3.
 */
public class ServiceConnectionManager {
    private static volatile ServiceConnectionManager singleton = null;
    private final ConcurrentHashMap<Class<? extends HermesService>, com.custom.hermes.aidlbean.HermesService>
            hermesServices = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Class<? extends HermesService>, HermesServiceConnection>
            hermesServiceConnections = new ConcurrentHashMap<>();

    private ServiceConnectionManager() {
    }

    public static ServiceConnectionManager getInstance() {
        if (singleton == null) {
            synchronized (ServiceConnectionManager.class) {
                if (singleton == null) {
                    singleton = new ServiceConnectionManager();
                }
            }
        }
        return singleton;
    }

    public void bind(Context context, String packageName, Class<? extends HermesService> service) {
        HermesServiceConnection connection = new HermesServiceConnection(service);
        hermesServiceConnections.put(service, connection);
        Intent intent;
        if (TextUtils.isEmpty(packageName)) {
            intent = new Intent(context, service);
        } else {
            intent = new Intent();
            intent.setClassName(packageName, service.getName());
        }
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }


    public HermesResponse request(Class<HermesService> hermesServiceClass, HermesRequest request) {
        com.custom.hermes.aidlbean.HermesService hermesService = hermesServices.get(hermesServiceClass);
        if (hermesService != null) {
            try {
                return hermesService.send(request);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private class HermesServiceConnection implements ServiceConnection {
        private Class<? extends HermesService> mClass;

        HermesServiceConnection(Class<? extends HermesService> service) {
            mClass = service;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            com.custom.hermes.aidlbean.HermesService hermesService =
                    com.custom.hermes.aidlbean.HermesService.Stub.asInterface(service);
            hermesServices.put(mClass, hermesService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
