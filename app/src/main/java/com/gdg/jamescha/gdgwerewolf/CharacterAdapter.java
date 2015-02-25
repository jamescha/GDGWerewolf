package com.gdg.jamescha.gdgwerewolf;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jamescha on 2/15/15.
 */
public class CharacterAdapter extends CursorAdapter {

    private final String LOG_TAG = CharacterAdapter.class.getSimpleName();

    public CharacterAdapter(Context context, Cursor cursor, int flags) {
        super (context, cursor, flags);
    }

    public static class ViewHolder {
        public final TextView nameView;
        public final ImageView iconView;

        public ViewHolder (View view) {
            nameView = (TextView) view.findViewById(R.id.list_item_name_textview);
            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_character, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.i(LOG_TAG, "Binding Character View");
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.iconView.setImageResource(cursor.getInt(CharacterFragment.COL_CHARACTER_LOGO_IMG));

        String nameString = cursor.getString(CharacterFragment.COL_CHARACTER_NAME);
        viewHolder.nameView.setText(nameString);
    }
}
