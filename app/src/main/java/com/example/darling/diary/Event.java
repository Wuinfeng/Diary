package com.example.darling.diary;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Darling on 2017/3/21.
 */

public class Event {
    private String mTitle;
    private UUID mId;
    private Date mDate;
    private boolean mSolved;

    public Event(){
        //Generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
