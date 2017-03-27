package com.example.darling.diary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * Created by Darling on 2017/3/24.
 */

public class EventFragment extends Fragment {
    private Event mEvent;
    private EditText mTitleField;
    private Button mDataButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mEvent =new Event();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle saveInstanceState){
        View v = inflater.inflate(R.layout.fragment_event,container,false);

        mTitleField = (EditText)v.findViewById(R.id.event_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                mEvent.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDataButton = (Button) v.findViewById(R.id.event_date);
        mDataButton.setText(mEvent.getDate().toString());
        mDataButton.setEnabled(false);

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.event_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set the event's solved property
                mEvent.setSolved(isChecked);
            }
        });
        return v;
    }
}
