package com.gdg.jamescha.gdgwerewolf.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jamescha on 2/7/15.
 */
public class WerewolfContract {
    public static final String CONTENT_AUTHORITY = "com.gdg.jamescha.gdgwerewolf";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CHARACTER = "character";
    public static final String PATH_RULES = "rules";
    public static final String PATH_WHO = "who";

    public static final String DATE_FORMAT = "yyyyMMdd";

    public static Date getDateFromDb(String dataText) {
        SimpleDateFormat dbDataFormat = new SimpleDateFormat(DATE_FORMAT);

        try {
            return dbDataFormat.parse(dataText);
        } catch (ParseException exp) {
            exp.printStackTrace();
            return null;
        }
    }

    public static final class CharacterEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                                                              .appendPath(PATH_CHARACTER)
                                                              .build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_CHARACTER;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_CHARACTER;

        public static final String TABLE_NAME = PATH_CHARACTER;

        public static final String COLUMN_CHARACTER_NAME = "character_name";
        public static final String COLUMN_CHARACTER_LOGO_IMG = "character_logo_image";
        public static final String COLUMN_CHARACTER_IMG = "character_image";

        public static Uri buildCharacterUri (long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildCharacterUriWithName (String name) {
            return CONTENT_URI.buildUpon().appendPath(name).build();
        }

        public static String getCharacterNameFromUri (Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class RulesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_RULES)
                .build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_RULES;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_RULES;

        public static final String TABLE_NAME = PATH_RULES;

        public static final String COLUMN_RULE_NAME = "rule_name";
        public static final String COLUMN_RULE_DESCRIPTION = "rule_description";

        public static Uri buildRuleUri (long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class WhoEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WHO)
                .build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_WHO;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_WHO;

        public static final String TABLE_NAME = PATH_WHO;

        public static final String COLUMN_WHO_NAME = "who_name";
        public static final String COLUMN_WHO_REGION = "who_region";
        public static final String COLUMN_WHO_CHAPTER = "who_chapter";
        public static final String COLUMN_WHO_IMG = "who_image";
        public static final String COLUMN_WHO_CHARACTER = "who_character";
        public static final String COLUMN_WHO_IS_DEAD = "who_is_dead";

        public static Uri buildWhoUri (long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
