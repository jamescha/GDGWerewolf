package com.gdg.jamescha.gdgwerewolf.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;


/**
 * Created by jamescha on 2/8/15.
 */
public class WerewolfProvider extends ContentProvider{

    private static final UriMatcher sUriMatcher= buildUriMatcher();

    private WerewolfDbHelper werewolfDbHelper;

    private static final int CHARACTER = 100;
    private static final int RULES = 200;
    private static final int WHO = 300;

    private static final int CHARACTER_ID = 101;
    private static final int RULES_ID = 201;
    private static final int WHO_ID = 301;

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

        return matcher;
    }

    private static final String sCharacterSelection = WerewolfContract.CharacterEntry.TABLE_NAME +
            "." + WerewolfContract.CharacterEntry.COLUMN_CHARACTER_NAME + " = ? ";

    private static final String sWHoSeletion = WerewolfContract.WhoEntry.TABLE_NAME +
            "." + WerewolfContract.WhoEntry.COLUMN_WHO_NAME + " = ? ";

    private Cursor getCharacters (Uri uri, String[] projection, String sortOrder) {
        return sQueryBuilder.query(werewolfDbHelper.getReadableDatabase(),
                projection,
                null,
                null,
                null,
                null,
                sortOrder);
    }

   // private getCharacters

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
                break;
            }

            case RULES_ID: {
                break;
            }

            default:
                throw new UnsupportedOperationException("Unkown uri: " + uri);
        }

        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
