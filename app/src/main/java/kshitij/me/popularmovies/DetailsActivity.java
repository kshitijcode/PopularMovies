package kshitij.me.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        detailsActivity = getIntent();
        DetailsFragment detailsFragment = new DetailsFragment();

        Bundle bundle = new Bundle();

        bundle.putString("id", detailsActivity.getStringExtra("id"));
        bundle.putString("original_title", detailsActivity.getStringExtra("original_title"));
        bundle.putString("vote_average", detailsActivity.getStringExtra("vote_average") + "/10");
        bundle.putString("movie_poster_url", detailsActivity.getStringExtra("movie_poster_url"));
        bundle.putString("overview", detailsActivity.getStringExtra("overview"));
        bundle.putString("release_date", detailsActivity.getStringExtra("release_date"));
        bundle.putStringArrayList("reviews", detailsActivity.getStringArrayListExtra("reviews"));
        bundle.putStringArrayList("trailer_url", detailsActivity.getStringArrayListExtra("trailer_url"));
        bundle.putString("runtime",detailsActivity.getStringExtra("runtime"));

        detailsFragment.setArguments(bundle);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.movie_detail_container, detailsFragment).commit();
        }
    }


}
