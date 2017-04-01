package com.example.darling.diary;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import java.util.UUID;

public class DiaryActivity extends SingleFragmentActivity {
    private static final String EXTRA_EVENT_ID = "com.example.darling.diary.event_id";
    public static Intent newIntent(Context packageContext, UUID eventId){
        Intent intent = new Intent(packageContext,DiaryActivity.class);
        intent.putExtra(EXTRA_EVENT_ID,eventId);
        return intent;
    }
   @Override
   protected Fragment createFragment(){
       UUID eventId = (UUID)getIntent().getSerializableExtra(EXTRA_EVENT_ID);
       return EventFragment.newInstance(eventId);
   }
}
