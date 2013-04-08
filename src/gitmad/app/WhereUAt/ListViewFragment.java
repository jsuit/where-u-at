package gitmad.app.WhereUAt;

import gitmad.app.WhereUAt.db.DatabaseHandler;
import gitmad.app.WhereUAt.model.Place;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewFragment extends Fragment {

    ArrayList<String> values;
    ArrayList<Place> places;
    EditText listAddTextView;
    PlaceAdapter placeAdapter;
    ListView listView;
	DatabaseHandler dbHandler;
	int placeID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        setupFragment(view);
        this.dbHandler = new DatabaseHandler(this.getActivity());
        this.placeID = this.dbHandler.getCurrentID();
        placeID ++; //remove later, need to recreate database
        placeID++;
        return view;
    }

    private void setupFragment(View view) {
        listAddTextView = (EditText)view.findViewById(R.id.editText1);
        listAddTextView.setText("Type some text");
        listView = (ListView) view.findViewById(R.id.locationListView);

        places = new ArrayList<Place>();
        places.add(new Place(places.size()+1,"Detroit", 1.2243, 1.234));
        places.add(new Place(places.size()+1,"San Francisco", 1.2243, 1.234));

        values = new ArrayList<String>();
        values.add("Detroit");
        values.add("San Francisco");

        //adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, places);
        placeAdapter = new PlaceAdapter(getActivity(), R.layout.listview_item_row, places);
        listView.setAdapter(placeAdapter); //apply the adapter to the listview so that it can show the contents of the array

        //onClick Listener which displays the item that we clicked on
        listView.setOnItemClickListener(new OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(),
                  "Clicked on " + places.get(position).getName(), Toast.LENGTH_LONG).show();
              }
            });

        final Button listAddButton = (Button) view.findViewById(R.id.list_addButton);
        listAddButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String typed = listAddTextView.getText().toString();
                
                //only add a place if the user typed something
                if(typed.length() > 0)
                {
	                Place entered = new Place(placeID +1,typed, 1.2243, 1.234);
	                places.add(entered);
	                dbHandler.addplace(entered);
	                placeID++;
	                listAddTextView.setText("");
	                placeAdapter.notifyDataSetChanged();
	                dbHandler.logPlaces();
                }
            }
        });
    }
}
