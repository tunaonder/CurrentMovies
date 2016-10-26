/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movienow.managers;

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
    
    Movie newMovie;
    Movie [] movies = new Movie[50];
    
    private String title;
    private int year;
    private String rating;
    private String runtime;
    private String release_date;
    private int critics_score;
    private int audience_score;
    private String synopsis;
    private String imageUrl;
    private ArrayList<String> cast = new ArrayList<String>();
    
    JSONArray jsonCast;
    
    String content = "";
    String content2 = "";
    JSONObject jsonResult;
    JSONArray movieContent;
    JSONObject jsonMovie;
  
        /**
     * This function initializes the bean for all buildings functionality
     * and should be called whenever the page is navigated to.
     */
    @PostConstruct
    public void init() {

        try {
            
            
            jsonResult = new JSONObject(readUrlContent(baseUrl));
            
            movieContent = jsonResult.getJSONArray("movies");
            
            
            for(int i = 0; i<movieContent.length(); i++){
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
                for(int j=0; j<jsonCast.length(); j++){
                    cast.add(jsonCast.getJSONObject(j).get("name").toString());                 
                    
                }
                
                newMovie = new Movie(title,year, rating, runtime, release_date, critics_score, audience_score, synopsis, imageUrl, cast);
                movies[i] = newMovie;
                
                
                
            }
//            jsonMovie = movieContent.getJSONObject(3);
//            
//            JSONObject poster = jsonMovie.getJSONObject("posters");
//            content2 = poster.get("original").toString();
//            imageUrl = poster.get("thumbnail").toString();
            
//           content2 = poster.getJSONObject("original").toString();
            
            
            
//            JSONObject photos = movie.getJSONObject("posters");
//            imageUrl = (String) photos.get("thumbnail");
//            
//            content = jsonResult.toString();
            
        } catch (Exception ex) {
            Logger.getLogger(MovieManager.class.getName()).log(Level.SEVERE, null, ex);
        }

      

    }


    /**
     * This method Returns JSON data as String from Given URL
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Movie[] getMovies() {
        return movies;
    }
    
    
    
    
    
    
   
}

