/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movienow.managers;

import com.movienow.entityclasses.Movie;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Onder
 */
@ManagedBean
@SessionScoped
@Named(value = "movieManager")
public class MovieManager implements Serializable {

    //Restful Requests Base URL
    private final String baseUrl = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=g9qcds978gjpn6a3k78zzj7m&page_limit=50";
    private String baseUrl2 = "http://www.omdbapi.com/?t=";
    Movie newMovie;
    Movie[] movies = new Movie[50];
    Movie displayedMovie;

    private String title;
    private int year;
    private String rating;
    private String runtime;
    private String release_date;
    private int critics_score;
    private int audience_score;
    private String synopsis;
    private String imageUrl;
    ArrayList<String> cast = new ArrayList<String>();

    JSONArray jsonCast;
    String omdbRequestUrl = "";

    String content = "";
    String content2 = "";
    JSONObject jsonResult;
    JSONObject jsonResult2;
    JSONArray movieContent;
    JSONObject jsonMovie;

    /**
     * This function initializes the bean for all buildings functionality and
     * should be called whenever the page is navigated to.
     */
    @PostConstruct
    public void init() {

        try {

            jsonResult = new JSONObject(readUrlContent(baseUrl));

            movieContent = jsonResult.getJSONArray("movies");

            for (int i = 0; i < movieContent.length(); i++) {
                jsonMovie = movieContent.getJSONObject(i);

                title = jsonMovie.get("title").toString();
                year = jsonMovie.getInt("year");
                rating = jsonMovie.get("mpaa_rating").toString();
                runtime = jsonMovie.get("runtime").toString();
                release_date = jsonMovie.getJSONObject("release_dates").get("theater").toString();
                critics_score = jsonMovie.getJSONObject("ratings").getInt("critics_score");
                audience_score = jsonMovie.getJSONObject("ratings").getInt("audience_score");
                synopsis = jsonMovie.get("synopsis").toString();
                imageUrl = jsonMovie.getJSONObject("posters").get("original").toString();

                jsonCast = jsonMovie.getJSONArray("abridged_cast");

                cast = new ArrayList<String>();
                //If there is no cast give add, cast info
                if (jsonCast.length() == 0) {
                    cast.add("No Cast For This Movie");
                    cast.add("");
                }
                for (int j = 0; j < jsonCast.length(); j++) {

                    cast.add(jsonCast.getJSONObject(j).get("name").toString());

                }

                //Create Another JSON REQUEST URL for Images
                //Make the json structure as OMDB requires
                omdbRequestUrl = baseUrl2 + title;
                omdbRequestUrl = omdbRequestUrl.replace(" ", "+");
                omdbRequestUrl = omdbRequestUrl + "&y=&plot=short&r=json";
                
                //Read the content from created url and create JSon Object
                jsonResult2 = new JSONObject(readUrlContent(omdbRequestUrl));
                
                //If Returned JSON has a Key Name is Poster (Do it only for first 20 items)
                if (jsonResult2.has("Poster") && i< 20) {
                    String posterUrl= jsonResult2.get("Poster").toString();
                    //If Image Url Is Equal to N/A do not change the image url which is gotten from previous API 
                    if(!posterUrl.equals("N/A")){
                        imageUrl = posterUrl;
                    }
                    
                }

                newMovie = new Movie(title, year, rating, runtime, release_date, critics_score, audience_score, synopsis, imageUrl, cast);
                movies[i] = newMovie;

            }

        } catch (Exception ex) {
            Logger.getLogger(MovieManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method Returns JSON data as String from Given URL
     *
     * @param urlString the url to request data from
     * @return the JSON data from the given URL
     * @throws Exception
     */
    public String readUrlContent(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();

            int read;
            char[] chars = new char[10240];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }
            return buffer.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public String getContent2() {
        return content2;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Movie[] getMovies() {
        return movies;
    }

    public Movie getDisplayedMovie() {
        return displayedMovie;
    }

    public String displayMovie(Movie movie) {

        displayedMovie = movie;

        return "movieView?faces-redirect=true";
    }

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

}
