package kshitij.me.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by kshitijsharma on 28/11/15.
 */
public class MoviePosterAdapter extends BaseAdapter{

    ArrayList<MoviesInfo> arrayListMoviesInfo;
    Context mContext;
    LayoutInflater mInflater;
    ViewHolder viewHolder;
    MoviesInfo moviesInfo;


    public MoviePosterAdapter(ArrayList<MoviesInfo> arrayListMoviesInfo, Context mContext) {
        this.arrayListMoviesInfo = arrayListMoviesInfo;
        this.mContext = mContext;
    }


    public static class ViewHolder {
        public ImageView ivMoviePoster;
    }


    @Override
    public int getCount() {
        return arrayListMoviesInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListMoviesInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {


        if (convertView == null) {

            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.gv_poster_layout, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.ivMoviePoster = (ImageView) convertView.findViewById(R.id.ivInGridView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        moviesInfo = arrayListMoviesInfo.get(position);
        Picasso.with(mContext).load(moviesInfo.getMoviePosterImageURL()).into(viewHolder.ivMoviePoster);

        return convertView;

    }
}
