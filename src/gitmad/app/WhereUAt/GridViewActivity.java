package gitmad.app.WhereUAt;


import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

//based off of turorial at http://www.compiletimeerror.com/2013/02/display-images-in-gridview.html

/*
 * Displays images in a gridview.
 * As an exercise, a good modification would be to add an onclick listener to gridview to navigate
 * to a larger version of the image that was selected.
 */
public class GridViewActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//set the content view
		setContentView(R.layout.imagegridview);
	}
}
