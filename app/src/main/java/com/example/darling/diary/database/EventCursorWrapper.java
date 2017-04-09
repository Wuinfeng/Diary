package com.example.darling.diary.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.darling.diary.Event;
import com.example.darling.diary.database.EventDbSchema.EventTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Darling on 2017/4/9.
 */

public class EventCursorWrapper extends CursorWrapper {
    public EventCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Event getEvent(){
        String uuidString = getString(getColumnIndex(EventTable.Cols.UUID));
        String title = getString(getColumnIndex(EventTable.Cols.TITLE));
        long date = getLong(getColumnIndex(EventTable.Cols.DTAE));
        int isSolved = getInt(getColumnIndex(EventTable.Cols.SOLVED));

        Event event = new Event(UUID.fromString(uuidString));
        event.setTitle(title);
        event.setDate(new Date(date));
        event.setSolved(isSolved !=0);
        return event;
    }
}
