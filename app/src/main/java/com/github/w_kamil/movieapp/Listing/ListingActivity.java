package com.github.w_kamil.movieapp.Listing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.github.w_kamil.movieapp.R;
import com.github.w_kamil.movieapp.RetrfofitProvider;
import com.github.w_kamil.movieapp.Search.SearchResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusAppCompatActivity<ListingPresenter> {

    private static final String SEARCH_TITLE = "search_title";
    private static final String SEARCH_YEAR = "search_year";
    public static final int NO_YEAR_SELECTED = -1;
    public static final String SEARCH_TYPE = "search_type";
    private LinearLayoutManager layoutManager;

    private MoviesListAdapter moviesListAdapter;

    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;

    @BindView(R.id.no_internet_image_view)
    ImageView noInteretImage;

    @BindView(R.id.results_view)
    FrameLayout resultsView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.counter_textview)
    TextView counterTextView;

    @BindView(R.id.no_result)
    FrameLayout noResult;

    private EndlessScrollListener endlessScrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            RetrfofitProvider retrfofitProvider = (RetrfofitProvider) getApplication();
            getPresenter().setRetrofit(retrfofitProvider.provideRetrofit());
        }

        String title = getIntent().getStringExtra(SEARCH_TITLE);
        int year = getIntent().getIntExtra(SEARCH_YEAR, NO_YEAR_SELECTED);
        String type = getIntent().getStringExtra(SEARCH_TYPE);

        moviesListAdapter = new MoviesListAdapter();
        recyclerView.setAdapter(moviesListAdapter);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        endlessScrollListener = new EndlessScrollListener(layoutManager, getPresenter());
        recyclerView.addOnScrollListener(endlessScrollListener);

        getPresenter().getDataAsync(title, year, type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::success, this::error);
    }

    @OnClick(R.id.no_internet_image_view)
    public void onNoInternetImageViewClick(View view) {
        Toast.makeText(this, "Kliknales obrazek", Toast.LENGTH_LONG).show();
    }

    private void error(Throwable throwable) {
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInteretImage));

    }

    public void appendItems(SearchResult searchResult){
        moviesListAdapter.addItems(searchResult.getItems());
        endlessScrollListener.setTotalItemNumber(Integer.parseInt(searchResult.getTotalResults()));
    }

    private void success(SearchResult searchResult) {
        if ("false".equalsIgnoreCase(searchResult.getResponse())) {
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noResult));
        } else {
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(resultsView));
            moviesListAdapter.setItems(searchResult.getItems());
            endlessScrollListener.setTotalItemNumber(Integer.parseInt(searchResult.getTotalResults()));
        }
    }


    public static Intent createIntent(String title, int year, String type, Context context) {
        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(SEARCH_TITLE, title);
        intent.putExtra(SEARCH_YEAR, year);
        intent.putExtra(SEARCH_TYPE, type);
        return intent;
    }

}
