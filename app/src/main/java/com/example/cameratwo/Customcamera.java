package com.example.cameratwo;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Customcamera extends AppCompatActivity implements SurfaceHolder.Callback {

   private Camera mCamera;
   private SurfaceView mPreview;
   private  SurfaceHolder mHolder;
   private int cameraId=1;
   private int mHeight;

   private Camera.PictureCallback mpictureCallback=new Camera.PictureCallback() {
       @Override
       public void onPictureTaken(byte[] data, Camera camera) {
           File tempfile=new File("/sdcard/emp.png");
           try {
               FileOutputStream fos=new FileOutputStream(tempfile);
               fos.write(data);
               fos.close();
               Intent intent=new Intent(Customcamera.this,ResultActivity.class);
               intent.putExtra("picpath",tempfile.getAbsolutePath());
               startActivity(intent);
               Customcamera.this.finish();
           }  catch (IOException e) {
               e.printStackTrace();
           }
       }
   };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom);
        mPreview=findViewById(R.id.preview);
        mPreview.setZOrderOnTop(false);
        mHolder=mPreview.getHolder();
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        mHolder.addCallback(this);
        mPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.autoFocus(null);
            }
        });
        final ImageButton imageButton=findViewById(R.id.button3);
        TranslucencyView translucencyView=findViewById(R.id.transView);
        imageButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageButton imageButton=findViewById(R.id.button3);
                        mHeight=getSupportActionBar().getHeight();
                        int left=imageButton.getLeft();
                        int right=imageButton.getRight();
                        int top=imageButton.getTop();
                        int bottom=imageButton.getBottom();
                        int mCoodinate[]={left,top,right,bottom};
                        TranslucencyView translucencyView=findViewById(R.id.transView);
                        translucencyView.setCircleLocation(mCoodinate);
                    }
                });

            }
        },1200);
        FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) translucencyView.getLayoutParams();
        translucencyView.setLayoutParams(layoutParams);
        translucencyView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mCamera.autoFocus(null);
                return false;
            }
        });
        mHolder.lockCanvas();
    }
    public void capture(View view){
        Camera.Parameters parameters=mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setPreviewSize(800,400);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.autoFocus(new Camera.AutoFocusCallback(){
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if(success){mCamera.takePicture(null,null, mpictureCallback);}
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera==null){
            mCamera=getCamera();
            if(mHolder!=null){
                setStartPreview(mCamera,mHolder);}
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera(); }

    private Camera getCamera(){
        Camera camera;
        try{
            camera=Camera.open(cameraId);
        }
        catch (Exception e){
            camera=null;
            e.printStackTrace(); }
        return camera;
    }

private void setStartPreview(Camera camera,SurfaceHolder holder){
    try {
        camera.setPreviewDisplay(holder);
        camera.setDisplayOrientation(90);
        camera.startPreview();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void releaseCamera(){
        if (mCamera!=null){
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera=null;
        }
}


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        setStartPreview(mCamera,mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        setStartPreview(mCamera,mHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    @Override
    public  void  onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
    }
}
