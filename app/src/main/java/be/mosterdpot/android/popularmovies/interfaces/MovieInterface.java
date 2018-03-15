package be.mosterdpot.android.popularmovies.interfaces;

import java.util.List;

import be.mosterdpot.android.popularmovies.model.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by aldof on 14/03/2018.
 */

public interface MovieInterface {

    @GET("movie/popular")
    Call<List<Movie>> listPopularMovies(@Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<List<Movie>> listTopRatedMovies(@Query("api_key") String api_key);
}
