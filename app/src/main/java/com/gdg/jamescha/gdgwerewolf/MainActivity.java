package com.gdg.jamescha.gdgwerewolf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;

import com.gdg.jamescha.gdgwerewolf.sync.WerewolfSyncAdapter;


public class MainActivity extends ActionBarActivity implements CharacterFragment.Callback{
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    ViewPager mViewPager;
    FragmentPagerAdapter fragmentPagerAdapter;

    private final String alreadyInsertedKey = "INSERTED_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preference = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        Bundle bundle = new Bundle();
        bundle.putInt(alreadyInsertedKey, preference.getInt(getString(R.string.already_inserted), 0));
        Log.d(LOG_TAG, "AlreadyInsterted is " + preference.getInt(getString(R.string.already_inserted), 0));
        WerewolfSyncAdapter.initializeSyncAdapter(this);

        mViewPager = (ViewPager) findViewById(R.id.vpPager);
        fragmentPagerAdapter = new PageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(fragmentPagerAdapter);
    }

    @Override
    public void onItemSelected(String character) {
        Intent intent = new Intent(this, CardActivity.class)
                .putExtra(CardActivity.NAME_KEY,character);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public class PageAdapter extends FragmentPagerAdapter {
        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new CharacterFragment();
                case 1:
                    return new NightRulesFragment();
                case 2:
                    return new DayRulesFragment();
                case 3:
                    return new WhoFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
