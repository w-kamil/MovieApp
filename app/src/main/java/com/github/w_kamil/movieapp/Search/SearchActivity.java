package com.github.w_kamil.movieapp.Search;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.w_kamil.movieapp.Detail.DetailActivity;
import com.github.w_kamil.movieapp.Listing.ListingActivity;
import com.github.w_kamil.movieapp.Listing.OnMovieItemClickListener;
import com.github.w_kamil.movieapp.R;
import com.github.w_kamil.movieapp.RetrfofitProvider;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity implements OnMovieItemClickListener {

    private static final String NUMBER_PICKER_STATE = "numerb_picker_state";

    private Map<Integer, String> apiKeysMap = new HashMap<Integer, String>() {{
        put(R.id.radio_movies, "movie");
        put(R.id.radio_series, "series");
        put(R.id.radio_games, "game");
    }};

    @BindView(R.id.year_number_picker)
    NumberPicker yearNumberPicker;

    @BindView(R.id.search_button)
    ImageButton searchButton;

    @BindView(R.id.edit_text)
    TextInputEditText searchInputText;

    @BindView(R.id.year_checkbox)
    CheckBox yearCheckBox;

    @BindView(R.id.type_checkbox)
    CheckBox typeCheckBox;

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @BindView(R.id.poster_header)
    RecyclerView posterHeadrRecyclerView;
    private PosterRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        yearNumberPicker.setMinValue(1950);
        yearNumberPicker.setMaxValue(year);
        yearNumberPicker.setValue(year);
        yearNumberPicker.setWrapSelectorWheel(true);

        searchInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onSearchButtonClick();
                }
                return false;
            }
        });

        adapter = new PosterRecyclerViewAdapter();
        posterHeadrRecyclerView.setAdapter(adapter);
        posterHeadrRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RetrfofitProvider retrfofitProvider = (RetrfofitProvider) getApplication();
        Retrofit retrofit = retrfofitProvider.provideRetrofit();
        SearchService searchService = retrofit.create(SearchService.class);
        searchService.search(1, "a*", "2016", null)
                .flatMap(searchResult -> Observable.fromIterable(searchResult.getItems()))
                .map(movieListingItem -> new SimpleMovieItem(movieListingItem.getPoster(), movieListingItem.getImdbID()))
                .filter(simpleMovieItem -> !"N/A".equalsIgnoreCase(simpleMovieItem.getPoster()))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).toList()
                .subscribe(this::success, this::error);

        adapter.setOnMovieItemClickListener(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NUMBER_PICKER_STATE, yearNumberPicker.getValue());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        yearNumberPicker.setValue(savedInstanceState.getInt(NUMBER_PICKER_STATE));
    }

    private void success(List<SimpleMovieItem> list) {
        adapter.setSimpleMovieItems(list);
    }

    private void error(Throwable throwable) {

    }

    @OnCheckedChanged(R.id.year_checkbox)
    void onYearCheckBoxStateChanged(CompoundButton buttonView, boolean isChecked) {
        yearNumberPicker.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
    }

    @OnCheckedChanged(R.id.type_checkbox)
    void onTyoeCheckBoxStateChanged(CompoundButton buttonView, boolean isChecked) {
        radioGroup.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
    }

    @OnClick(R.id.search_button)
    void onSearchButtonClick() {
        int checkedRadio = radioGroup.getCheckedRadioButtonId();
        String typeKey = typeCheckBox.isChecked() ? apiKeysMap.get(checkedRadio) : null;
        int year = yearCheckBox.isChecked() ? yearNumberPicker.getValue() : ListingActivity.NO_YEAR_SELECTED;
        startActivity(ListingActivity.createIntent(searchInputText.getText().toString(), year, typeKey, SearchActivity.this));
    }

    @Override
    public void onMovieItemClick(String imdbID) {
        startActivity(DetailActivity.createIntent(this, imdbID));
    }
}
