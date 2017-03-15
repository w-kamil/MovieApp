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


public class MoviesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int GAMES_VIEW_HOLDER = 1;
    private static final int MY_VIEW_HOLDER = 2;

    private List<MovieListingItem> items = Collections.emptyList();
    private OnMovieItemClickListener onMovieItemClickListener;


    public void setOnMovieItemClickListener(OnMovieItemClickListener onMovieItemClickListener) {
        this.onMovieItemClickListener = onMovieItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == GAMES_VIEW_HOLDER) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item, parent, false);
            return new GamesViewHolder(layout);
        } else if (viewType == MY_VIEW_HOLDER) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
            return new MyViewHolder(layout);
        }
        return null;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieListingItem movieListingItem = items.get(position);
        if (getItemViewType(position) == MY_VIEW_HOLDER) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;

            Glide.with(myViewHolder.poster.getContext()).load(movieListingItem.getPoster()).into(myViewHolder.poster);
            myViewHolder.titleAndYear.setText(movieListingItem.getTitle() + " (" + movieListingItem.getYear() + ")");
            myViewHolder.type.setText("Typ: " + movieListingItem.getType());
            myViewHolder.itemView.setOnClickListener(v -> {
                if (onMovieItemClickListener != null) {
                    onMovieItemClickListener.onMovieItemClick(movieListingItem.getImdbID());
                }
            });
        } else {
            GamesViewHolder gamesViewHolder = (GamesViewHolder) holder;
            Glide.with(gamesViewHolder.poster.getContext()).load(movieListingItem.getPoster()).into(gamesViewHolder.poster);
            gamesViewHolder.title.setText(movieListingItem.getTitle());
            gamesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMovieItemClickListener != null) {
                        onMovieItemClickListener.onMovieItemClick(movieListingItem.getImdbID());
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return "Game".equalsIgnoreCase(items.get(position).getType()) ? GAMES_VIEW_HOLDER : MY_VIEW_HOLDER;
    }

    public void addItems(List<MovieListingItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }


    class GamesViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;
        TextView title;

        public GamesViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.game_poster);
            title = (TextView) itemView.findViewById(R.id.game_title);

        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;
        TextView titleAndYear;
        TextView type;

        public MyViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster_view);
            titleAndYear = (TextView) itemView.findViewById(R.id.tile_and_year_text_view);
            type = (TextView) itemView.findViewById(R.id.type_text_view);
        }
    }

}
