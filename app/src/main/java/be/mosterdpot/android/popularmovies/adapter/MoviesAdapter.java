package be.mosterdpot.android.popularmovies.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import be.mosterdpot.android.popularmovies.R;
import be.mosterdpot.android.popularmovies.model.Movie;
import be.mosterdpot.android.popularmovies.utils.RecyclerItemClickListener;

/**
 * Created by aldof on 15/03/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> moviesList;
    private RecyclerItemClickListener clickListener;

    public MoviesAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);

        final Context context = itemView.getContext();

        // TODO: grid_number_cols needs to be the same value as gridNumberCols from MainActivity, this changes based on the dimens 
        int gridColsNumber = context.getResources()
                .getInteger(R.integer.grid_number_cols);


        itemView.getLayoutParams().height = (int) (parent.getWidth() / gridColsNumber *
                Movie.POSTER_ASPECT_RATIO);

        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final Context context = holder.view.getContext();
        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        Picasso.with(context)

                .load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath())
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.ic_launcher_background)
                .config(Bitmap.Config.RGB_565)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void setClickListener(RecyclerItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void updateList(List<Movie> movieList) {
        moviesList = movieList;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public View view;

        public MovieViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            image = view.findViewById(R.id.image);
            this.view = view;
        }

    }
}
