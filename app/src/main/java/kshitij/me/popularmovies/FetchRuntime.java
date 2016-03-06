package kshitij.me.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kshitijsharma on 06/03/16.
 */
public class FetchRuntime extends AsyncTask<String, Void, String> {

    protected String BASE_URL = "http://api.themoviedb.org/3/movie/%s?";
    private HttpURLConnection httpURLConnection;
    private BufferedReader reader;
    private String movieResponseJSONString;
    private Long runtime;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... defaultSortParameter) {

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


        return movieRuntime(movieResponseJSONString);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    private String movieRuntime(String movieResponseJSONString) {

        try {
            JSONObject rootObject = new JSONObject(movieResponseJSONString);
            runtime = rootObject.getLong("runtime");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return String.valueOf(runtime);


    }


}

