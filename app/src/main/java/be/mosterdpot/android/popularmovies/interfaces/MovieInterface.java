package be.mosterdpot.android.popularmovies.interfaces;

import be.mosterdpot.android.popularmovies.model.Movie;
import be.mosterdpot.android.popularmovies.model.Page;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by aldof on 14/03/2018.
 */

public interface MovieInterface {

    @GET("movie/popular")
    Call<Page> listPopularMovies(@Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<Page> listTopRatedMovies(@Query("api_key") String api_key);

    @GET("movie/{id}")
    Call<Movie> getMovieById(@Path("id") int id, @Query("api_key") String api_key);
}
