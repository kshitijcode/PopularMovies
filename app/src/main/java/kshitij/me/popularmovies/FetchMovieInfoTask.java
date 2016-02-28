package kshitij.me.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by kshitijsharma on 28/11/15.
 */


public class FetchMovieInfoTask extends AsyncTask<String, Void, ArrayList<MoviesInfo>> {

    protected String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
    private HttpURLConnection httpURLConnection;
    private BufferedReader reader;
    private String movieResponseJSONString;
    private MoviesInfo moviesInfo;
    protected String MOVIE_POSTER_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500/";
    private ArrayList<MoviesInfo> arrayListMoviesInfo;
    private MoviePosterAdapter moviesPosterAdapter;
    private Context mContext;
    private GridView gridView;
    private ArrayList<String> reviewsArrayList;
    private ArrayList<String> movieTrailersURL;

    public FetchMovieInfoTask(Context mContext, GridView gridView
            , MoviePosterAdapter moviePosterAdapter) {
        this.mContext = mContext;
        this.gridView = gridView;
        this.moviesPosterAdapter = moviePosterAdapter;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        arrayListMoviesInfo = new ArrayList<>();
    }


    @Override
    protected ArrayList<MoviesInfo> doInBackground(String... defaultSortParameter) {

        try {
            final String SORT_PARAMETER = "sort_by";
            final String API_KEY = "api_key";


            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_PARAMETER, defaultSortParameter[0])
                    .appendQueryParameter(API_KEY, defaultSortParameter[1])
                    .build();

            URL url = new URL(builtUri.toString());

            Log.i("URL", url.toString());

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            // Reading Response
            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {

                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {

                return null;
            }
            movieResponseJSONString = buffer.toString();



        } catch (Exception e) {

            Log.i("Exceptional", e.getMessage());
        }


        return arrayListMoviesInfo;
    }


    @Override
    protected void onPostExecute(ArrayList<MoviesInfo> moviesInfos) {
        addMovieDatatoArrayList(movieResponseJSONString);
        moviesPosterAdapter = new MoviePosterAdapter(moviesInfos, mContext);
        gridView.setAdapter(moviesPosterAdapter);
    }

    private void addMovieDatatoArrayList(String movieResponseJSONString) {

        try {
            JSONObject rootObject = new JSONObject(movieResponseJSONString);
            JSONArray resultsArray = rootObject.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject eachMovieObject = resultsArray.getJSONObject(i);

                long movieID = eachMovieObject.getLong("id");
                try {
                    reviewsArrayList = new FetchReviewsTask().execute(movieID).get();
                    movieTrailersURL = new FetchTrailersLink().execute(movieID).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                String originalTitle = eachMovieObject.getString("original_title");
                String overView = eachMovieObject.getString("overview");
                String voteAverage = eachMovieObject.getString("vote_average");
                String releaseDate = eachMovieObject.getString("release_date");
                String moviePosterImageURL = MOVIE_POSTER_IMAGE_BASE_URL + eachMovieObject.getString("poster_path");

                moviesInfo = new MoviesInfo(movieID, originalTitle, overView, voteAverage,
                        releaseDate, moviePosterImageURL, reviewsArrayList, movieTrailersURL);
                arrayListMoviesInfo.add(moviesInfo);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

