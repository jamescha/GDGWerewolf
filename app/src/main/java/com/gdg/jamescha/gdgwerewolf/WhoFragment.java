package com.gdg.jamescha.gdgwerewolf;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gdg.jamescha.gdgwerewolf.data.WerewolfContract.WhoEntry;

public class WhoFragment extends Fragment implements LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = WhoFragment.class.getSimpleName();
    private WhoAdapter mWhoAdapter;
    private static final String SELECTED_KEY = "selected_position";
    private static final int WHO_LOADER = 0;
    private int mPosition = ListView.INVALID_POSITION;
    private ListView mListView;

    public WhoFragment() {
        // Required empty public constructor
    }

    public interface Callback {
        public void onItemSelected(String who);
    }

    private static final String[] WHO_COLUMNS = {
            WhoEntry.TABLE_NAME + "." + WhoEntry._ID,
            WhoEntry.COLUMN_WHO_NAME,
            WhoEntry.COLUMN_WHO_CHAPTER,
            WhoEntry.COLUMN_WHO_REGION,
            WhoEntry.COLUMN_WHO_IMG,
            WhoEntry.COLUMN_WHO_IS_DEAD,
            WhoEntry.COLUMN_WHO_CHARACTER

    };

    public static final int COL_WHO_ID = 0;
    public static final int COL_WHO_NAME = 1;
    public static final int COL_WHO_CHAPTER = 2;
    public static final int COL_WHO_REGION = 3;
    public static final int COL_WHO_IMG = 4;
    public static final int COL_WHO_IS_DEAD = 5;
    public static final int COL_WHO_CHARACTER = 6;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Who Fragment Created");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(LOG_TAG, "View Created");
        mWhoAdapter = new WhoAdapter(getActivity(), null, 0);
        View rootView = inflater.inflate(R.layout.fragment_who, container, false);

        mListView = (ListView) rootView.findViewById(R.id.listview_who);
        mListView.setAdapter(mWhoAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = mWhoAdapter.getCursor();

                if(cursor != null && cursor.moveToPosition(position)) {


                    Intent intent = new Intent(getActivity(), WhoDetailActivity.class);
                    intent.putExtra("who_id", cursor.getInt(COL_WHO_ID));
                    intent.putExtra("who_name", cursor.getString(COL_WHO_NAME));
                    intent.putExtra("who_dead", cursor.getInt(COL_WHO_IS_DEAD));
                    intent.putExtra("who_character", cursor.getInt(COL_WHO_CHARACTER));

                    startActivity(intent);
                }
                mPosition = position;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Activity Created");
        getLoaderManager().initLoader(WHO_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "Created Loader");

        String selection  = WhoEntry.COLUMN_WHO_REGION + "=?";
        String[] selectionArgs = {"1"};

        return new CursorLoader(
                getActivity(),
                WhoEntry.CONTENT_URI,
                WHO_COLUMNS,
                selection,
                selectionArgs,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mWhoAdapter.swapCursor(data);
        Log.d(LOG_TAG, "Load Finished");
        if(mPosition != ListView.INVALID_POSITION) {
            mListView.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mWhoAdapter.swapCursor(null);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
////
//    @Override
//    public boolean onOptionsItemSelected() {
//        int id = item.getItemId();
//        Intent intent = new Intent();
//        intent.put
//        startActivity(intent);
//
//    }intent
}
