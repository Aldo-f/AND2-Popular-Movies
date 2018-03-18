package be.mosterdpot.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import be.mosterdpot.android.popularmovies.adapter.MoviesAdapter;
import be.mosterdpot.android.popularmovies.interfaces.MovieInterface;
import be.mosterdpot.android.popularmovies.model.Movie;
import be.mosterdpot.android.popularmovies.model.MovieResponse;
import be.mosterdpot.android.popularmovies.utils.OnItemClickListener;
import be.mosterdpot.android.popularmovies.utils.RecyclerItemClickListener;
import io.objectbox.Box;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "d21283b34416ede4cf71abdc3e0f4672";
    public int gridNumberCols;
    //@BindView(R.id.recyclerMovies)
    RecyclerView recyclerView;
    private Box<Movie> movieBox;
    private MoviesAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();
    private MovieInterface service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridNumberCols = this.getResources().getInteger(R.integer.grid_number_cols);
        App app = (App) getApplication();

        movieBox = app.getBoxStore().boxFor(Movie.class);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(MovieInterface.class);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridNumberCols));
        adapter = new MoviesAdapter(movieList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("MovieId", movieList.get(position).getId());
                startActivity(intent);
//                Toast.makeText(MainActivity.this, "" + position + " = "+movies.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View v, int position) {
            }
        }));

        makeCall("favorite");
    }

    private void makeCall(String sortBy) {
        Call<MovieResponse> call;
        if (sortBy.equals("popular")) {
            call = service.listPopularMovies(BuildConfig.THE_MOVIE_DATABASE_API_KEY);
        } else
            call = service.listTopRatedMovies(BuildConfig.THE_MOVIE_DATABASE_API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.isSuccessful()) {
//                    for (Movie movie : response.body().getResults()) {
//                        movie.setId(0);
//                        movieBox.put(movie);
//                    }
                    movieList = response.body().getResults();
                    adapter.notifyDataSetChanged();
                    adapter.updateList(movieList);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
