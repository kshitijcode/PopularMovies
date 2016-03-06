package kshitij.me.popularmovies;

import java.util.ArrayList;

/**
 * Created by kshitijsharma on 28/11/15.
 */
public class MoviesInfo {

    protected long movieID;
    protected String originalTitle;
    protected String overView;
    protected String voteAverage;
    protected String releaseDate;
    protected String moviePosterImageURL;
    protected ArrayList<String> arrayListReviews;
    protected ArrayList<String> movieTrailersURL;

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    protected String runtime;

    public void setArrayListReviews(ArrayList<String> arrayListReviews) {
        this.arrayListReviews = arrayListReviews;
    }

    public void setMovieTrailersURL(ArrayList<String> movieTrailersURL) {
        this.movieTrailersURL = movieTrailersURL;
    }

    public long getMovieID() {
        return movieID;
    }

    public MoviesInfo(long movieID, String originalTitle, String overView,
                      String voteAverage, String releaseDate, String moviePosterImageURL) {
        this.movieID = movieID;
        this.originalTitle = originalTitle;
        this.overView = overView;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.moviePosterImageURL = moviePosterImageURL;

    }

    public ArrayList<String> getMovieTrailersURL() {
        return movieTrailersURL;
    }


    public ArrayList<String> getArrayListReviews() {
        return arrayListReviews;
    }

    public String getMoviePosterImageURL() {
        return moviePosterImageURL;
    }


    public String getOverView() {
        return overView;
    }


    public String getVoteAverage() {
        return voteAverage;
    }


    public String getReleaseDate() {
        return releaseDate;
    }


    public String getOriginalTitle() {
        return originalTitle;
    }


}
