package com.gdg.jamescha.gdgwerewolf.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jamescha on 2/10/15.
 */
public class WerewolfSyncAdapter extends AbstractThreadedSyncAdapter {

    private final String LOG_TAG = WerewolfSyncAdapter.class.getSimpleName();
    private static GoogleApiClient mClient = null;
    private Boolean alreadyInserted = true;

    public static final int SYNC_INTERNAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERNAL/3;
    Firebase firebase;

    public  WerewolfSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(LOG_TAG, "Starting sync");
        firebase = new Firebase("https://gdgwerewolf.firebaseio.com/");

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        final String DATE_FORMAT = "yyyy-MM-dd";
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.MONTH, -1);
        long startTime = cal.getTimeInMillis();

    }


    public static void onAccountCreated(Account newAccount, Context context) {
       // WerewolfSyncAdapter.
    }
}
