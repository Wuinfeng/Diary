package com.example.darling.diary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.darling.database.EventBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Darling on 2017/3/25.
 */

public class EventLab {
    private static EventLab sEventLab;
    private List<Event> mEvents;
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
        mEvents = new ArrayList<>();

    }
    public void addEvent(Event event){
        mEvents.add(event);
    }

    public List<Event> getEvents(){
        return mEvents;
    }

    public Event getEvent(UUID id){
        for (Event event : mEvents){
            if(event.getId().equals(id)){
                return event;
            }
        }
        return  null;
    }
}
