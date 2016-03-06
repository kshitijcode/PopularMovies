package kshitij.me.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements MoviesListFragment.Callback {

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;
    private DetailsFragment detailsFragment;
    private ArrayList<String> movieTrailersURL;
    private ArrayList<String> reviewsArrayList;
    private String runtime = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailsFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

    }


    @Override
    public void onItemSelected(MoviesInfo moviesInfo) {
        if (mTwoPane) {

            Bundle args = new Bundle();

            initializeDetails(moviesInfo);
            args.putString("id", String.valueOf(moviesInfo.getMovieID()));
            args.putString("original_title", moviesInfo.getOriginalTitle());
            args.putString("movie_poster_url", moviesInfo.getMoviePosterImageURL());
            args.putString("overview", moviesInfo.getOverView());
            args.putString("vote_average", moviesInfo.getVoteAverage());
            args.putString("release_date", moviesInfo.getReleaseDate());
            args.putStringArrayList("reviews", moviesInfo.getArrayListReviews());
            args.putStringArrayList("trailer_url", moviesInfo.getMovieTrailersURL());
            args.putString("runtime", moviesInfo.getRuntime());
            detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, detailsFragment, DETAILFRAGMENT_TAG)
                    .commit();

        } else {


            initializeDetails(moviesInfo);

            moviesInfo.setMovieTrailersURL(movieTrailersURL);
            moviesInfo.setArrayListReviews(reviewsArrayList);
            moviesInfo.setRuntime(runtime);
            Intent detailsActivity = new Intent(this, DetailsActivity.class);
            detailsActivity.putExtra("id", String.valueOf(moviesInfo.getMovieID()));
            detailsActivity.putExtra("original_title", moviesInfo.getOriginalTitle());
            detailsActivity.putExtra("movie_poster_url", moviesInfo.getMoviePosterImageURL());
            detailsActivity.putExtra("overview", moviesInfo.getOverView());
            detailsActivity.putExtra("vote_average", moviesInfo.getVoteAverage());
            detailsActivity.putExtra("release_date", moviesInfo.getReleaseDate());
            detailsActivity.putExtra("reviews", moviesInfo.getArrayListReviews());
            detailsActivity.putExtra("trailer_url", moviesInfo.getMovieTrailersURL());
            detailsActivity.putExtra("runtime", moviesInfo.getRuntime());
            startActivity(detailsActivity);
        }
    }

    private void initializeDetails(MoviesInfo moviesInfo) {
        try {
            movieTrailersURL = new FetchTrailersLink().execute(moviesInfo.getMovieID()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            reviewsArrayList = new FetchReviewsTask().execute(moviesInfo.getMovieID()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            runtime = new FetchRuntime().execute(String.valueOf(moviesInfo.getMovieID()), MoviesListFragment.API_KEY).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }
}
