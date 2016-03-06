package kshitij.me.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kshitijsharma on 08/02/16.
 */
public class DetailsFragment extends android.support.v4.app.Fragment {


    TextView tvTitle, tvOverView, tvReleaseDate, tvRating, tvTrailers, tvRunningTime;
    ImageView ivMoviePoster;
    Bundle arguments;
    static Set<String> mSetFavorites;


    Button btFavorite;
    ViewGroup videoContainer, reviewContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arguments = getArguments();
        mSetFavorites = new HashSet();
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
        btFavorite = (Button) rootView.findViewById(R.id.btFavorite);
        videoContainer = (ViewGroup) rootView.findViewById(R.id.llTrailersContainer);
        reviewContainer = (ViewGroup) rootView.findViewById(R.id.llReviewsContainer);
        tvRunningTime = (TextView) rootView.findViewById(R.id.tvRunningTime);
        if (arguments != null) {
            setValuesForRespectiveViews();
        }

        return rootView;

    }

    private void setValuesForRespectiveViews() {

        tvTitle.setText(arguments.getString("original_title"));
        tvRating.setText(arguments.getString("vote_average"));
        tvReleaseDate.setText(arguments.getString("release_date").split("-")[0]);
        tvOverView.setText(arguments.getString("overview"));
        tvRunningTime.setText(arguments.getString("runtime") + "min");
        Picasso.with(getActivity()).load(arguments.getString("movie_poster_url")).into(ivMoviePoster);
        tvTrailers.setText("Trailers :");


        for (int i = 0; i < arguments.getStringArrayList("trailer_url").size(); i++) {
            String videoName = arguments.getStringArrayList("trailer_url").get(i);
            videoContainer.addView(addNewChild(videoName, i));
        }

        for (int i = 0; i < arguments.getStringArrayList("reviews").size(); i++) {
            String reviews = arguments.getStringArrayList("reviews").get(i).split("-->")[0];
            String authorName = arguments.getStringArrayList("reviews").get(i).split("-->")[1];
            reviewContainer.addView(addReview(reviews, authorName));
        }

        btFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSetFavorites.add(arguments.getString("id"));

            }
        });

        btFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSetFavorites.add(arguments.getString("id"));
                SharedPreferences sharedPref = getActivity().getSharedPreferences("Movies Id Preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putStringSet("id", mSetFavorites);
                editor.commit();
            }
        });


    }

    private View addReview(String reviews, String authorName) {
        View reviewView = getActivity().getLayoutInflater()
                .inflate(R.layout.reviews_layout, null);

        TextView tvAuthorName =
                (TextView) reviewView.findViewById(R.id.tvAuthorName);
        TextView tvReview =
                (TextView) reviewView.findViewById(R.id.tvReviews);

        tvReview.setText(reviews);
        tvAuthorName.setText(authorName);
        return reviewView;
    }


    private View addNewChild(String videoURL, int position) {
        View videoView = getActivity().getLayoutInflater()
                .inflate(R.layout.trailers_layout, null);
        TextView tvURL =
                (TextView) videoView.findViewById(R.id.tvURL);
        tvURL.setClickable(true);
        tvURL.setMovementMethod(LinkMovementMethod.getInstance());
        String tvURLText = "<a href='%s'> Trailer %s</a>";
        tvURL.setText(Html.fromHtml(String.format(tvURLText, videoURL, String.valueOf(position + 1))));
        return videoView;
    }


}
