package com.example.darling.diary;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import static com.example.darling.diary.R.layout.dialog_date;

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
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //使用getArgument()方法取出照片文件路径
        String path = getArguments().getString(ARG_PATH);

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_image_glance);

        mImage = (ImageView) dialog.findViewById(R.id.glance_image);
        mImage.setImageBitmap(PictureUtils.getScaledBitmap(path,getActivity()));
        mImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.dismiss();//点击图片退出
            }
        });
        return dialog;
    }
}
