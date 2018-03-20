package be.mosterdpot.android.popularmovies.utils;

import android.view.View;

/**
 * Created by aldof on 18/03/2018.
 */

public interface OnItemClickListener {

    void onItemClick(View v, int position);
    void onLongClick(View v, int position);
}
