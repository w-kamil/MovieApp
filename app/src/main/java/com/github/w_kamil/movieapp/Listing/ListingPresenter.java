package com.github.w_kamil.movieapp.Listing;


import android.widget.Toast;

import com.github.w_kamil.movieapp.Search.SearchResult;
import com.github.w_kamil.movieapp.Search.SearchService;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;


public class ListingPresenter extends Presenter<ListingActivity> implements OnLoadNextPageListener {

    private SearchResult searchResultOfAllItems;

    private Retrofit retrofit;
    private String title;
    private String stringYear;
    private String type;

    public ListingPresenter() {

    }


    public Observable<SearchResult> getDataAsync(final String title, int year, String type) {
        this.title = title;
        this.type = type;
        stringYear = year == ListingActivity.NO_YEAR_SELECTED ? null : String.valueOf(year);

        return retrofit.create(SearchService.class).search(1, title, stringYear, type);


    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }


    @Override
    public void loadNextPage(int page) {
        retrofit.create(SearchService.class).search(page, title, stringYear, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResult -> {
                    getView().appendItems(searchResult);
                }, throwable -> {
                    Toast.makeText(getView(), "Brak dostępu do sieci", Toast.LENGTH_SHORT).show();
                });
    }
}
