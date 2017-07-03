package com.example.winaditi.retro;

import android.app.Activity;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main2Activity extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;
    int count ;
    View m_v;
    int k=0;
    byte[] data2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        count = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main2);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        /*// Add a listener to the Capture button
        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        Camera.Parameters parametro = mCamera.getParameters();
                        parametro.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                        mCamera.setParameters(parametro);
                        //mCamera.takePicture(null, null, mPicture);
                        mCamera.takePicture(null, null, mPicture);
                    }
                }
        );*/
    }

    public void onClick(View v) {
        Log.d("onclick", "entered "+count);
        m_v = v;
        if(count==1){
            //mCamera = getCameraInstance();
            // get an image from the camera
            //mPreview = new CameraPreview(this, mCamera);
            //FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            //preview.addView(mPreview);
            mCamera.startPreview();
            Camera.Parameters parametro = mCamera.getParameters();
            parametro.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parametro);
        }
        else{
            Camera.Parameters parametro = mCamera.getParameters();
            parametro.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            mCamera.setParameters(parametro);
        }
        //mCamera.takePicture(null, null, mPicture);
        mCamera.takePicture(null, null, mPicture);

    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        //@Override

        public void onPictureTaken(byte[] data1, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                Log.d( "ABCD", "Error creating media file, check storage permissions: " );
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);


                /*if(k<1){
                    fos.write(data1);
                  data2=data1;
                    k++;
                }
                else{
                    Bitmap bMap1 = BitmapFactory.decodeByteArray(data1, 0, data1.length);
                    Bitmap bMap2 = BitmapFactory.decodeByteArray(data2, 0, data2.length);
                    Bitmap mutableBitmap = bMap1.copy(bMap1.getConfig(), true);
                    for(int i=0;i<bMap1.getWidth();i++) {
                        for (int j = 0; j < bMap1.getHeight(); j++) {
                            int c=(bMap2.getPixel(i, j)+bMap1.getPixel(i, j))/2;
                            mutableBitmap.setPixel(i,j,c);

                        }

                    }

                    int quality=50;
                    //fos.write(mutableBitmap.getNinePatchChunk());
                }*/

                fos.close();

                if(count==0){
                    count++;
                    //releaseCamera();
                    onClick(m_v);
                    //finish();
                }
                else{
                    releaseCamera();
                    finish();
                }
            } catch (FileNotFoundException e) {
                Log.d("ABCD", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("ABCD", "Error accessing file: " + e.getMessage());
            }
        }

    };

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              // release the camera immediately on pause event
    }


    private void releaseCamera(){
        if (mCamera != null){
            mCamera.stopPreview();
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static  File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


}