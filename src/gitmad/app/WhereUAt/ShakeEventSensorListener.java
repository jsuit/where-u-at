package gitmad.app.WhereUAt;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class ShakeEventSensorListener implements SensorEventListener {

    public static final String SHAKE_DETECT = ShakeEventSensorListener.class.getName() + ".ShakeDetect";

    private long lastTime = 0;
    private Context context;

    private int numShakesThreshold;
    private int numShakes = 0;

    private Thread gestureTimeoutThread;
    private boolean resetThread;

    public ShakeEventSensorListener(Context context, int numShakesThreshold) {
        this.context = context;
        this.numShakesThreshold = numShakesThreshold;

    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        
        //break up into coordinates
        float x = values[0];
        float y = values[1];
        float z = values[2];
        
        float accelerationSquareRoot = (x*x + y*y + z*z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        
        long timestamp = System.currentTimeMillis();
        
        if(accelerationSquareRoot >= 3) {
            if(timestamp - lastTime < 200)
                return;
            
            lastTime = timestamp;
            Log.d("Demo", "Device shuffled");
            if (this.numShakes == 0) {
                //the start of a gesture...start a timeout just in case the user didn't mean to shake the phone
                startGestureTimeout();
            }

            //count the number of shakes, and broadcast the shake if it reaches the specified number passed in
            // at construction
            if (++this.numShakes >= numShakesThreshold) {
                broadcastShake();
                this.numShakes = 0;
            }
        }
    }

    /**
     * Start the gesture timeout.  This is to reset the shake counter after some period of inactivity.
     * 
     */
    private void startGestureTimeout() {
        if (this.gestureTimeoutThread != null && this.gestureTimeoutThread.isAlive()) {
            this.resetThread = true;
        } else {
            this.resetThread = false;
            this.gestureTimeoutThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    for (int ii = 0; ii < 10; ii++) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
                        if (resetThread) {
                            ii = 0;
                            resetThread = false;
                        }
                    }
                 
                    Log.d("Demo", "Resetting shuffle count");

                    numShakes = 0;
                }
            });
            gestureTimeoutThread.start();
        }
    }

    private void broadcastShake() {
        Intent intent = new Intent();
        intent.setAction(DropEventSensorListener.DROP_DETECT);
        context.sendBroadcast(intent);
    }
}
