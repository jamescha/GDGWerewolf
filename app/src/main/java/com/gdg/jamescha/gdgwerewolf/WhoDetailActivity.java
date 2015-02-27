package com.gdg.jamescha.gdgwerewolf;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.gdg.jamescha.gdgwerewolf.data.WerewolfContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class WhoDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_who_detail);

        int who_id = getIntent().getExtras().getInt("who_id");
        final String who_name = getIntent().getExtras().getString("who_name");
        String who_character = getIntent().getExtras().getString("who_character");

        final TextView textViewName = (TextView) findViewById(R.id.textView_WhoName);
        textViewName.setText(who_name);

        final Switch switchIsDead = (Switch) findViewById(R.id.switch_IsDead);
        switchIsDead.setChecked(getIntent().getExtras().getInt("who_dead") == 1);

        final List<String> characters = Arrays.asList(getResources().getStringArray(R.array.characters));

        final Spinner spinnerCharacter = (Spinner)findViewById(R.id.spinner_Role);
        spinnerCharacter.setSelection(characters.indexOf(who_character));

        final TypedArray characterImages = getResources().obtainTypedArray(R.array.character_image);
        final Activity activityContext = this;
        Button saveButton = (Button)findViewById(R.id.button_Save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = WerewolfContract.WhoEntry.COLUMN_WHO_NAME + "=?";
                String[] selectionArgs = {who_name};

                ContentValues contentValues = new ContentValues();
                contentValues.put(WerewolfContract.WhoEntry.COLUMN_WHO_IS_DEAD, switchIsDead.isChecked() ? 1 : 0);
                contentValues.put(WerewolfContract.WhoEntry.COLUMN_WHO_CHARACTER, characterImages.getResourceId(spinnerCharacter.getSelectedItemPosition(), 2));

                getContentResolver().update(WerewolfContract.WhoEntry.CONTENT_URI, contentValues, selection, selectionArgs);

                Toast.makeText(activityContext, "Saved!", Toast.LENGTH_LONG).show();
                finish();
            }
        });


        Button removeButton = (Button)findViewById(R.id.button_Remove);
        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String selection = WerewolfContract.WhoEntry.COLUMN_WHO_NAME + "=?";
                String[] selectionArgs = {who_name};

                ContentValues contentValues = new ContentValues();
                contentValues.put(WerewolfContract.WhoEntry.COLUMN_WHO_REGION, "0");
                getContentResolver().update(WerewolfContract.WhoEntry.CONTENT_URI, contentValues, selection, selectionArgs);

                Toast.makeText(activityContext, "Removed!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
