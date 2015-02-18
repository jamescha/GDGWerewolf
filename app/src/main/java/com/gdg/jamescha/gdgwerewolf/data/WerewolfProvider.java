package com.gdg.jamescha.gdgwerewolf.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;


/**
 * Created by jamescha on 2/8/15.
 */
public class WerewolfProvider extends ContentProvider{

    private static final String LOG_TAG = WerewolfProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher= buildUriMatcher();

    private WerewolfDbHelper werewolfDbHelper;

    private static final int CHARACTER = 100;
    private static final int RULES = 200;
    private static final int WHO = 300;

    private static final int CHARACTER_ID = 101;
    private static final int RULES_ID = 201;
    private static final int WHO_ID = 301;

    private static final int CHARACTER_NAME = 102;

    private static final SQLiteQueryBuilder sQueryBuilder;

    static {
        sQueryBuilder = new SQLiteQueryBuilder();
        sQueryBuilder.setTables(WerewolfContract.CharacterEntry.TABLE_NAME + ", " +
                                WerewolfContract.RulesEntry.TABLE_NAME + ", " +
                                WerewolfContract.WhoEntry.TABLE_NAME);
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = WerewolfContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, WerewolfContract.PATH_CHARACTER, CHARACTER);
        matcher.addURI(authority, WerewolfContract.PATH_RULES, RULES);
        matcher.addURI(authority, WerewolfContract.PATH_WHO, WHO);

        matcher.addURI(authority, WerewolfContract.PATH_CHARACTER + "/#", CHARACTER_ID);
        matcher.addURI(authority, WerewolfContract.PATH_RULES + "/#", RULES_ID);
        matcher.addURI(authority, WerewolfContract.PATH_WHO + "/#", WHO_ID);

        matcher.addURI(authority, WerewolfContract.PATH_CHARACTER + "/*", CHARACTER_NAME);

        return matcher;
    }

    private static final String sCharacterSelection = WerewolfContract.CharacterEntry.TABLE_NAME +
            "." + WerewolfContract.CharacterEntry.COLUMN_CHARACTER_NAME + " = ? ";

    private static final String sWHoSeletion = WerewolfContract.WhoEntry.TABLE_NAME +
            "." + WerewolfContract.WhoEntry.COLUMN_WHO_NAME + " = ? ";


    @Override
    public boolean onCreate() {
        werewolfDbHelper = new WerewolfDbHelper((getContext()));
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            case CHARACTER: {
                retCursor = werewolfDbHelper.getReadableDatabase().query(
                        WerewolfContract.CharacterEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case CHARACTER_ID: {
                retCursor = werewolfDbHelper.getReadableDatabase().query(
                        WerewolfContract.CharacterEntry.TABLE_NAME,
                        projection,
                        WerewolfContract.CharacterEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case CHARACTER_NAME: {
                String characterName = WerewolfContract.CharacterEntry.getCharacterNameFromUri(uri);
                selectionArgs = new String[]{characterName};
                retCursor = werewolfDbHelper.getReadableDatabase().query(
                        WerewolfContract.CharacterEntry.TABLE_NAME,
                        projection,
                        sCharacterSelection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

                String message = new StringBuilder().append("Count: ").append(retCursor.getCount())
                        .append("  CharacterName: ").append(characterName).toString();
                Log.d(LOG_TAG, message);
                break;
            }

            case WHO: {
                retCursor = werewolfDbHelper.getReadableDatabase().query(
                        WerewolfContract.WhoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case WHO_ID: {
                retCursor = werewolfDbHelper.getReadableDatabase().query(
                        WerewolfContract.WhoEntry.TABLE_NAME,
                        projection,
                        WerewolfContract.WhoEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case RULES: {
                retCursor = werewolfDbHelper.getReadableDatabase().query(
                        WerewolfContract.RulesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case RULES_ID: {
                retCursor = werewolfDbHelper.getReadableDatabase().query(
                        WerewolfContract.RulesEntry.TABLE_NAME,
                        projection,
                        WerewolfContract.RulesEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unkown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case CHARACTER:
                return WerewolfContract.CharacterEntry.CONTENT_TYPE;
            case CHARACTER_ID:
                return WerewolfContract.CharacterEntry.CONTENT_ITEM_TYPE;
            case RULES:
                return WerewolfContract.RulesEntry.CONTENT_TYPE;
            case RULES_ID:
                return WerewolfContract.RulesEntry.CONTENT_ITEM_TYPE;
            case WHO:
                return WerewolfContract.WhoEntry.CONTENT_TYPE;
            case WHO_ID:
                return WerewolfContract.WhoEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unkown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = werewolfDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case CHARACTER: {
                long _id = db.insert(WerewolfContract.CharacterEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = WerewolfContract.CharacterEntry.buildCharacterUri(_id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            }
            case RULES: {
                long _id = db.insert(WerewolfContract.RulesEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = WerewolfContract.RulesEntry.buildRuleUri(_id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            }
            case WHO: {
                long _id = db.insert(WerewolfContract.WhoEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = WerewolfContract.WhoEntry.buildWhoUri(_id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = werewolfDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case CHARACTER:
                rowsUpdated = db.update(WerewolfContract.CharacterEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case RULES:
                rowsUpdated = db.update(WerewolfContract.RulesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case WHO:
                rowsUpdated = db.update(WerewolfContract.WhoEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unkown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = werewolfDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CHARACTER: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value: values) {
                        long _id = db.insert(WerewolfContract.CharacterEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }
            case RULES: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value: values) {
                        long _id = db.insert(WerewolfContract.RulesEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }
            case WHO: {
                int returnCount = 0;
                try {
                    for (ContentValues value: values) {
                        long _id = db.insert(WerewolfContract.WhoEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }

            default:
                return super.bulkInsert(uri, values);
        }
    }
}
