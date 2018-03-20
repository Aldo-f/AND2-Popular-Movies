package be.mosterdpot.android.popularmovies;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import be.mosterdpot.android.popularmovies.interfaces.MovieInterface;
import be.mosterdpot.android.popularmovies.model.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    private final String BASE_URL = "http://api.themoviedb.org/3/";
    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    private final String WIDTH_POSTER = "w185/";
    private final String WIDTH_BACKDROP = "w780/";

    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.tv_overview)
    TextView overview;
    @BindView(R.id.tv_release_date)
    TextView releaseDate;
    @BindView(R.id.tv_vote_average)
    TextView voteAverage;
    @BindView(R.id.tv_homepage)
    TextView homepage;
    @BindView(R.id.tv_original_title)
    TextView originalTitle;
    @BindView(R.id.iv_poster_path)
    ImageView posterPath;
    @BindView(R.id.iv_backdrop_path)
    ImageView backdropPath;
    private Menu menu;
    private Movie movie;

    private Box<Movie> movieBox;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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
                    final Movie movie = response.body();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            title.setText(movie.getTitle());
                            overview.setText(movie.getOverview());
                            //setTitle(movie.getTitle()); // Sets title ov the DetailView to current movie
                            setTitle(""); // easy fix to add a back arrow
                            // Format releaseDate to releaseYear
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // from pattern
                            SimpleDateFormat year_date = new SimpleDateFormat("yyyy"); // to pattern
                            String formatReleaseDate = movie.getReleaseDate();
                            Date date = null;
                            try {
                                date = sdf.parse(formatReleaseDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String releaseYear = year_date.format(date);
                            releaseDate.setText(releaseYear);

                            voteAverage.setText(String.valueOf(movie.getVoteAverage()));
                            originalTitle.setText(movie.getOriginalTitle());
                            homepage.setText(movie.getHomepage());
                            Picasso.with(DetailActivity.this)
                                    .load(BASE_IMAGE_URL + WIDTH_POSTER + movie.getPosterPath())
                                    .config(Bitmap.Config.RGB_565)
                                    .resize(posterPath.getMeasuredWidth(), posterPath.getMeasuredHeight())
                                    .centerInside() // centerInside() or centerCrop()
                                    .into(posterPath);

                            Picasso.with(DetailActivity.this)
                                    .load(BASE_IMAGE_URL + WIDTH_BACKDROP + movie.getBackdropPath())
                                    .config(Bitmap.Config.RGB_565)
                                    .resize(backdropPath.getMeasuredWidth(), backdropPath.getMeasuredHeight())
                                    .transform(new BlurTransformation(DetailActivity.this))
                                    .centerCrop() // centerInside() or centerCrop()
                                    .into(backdropPath);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        this.menu = menu;
        updateMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.favorite) {
//            if (movie.isFavorite())
//                movie.setFavorite(false);
//            else
//                movie.setFavorite(true);
//            movieBox.put(movie);

            updateMenu();
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateMenu() {
        //TODO fix this
//        if (movie.isFavorite())
        if (true)
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star_border));
        else
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star_fill));
    }
}
