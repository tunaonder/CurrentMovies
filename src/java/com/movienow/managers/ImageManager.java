package com.movienow.managers;
 
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
 
@ManagedBean
public class ImageManager {
     
    private List<String> images;
     
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            images.add("movie" + i + ".jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
    
    public String movieSelected(){
        System.out.println("asdasd");
        return "movieView.xhtml?faces-redirect=true";
    }
}