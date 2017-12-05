package com.luoxiang.glproject;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.luoxiang.glproject.surfaces.TranslationSurfaceView;

public class MainActivity
        extends AppCompatActivity
{
    //平移用surfaceview
    private TranslationSurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        mSurfaceView = new TranslationSurfaceView(this);


        mSurfaceView.requestFocus();
        mSurfaceView.setFocusableInTouchMode(true);
        setContentView(mSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSurfaceView.onPause();
    }
}
