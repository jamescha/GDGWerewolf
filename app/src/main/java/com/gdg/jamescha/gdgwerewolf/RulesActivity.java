package com.gdg.jamescha.gdgwerewolf;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class RulesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        if (savedInstanceState == null) {

            NightRulesFragment fragment = new NightRulesFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rules_main, fragment)
                    .commit();
        }
    }
}
