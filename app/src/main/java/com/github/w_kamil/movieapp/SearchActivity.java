package com.github.w_kamil.movieapp;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

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

}
