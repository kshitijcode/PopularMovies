package kshitij.me.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by kshitijsharma on 08/02/16.
 */
public class DetailsFragment extends android.support.v4.app.Fragment {


    TextView tvTitle, tvOverView, tvReleaseDate, tvRating, tvTrailers;
    ImageView ivMoviePoster;

    Bundle arguments;
    ListView lvReviews, lvTrailers;
    ReviewsArrayAdapter reviewsArrayAdapter;
    YoutubeURLAdapter youtubeArrayAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arguments = getArguments();
        reviewsArrayAdapter = new ReviewsArrayAdapter(arguments.getStringArrayList("reviews"), getActivity());
        youtubeArrayAdapter = new YoutubeURLAdapter(arguments.getStringArrayList("trailer_url"), getActivity());

    }

    @Override
    public void onStart() {
        super.onStart();




    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.movies_detail_fragment, container, false);
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        tvOverView = (TextView) rootView.findViewById(R.id.tvOverView);
        tvReleaseDate = (TextView) rootView.findViewById(R.id.tvReleaseDate);
        tvRating = (TextView) rootView.findViewById(R.id.tvRating);
        ivMoviePoster = (ImageView) rootView.findViewById(R.id.ivMoviePoster);
        tvTrailers = (TextView) rootView.findViewById(R.id.tvTrailers);
        lvReviews = (ListView) rootView.findViewById(R.id.lvReviews);
        lvTrailers = (ListView) rootView.findViewById(R.id.lvTrailers);

        setValuesForRespectiveViews();
        return rootView;

    }

    private void setValuesForRespectiveViews() {

        Log.i("original_title", arguments.getString("original_title"));

        tvTitle.setText(arguments.getString("original_title"));
        tvRating.setText(arguments.getString("vote_average"));
        tvReleaseDate.setText(arguments.getString("release_date").split("-")[0]);
        Log.i("overview",arguments.getString("overview"));
        tvOverView.setText(arguments.getString("overview"));
        Picasso.with(getActivity()).load(arguments.getString("movie_poster_url")).into(ivMoviePoster);
        tvTrailers.setText("Trailers :");
        lvReviews.setAdapter(reviewsArrayAdapter);
        lvTrailers.setAdapter(youtubeArrayAdapter);


    }
}
