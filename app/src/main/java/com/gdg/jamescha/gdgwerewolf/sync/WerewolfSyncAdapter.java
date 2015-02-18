package com.gdg.jamescha.gdgwerewolf.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gdg.jamescha.gdgwerewolf.R;
import com.gdg.jamescha.gdgwerewolf.data.WerewolfContract;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Vector;

/**
 * Created by jamescha on 2/10/15.
 */
public class WerewolfSyncAdapter extends AbstractThreadedSyncAdapter {

    private final String LOG_TAG = WerewolfSyncAdapter.class.getSimpleName();
    private static GoogleApiClient mClient = null;
    private Boolean alreadyInserted = false;

    public static final int SYNC_INTERNAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERNAL/3;
    Firebase firebase;

    public  WerewolfSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(LOG_TAG, "Starting sync");

        initializeCharacterTable();

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
    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        Bundle bundle = new Bundle();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SyncRequest request = new SyncRequest.Builder()
                    .syncPeriodic(syncInterval, flexTime)
                    .setSyncAdapter(account, authority)
                    .setExtras(bundle)
                    .build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account, authority, new Bundle(), syncInterval);
        }
    }

    public static Account getSyncAccount(Context context) {
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        if (null == accountManager.getPassword(newAccount)) {
            if(!accountManager.addAccountExplicitly(newAccount,"", null)) {
                return null;
            }

            onAccountCreated(newAccount, context);
        }

        return newAccount;
    }


    public static void onAccountCreated(Account newAccount, Context context) {
       WerewolfSyncAdapter.configurePeriodicSync(context, SYNC_INTERNAL, SYNC_FLEXTIME);
       ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        syncImmediately(context);
    }

    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority), bundle);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
        syncImmediately(context);
    }

    public void initializeCharacterTable ()
    {
        //Adding Character Cards
        if(alreadyInserted == false) {
            Vector<ContentValues> contentValuesVector = new Vector<>(7);

            ContentValues contentValues = new ContentValues();
            contentValues.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_NAME, "Developer Group Organizer");
            contentValues.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_LOGO_IMG, R.drawable.devgrouporglogo);
            contentValues.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_IMG, R.drawable.devgrouporg);
            contentValuesVector.add(contentValues);

            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_NAME, "Engineer");
            contentValues2.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_LOGO_IMG, R.drawable.engineerlogo);
            contentValues2.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_IMG, R.drawable.engineer);
            contentValuesVector.add(contentValues2);

            ContentValues contentValues3 = new ContentValues();
            contentValues3.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_NAME, "Hacker");
            contentValues3.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_LOGO_IMG, R.drawable.hackerlogo);
            contentValues3.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_IMG, R.drawable.hacker);
            contentValuesVector.add(contentValues3);

            ContentValues contentValues4 = new ContentValues();
            contentValues4.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_NAME, "Hacktivist");
            contentValues4.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_LOGO_IMG, R.drawable.hacktivistlogo);
            contentValues4.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_IMG, R.drawable.hacktivist);
            contentValuesVector.add(contentValues4);

            ContentValues contentValues5 = new ContentValues();
            contentValues5.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_NAME, "Script Kiddie");
            contentValues5.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_LOGO_IMG, R.drawable.scriptkiddielogo);
            contentValues5.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_IMG, R.drawable.scriptkiddie);
            contentValuesVector.add(contentValues5);

            ContentValues contentValues6 = new ContentValues();
            contentValues6.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_NAME, "Spy");
            contentValues6.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_LOGO_IMG, R.drawable.spylogo);
            contentValues6.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_IMG, R.drawable.spy);
            contentValuesVector.add(contentValues6);

            ContentValues contentValues7 = new ContentValues();
            contentValues7.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_NAME, "Werewolf");
            contentValues7.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_LOGO_IMG, R.drawable.werewolflogo);
            contentValues7.put(WerewolfContract.CharacterEntry.COLUMN_CHARACTER_IMG, R.drawable.werewolf);
            contentValuesVector.add(contentValues7);

            ContentValues[] contentValueses = new ContentValues[contentValuesVector.size()];
            contentValuesVector.toArray(contentValueses);
            getContext().getContentResolver().bulkInsert(WerewolfContract.CharacterEntry.CONTENT_URI, contentValueses);
            alreadyInserted = true;

            Log.d(LOG_TAG, "Character Data added.");
        }
    }
}
