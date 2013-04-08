package gitmad.app.WhereUAt;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
	
	public final static String EXTRA_MESSAGE = "gitmad.app.WhereUAt";
	
    private SensorManager sensorManager;
    private Sensor accelerometer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // sensors to detect amount of force on phone
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(new ShakeEventSensorListener(this, 3), accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST);
//        sensorManager.registerListener(new DropEventSensorListener(this), accelerometer,
//                SensorManager.SENSOR_DELAY_FASTEST);
        
        IntentFilter filter = new IntentFilter();
        filter.addAction(ShakeEventSensorListener.SHAKE_DETECT);
        filter.addAction(DropEventSensorListener.DROP_DETECT);
        this.registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ShakeEventSensorListener.SHAKE_DETECT)) {
                    onShakeEvent();
                }
                if (intent.getAction().equals(DropEventSensorListener.DROP_DETECT)) {
                    onShakeEvent();
                }
                //otherwise do nothing
            }
        }, filter);
        
        View findMeButton = findViewById(R.id.find_me_button);
        findMeButton.setOnClickListener(this);
        
        //Instantiate the location list button
        Button locationListbutton = (Button) findViewById(R.id.location_list_button);
        locationListbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
        		Log.d("Demo", "Location list button pressed");
        		//launch intent to start the ListExampleActivity
            	Intent i = new Intent(MainActivity.this, ListExampleActivity.class); //private intent
            	MainActivity.this.startActivity(i);
            }
        });
        
        //Instantiate the gridViewButton
        Button gridViewButton = (Button) findViewById(R.id.image_gridview_button);
        gridViewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
        		Log.d("Demo", "GridView button pressed");		
        		//launch intent to start the GridViewActivity
        		Intent i = new Intent(MainActivity.this, GridViewActivity.class); //private intent
            	MainActivity.this.startActivity(i);
            }
        });
        
        Button recordMemoButton = (Button) findViewById(R.id.record_memo_button);
        recordMemoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Perform action on click
                Log.d("Demo", "Record Memo button pressed");       
                //launch intent to start the RecordMemoActivity
                Intent i = new Intent(MainActivity.this, RecordMemoActivity.class); //private intent
                MainActivity.this.startActivity(i);
            }
        });
    }
    
    protected void onShakeEvent() {
        //clear the text on a shake event
        EditText editText = (EditText)findViewById(R.id.editText1);
        editText.setText("");
    }

    @Override
	public void onClick(View v) {
		Intent i = new Intent(this, ResultActivity.class); //private intent
		EditText editText = (EditText)findViewById(R.id.editText1);
		String message = editText.getText().toString();
		i.putExtra(EXTRA_MESSAGE, message);
		startActivity(i);
		Log.d("Demo", "Find me button pressed");		
	}
}