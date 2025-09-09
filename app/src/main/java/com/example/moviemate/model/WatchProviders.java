// File: app/src/main/java/com/example/moviemate/model/WatchProviders.java
package com.example.moviemate.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class WatchProviders {
    @SerializedName("results")
    private Map<String, CountryProviders> results;

    public Map<String, CountryProviders> getResults() {
        return results;
    }

    public static class CountryProviders {
        @SerializedName("link")
        private String link;
        @SerializedName("flatrate") // Subscription
        private List<Provider> flatrate;
        @SerializedName("buy")
        private List<Provider> buy;

        public String getLink() { return link; }
        public List<Provider> getFlatrate() { return flatrate; }
        public List<Provider> getBuy() { return buy; }
    }

    public static class Provider {
        @SerializedName("logo_path")
        private String logoPath;
        @SerializedName("provider_name")
        private String providerName;

        public String getLogoPath() { return logoPath; }
        public String getProviderName() { return providerName; }
    }
}