package com.example.darling.diary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Darling on 2017/3/30.
 */

public class EventListFragment extends Fragment {

    private RecyclerView mEventRecylerView;
    private EventAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_event_list,container,false);

        mEventRecylerView = (RecyclerView) view.findViewById(R.id.event_recycler_view);
        mEventRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }
    private void updateUI(){
        EventLab eventLab = EventLab.get(getActivity());
        List<Event> events = eventLab.getEvents();

        mAdapter = new EventAdapter(events);
        mEventRecylerView.setAdapter(mAdapter);
    }

    private class EventHolder extends RecyclerView.ViewHolder{
        public TextView mTitleTextView;

        public EventHolder(View itemView){
            super(itemView);

            mTitleTextView = (TextView)itemView;
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
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            return new EventHolder(view);
        }
        @Override
        public void onBindViewHolder(EventHolder holder,int position){
            Event event = mEvents.get(position);
            holder.mTitleTextView.setText(event.getTitle());
        }
        @Override
        public int getItemCount(){
            return mEvents.size();
        }
    }


}
