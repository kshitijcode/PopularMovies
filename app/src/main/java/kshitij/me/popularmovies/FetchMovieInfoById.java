package kshitij.me.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kshitijsharma on 28/11/15.
 */


public class FetchMovieInfoById extends AsyncTask<String, Void, ArrayList<MoviesInfo>> {

    protected String BASE_URL = "http://api.themoviedb.org/3/movie/%s?";
    private HttpURLConnection httpURLConnection;
    private BufferedReader reader;
    private String movieResponseJSONString;
    private MoviesInfo moviesInfo;
    protected String MOVIE_POSTER_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500/";
    private ArrayList<MoviesInfo> arrayListMoviesInfo;
    private MoviePosterAdapter moviesPosterAdapter;
    private Context mContext;
    private GridView gridView;

    public FetchMovieInfoById(Context mContext, GridView gridView
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

            final String API_KEY = "api_key";


            Uri builtUri = Uri.parse(String.format(BASE_URL, defaultSortParameter[0])).buildUpon()
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

            long movieID = rootObject.getLong("id");
            String originalTitle = rootObject.getString("original_title");
            String overView = rootObject.getString("overview");
            String voteAverage = rootObject.getString("vote_average");
            String releaseDate = rootObject.getString("release_date");
            String moviePosterImageURL = MOVIE_POSTER_IMAGE_BASE_URL + rootObject.getString("poster_path");
            String runtime = rootObject.getString("runtime");


            moviesInfo = new MoviesInfo(movieID, originalTitle, overView, voteAverage,
                    releaseDate, moviePosterImageURL);
            moviesInfo.setRuntime(runtime);
            arrayListMoviesInfo.add(moviesInfo);


        } catch (JSONException e1) {
            e1.printStackTrace();
        }


    }


}

