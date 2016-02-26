package kshitij.me.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kshitijsharma on 25/02/16.
 */
public class ReviewsArrayAdapter extends BaseAdapter {

    private ArrayList<String> reviews;
    private Context mContext;
    private LayoutInflater mInflater;
    private ViewHolder viewHolder;


    public static class ViewHolder {
        public TextView tvReviews;
        public TextView tvAuthorName;
    }

    ReviewsArrayAdapter(ArrayList<String> reviews,Context mContext) {
        this.reviews = reviews;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int i) {
        return reviews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {


        if (convertView == null) {

            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.reviews_layout, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvReviews = (TextView) convertView.findViewById(R.id.tvReviews);
            viewHolder.tvAuthorName =(TextView)convertView.findViewById(R.id.tvAuthorName);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvReviews.setText(reviews.get(i).split("-->")[0]);
        viewHolder.tvAuthorName.setText(reviews.get(i).split("-->")[1]);
        return convertView;
    }
}
