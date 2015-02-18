package com.gdg.jamescha.gdgwerewolf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.firebase.client.Firebase;
import com.gdg.jamescha.gdgwerewolf.sync.WerewolfSyncAdapter;


public class MainActivity extends ActionBarActivity implements CharacterFragment.Callback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CharacterFragment characterFragment = ((CharacterFragment) getSupportFragmentManager().findFragmentById(R.id.character_fragment));
        Firebase.setAndroidContext(this);

        WerewolfSyncAdapter.initializeSyncAdapter(this);
    }

    @Override
    public void onItemSelected(String character) {
        Intent intent = new Intent(this, CardActivity.class)
                .putExtra(CardActivity.NAME_KEY,character);
        startActivity(intent);
    }
}
