package com.github.w_kamil.movieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusAppCompatActivity<ListingPresenter> {

    private static final String SEARCH_TITLE = "search_title";

    private MoviesListAdapter moviesListAdapter;
    private ViewFlipper viewFlipper;
    private ImageView noInteretImage;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        String title = getIntent().getStringExtra(SEARCH_TITLE);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        moviesListAdapter = new MoviesListAdapter();
        recyclerView.setAdapter(moviesListAdapter);
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        noInteretImage = (ImageView) findViewById(R.id.no_internet_image_view);
        getPresenter().getDataAsync(title);


    }


    public static Intent createIntent(String title, Context context) {
        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(SEARCH_TITLE, title);
        return intent;
    }

    public void setDataOnUiThread(final SearchResult result, final boolean isProblemWithInternet) {
        runOnUiThread(() -> {
            if (isProblemWithInternet) {
                viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInteretImage));
            } else {
                viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
                moviesListAdapter.setItems(result.getItems());
            }
        });
    }
}
