package be.mosterdpot.android.popularmovies;

import android.app.Application;

import be.mosterdpot.android.popularmovies.model.MyObjectBox;
import io.objectbox.BoxStore;

/**
 * Created by aldof on 14/03/2018.
 */

public class App extends Application {

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

}
