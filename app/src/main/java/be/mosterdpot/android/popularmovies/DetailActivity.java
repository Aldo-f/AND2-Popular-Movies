package be.mosterdpot.android.popularmovies;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import be.mosterdpot.android.popularmovies.interfaces.MovieInterface;
import be.mosterdpot.android.popularmovies.model.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    private final String BASE_URL = "http://api.themoviedb.org/3/";
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.tv_overview)
    TextView overview;
    @BindView(R.id.tv_release_date)
    TextView releaseDate;
    @BindView(R.id.tv_vote_average)
    TextView voteAverage;
    @BindView(R.id.iv_poster_path)
    ImageView posterPath;
    @BindView(R.id.tv_original_title)
    TextView originalTitle;
    private Box<Movie> movieBox;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieInterface service = retrofit.create(MovieInterface.class);

        App app = (App) getApplication();
        movieBox = app.getBoxStore().boxFor(Movie.class);

        id = (int) getIntent().getLongExtra("MovieId", 0);
        ButterKnife.bind(this);

        Call<Movie> call = service.getMovieById(id, BuildConfig.THE_MOVIE_DATABASE_API_KEY);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                if (response.isSuccessful()) {
//
                    final Movie movie = response.body();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            title.setText(movie.getTitle());
                            overview.setText(movie.getOverview());
                            releaseDate.setText(movie.getReleaseDate());
                            voteAverage.setText(String.valueOf(movie.getVoteAverage()));
                            originalTitle.setText(movie.getOriginalTitle());
                            Picasso.with(DetailActivity.this).load("http://image.tmdb.org/t/p/original/" + movie.getPosterPath()).config(Bitmap.Config.RGB_565).into(posterPath);
                            setTitle(movie.getTitle());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }
}
