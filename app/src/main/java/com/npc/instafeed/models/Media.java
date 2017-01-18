package com.npc.instafeed.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by USER on 17/01/2017.
 */

public class Media implements Serializable {

    public class User {
        @Expose
        @SerializedName("profile_picture")
        public String profile_picture;
        @Expose
        @SerializedName("id")
        public String id;
        @Expose
        @SerializedName("username")
        public String username;
        @Expose
        @SerializedName("full_name")
        public String full_name;
    }

    public class Thumbnail {
        @Expose
        @SerializedName("width")
        public int width;
        @Expose
        @SerializedName("height")
        public int height;
        @Expose
        @SerializedName("url")
        public String url;
    }

    public class StandardResolution {
        @Expose
        @SerializedName("width")
        public int width;
        @Expose
        @SerializedName("height")
        public int height;
        @Expose
        @SerializedName("url")
        public String url;
    }

    public class LowResolution {
        @Expose
        @SerializedName("width")
        public int width;
        @Expose
        @SerializedName("height")
        public int height;
        @Expose
        @SerializedName("url")
        public String url;
    }

    public class Images {
        @Expose
        @SerializedName("thumbnail")
        public Thumbnail thumbnail;
        @Expose
        @SerializedName("standard_resolution")
        public StandardResolution standard_resolution;
        @Expose
        @SerializedName("low_resolution")
        public LowResolution low_resolution;
    }

    public class From {
        @Expose
        @SerializedName("profile_picture")
        public String profile_picture;
        @Expose
        @SerializedName("id")
        public String id;
        @Expose
        @SerializedName("username")
        public String username;
        @Expose
        @SerializedName("full_name")
        public String full_name;
    }

    public class Caption {
        @Expose
        @SerializedName("id")
        public String id;
        @Expose
        @SerializedName("created_time")
        public String created_time;
        @Expose
        @SerializedName("from")
        public From from;
        @Expose
        @SerializedName("text")
        public String text;
    }

    public class Comments {
        @Expose
        @SerializedName("count")
        public int count;
    }

    public class Likes {
        @Expose
        @SerializedName("count")
        public int count;
    }

    public class Videos {
        @Expose
        @SerializedName("low_bandwidth")
        public StandardResolution low_bandwidth;
        @Expose
        @SerializedName("standard_resolution")
        public StandardResolution standard_resolution;
        @Expose
        @SerializedName("low_resolution")
        public StandardResolution low_resolution;
    }

    public class Location {
        @Expose
        @SerializedName("longitude")
        public double longitude;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("latitude")
        public double latitude;
        @Expose
        @SerializedName("id")
        public int id;
    }
    @Expose
    @SerializedName("user")
    public User user;
    @Expose
    @SerializedName("tags")
    public List<Object> tags;
    @Expose
    @SerializedName("created_time")
    public String created_time;
    @Expose
    @SerializedName("link")
    public String link;
    @Expose
    @SerializedName("filter")
    public String filter;
    @Expose
    @SerializedName("images")
    public Images images;
    @Expose
    @SerializedName("videos")
    public Videos videos;
    @Expose
    @SerializedName("caption")
    public Caption caption;
    @Expose
    @SerializedName("users_in_photo")
    public List<Object> users_in_photo;
    @Expose
    @SerializedName("comments")
    public Comments comments;
    @Expose
    @SerializedName("type")
    public String type;
    @Expose
    @SerializedName("user_has_liked")
    public boolean user_has_liked;
    @Expose
    @SerializedName("id")
    public String id;
    @Expose
    @SerializedName("location")
    public Location location;
    @Expose
    @SerializedName("likes")
    public Likes likes;
}
