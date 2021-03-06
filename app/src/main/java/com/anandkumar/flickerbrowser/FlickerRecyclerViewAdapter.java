package com.anandkumar.flickerbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Anand on 5/7/2016.
 */
public class FlickerRecyclerViewAdapter extends RecyclerView.Adapter<FlickerImageViewHolder> {

    private List<Photo> mPhotolist;
    private Context context;

    public FlickerRecyclerViewAdapter(Context context,List<Photo> photoList) {
        this.mPhotolist = photoList;
        this.context = context;
    }

    @Override
    public FlickerImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.browse,null);
        FlickerImageViewHolder flickerImageViewHolder=new FlickerImageViewHolder(view);
        return flickerImageViewHolder;
    }

    @Override
    public void onBindViewHolder(FlickerImageViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(FlickerImageViewHolder flickerImageViewHolder, int position) {
        Photo photoObject=mPhotolist.get(position);
        Picasso.with(context).load(photoObject.getmImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(flickerImageViewHolder.thumbnail);
        flickerImageViewHolder.title.setText(photoObject.getmTitle());
    }

    @Override
    public int getItemCount() {
        return (null!=mPhotolist? mPhotolist.size() : 0);
    }

    public void loadNewData(List<Photo> newPhotos){
        mPhotolist=newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position){
        return (null!= mPhotolist ? mPhotolist.get(position):null);
    }
}
