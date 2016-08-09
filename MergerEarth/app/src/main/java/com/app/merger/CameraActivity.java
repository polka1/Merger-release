package com.app.merger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class CameraActivity extends AppCompatActivity {
    Button mButton;
    ImageView mImage;
    Uri mUri;
    Context mContext;

    private static final int PHOTO_INTENT_REQUEST_CODE = 100;
    private Uri generateFileUri() {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return null;
        File path = new File (Environment.getExternalStorageDirectory(), "Merger");
        if (! path.exists()){
            if (! path.mkdirs()){
                return null;
            }
        }
        String timeStamp = String.valueOf(System.currentTimeMillis());
        File newFile = new File(path.getPath() + File.separator + timeStamp + ".jpg");
        return Uri.fromFile(newFile);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mContext = this;

        mButton = (Button) findViewById(R.id.button);
        mImage = (ImageView) findViewById(R.id.image);

        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mUri = generateFileUri();
                if (mUri == null) {
                    Toast.makeText(mContext, "SD card not available", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                startActivityForResult(intent, PHOTO_INTENT_REQUEST_CODE);
            }
        });
    }
}
