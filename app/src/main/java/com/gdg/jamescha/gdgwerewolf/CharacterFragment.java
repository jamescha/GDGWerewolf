package com.gdg.jamescha.gdgwerewolf;

import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import static com.gdg.jamescha.gdgwerewolf.data.WerewolfContract.CharacterEntry;


public class CharacterFragment extends Fragment implements LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = CharacterFragment.class.getSimpleName();
    private CharacterAdapter mCharacterAdapter;
    private static final String SELECTED_KEY = "selected_position";
    private static final int CHARACTER_LOADER = 0;
    private int mPosition = ListView.INVALID_POSITION;
    private ListView mListView;


    private static final String[] CHARACTER_COLUMNS = {
            CharacterEntry.TABLE_NAME + "." + CharacterEntry._ID,
            CharacterEntry.COLUMN_CHARACTER_NAME,
            CharacterEntry.COLUMN_CHARACTER_IMG,
            CharacterEntry.COLUMN_CHARACTER_DESCRIPTION
    };

    public static final int COL_CHARACTER_ID = 0;
    public static final int COL_CHARACTER_NAME = 1;
    public static final int COL_CHARACTER_IMG = 2;
    public static final int COL_CHARACTER_DESCRIPTION = 3;

    public interface Callback {
        public void onItemSelected(String character);
    }

    public CharacterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mCharacterAdapter = new CharacterAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_character, container, false);

        mListView = (ListView) rootView.findViewById(R.id.listview_character);
        mListView.setAdapter(mCharacterAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = mCharacterAdapter.getCursor();

                if(cursor != null && cursor.moveToPosition(position)) {
                    ((Callback)getActivity())
                            .onItemSelected(cursor.getString(COL_CHARACTER_NAME));
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
        getLoaderManager().initLoader(CHARACTER_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
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
        return new CursorLoader(
                getActivity(),
                CharacterEntry.CONTENT_URI,
                CHARACTER_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCharacterAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCharacterAdapter.swapCursor(null);
    }
}
