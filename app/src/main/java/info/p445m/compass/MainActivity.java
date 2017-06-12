package info.p445m.compass;

import android.content.Context;
import android.content.Intent;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    SensorManager mSensorManager;
    Sensor mag;
    Sensor acc;
    float []magray; // magnetic sensor values
    float []accray; // accelerometer values
    float []Rotmax=null;
    float []rotation_remap= new float[9];
    float []I = null;
    float [] orientation = null;
    StringBuilder outstring;

    boolean heads=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outstring =  new StringBuilder(100);
        Rotmax = new float[9];
        I = new float[9];
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.output);
                heads=!heads;
                try {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }catch (NullPointerException e) {
                    output.setText("");
                    StackTraceElement []errs = e.getStackTrace();
                    for (int k =0; k < 5; k++) {
                        output.append(
                                String.format(Locale.US,"%s %s %s:%d\n",
                                        errs[k].getClassName(),
                                        errs[k].getFileName(),
                                        errs[k].getMethodName(),
                                        errs[k].getLineNumber()
                                ));
                    }
                }
            }
        });
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mag = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        acc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magray = new float[3];
        accray = new float[3];
        orientation = new float[3];

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void showDir() {
        TextView output = (TextView) findViewById(R.id.output);
        outstring.setLength(0);

        SensorManager.getRotationMatrix(Rotmax, I, accray, magray);
        float azrad;
        float asdeg;

        if (Rotmax == null)outstring.append("rotmaxNULL \n");
        orientation = new float[3];
        SensorManager.remapCoordinateSystem(Rotmax,
                SensorManager.AXIS_X, SensorManager.AXIS_Z, rotation_remap);
        if (heads)
            SensorManager.getOrientation(rotation_remap, orientation);
        else
            SensorManager.getOrientation(Rotmax, orientation);

        String tmpstring = String.format(Locale.US ,"mag: %f,%f,%f\n", magray[0], magray[1], magray[2]);
        outstring.append(tmpstring);


        tmpstring = String.format(Locale.US, "orientation %f, %f, %f\n",
                Math.toDegrees(orientation[0]),
                Math.toDegrees(orientation[1]),
                Math.toDegrees(orientation[2])
        );
        outstring.append(tmpstring);
        azrad = orientation[0];
        GeomagneticField geo = new GeomagneticField((float)38.55, (float)-121.75,(float) 17.0,new Date().getTime());

        if (azrad < 0)
            azrad = (float)(Math.PI + Math.PI) +azrad;
        asdeg= (float)Math.toDegrees(azrad);


        outstring.append(String.format("uncorrected: %f\n", asdeg));
        asdeg += geo.getDeclination();
        if (asdeg <0)
            asdeg +=360;
        if (asdeg >=360)
            asdeg -= 360;

        tmpstring = String.format(Locale.US,"corrected %f\n", asdeg);
        outstring.append(tmpstring);
        output.setText(outstring);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


//        if (id == R.id.action_mag_field) {
//            Intent intent = new Intent(this, MagFieldActivity.class);
//            startActivity(intent);
//        }
        if (id == R.id.action_headsup) {
            Intent intent = new Intent(this, HeadsUp.class);
            startActivity(intent);
        }
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_needle) {
            Intent intent = new Intent(this, NeedleActivity.class);
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor == mag) {
            System.arraycopy(event.values,0,magray,0,event.values.length);
            showDir();
        } else if (event.sensor == acc) {
            System.arraycopy(event.values, 0, accray, 0, event.values.length);
        } else {
            alert("sensory type not mag or acc?!");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        TextView aview = (TextView) findViewById(R.id.accuracy);
        if (sensor == mag) {
            aview.append("mag: " + Util.accuracy(accuracy)+ "\n");

        } else if (sensor == acc) {
            aview.append("acc: " + Util.accuracy(accuracy)+ "\n");
        } else {
            alert("sensory type not mag or acc?!");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);


    }

    @Override
    protected void onResume() {
        boolean result;
        super.onResume();
        result = mSensorManager.registerListener(this, mag, SensorManager.SENSOR_DELAY_NORMAL);
        if (!result) {
            alert("magnetic sensor cannot registerListener");
        }
        result = mSensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);
        if (!result) {
            alert("accelerometer cannot registerListener");
        }

    }
    protected void alert(String message ) {
        TextView output = (TextView) findViewById(R.id.output);
        Snackbar.make(output, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
