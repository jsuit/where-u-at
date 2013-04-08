package gitmad.app.WhereUAt;

import gitmad.app.WhereUAt.model.Place;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlaceAdapter extends ArrayAdapter<Place> {

	// declaring our ArrayList of items
	private ArrayList<Place> objects;
	int textViewResourceId;
	Context context;
	
	/* here we must override the constructor for ArrayAdapter
	* the only variable we care about now is ArrayList<Item> objects,
	* because it is the list of objects we want to display.
	*/
	public PlaceAdapter(Context context, int textViewResourceId, ArrayList<Place> objects) {
		super(context, textViewResourceId, objects);
		this.objects = objects;
		this.context = context;
		this.textViewResourceId = textViewResourceId;
	}

	/*
	 * we are overriding the getView method here - this is what defines how each
	 * list item will look.
	 */
	public View getView(int position, View convertView, ViewGroup parent){

		// assign the view we are converting to a local variable
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		// to inflate it basically means to render, or show, the view.
		if (v == null) {
			//LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			v = inflater.inflate(textViewResourceId, parent, false);
		}

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 * 
		 * Therefore, i refers to the current Item object.
		 */
		Place i = objects.get(position);

		if (i != null) {

			//Deal with stuff for the view here
			//text1 is a built in textview that is used for the simple entry for the list
			//using it just to save me some time
			//look into making custom array adapter stuff later
			TextView text1 = (TextView) v.findViewById(R.id.txtTitle);
			if(text1 != null)
			{
				text1.setText(i.getName());
			}
			else
			{
				Log.e("Place Adapter", "TextView text1 is NULL");
			}
			
		}

		// the view must be returned to our activity
		return v;

	}

}