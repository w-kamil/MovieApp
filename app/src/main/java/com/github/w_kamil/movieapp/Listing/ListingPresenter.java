package com.github.w_kamil.movieapp.Listing;


import com.github.w_kamil.movieapp.Search.SearchResult;
import com.github.w_kamil.movieapp.Search.SearchService;

import io.reactivex.Observable;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;


public class ListingPresenter extends Presenter<ListingActivity> {

    private Retrofit retrofit;

    public ListingPresenter() {

    }


    public Observable<SearchResult> getDataAsync(final String title, int year, String type) {

        String stringYear = year == ListingActivity.NO_YEAR_SELECTED ? null : String.valueOf(year);
        return retrofit.create(SearchService.class).search(title, stringYear, type);


    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }


}
