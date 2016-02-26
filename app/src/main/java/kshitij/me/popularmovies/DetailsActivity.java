package kshitij.me.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class DetailsActivity extends AppCompatActivity {


    Intent detailsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        detailsActivity = getIntent();
        DetailsFragment detailsFragment = new DetailsFragment();

        Bundle bundle = new Bundle();

        bundle.putString("original_title", detailsActivity.getStringExtra("original_title"));
        bundle.putString("vote_average", detailsActivity.getStringExtra("vote_average") + "/10");
        bundle.putString("movie_poster_url", detailsActivity.getStringExtra("movie_poster_url"));
        bundle.putString("overview", detailsActivity.getStringExtra("overview"));
        bundle.putString("release_date", detailsActivity.getStringExtra("release_date"));
        bundle.putStringArrayList("reviews", detailsActivity.getStringArrayListExtra("reviews"));
        bundle.putStringArrayList("trailer_url", detailsActivity.getStringArrayListExtra("trailer_url"));

        detailsFragment.setArguments(bundle);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.movie_detail_container, detailsFragment).commit();
        }
    }


}
