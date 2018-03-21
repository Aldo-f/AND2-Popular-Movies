package be.mosterdpot.android.popularmovies.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import be.mosterdpot.android.popularmovies.model.Movie;

/**
 * Created by aldof on 14/03/2018.
 */

public class MovieDeserializer implements JsonDeserializer<List<Movie>> {


    @Override
    public List<Movie> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {


        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonArray jsonarray = jsonObject.getAsJsonArray("results");

        List<Movie> movies = new ArrayList<>();

        for (JsonElement ob : jsonarray) {
            JsonObject temp = ob.getAsJsonObject();
            String title = temp.get("title").getAsString();
            String posterPath = temp.get("poster_path").getAsString();
            String overview = temp.get("overview").getAsString();
            String releaseDate = temp.get("release_date").getAsString();
            double voteAverage = temp.get("vote_average").getAsDouble();

//            Movie movie = new Movie(0, title, posterPath, voteAverage, releaseDate, overview);

//            movies.add(movie);
        }

        return movies;
    }
}
