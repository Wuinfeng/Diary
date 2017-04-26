package com.example.darling.diary;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.text.format.DateFormat;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;



/**
 * Created by Darling on 2017/3/24.
 */

public class EventFragment extends Fragment {
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_PHOTO = "DialogPhoto";
    private static final String ARG_EVENT_ID = "event_id";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 2;
    private static final int REQUEST_VIEW = 3;
    private Event mEvent;
    private File mPhotoFile;
    private EditText mTitleField;
    private Button mDataButton;
    private CheckBox mSolvedCheckBox;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private ImageButton mReportButton;
    private ViewTreeObserver mPhotoObserver;
    private Bitmap mBitmap;

    public static EventFragment newInstance(UUID eventId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT_ID,eventId);
        EventFragment fragment = new EventFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID eventId = (UUID)getArguments().getSerializable(ARG_EVENT_ID);
        mEvent = EventLab.get(getActivity()).getEvent(eventId);
        mPhotoFile = EventLab.get(getActivity()).getPhotoFile(mEvent);
    }

    @Override
    public void onPause(){
        super.onPause();
        EventLab.get(getActivity()).updateEvent(mEvent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle saveInstanceState){
        View v = inflater.inflate(R.layout.fragment_event,container,false);

        mTitleField = (EditText)v.findViewById(R.id.event_title);
        mTitleField.setText(mEvent.getTitle());
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
        updateDate();
        mDataButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mEvent.getDate());
                dialog.setTargetFragment(EventFragment.this,REQUEST_DATE);
                dialog.show(manager,DIALOG_DATE);
            }
        });

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.event_solved);
        mSolvedCheckBox.setChecked(mEvent.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set the event's solved property
                mEvent.setSolved(isChecked);
            }
        });


        PackageManager packageManager = getActivity().getPackageManager();

        mPhotoButton = (ImageButton) v.findViewById(R.id.event_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile !=null && captureImage.resolveActivity(packageManager)!=null;
        mPhotoButton.setEnabled(canTakePhoto);
        if(canTakePhoto){
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        }
        mPhotoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivityForResult(captureImage,REQUEST_PHOTO);
            }
        });
        mPhotoView = (ImageView) v.findViewById(R.id.event_photo);
        mPhotoView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(),"ksksk",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra("crop",true);
                intent.putExtra("return_data",true);
                startActivityForResult(intent,REQUEST_VIEW);
                //FragmentManager manager = getFragmentManager();
                //GlancePictureFragment Fragment = GlancePictureFragment.newInatance(mPhotoFile.toString());
                //Fragment.show(manager,DIALOG_PHOTO);
            }
        });
/*报错，调试不好 16.8挑战练习
        mPhotoObserver = mPhotoView.getViewTreeObserver();
        mPhotoObserver.addOnDrawListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout(){
                updatePhotoView();
                Log.i("EventFragment","onGlobalLayout:Observed");
            }
        });*/

        updatePhotoView(mBitmap);

        return v;
    }

    private void eventShare(){
        ShareCompat.IntentBuilder.from(getActivity())
                .setType("text/plain")
                .setSubject(getString(R.string.event_report_subject))
                .setText(getEventReport())
                .setChooserTitle(R.string.send_report)
                .startChooser();
    }


    private void updateDate() {
        //mDataButton.setText(mEvent.getDate().toString());
        mDataButton.setText(mEvent.getTime(mEvent.getDate()));
    }

    private String getEventReport(){
        String solvedString = null;
        if (mEvent.isSolved()){
            solvedString = getString(R.string.event_report_solved);
        }else{
            solvedString = getString(R.string.event_report_unsolved);
        }
        String dateFormat = "EEE,MMMdd日";
        String dateString = DateFormat.format(dateFormat,mEvent.getDate()).toString();

        String suspect = mEvent.getSuspect();
        if (suspect ==null){
            suspect = getString(R.string.event_report_no_suspect);
        }else{
            suspect = getString(R.string.event_report_suspect);
        }

        String report = getString(R.string.event_report,mEvent.getTitle(),dateString,solvedString,suspect);

        return report;
    }

    private void updatePhotoView(Bitmap bitmap){
        mPhotoView.setImageBitmap(bitmap);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode!= Activity.RESULT_OK){
            //return;
            System.out.println("resquestCode"+resultCode);
        }
        if(resultCode ==REQUEST_VIEW){
            Uri uri = data.getData();
            System.out.println(uri.getPath());
            ContentResolver cr = getActivity().getContentResolver();
            //try {
                //mBitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                mBitmap = PictureUtils.getScaledBitmap(uri.getPath(),getActivity());
                updatePhotoView(mBitmap);
            //catch (FileNotFoundException e) {
                //e.printStackTrace();
            //}
        }
        if(requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mEvent.setDate(date);
            updateDate();
        }else if(requestCode == REQUEST_PHOTO){
            if(mPhotoFile == null ||!mPhotoFile.exists()){
                mPhotoView.setImageDrawable(null);
            }else{
                mBitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(),getActivity());
            }
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        //menu.add("Menu 1a").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        inflater.inflate(R.menu.fragment_event,menu);
  }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_delete_event:
                EventLab.get(getActivity()).delEvent(mEvent);
                this.getActivity().finish();
                return true;
            case R.id.menu_item_share_event:
                eventShare();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
