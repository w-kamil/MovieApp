package com.github.w_kamil.movieapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Frod_ on 07.03.2017.
 */

public class SearchResult {


    @SerializedName("Search")
    private List<MovieListingItem> items;

    private String totalResults;
    @SerializedName("Response")
    private String Response;

    public List<MovieListingItem> getItems() {
        return items;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getResponse() {
        return Response;
    }
}
