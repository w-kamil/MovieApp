package com.github.w_kamil.movieapp.Listing;

import com.github.w_kamil.movieapp.Detail.MovieItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frod_ on 15.03.2017.
 */

public class ResultAggregator {

    private List<MovieListingItem> movieItems = new ArrayList<>();
    private int totalItemsResult;
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setTotalItemsResult(int totalItemsResult) {
        this.totalItemsResult = totalItemsResult;
    }

    public void addNewItems(List<MovieListingItem> newItems){
        movieItems.addAll(newItems);
    }

    public List<MovieListingItem> getMovieItems() {
        return movieItems;
    }

    public int getTotalItemsResult() {
        return totalItemsResult;
    }
}
