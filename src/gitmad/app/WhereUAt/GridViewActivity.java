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
public class GridViewActivity extends Activity implements ListAdapter
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//set the content view
		setContentView(R.layout.imagegridview);
		//instantiate the gridview
		GridView gridview = (GridView) findViewById(R.id.imageGrid);
		//set the adapter for the gridview
		gridview.setAdapter(new ImageAdapter(this));
	}
	
	//ImageAdapter class that will be used to populate the view.
	public class ImageAdapter extends BaseAdapter
	{
        private Context mContext;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return thumbnails.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return thumbnails[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
		
        public ImageAdapter(Context c) {
            mContext = c;
       } 

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
            ImageView imageView;
            if (convertView == null){  
               imageView = new ImageView(mContext);
               imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
               imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
               imageView.setPadding(8, 8, 8, 8);
            } 
            else{
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(thumbnails[position]);
            return imageView;
		}
		
		private Integer[] thumbnails = 
		{
			R.drawable.china0,
			R.drawable.china1,
			R.drawable.china2,
			R.drawable.china3,
			R.drawable.china4,
			R.drawable.china5,
			R.drawable.china0,
			R.drawable.china1,
			R.drawable.china2,
			R.drawable.china3,
			R.drawable.china4,
			R.drawable.china5,
		};
		
	
	}

	
	//Just for show, here are all the methods that we need to implement for the ListAdapter Interface
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
