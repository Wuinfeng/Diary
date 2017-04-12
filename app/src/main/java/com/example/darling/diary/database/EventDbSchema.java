package com.example.darling.diary.database;

/**
 * Created by Darling on 2017/4/8.
 */

public class EventDbSchema {
    public static final class EventTable{
        public static final String NAME = "events";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DTAE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
        }
    }
}
