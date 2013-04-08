package gitmad.app.WhereUAt;

import android.app.Fragment;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        setupFragment(view);
        return view;
    }
    
    private void setupFragment(View view) {
        //instantiate the gridview
        GridView gridview = (GridView) view.findViewById(R.id.imageGrid);
        //set the adapter for the gridview
        gridview.setAdapter(new ImageAdapter(this.getActivity()));
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
}
