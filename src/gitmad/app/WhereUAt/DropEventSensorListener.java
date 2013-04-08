package gitmad.app.WhereUAt;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

/**
 * An event listener to detect a single shake of the phone.
 * 
 * @author Jesse Rosalia
 *
 */
public class DropEventSensorListener implements SensorEventListener {
    
    public static final String DROP_DETECT = DropEventSensorListener.class.getName() + ".DropDetect";

    private ThreeAxisValue gravity             = new ThreeAxisValue();
    private ThreeAxisValue linear_acceleration = new ThreeAxisValue();
    private ThreeAxisValue last;
    private boolean peakFound = false;
    private Context context;

    public DropEventSensorListener(Context context) {
        this.context = context;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        ThreeAxisValue evals = new ThreeAxisValue(event.values);
        final float alpha = 0.1f;
        // really just copypasta code
        last = linear_acceleration.clone();

        // Isolate the force of gravity with the low-pass filter.
        gravity = gravity.scale(alpha).add(evals.scale(1 - alpha));

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration = evals.subtract(gravity);

        // Convert meters per second squared to centimeters per second squared
        linear_acceleration = linear_acceleration.scale(100);

        // Check if we've reached the peak... or, by peak, I mean if we've
        // gone from an increasing amount of force to a decreasing amount of
        // force.
        if ((Math.abs(last.z) > Math.abs(linear_acceleration.z))
                && !(Math.abs(last.z) <= 5) && !peakFound) {

            if (Math.abs(last.z) > 90) {
                broadcastShake();
                Log.d("Last x: ", "" + last.x);
                Log.d("Last y: ", "" + last.y);
                Log.d("Last z: ", "" + last.z);
                Log.d("Current x: ", "" + linear_acceleration.x + " cm/s^2");
                Log.d("Current y: ", "" + linear_acceleration.y + " cm/s^2");
                Log.d("Current z: ", "" + linear_acceleration.z + " cm/s^2");
            }
            peakFound = true;

        } else if (Math.abs(linear_acceleration.z) <= 5 && peakFound) {
            //we've settled...look for more peaks
            peakFound = false;
        }
    }

    private void broadcastShake() {
        Intent intent = new Intent();
        intent.setAction(DropEventSensorListener.DROP_DETECT);
        context.sendBroadcast(intent);
    }
}
