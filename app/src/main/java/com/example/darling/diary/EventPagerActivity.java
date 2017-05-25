package com.example.darling.diary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

/**
 * Created by Darling on 2017/4/5.
 */

public class EventPagerActivity extends AppCompatActivity {
    private static final String EXTRA_EVENT_ID = "com.example.darling.Diary.event_id";
    private ViewPager mViewPager;
    private List<Event> mEvents;
    private DrawerLayout mDrawerLayout = null;

    public static Intent newIntent(Context packageContext,UUID eventId){
        Intent intent = new Intent(packageContext,EventPagerActivity.class);
        intent.putExtra(EXTRA_EVENT_ID,eventId);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pager);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // 按钮按下，将抽屉打开
                mDrawerLayout.openDrawer(Gravity.LEFT);

            }
        });

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
