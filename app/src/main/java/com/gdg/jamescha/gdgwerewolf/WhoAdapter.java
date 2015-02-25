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
 * Created by jamescha on 2/19/15.
 */
public class WhoAdapter extends CursorAdapter {
    public final String LOG_TAG = WhoAdapter.class.getSimpleName();

    public WhoAdapter(Context context, Cursor cursor, int flags) {
        super (context, cursor, flags);
    }

    public static class ViewHolder {
        public final ImageView personImage;
        public final TextView personName;
        public final TextView region;
        public final TextView chapter;
        public final ImageView characterImage;


        public ViewHolder (View view) {
            region = (TextView) view.findViewById(R.id.list_item_region);
            chapter = (TextView) view.findViewById(R.id.list_item_chapter);
            personImage = (ImageView) view.findViewById(R.id.list_item_who_person_image);
            personName = (TextView) view.findViewById(R.id.list_item_who_person_name);
            characterImage = (ImageView) view.findViewById(R.id.list_item_who_character_image);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.d(LOG_TAG, "Created new Who view.");
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_who, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.i(LOG_TAG, "Binding Who View");
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.personImage.setImageResource(cursor.getInt(WhoFragment.COL_WHO_IMG));

        String nameString = cursor.getString(WhoFragment.COL_WHO_NAME);
        viewHolder.personName.setText(nameString);

        String region = cursor.getString(WhoFragment.COL_WHO_REGION);
        viewHolder.region.setText(region);

        String chapter = cursor.getString(WhoFragment.COL_WHO_CHAPTER);
        viewHolder.chapter.setText(chapter);

        viewHolder.characterImage.setImageResource(cursor.getInt(WhoFragment.COL_WHO_CHARACTER));
    }
}
