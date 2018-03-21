package be.mosterdpot.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by aldof on 14/03/2018.
 */

@Entity
public class Movie {

    public static final float POSTER_ASPECT_RATIO = 1.5f;

    @Id
    long id;
    int movieId;
    private String sortBy;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("overview")
    private String overview;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("backdrop_path")
    private String backdropPath;
    // TODO
//    @SerializedName("favorite")
    private boolean favorite;
    public Movie() {

    }
    public Movie(long id, int movieId, String title, String posterPath, double voteAverage, String releaseDate, String overview, String originalTitle, String homepage, String backdropPath, boolean favorite) {
        this.id = id;
        this.movieId = movieId;
        this.title = title;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.originalTitle = originalTitle;
        this.homepage = homepage;
        this.backdropPath = backdropPath;
        this.favorite = false;

    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
