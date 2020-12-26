package com.example.foodsharing1;

public class FoodPost {
    String postid;
    String foodname;
    String foodloc;
    String imageurl;
    String userid;
    String fooddesc;

    public FoodPost(String postid, String foodName, String postLocation, String imageUrl,String userid,String fooddesc) {
        this.postid = postid;
        this.foodname = foodName;
        this.foodloc = postLocation;
        this.imageurl = imageUrl;
        this.userid=userid;
        this.fooddesc=fooddesc;
    }
    public FoodPost(){

    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public void setFoodName(String foodName) {
        this.foodname = foodName;
    }

    public void setPostLocation(String postLocation) {
        this.foodloc = postLocation;
    }

    public void setImageUrl(String imageUrl) {
        this.imageurl = imageUrl;
    }

    public String getPostid() {
        return postid;
    }

    public String getFoodName() {
        return foodname;
    }

    public String getPostLocation() {
        return foodloc;
    }

    public String getImageUrl() {
        return imageurl;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setFooddesc(String fooddesc) {
        this.fooddesc = fooddesc;
    }

    public String getFooddesc() {
        return fooddesc;
    }
}
