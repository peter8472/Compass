package info.p445m.compass;

import android.hardware.Camera;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;


import static info.p445m.compass.Util.getCameraInstance;

public class HeadsUp extends AppCompatActivity {
    private Camera mCamera;
    private CameraPreview mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heads_up);

        mCamera = getCameraInstance();
        FrameLayout frame = (FrameLayout) findViewById(R.id.myFrame);
        if (mCamera != null) {

            mPreview = new CameraPreview(this, mCamera);

            frame.addView(mPreview);
        } else {
            TextView tmp = new android.support.v7.widget.AppCompatTextView(getApplicationContext());
            tmp.setText("no camera");
            frame.addView(tmp);

        }
    }

}