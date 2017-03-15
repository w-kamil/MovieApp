package com.github.w_kamil.movieapp.Detail;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface DetailService {

    @GET("/")
    Observable<MovieItem> getDetailInfo(@Query("i") String id);

}
