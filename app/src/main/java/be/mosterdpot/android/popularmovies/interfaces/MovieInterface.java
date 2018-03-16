package be.mosterdpot.android.popularmovies.interfaces;

import be.mosterdpot.android.popularmovies.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by aldof on 14/03/2018.
 */

public interface MovieInterface {

    @GET("movie/popular")
    Call<MovieResponse> listPopularMovies(@Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<MovieResponse> listTopRatedMovies(@Query("api_key") String api_key);
}
