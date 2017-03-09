package com.github.w_kamil.movieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusAppCompatActivity<ListingPresenter> {

    private static final String SEARCH_TITLE = "search_title";
    private static final String SEARCH_YEAR = "search_year";
    public static final int NO_YEAR_SELECTED = -1;
    public static final String SEARCH_TYPE = "search_type";

    private MoviesListAdapter moviesListAdapter;

    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;

    @BindView(R.id.no_internet_image_view)
    ImageView noInteretImage;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.no_result)
    FrameLayout noResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        ButterKnife.bind(this);

        String title = getIntent().getStringExtra(SEARCH_TITLE);
        int year = getIntent().getIntExtra(SEARCH_YEAR, NO_YEAR_SELECTED);
        String type = getIntent().getStringExtra(SEARCH_TYPE);

        moviesListAdapter = new MoviesListAdapter();
        recyclerView.setAdapter(moviesListAdapter);

        getPresenter().getDataAsync(title, year, type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::success, this::error);
    }

    @OnClick(R.id.no_internet_image_view)
    public void onNoInternetImageViewClick(View view){
        Toast.makeText(this, "Kliknales obrazek", Toast.LENGTH_LONG).show();
    }

    private void error(Throwable throwable) {
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInteretImage));
    }

    private void success(SearchResult searchResult) {
        if("false".equalsIgnoreCase(searchResult.getResponse())) {
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noResult));
        } else{
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
            moviesListAdapter.setItems(searchResult.getItems());
        }
    }


    public static Intent createIntent(String title,  int year, String type, Context context) {
        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(SEARCH_TITLE, title);
        intent.putExtra(SEARCH_YEAR, year);
        intent.putExtra(SEARCH_TYPE, type);
        return intent;
    }

//    public void setDataOnUiThread(final SearchResult result, final boolean isProblemWithInternet) {
//        runOnUiThread(() -> {
//            if (isProblemWithInternet) {
//                viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInteretImage));
//            } else {
//                viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
//                moviesListAdapter.setItems(result.getItems());
//            }
//        });
//    }
}
