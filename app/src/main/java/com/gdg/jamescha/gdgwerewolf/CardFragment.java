package com.gdg.jamescha.gdgwerewolf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ShareActionProvider;

import com.gdg.jamescha.gdgwerewolf.data.WerewolfContract;


public class CardFragment extends Fragment implements LoaderCallbacks {

    private static final String LOG_TAG = CardFragment.class.getSimpleName();

    private ShareActionProvider mShareActionProvider;
    private String mNameStr;
    private String mForcast;

    private static final int CARD_LOADER = 0;

    private static final String[] CHARACTER_COLUMNS = {
            WerewolfContract.CharacterEntry.TABLE_NAME + "." + WerewolfContract.CharacterEntry._ID,
            WerewolfContract.CharacterEntry.COLUMN_CHARACTER_NAME,
            WerewolfContract.CharacterEntry.COLUMN_CHARACTER_DESCRIPTION,
            WerewolfContract.CharacterEntry.COLUMN_CHARACTER_IMG
    };

    private ImageView imageView;

    public CardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card, container, false);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader loader) {

    }
}
