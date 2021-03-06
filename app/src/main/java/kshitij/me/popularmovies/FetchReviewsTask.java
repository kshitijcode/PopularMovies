package kshitij.me.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kshitijsharma on 25/02/16.
 */

public class FetchReviewsTask extends AsyncTask<Long, Void, ArrayList<String>> {

    protected static ArrayList<String> reviewsList;
    protected String BASE_URL = "http://api.themoviedb.org/3/movie/%s/reviews?";
    private HttpURLConnection httpURLConnection;
    private BufferedReader reader;
    private String movieResponseJSONString;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        reviewsList = new ArrayList<>();
    }


    @Override
    protected ArrayList doInBackground(Long... longs) {


        final String API_KEY = "api_key";

        Uri builtUri = Uri.parse(String.format(BASE_URL, longs[0])).buildUpon()
                .appendQueryParameter(API_KEY, MoviesListFragment.API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e1) {

        }



        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            // Reading Response
            InputStream inputStream = null;
            inputStream = httpURLConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {

                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            if (buffer.length() == 0) {

                return null;
            }
            movieResponseJSONString = buffer.toString();


        } catch (Exception e) {
        }

        return addReviewsToArrayList(movieResponseJSONString);

    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
    }

    private ArrayList<String> addReviewsToArrayList(String movieResponseJSONString) {

        try {
            JSONObject rootObject = new JSONObject(movieResponseJSONString);
            JSONArray resultsArray = rootObject.getJSONArray("results");
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject eachMovieObject = resultsArray.getJSONObject(i);
                reviewsList.add(eachMovieObject.getString("author") + "-->" + eachMovieObject.getString("content"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewsList;

    }
}
