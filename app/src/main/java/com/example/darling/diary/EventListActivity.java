package com.example.darling.diary;

import android.support.v4.app.Fragment;

/**
 * Created by Darling on 2017/3/30.
 */

public class EventListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new EventListFragment();
    }
}
