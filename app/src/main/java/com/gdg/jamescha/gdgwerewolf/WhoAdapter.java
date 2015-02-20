package com.gdg.jamescha.gdgwerewolf;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by jamescha on 2/19/15.
 */
public class WhoAdapter extends CursorAdapter {
    public final String LOG_TAG = WhoAdapter.class.getSimpleName();

    public WhoAdapter(Context context, Cursor cursor, int flags) {
        super (context, cursor, flags);
    }

    public static class ViewHolder {
        public ViewHolder (View view) {

        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_who, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.i(LOG_TAG, "Binding Who View");
        ViewHolder viewHolder = (ViewHolder) view.getTag();


    }
}
