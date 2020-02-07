package com.custom.hermes.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.custom.hermes.aidlbean.HermesRequest;
import com.custom.hermes.aidlbean.HermesResponse;
import com.custom.hermes.core.Hermes;
import com.custom.hermes.core.InstanceResponseMake;
import com.custom.hermes.core.ObjectResponseMake;
import com.custom.hermes.core.ResponseMake;

import androidx.annotation.Nullable;

/**
 * Created by: Ysw on 2020/2/3.
 */
public class HermesService extends Service {

    private com.custom.hermes.aidlbean.HermesService.Stub mBinder = new com.custom.hermes.aidlbean.HermesService.Stub() {
        @Override
        public HermesResponse send(HermesRequest request) throws RemoteException {
            ResponseMake responseMake = null;
            switch (request.getType()) {
                case Hermes.TYPE_GET:
                    responseMake = new InstanceResponseMake();
                    break;
                case Hermes.TYPE_NEW:
                    responseMake = new ObjectResponseMake();
                    break;
            }
            return responseMake.makeResponse(request);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}
