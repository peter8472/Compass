package info.p445m.compass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class TiltActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager mSensorManager;
    Sensor acc;
    Sensor grav=null;
    Sensor linacc= null;
    float[] accray; // accelerometer values
    float []gravray;
    float[] I = null;

    boolean heads = false;
    AccView acc_output;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tilt);



        I = new float[9];
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        grav = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);


        accray = new float[3];
        gravray  = new float[3];
        // never works, always get null here
        acc_output = (AccView) findViewById(R.id.my_gravity);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_mag_field) {
            Intent intent = new Intent(this, MagFieldActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_headsup) {
            Intent intent = new Intent(this, HeadsUp.class);
            startActivity(intent);
        }*/
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
        StringBuilder outputBuilder = new StringBuilder(50);
        outputBuilder.append(String.format("x: %f, y: %f, z: %f\n",
                event.values[0],
                event.values[1],
                event.values[2]
        ));
        outputBuilder.append(String.format("total acceleration: %f\n",
                Math.sqrt(event.values[0] * event.values[0] + event.values[1] * event.values[1] +
                        event.values[2] * event.values[2])));
        if (event.sensor == acc)
            acc_output.setAcc(outputBuilder.toString());
        else if (event.sensor == grav)
            acc_output.setGrav(outputBuilder.toString());
        else if (event.sensor == linacc)
            acc_output.setLinAcc(outputBuilder.toString());
        else
            alert("sensory type not acc/grav/linacc?!");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);


    }

    @Override
    protected void onResume() {
        boolean result = true;
        super.onResume();

        result = mSensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);
        if (!result) {
            alert("accelerometer cannot registerListenr");
        }

    }

    protected void alert(String message) {
        TextView output = (TextView) findViewById(R.id.output);
        Snackbar.make(output, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
