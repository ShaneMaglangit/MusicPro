package com.example.musicpro.data;

import android.provider.BaseColumns;

public class VenueContract {

    private VenueContract() {}

    /* Inner class that defines the table contents */
    public static class VenueEntry implements BaseColumns {
        public static final String TABLE_NAME = "venue_table";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_OPENING_TIME = "openingTime";
        public static final String COLUMN_NAME_UPDATED_AT = "updatedAt";
    }
}
