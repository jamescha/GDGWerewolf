package com.gdg.jamescha.gdgwerewolf.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by jamescha on 2/10/15.
 */
public class WerewolfAuthenticatorService extends Service{
    private WerewolfAuthenticator mWerewolfAuthenticator;

    @Override
    public void onCreate() {
        mWerewolfAuthenticator = new WerewolfAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mWerewolfAuthenticator.getIBinder();
    }
}
