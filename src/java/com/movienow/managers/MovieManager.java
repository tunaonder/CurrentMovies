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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Onder
 */
@ManagedBean
@SessionScoped
@Named(value = "movieManager")
public class MovieManager implements Serializable {

    //OMDB API BASE URL
    private final String baseUrl2 = "http://www.omdbapi.com/?t=";

    private final String moviesUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=dd424332d7b9f6b37f3aeaab413fbca7";

    Movie newMovie;
    Movie[] movies;
    Movie displayedMovie;

    private String title;
    private String year;
    private String rating;
    private String runtime;
    private String release_date;
    private String meta_score;
    private String imdb_score;
    private String synopsis;
    private String imageUrl;
    private String movieCast;
    List<String> cast = new ArrayList<>();

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
            jsonResult = new JSONObject(readUrlContent(moviesUrl));
            JSONArray movieContent = jsonResult.getJSONArray("results");
            
            
            int count = movieContent.length();
           
            movies = new Movie[count];            
            for (int i = 0; i < count; i++) {
                jsonMovie = movieContent.getJSONObject(i);
                
                
                title = jsonMovie.get("title").toString();

                //Create Another JSON REQUEST URL for Images
                //Make the json structure as OMDB requires
                omdbRequestUrl = baseUrl2 + title;
                omdbRequestUrl = omdbRequestUrl.replace(" ", "+");
                omdbRequestUrl = omdbRequestUrl + "&y=&plot=short&r=json";

                //Read the content from created url and create JSon Object
                jsonResult2 = new JSONObject(readUrlContent(omdbRequestUrl));

                if (jsonResult2.has("Year")) {
                    year = jsonResult2.get("Year").toString();
                } else {
                    year = "N/A";
                }
                if (jsonResult2.has("Rated")) {
                    rating = jsonResult2.get("Rated").toString();
                } else {
                    rating = "N/A";
                }
                if (jsonResult2.has("Runtime")) {
                    runtime = jsonResult2.get("Runtime").toString();
                } else {
                    runtime = "N/A";
                }
                if (jsonResult2.has("Metascore")) {
                    meta_score = jsonResult2.get("Metascore").toString();
                } else {
                    meta_score = "N/A";
                }
                if (jsonResult2.has("imdbRating")) {
                    imdb_score = jsonResult2.get("imdbRating").toString();
                } else {
                    imdb_score = "N/A";
                }
                if (jsonResult2.has("Release")) {
                    release_date = jsonResult2.get("Release").toString();
                } else {
                    release_date = "N/A";
                }
                if (jsonResult2.has("Plot")) {
                    synopsis = jsonResult2.get("Plot").toString();
                    if(synopsis.equals("N/A")){
                        synopsis = jsonMovie.get("overview").toString();
                    }
                } else {
                    synopsis = "N/A";
                }
                if (jsonResult2.has("Actors")) {
                    movieCast = jsonResult2.get("Actors").toString();
                    String[] actors = movieCast.split(",");
                    cast = Arrays.asList(actors);
                    if(cast.isEmpty()){
                        cast = new ArrayList<>();
                        cast.add("N/A");
                        cast.add("N/A");
                    }
                    else if(cast.size() == 1){
                        String temp = cast.get(0);
                        cast = new ArrayList<>();
                        cast.add(temp);
                        cast.add("N/A");
                    }
                    
                }
                
               
                //If Returned JSON has a Key Name Poster
                if (jsonResult2.has("Poster")) {
                    String posterUrl = jsonResult2.get("Poster").toString();
                    //If Image Url Is Equal to N/A do not change the image url which is gotten from previous API 
                    if (!posterUrl.equals("N/A")) {
                        imageUrl = posterUrl;
                    } else {
                        imageUrl = "";
                    }

                }

                newMovie = new Movie(title, year, rating, runtime, release_date, meta_score, imdb_score, synopsis, imageUrl, cast);
                movies[i] = newMovie;

            }
            displayedMovie = newMovie;

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

        return "movieView.xhtml?faces-redirect=true";
    }

}
