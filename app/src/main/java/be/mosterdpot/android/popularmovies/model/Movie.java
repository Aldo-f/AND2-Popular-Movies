package be.mosterdpot.android.popularmovies.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by aldof on 14/03/2018.
 */

@Entity
public class Movie {

    @Id
    long id;


    private String title;
    private String poster;
    private double vote_average;
    private String release_date;
    private String overview;

    public Movie() {
    }

    public Movie(String title, String poster, double vote_average, String release_date, String overview) {
        this.title = title;
        this.poster = poster;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
