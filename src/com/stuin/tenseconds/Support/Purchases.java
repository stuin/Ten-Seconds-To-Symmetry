package com.stuin.tenseconds.Support;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.android.vending.billing.IInAppBillingService;

/**
 * Created by Stuart on 5/2/2017.
 */
public class Purchases {

    private IInAppBillingService mService;

    public Purchases(Activity activity) {
        String place = "com.android.vending";
        Intent serviceIntent = new Intent(place + ".billing.InAppBillingService.BIND");
        serviceIntent.setPackage(place);
        activity.bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
        }
    };
}
