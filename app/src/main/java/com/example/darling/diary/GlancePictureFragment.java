package com.example.darling.diary;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/4/14.
 * 用于图片预览
 */

public class GlancePictureFragment extends DialogFragment {
    private static final String ARG_PATH = "path";
    private ImageView mImage;

    public static GlancePictureFragment newInatance(String path){
        Bundle args = new Bundle();
        args.putString(ARG_PATH,path);
        GlancePictureFragment fragment = new GlancePictureFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInatanceState){
        
    }
}
