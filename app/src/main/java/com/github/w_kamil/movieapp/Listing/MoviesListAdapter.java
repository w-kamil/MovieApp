package com.github.w_kamil.movieapp.Listing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.w_kamil.movieapp.R;

import java.util.Collections;
import java.util.List;


public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MyViewHolder> {

    private List<MovieListingItem> items = Collections.emptyList();
    private OnMovieItemClickListener onMovieItemClickListener;


    public void setOnMovieItemClickListener(OnMovieItemClickListener onMovieItemClickListener) {
        this.onMovieItemClickListener = onMovieItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);

        return new MyViewHolder(layout);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void setItems(List<MovieListingItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieListingItem movieListingItem = items.get(position);
        Glide.with(holder.poster.getContext()).load(movieListingItem.getPoster()).into(holder.poster);
        holder.titleAndYear.setText(movieListingItem.getTitle() + " (" + movieListingItem.getYear() + ")");
        holder.type.setText("Typ: " + movieListingItem.getType());
        holder.itemView.setOnClickListener(v -> {
            if(onMovieItemClickListener != null){
                onMovieItemClickListener.onMovieItemClick(movieListingItem.getImdbID());
            }
        });
    }

    public void addItems(List<MovieListingItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView poster;
        TextView titleAndYear;
        TextView type;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            poster = (ImageView) itemView.findViewById(R.id.poster_view);
            titleAndYear = (TextView) itemView.findViewById(R.id.tile_and_year_text_view);
            type = (TextView) itemView.findViewById(R.id.type_text_view);
        }
    }

}
