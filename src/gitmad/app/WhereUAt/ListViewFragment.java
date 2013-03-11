package gitmad.app.WhereUAt;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListViewFragment extends Fragment {

    //Code based off tutorial found at:
    //http://www.vogella.com/articles/AndroidListView/article.html#listview_listviewexample

    ArrayList<String> values;
    EditText listAddTextView;
    ArrayAdapter<String> adapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        
        setupFragment(view);

        return view;
    }

    private void setupFragment(View view) {
        listAddTextView = (EditText)view.findViewById(R.id.editText1);
        listAddTextView.setText("Type some text");
        listView = (ListView) view.findViewById(R.id.locationListView);

        values = new ArrayList<String>();
        values.add("Detroit");
        values.add("San Francisco");
        values.add("Seattle");
        values.add("New York");
        values.add("Miami");
        values.add("Chicago");
            
        //simple_list_item_1 is something that is included in android by default
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter); //apply the adapter to the listview so that it can show the contents of the array

        //onClick Listener which displays the item that we clicked on
        listView.setOnItemClickListener(new OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(),
                  "Clicked on " + values.get(position), Toast.LENGTH_LONG).show();
              }
            });

        final Button listAddButton = (Button) view.findViewById(R.id.list_addButton);
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
