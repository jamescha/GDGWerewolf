package com.gdg.jamescha.gdgwerewolf.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.gdg.jamescha.gdgwerewolf.data.WerewolfContract.CharacterEntry;
import static com.gdg.jamescha.gdgwerewolf.data.WerewolfContract.RulesEntry;
import static com.gdg.jamescha.gdgwerewolf.data.WerewolfContract.WhoEntry;

/**
 * Created by jamescha on 2/7/15.
 */
public class WerewolfDbHelper extends SQLiteOpenHelper {

    private final String LOG_TAG = WerewolfDbHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "werewolf.db";

    public WerewolfDbHelper (Context context)
    {
        super (context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CHARACTER_TABLE = "CREATE TABLE " + CharacterEntry.TABLE_NAME + " (" +
                CharacterEntry._ID + " INTEGER PRIMARY KEY, " +
                CharacterEntry.COLUMN_CHARACTER_NAME + " TEXT UNIQUE NOT NULL, " +
                CharacterEntry.COLUMN_CHARACTER_LOGO_IMG + " INTEGER UNIQUE NOT NULL, " +
                CharacterEntry.COLUMN_CHARACTER_IMG + " INTEGER UNIQUE NOT NULL" +
                " )";

        final String SQL_CREATE_RULES_TABLE = "CREATE TABLE " + RulesEntry.TABLE_NAME + " (" +
                RulesEntry._ID + " INTEGER PRIMARY KEY, " +
                RulesEntry.COLUMN_RULE_NAME + " TEXT UNIQUE NOT NULL, " +
                RulesEntry.COLUMN_RULE_DESCRIPTION + " TEXT UNIQUE NOT NULL" +
                " )";

        final String SQL_CREATE_WHO_TABLE = "CREATE TABLE " + WhoEntry.TABLE_NAME + " (" +
                WhoEntry._ID + " INTEGER PRIMARY KEY, " +
                WhoEntry.COLUMN_WHO_NAME + " TEXT UNIQUE NOT NULL, " +
                WhoEntry.COLUMN_WHO_REGION + " TEXT NOT NULL, " +
                WhoEntry.COLUMN_WHO_CHAPTER + " TEXT NOT NULL, " +
                WhoEntry.COLUMN_WHO_IMG + " TEXT NOT NULL, " +
                WhoEntry.COLUMN_WHO_CHARACTER + " TEXT NOT NULL, " +
                WhoEntry.COLUMN_WHO_IS_DEAD + " INTEGER NOT NULL" +
                " )";

        db.execSQL(SQL_CREATE_CHARACTER_TABLE);
        db.execSQL(SQL_CREATE_RULES_TABLE);
        db.execSQL(SQL_CREATE_WHO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CharacterEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RulesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WhoEntry.TABLE_NAME);

        onCreate(db);
    }
}
