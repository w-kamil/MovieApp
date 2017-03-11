package com.github.w_kamil.movieapp.Search;

import com.github.w_kamil.movieapp.Listing.MovieListingItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SearchResult {


    @SerializedName("Search")
    private List<MovieListingItem> items;

    private String totalResults;
    @SerializedName("Response")
    private String response;

    public List<MovieListingItem> getItems() {
        return items;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setItems(List<MovieListingItem> items) {
        this.items = items;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }
}
