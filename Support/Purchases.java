package com.stuin.tenseconds.Support;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import aidl.com.android.vending.billing.*;

/**
 * Created by Stuart on 5/2/2017.
 */
public class Purchases {

    IInAppBillingService mService;

    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
        }
    };
}
