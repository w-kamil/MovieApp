package com.github.w_kamil.movieapp;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class SearchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        ImageButton searchButton = (ImageButton) findViewById(R.id.search_button);
        final TextInputEditText searchInputText = (TextInputEditText) findViewById(R.id.edit_text);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ListingActivity.createIntent(searchInputText.getText().toString(), SearchActivity.this));


            }
        });


    }


}
