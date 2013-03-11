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
public class ShakeEventSensorListener implements SensorEventListener {
    
    public static final String SHAKE_DETECT = ShakeEventSensorListener.class.getName() + ".ShakeDetect";

    private ThreeAxisValue gravity             = new ThreeAxisValue();
    private ThreeAxisValue linear_acceleration = new ThreeAxisValue();
    private ThreeAxisValue last;
    private boolean peakFound = false;
    private boolean chill = false; // used to stop sounds when looking at
    private Context context;

    private int numShakes;
                                   // instructions

    public ShakeEventSensorListener(Context context, int numShakes) {
        this.context = context;
        this.numShakes = numShakes;
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
//        lastx = linear_acceleration[0];
//        lasty = linear_acceleration[1];
//        lastz = linear_acceleration[2];

        // Isolate the force of gravity with the low-pass filter.
        gravity = gravity.scale(alpha).add(evals.scale(1 - alpha));
//        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
//        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
//        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration = evals.subtract(gravity);
//        linear_acceleration[0] = event.values[0] - gravity[0];
//        linear_acceleration[1] = event.values[1] - gravity[1];
//        linear_acceleration[2] = event.values[2] - gravity[2];

        // Convert meters per second squared to centimeters per second squared
        linear_acceleration = linear_acceleration.scale(100);
//        linear_acceleration[0] *= 100;
//        linear_acceleration[1] *= 100;
//        linear_acceleration[2] *= 100;

        // Check if we've reached the peak... or, by peak, I mean if we've
        // gone from an increasing amount of force to a decreasing amount of
        // force. Too lazy to change at night. However, need to set it so
        // that I wait until the "slap" is finished being in motion before
        // playing the sound
        if ((Math.abs(last.z) > Math.abs(linear_acceleration.z))
                && !(Math.abs(last.z) <= 5) && !peakFound) {

            if (Math.abs(last.z) > 90) {
                broadcastShake();
            }
//            Log.d("Last x: ", "" + last.x);
//            Log.d("Last y: ", "" + last.y);
//            Log.d("Last z: ", "" + last.z);
//            Log.d("Current x: ", "" + linear_acceleration.x + " cm/s^2");
//            Log.d("Current y: ", "" + linear_acceleration.y + " cm/s^2");
//            Log.d("Current z: ", "" + linear_acceleration.z + " cm/s^2");
//            peakFound = true;

//        } else if (Math.abs(linear_acceleration.z) <= 5 && peakFound) {
//
//            peakFound = false;
        }

        // Log.d("Count", ""+count);
    }

    private void broadcastShake() {
        Intent intent = new Intent();
        intent.setAction(ShakeEventSensorListener.SHAKE_DETECT);
        context.sendBroadcast(intent);
    }
}
