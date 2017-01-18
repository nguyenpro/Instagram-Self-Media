package com.npc.instafeed.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by USER on 17/01/2017.
 */

public class MediaResponse implements Serializable {
    public class Pagination {
        @Expose
        @SerializedName("next_max_id")
        public String next_max_id;
        @Expose
        @SerializedName("next_url")
        public String next_url;
    }

    public class Meta {
        @Expose
        @SerializedName("code")
        public int code;
    }
    @Expose
    @SerializedName("data")
    public List<Media > data;
    @Expose
    @SerializedName("pagination")
    public Pagination pagination;
    @Expose
    @SerializedName("meta")
    public Meta meta;
}
