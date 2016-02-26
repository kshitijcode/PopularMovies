package kshitij.me.popularmovies;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kshitijsharma on 25/02/16.
 */
public class YoutubeURLAdapter extends BaseAdapter {


    private ArrayList<String> trailerUrl;
    private Context mContext;
    private LayoutInflater mInflater;


    public static class ViewHolder {
        public TextView tvURL;
    }

    YoutubeURLAdapter(ArrayList<String> trailerUrl, Context mContext) {

        this.trailerUrl = trailerUrl;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return trailerUrl.size();
    }

    @Override
    public Object getItem(int i) {
        return trailerUrl.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (convertView == null) {

            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.trailers_layout, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvURL = (TextView) convertView.findViewById(R.id.tvURL);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.tvURL.setClickable(true);
        viewHolder.tvURL.setMovementMethod(LinkMovementMethod.getInstance());
        String tvURL = "<a href='%s'> Trailer %s</a>";
        viewHolder.tvURL.setText(Html.fromHtml(String.format(tvURL, trailerUrl.get(i), String.valueOf(i+1))));
        return convertView;
    }
}
