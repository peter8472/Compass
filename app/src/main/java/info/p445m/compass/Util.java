package info.p445m.compass;

import android.hardware.Camera;
import android.hardware.SensorManager;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * copied from Compass-old on 2017-6-5
 * Created by Administrator on 2/9/2016.
 */
public class Util {
    public static String accuracy(int accuracy) {

        if (accuracy == SensorManager.SENSOR_STATUS_ACCURACY_HIGH)
            return ("SENSOR_STATUS_ACCURACY_HIGH");
        else if (accuracy == SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM)
            return ("SENSOR_STATUS_ACCURACY_MEDIUM");
        else if (accuracy == SensorManager.SENSOR_STATUS_ACCURACY_LOW)
            return ("SENSOR_STATUS_ACCURACY_LOW");
        else
            return (String.format("accuracy: %d", accuracy));
    }
    public static Camera getCameraInstance() {
        Camera c =  null;
        try {
            c = Camera.open();

        } catch (Exception e) {
            Log.w(TAG, "cannot camera: "+ e.getMessage() );
        }
        return c;
    }
}