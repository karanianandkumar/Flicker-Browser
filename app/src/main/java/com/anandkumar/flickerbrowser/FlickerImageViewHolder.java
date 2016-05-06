package com.anandkumar.flickerbrowser;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Anand on 5/6/2016.
 */
public class FlickerImageViewHolder extends RecyclerView.ViewHolder {

    protected ImageView thumbnail;
    protected TextView title;

    public FlickerImageViewHolder(View view) {
        super(view);
        this.thumbnail = (ImageView)view.findViewById(R.id.thumbanail);
        this.title=(TextView)view.findViewById(R.id.title);
    }
}
