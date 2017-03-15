package com.github.w_kamil.movieapp.Search;


public class SimpleMovieItem {

    private String poster;
    private String imdbID;

    public SimpleMovieItem(String poster, String imdbID) {
        this.poster = poster;
        this.imdbID = imdbID;
    }

    public String getPoster() {
        return poster;
    }

    public String getImdbID() {
        return imdbID;
    }
}
