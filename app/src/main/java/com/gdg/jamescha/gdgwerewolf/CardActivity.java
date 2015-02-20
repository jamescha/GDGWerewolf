package com.gdg.jamescha.gdgwerewolf;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class CardActivity extends ActionBarActivity {

    public static final String NAME_KEY = "character_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        getSupportActionBar().setTitle("Card Detail");
        if (savedInstanceState == null) {
            String name = getIntent().getStringExtra(NAME_KEY);

            Bundle arguments = new Bundle();
            arguments.putString(CardActivity.NAME_KEY, name);

            CardFragment fragment = new CardFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.card_main, fragment)
                    .commit();
        }
    }
}
