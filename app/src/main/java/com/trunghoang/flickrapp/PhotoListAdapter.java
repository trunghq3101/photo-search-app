package com.trunghoang.flickrapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder> {

    private static final String TAG = "PhotoListAdapter";

    private ArrayList<Photo> photos;

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView photoThumb;

        PhotoViewHolder(View layout) {
            super(layout);
            photoThumb = layout.findViewById(R.id.photo_thumb);
        }
    }

    PhotoListAdapter(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View layout = layoutInflater.inflate(R.layout.content_item, parent, false);
        PhotoViewHolder viewHolder = new PhotoViewHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Picasso.get()
                .load(photos.get(position).getImage())
                .into(holder.photoThumb);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    void updatePhotosData(ArrayList<Photo> data) {
        photos = data;
        notifyDataSetChanged();
    }

    Photo getPhoto(int position) {
        return photos.get(position);
    }
}
