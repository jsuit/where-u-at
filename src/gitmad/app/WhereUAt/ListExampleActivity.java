package gitmad.app.WhereUAt;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

//Code based off tutorial found at:
//http://www.vogella.com/articles/AndroidListView/article.html#listview_listviewexample

public class ListExampleActivity extends Activity
{
	ArrayList<String> values;
	EditText listAddTextView;
	ArrayAdapter<String> adapter;
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listactivity_layout);

		listAddTextView = (EditText)findViewById(R.id.editText1);
		listAddTextView.setText("Type some text");
		listView = (ListView) findViewById(R.id.locationListView);

		values = new ArrayList<String>();
		values.add("Detroit");
		values.add("San Francisco");
		values.add("Seattle");
		values.add("New York");
		values.add("Miami");
		values.add("Chicago");
	    	
	    	//simple_list_item_1 is something that is included in android by default
	        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
	        listView.setAdapter(adapter); //apply the adapter to the listview so that it can show the contents of the array
	
	        //onClick Listener which displays the item that we clicked on
	        listView.setOnItemClickListener(new OnItemClickListener() {
	        	  @Override
	        	  public void onItemClick(AdapterView<?> parent, View view,
	        	    int position, long id) {
	        	    Toast.makeText(getApplicationContext(),
	        	      "Clicked on " + values.get(position), Toast.LENGTH_LONG).show();
	        	  }
	        	});

	        final Button listAddButton = (Button) findViewById(R.id.list_addButton);
	        listAddButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                // Perform action on click
	        		String typed = listAddTextView.getText().toString();
	        		adapter.add(typed);
	        		listAddTextView.setText("");
	        		adapter.notifyDataSetChanged();
	            }
	        });
	}


}