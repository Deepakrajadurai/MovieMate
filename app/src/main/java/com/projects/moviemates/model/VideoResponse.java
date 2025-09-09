// File: app/src/main/java/com/example/moviemate/model/VideoResponse.java
package com.projects.moviemates.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class VideoResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<Video> results;

    public List<Video> getResults() {
        return results;
    }

    public static class Video {
        @SerializedName("key")
        private String key; // YouTube key
        @SerializedName("site")
        private String site;
        @SerializedName("type")
        private String type; // e.g., "Trailer"

        public String getKey() { return key; }
        public String getSite() { return site; }
        public String getType() { return type; }
    }
}