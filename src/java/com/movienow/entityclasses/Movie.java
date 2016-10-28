/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movienow.entityclasses;

import java.util.ArrayList;

/**
 *
 * @author Onder
 */
public class Movie {

    String title;
    int year;
    String rating;
    String runtime;
    String release_date;
    int critics_score;
    int audience_score;
    String synopsis;
    String imageUrl;
    ArrayList<String> cast = new ArrayList<>();

    public Movie(String title, int year, String rating, String runtime, String release_date, int critics_score, int audience_score, String synopsis, String imageUrl, ArrayList<String> cast) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.runtime = runtime;
        this.release_date = release_date;
        this.critics_score = critics_score;
        this.audience_score = audience_score;
        this.synopsis = synopsis;
        this.imageUrl = imageUrl;
        this.cast = cast;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
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

    public int getCritics_score() {
        return critics_score;
    }

    public int getAudience_score() {
        return audience_score;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

}
