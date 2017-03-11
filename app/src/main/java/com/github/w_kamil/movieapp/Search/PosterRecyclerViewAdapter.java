package com.github.w_kamil.movieapp.Search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.w_kamil.movieapp.R;

import java.util.Collections;
import java.util.List;


import static butterknife.ButterKnife.*;


/**
 * Created by Frod_ on 11.03.2017.
 */

public class PosterRecyclerViewAdapter extends RecyclerView.Adapter<PosterRecyclerViewAdapter.ViewHolder> {


    private List<String> urls = Collections.emptyList();

    public void setUrls(List<String> urls){
        this.urls = urls;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_layout, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(holder.posterImageView.getContext()).load(urls.get(position)).into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView posterImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            posterImageView = findById(itemView, R.id.poster);
        }
    }
}
