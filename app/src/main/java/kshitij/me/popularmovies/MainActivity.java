package kshitij.me.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements MoviesListFragment.Callback {

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;
    private DetailsFragment detailsFragment;

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

        //  MoviesListFragment forecastFragment = ((MoviesListFragment) getSupportFragmentManager()
        //      .findFragmentById(R.id.movies_list_fragment));
        //forecastFragment.setUseTodayLayout(!mTwoPane);

    }


    @Override
    public void onItemSelected(MoviesInfo moviesInfo) {
        if (mTwoPane) {

            Bundle args = new Bundle();
            args.putString("original_title", moviesInfo.getOriginalTitle());
            args.putString("movie_poster_url", moviesInfo.getMoviePosterImageURL());
            args.putString("overview", moviesInfo.getOverView());
            args.putString("vote_average", moviesInfo.getVoteAverage());
            args.putString("release_date", moviesInfo.getReleaseDate());
            detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, detailsFragment, DETAILFRAGMENT_TAG)
                    .commit();

        } else {

            Intent detailsActivity = new Intent(this, DetailsActivity.class);
            detailsActivity.putExtra("original_title", moviesInfo.getOriginalTitle());
            detailsActivity.putExtra("movie_poster_url", moviesInfo.getMoviePosterImageURL());
            detailsActivity.putExtra("overview", moviesInfo.getOverView());
            detailsActivity.putExtra("vote_average", moviesInfo.getVoteAverage());
            detailsActivity.putExtra("release_date", moviesInfo.getReleaseDate());
            detailsActivity.putExtra("reviews",moviesInfo.getArrayListReviews());
            detailsActivity.putExtra("trailer_url",moviesInfo.getMovieTrailersURL());
            startActivity(detailsActivity);
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
