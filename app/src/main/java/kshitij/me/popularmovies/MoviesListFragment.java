package kshitij.me.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by kshitijsharma on 08/02/16.
 */
public class MoviesListFragment extends android.support.v4.app.Fragment {


    GridView gvMoviePoster;
    ArrayList<MoviesInfo> arrayListMoviesInfo;
    public static String API_KEY = "";
    MoviePosterAdapter moviePosterAdapter;
    MoviesInfo moviesInfo;

    protected String DEFAULT_SORT_PARAMETER = "popularity.desc";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.movies_list_fragment, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        gvMoviePoster = (GridView) rootView.findViewById(R.id.gvMoviePosters);
        setGridViewListener();
        return rootView;

    }

    private void callFetchMovieInfoTask(String default_sort_parameter) {


        try {
            arrayListMoviesInfo = new FetchMovieInfoTask(getActivity(), gvMoviePoster, moviePosterAdapter).execute(
                    default_sort_parameter, API_KEY).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void setGridViewListener() {

        gvMoviePoster.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                moviesInfo = arrayListMoviesInfo.get(i);
                ((Callback) getActivity())
                        .onItemSelected(moviesInfo);
            }
        });

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        arrayListMoviesInfo = new ArrayList<>();
        if(DEFAULT_SORT_PARAMETER.equals("Favorite"))
            callFetchMovieInfobyIDTask();
        else
        callFetchMovieInfoTask(DEFAULT_SORT_PARAMETER);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.pref_highest_rated) {
            DEFAULT_SORT_PARAMETER = "vote_average.desc";
            callFetchMovieInfoTask(DEFAULT_SORT_PARAMETER);
           item.
        } else if (id == R.id.pref_most_popular) {
            DEFAULT_SORT_PARAMETER = "popularity.desc";
            callFetchMovieInfoTask(DEFAULT_SORT_PARAMETER);

        } else {
            DEFAULT_SORT_PARAMETER ="Favorite";
            callFetchMovieInfobyIDTask();

        }
        return false;
    }

    private void callFetchMovieInfobyIDTask() {


        SharedPreferences sharedPref = getActivity().getSharedPreferences("Movies Id Preferences", Context.MODE_PRIVATE);
        Set<String> mFavoritesId = sharedPref.getStringSet("id", null);
        Iterator iterator = mFavoritesId.iterator();
        while (iterator.hasNext()) {
            try {
                arrayListMoviesInfo = new FetchMovieInfoById(getActivity(), gvMoviePoster, moviePosterAdapter).execute(iterator.next().toString(), API_KEY).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    public interface Callback {
        void onItemSelected(MoviesInfo moviesInfo);
    }
}
