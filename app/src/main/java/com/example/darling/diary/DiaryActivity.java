package com.example.darling.diary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class DiaryActivity extends SingleFragmentActivity {

   @Override
   protected Fragment createFragment(){
       return new EventFragment();
   }
}
