package com.example.darling.diary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Darling on 2017/4/5.
 */

public class EventPagerActivity extends AppCompatActivity {
    private static final String EXTRA_EVENT_ID = "com.example.darling.Diary.event_id";
    private ViewPager mViewPager;
    private List<Event> mEvents;

    public static Intent newIntent(Context packageContext,UUID eventId){
        Intent intent = new Intent(packageContext,EventPagerActivity.class);
        intent.putExtra(EXTRA_EVENT_ID,eventId);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pager);
        UUID eventId = (UUID) getIntent().getSerializableExtra(EXTRA_EVENT_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_event_pager_view_pager);
        mEvents = EventLab.get(this).getEvents();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Event event = mEvents.get(position);
                return EventFragment.newInstance(event.getId());
            }

            @Override
            public int getCount() {
                return mEvents.size();
            }

        });
        for (int i=0;i<mEvents.size();i++){
            if (mEvents.get(i).getId().equals(eventId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
