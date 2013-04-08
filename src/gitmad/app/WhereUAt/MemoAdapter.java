package gitmad.app.WhereUAt;

import gitmad.app.WhereUAt.model.Memo;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MemoAdapter extends BaseAdapter {
    
    private Context context;
    private List<Memo> memos;
    private int selectedPosition = -1;

    public MemoAdapter(Context context, List<Memo> memos) {
        this.context = context;
        this.memos = memos;
    }
    
    public void addMemo(Memo memo) {
        this.memos.add(memo);
        notifyDataSetChanged();
    }

    public void removeMemo(Memo memo) {
        this.memos.remove(memo);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.memos.size();
    }

    @Override
    public Memo getItem(int position) {
        return this.memos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.memos.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = setupView(convertView);
        Memo memo = this.memos.get(position);

        TextView tv = (TextView) v.findViewById(android.R.id.text1);
        tv.setText(memo.getName());
        tv = (TextView) v.findViewById(android.R.id.text2);
        tv.setText(memo.getLocation());
        
        if (position == this.selectedPosition) {
            v.setBackgroundResource(android.R.color.holo_blue_dark);
        } else {
            v.setBackgroundResource(android.R.color.transparent);
        }
        return v;
    }

    public void clearSelection() {
        this.selectedPosition = -1;
        notifyDataSetChanged();
    }

    public Memo select(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
        return getItem(position);
    }

    private View setupView(View convertView) {
        View v;
        final LayoutInflater li = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            v = li.inflate(android.R.layout.simple_list_item_2, null);
        } else {
            v = convertView;
        }
        return v;
    }
}
