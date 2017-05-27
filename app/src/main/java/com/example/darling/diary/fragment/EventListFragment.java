package com.example.darling.diary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.darling.diary.Event;
import com.example.darling.diary.EventLab;
import com.example.darling.diary.R;
import com.example.darling.diary.activity.EventPagerActivity;

import java.util.List;

/**
 * Created by Darling on 2017/3/30.
 */

public class EventListFragment extends Fragment {
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private RecyclerView mEventRecylerView;
    private EventAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_event_list,container,false);

        mEventRecylerView = (RecyclerView) view.findViewById(R.id.event_recycler_view);
        mEventRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE,mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu , MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_event_list,menu);
/*
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);}
        else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }*/
    }
/*
    //这部分功能已被EventListActivity接管
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_new_event:
                Event event = new Event();
                EventLab.get(getActivity()).addEvent(event);
                Intent intent = EventPagerActivity.newIntent(getActivity(),event.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/

    private void updateSubtitle(){
        EventLab eventLab = EventLab.get(getActivity());
        int eventCount = eventLab.getEvents().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_format,eventCount,eventCount);

        if(!mSubtitleVisible){
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI(){
        EventLab eventLab = EventLab.get(getActivity());
        List<Event> events = eventLab.getEvents();
        if(mAdapter == null) {
            mAdapter = new EventAdapter(events);
            mEventRecylerView.setAdapter(mAdapter);
        }else{
            mAdapter.setEvents(events);
            mAdapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Event mEvent;


        public EventHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_event_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_event_solved_check_box);
        }
        public void bindEvent(Event event){
            mEvent = event;
            mTitleTextView.setText(mEvent.getTitle());
            mDateTextView.setText(mEvent.getTime(mEvent.getDate()));
            mSolvedCheckBox.setChecked(mEvent.isSolved());
        }
        @Override
        public void onClick(View v){
            Intent intent = EventPagerActivity.newIntent(getActivity(),mEvent.getId());
            startActivity(intent);
        }

    }
    private class EventAdapter extends RecyclerView.Adapter<EventHolder>{
        private List<Event> mEvents;

        public EventAdapter(List<Event> events){
            mEvents = events;
        }
        @Override
        public  EventHolder onCreateViewHolder(ViewGroup parent,int viewtype){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_event,parent,false);
            return new EventHolder(view);
        }
        @Override
        public void onBindViewHolder(EventHolder holder,int position){
            Event event = mEvents.get(position);
            holder.bindEvent(event);
        }
        @Override
        public int getItemCount(){
            return mEvents.size();
        }

        public void setEvents(List<Event> events){
            mEvents = events;
        }
    }


}
