package com.github.w_kamil.movieapp;
//
//import com.google.gson.Gson;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Scanner;

import io.reactivex.Observable;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListingPresenter extends Presenter<ListingActivity> {

    private Retrofit retrofit;

    public ListingPresenter() {
        retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).baseUrl("http://www.omdbapi.com").build();
    }


    public Observable<SearchResult> getDataAsync(final String title, int year, String type) {

        String stringYear = year == ListingActivity.NO_YEAR_SELECTED ? null : String.valueOf(year);
        return retrofit.create(SearchService.class).search(title, stringYear, type);


//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    String result = getData(title);
//                    SearchResult searchResult = new Gson().fromJson(result, SearchResult.class);
//                    getView().setDataOnUiThread(searchResult, false);
//                } catch (IOException e) {
//                    getView().setDataOnUiThread(null, true);
//                    e.printStackTrace();
//                }
//            }
//        }.start();

    }
//
//    private String getData(String title) throws IOException {
//        String stringUrl = "http://www.omdbapi.com/?s=" + title;
//        URL url = new URL(stringUrl);
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        urlConnection.setConnectTimeout(3000);
//        InputStream inputStream = urlConnection.getInputStream();
//        return convertStreamToString(inputStream);
//    }
//
//    private String convertStreamToString(InputStream is) {
//        Scanner scanner = new Scanner(is).useDelimiter("\\A");
//        return scanner.hasNext() ? scanner.next() : "";
//
//    }

}
