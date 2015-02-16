package com.gdg.jamescha.gdgwerewolf.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by jamescha on 2/10/15.
 */
public class WerewolfSyncService extends Service{
    private static final Object sSyncAdapterLock = new Object();
    private static WerewolfSyncAdapter sWerewolfSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("WerewolfSyncService", "OnCreate - WerewolfSyncService");
        synchronized (sSyncAdapterLock) {
            if(sWerewolfSyncAdapter == null) {
                sWerewolfSyncAdapter = new WerewolfSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sWerewolfSyncAdapter.getSyncAdapterBinder();
    }
}
