package be.mosterdpot.android.popularmovies.interfaces;

import be.mosterdpot.android.popularmovies.model.Movie;
import be.mosterdpot.android.popularmovies.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by aldof on 14/03/2018.
 */

public interface MovieInterface {

    @GET("movie/popular")
    Call<MovieResponse> listPopularMovies(@Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<MovieResponse> listTopRatedMovies(@Query("api_key") String api_key);

    @GET("movie/{id}")
    Call<Movie> getMovieById(@Path("id") int id, @Query("api_key") String api_key);


//                https://api.themoviedb.org/3/movie/372058?api_key=d21283b34416ede4cf71abdc3e0f4672
}
