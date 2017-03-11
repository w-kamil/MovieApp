package com.github.w_kamil.movieapp.Listing;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.Collection;
import java.util.Collections;


public class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager layoutManager ;
    static final double PAGE_SZIE = 10;
    private boolean isLoading;
    private int totalItemNumber;
    private OnLoadNextPageListener listener;

    public void setTotalItemNumber(int totalItemNumber) {
        this.totalItemNumber = totalItemNumber;
        isLoading = false;
    }


    public EndlessScrollListener(LinearLayoutManager layoutManager, OnLoadNextPageListener listener) {
        this.layoutManager = layoutManager;
        this.listener = listener;

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int alreadyLoadad = layoutManager.getItemCount();
        int currentPage = (int) Math.ceil(alreadyLoadad/PAGE_SZIE);
        double numberOfAllPages = Math.ceil(totalItemNumber / PAGE_SZIE);
        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition() + 1;

        if (currentPage < numberOfAllPages && lastVisiblePosition == alreadyLoadad && !isLoading) {
            loadNextPage(++currentPage);
            isLoading = true;
        }
        Log.d("result", "strona" + lastVisiblePosition);

    }

    private void loadNextPage(int pageNumber) {
        listener.loadNextPage(pageNumber);
    }

}
