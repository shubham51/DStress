package com.example.ritam.dstress;

/**
 * Created by shubham on 31/1/18.
 */

public class CustomPost {
    private String title;
    private String image_url;

    public CustomPost(String title, String url){
        this.title=title;
        this.image_url=url;
    }
    public String getTitle(){
        return this.title;
    }
    public String getImageUrl(){
        return this.image_url;
    }
}
