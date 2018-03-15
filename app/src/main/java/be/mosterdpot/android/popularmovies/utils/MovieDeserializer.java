package be.mosterdpot.android.popularmovies.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;

import be.mosterdpot.android.popularmovies.model.Movie;

/**
 * Created by aldof on 14/03/2018.
 */

public class MovieDeserializer implements JsonDeserializer<Movie>{


    @Override
    public Movie deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {


        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonArray jsonarray = jsonObject.getAsJsonArray("results");

        Movie movie = new Movie();
//        movie.setOverview("test");
        return movie;
    }
}
