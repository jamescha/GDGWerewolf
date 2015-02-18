package com.gdg.jamescha.gdgwerewolf;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gdg.jamescha.gdgwerewolf.data.WerewolfContract;


public class CardFragment extends Fragment implements LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = CardFragment.class.getSimpleName();

    private String mNameStr;

    private static final int CARD_LOADER = 0;

    private static final String[] CHARACTER_COLUMNS = {
            WerewolfContract.CharacterEntry.TABLE_NAME + "." + WerewolfContract.CharacterEntry._ID,
            WerewolfContract.CharacterEntry.COLUMN_CHARACTER_NAME,
            WerewolfContract.CharacterEntry.COLUMN_CHARACTER_LOGO_IMG,
            WerewolfContract.CharacterEntry.COLUMN_CHARACTER_IMG
    };

    private ImageView cardView;

    public CardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if(arguments != null) {
            mNameStr = arguments.getString(CardActivity.NAME_KEY);
        }

        View rootView = inflater.inflate(R.layout.fragment_card, container, false);
        cardView = (ImageView) rootView.findViewById(R.id.card_image_view);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle arguments = getArguments();
        if(arguments != null && arguments.containsKey(CardActivity.NAME_KEY)) {
            getLoaderManager().restartLoader(CARD_LOADER, null, this);
        }
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null && arguments.containsKey(CardActivity.NAME_KEY)) {
            getLoaderManager().initLoader(CARD_LOADER, null, this);
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        Uri characterForNameUri = WerewolfContract.CharacterEntry.buildCharacterUriWithName(mNameStr);

        return new CursorLoader(
                getActivity(),
                characterForNameUri,
                CHARACTER_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {


        if(data != null && data.moveToFirst()) {
            Log.d(LOG_TAG, "Data is not null");
            cardView.setImageResource(data.getInt(CharacterFragment.COL_CHARACTER_IMG));
        } else {
            Log.d(LOG_TAG, "Data is null");
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
