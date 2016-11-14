/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movienow.entityclasses;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Onder
 */
public class Movie {

    String title;
    String year;
    String rating;
    String runtime;
    String release_date;
    String meta_score;
    String imdb_score;
    String synopsis;
    String imageUrl;
    List<String> cast = new ArrayList<>();

    public Movie(String title, String year, String rating, String runtime, String release_date, String meta_score, String imdb_score, String synopsis, String imageUrl, List<String> cast) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.runtime = runtime;
        this.release_date = release_date;
        this.meta_score = meta_score;
        this.imdb_score = imdb_score;
        this.synopsis = synopsis;
        this.imageUrl = imageUrl;
        this.cast = cast;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getCast() {
        return cast;
    }

    public String getMeta_score() {
        return meta_score;
    }

    public String getImdb_score() {
        return imdb_score;
    }
    
    

}
