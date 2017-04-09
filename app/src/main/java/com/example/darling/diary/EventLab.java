package com.example.darling.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.darling.diary.database.EventBaseHelper;
import com.example.darling.diary.database.EventCursorWrapper;
import com.example.darling.diary.database.EventDbSchema.EventTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Darling on 2017/3/25.
 */

public class EventLab {
    private static EventLab sEventLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static EventLab get(Context context){
        if (sEventLab == null){
            sEventLab = new EventLab(context);
        }
        return  sEventLab;
    }

    private EventLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new EventBaseHelper(mContext).getWritableDatabase();


    }
    public void addEvent(Event event){
        ContentValues values = getContentValues(event);
        mDatabase.insert(EventTable.NAME,null,values);
    }

    public List<Event> getEvents(){
        List<Event> events = new ArrayList<>();
        EventCursorWrapper cursor = queryEvents(null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                events.add(cursor.getEvent());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return events;
    }

    public Event getEvent(UUID id){
        EventCursorWrapper cursor = queryEvents(EventTable.Cols.UUID + " = ?",new String[]{id.toString()});
        try{
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getEvent();
        }finally {
            cursor.close();
        }
    }

    public void updateEvent(Event event){
        String uuidString = event.getId().toString();
        ContentValues values = getContentValues(event);

        mDatabase.update(EventTable.NAME,values,EventTable.Cols.UUID+" = ?",new String[]{uuidString});
    }
    private static ContentValues getContentValues(Event event){
        ContentValues values = new ContentValues();
        values.put(EventTable.Cols.UUID,event.getId().toString());
        values.put(EventTable.Cols.TITLE,event.getTitle());
        values.put(EventTable.Cols.DTAE,event.getDate().getTime());
        values.put(EventTable.Cols.SOLVED,event.isSolved()?1:0);
        return values;
    }

    private EventCursorWrapper queryEvents(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                EventTable.NAME,
                null,//Columns - null select all columns
                whereClause,
                whereArgs,
                null,//groupBy
                null,//having
                null //orderBY
        );
        return new EventCursorWrapper(cursor);
    }

}
