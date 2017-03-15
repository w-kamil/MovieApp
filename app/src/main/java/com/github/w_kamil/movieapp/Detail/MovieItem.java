package com.github.w_kamil.movieapp.Detail;


import com.annimon.stream.Objects;
import com.google.gson.annotations.SerializedName;


public class MovieItem {
    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Rated")
    private String rated;

    @SerializedName("Runtime")
    private String runtime;

    @SerializedName("Director")
    private String director;

    @SerializedName("Actors")
    private String actors;

    @SerializedName("Plot")
    private String plot;

    @SerializedName("Awards")
    private String awards;

    @SerializedName("Poster")
    private String poster;

    private String imdbRating;

    @SerializedName("Type")
    private String type;

    @SerializedName("Response")
    private String response;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieItem movieItem = (MovieItem) o;
        return Objects.equals(title, movieItem.title) &&
                Objects.equals(year, movieItem.year) &&
                Objects.equals(rated, movieItem.rated) &&
                Objects.equals(runtime, movieItem.runtime) &&
                Objects.equals(director, movieItem.director) &&
                Objects.equals(actors, movieItem.actors) &&
                Objects.equals(plot, movieItem.plot) &&
                Objects.equals(awards, movieItem.awards) &&
                Objects.equals(poster, movieItem.poster) &&
                Objects.equals(imdbRating, movieItem.imdbRating) &&
                Objects.equals(type, movieItem.type) &&
                Objects.equals(response, movieItem.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, rated, runtime, director, actors, plot, awards, poster, imdbRating, type, response);
    }

    public String getTitle() {

        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRated() {
        return rated;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getAwards() {
        return awards;
    }

    public String getPoster() {
        return poster;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getType() {
        return type;
    }

    public String getResponse() {
        return response;
    }
}
