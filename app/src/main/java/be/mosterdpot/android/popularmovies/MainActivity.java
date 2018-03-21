package be.mosterdpot.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.mosterdpot.android.popularmovies.adapter.MoviesAdapter;
import be.mosterdpot.android.popularmovies.interfaces.MovieInterface;
import be.mosterdpot.android.popularmovies.model.Movie;
import be.mosterdpot.android.popularmovies.model.Page;
import be.mosterdpot.android.popularmovies.utils.OnItemClickListener;
import be.mosterdpot.android.popularmovies.utils.RecyclerItemClickListener;
import io.objectbox.Box;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static be.mosterdpot.android.popularmovies.R.id.recycler_view;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public int gridNumberCols;

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

        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        movieBox = app.getBoxStore().boxFor(Movie.class);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(MovieInterface.class);

        RecyclerView recyclerView = findViewById(recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridNumberCols));
        adapter = new MoviesAdapter(movieList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                int currentPref = sharedPref.getInt(getString(R.string.pref_key_sort), R.string.sort_by_most_popular);
                if (currentPref == R.string.sort_by_favorites) {
                    Toast.makeText(MainActivity.this, "Under construction", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("MovieId", movieList.get(position).getId());
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View v, int position) {
            }
        }));


        int sortBy = sharedPref.getInt(getString(R.string.pref_key_sort), R.string.sort_by_most_popular);

        makeCall(sortBy);
    }

    private void makeCall(int sortBy) {
        Call<Page> call;
        if (sortBy == (R.string.sort_by_most_popular)) {
            setTitle(R.string.title_popular);
            call = service.listPopularMovies(BuildConfig.THE_MOVIE_DATABASE_API_KEY);
        } else if (sortBy == (R.string.sort_by_top_rated)) {
            setTitle(R.string.title_top_rated);
            call = service.listTopRatedMovies(BuildConfig.THE_MOVIE_DATABASE_API_KEY);
        } else {
            setTitle(R.string.title_favorites);
            movieList = movieBox.getAll();
            adapter.notifyDataSetChanged();
            adapter.updateList(movieList);
            return;
        }
        call.enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {

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
            public void onFailure(Call<Page> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        int sortBy = sharedPref.getInt(getString(R.string.pref_key_sort), R.string.sort_by_most_popular);

        if (sortBy == (R.string.sort_by_most_popular)) {
            menu.findItem(R.id.sort_by_most_popular).setChecked(true);
        } else if (sortBy == (R.string.sort_by_top_rated)) {
            menu.findItem(R.id.sort_by_top_rated).setChecked(true);
        } else
            menu.findItem(R.id.sort_by_favorite).setChecked(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        switch (item.getItemId()) {
            case R.id.sort_by_top_rated:
                item.setChecked(true);
                setTitle(R.string.title_top_rated);
                editor.putInt(getString(R.string.pref_key_sort), (R.string.sort_by_top_rated));

                makeCall((R.string.sort_by_top_rated));
                break;
            case R.id.sort_by_most_popular:
                item.setChecked(true);
                setTitle(R.string.title_popular);
                editor.putInt(getString(R.string.pref_key_sort), (R.string.sort_by_most_popular));

                makeCall((R.string.sort_by_most_popular));
                break;
            case R.id.sort_by_favorite:
                item.setChecked(true);
                setTitle(R.string.title_favorites);
                editor.putInt(getString(R.string.pref_key_sort), (R.string.sort_by_favorites));

                makeCall(R.string.sort_by_favorites);
            default:
                break;
        }
        editor.apply();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
