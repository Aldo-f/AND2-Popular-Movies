package be.mosterdpot.android.popularmovies.adapter;

import android.content.Context;
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

/**
 * Created by aldof on 15/03/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Movie> moviesList;

    public MoviesAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Context context = holder.view.getContext();
        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
//       Picasso.with(context).load(" http://image.tmdb.org/t/p/w185/" + movie.getPosterPath()).config(Bitmap.Config.RGB_565).into(holder.image);
        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(holder.image);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public  View view;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            image = view.findViewById(R.id.image);
            this.view = view;
        }
    }
}
