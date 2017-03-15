package com.github.w_kamil.movieapp.Search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.w_kamil.movieapp.Listing.OnMovieItemClickListener;
import com.github.w_kamil.movieapp.R;

import java.util.Collections;
import java.util.List;


import static butterknife.ButterKnife.*;




public class PosterRecyclerViewAdapter extends RecyclerView.Adapter<PosterRecyclerViewAdapter.ViewHolder> {


    private List<SimpleMovieItem> simpleMowieItems = Collections.emptyList();
    private OnMovieItemClickListener onMovieItemClickListener;

    public void setOnMovieItemClickListener(OnMovieItemClickListener onMovieItemClickListener) {
        this.onMovieItemClickListener = onMovieItemClickListener;
    }

    public void setSimpleMovieItems(List<SimpleMovieItem> simpleMovieItems){
        this.simpleMowieItems = simpleMovieItems;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_layout, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(holder.posterImageView.getContext()).load(simpleMowieItems.get(position).getPoster()).into(holder.posterImageView);

        holder.posterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onMovieItemClickListener != null){
                    onMovieItemClickListener.onMovieItemClick(simpleMowieItems.get(position).getImdbID());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return simpleMowieItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView posterImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            posterImageView = findById(itemView, R.id.poster);
        }
    }
}
