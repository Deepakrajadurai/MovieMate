package com.projects.moviemates.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieDetail extends Movie {
    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("runtime")
    private int runtime; // in minutes

    public List<Genre> getGenres() { return genres; }
    public int getRuntime() { return runtime; }

    // --- Parcelable Implementation for the Child Class ---

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 1. MUST call the super method first to write the parent's fields
        super.writeToParcel(dest, flags);
        // 2. Now, write the fields that are unique to this child class
        dest.writeTypedList(genres);
        dest.writeInt(runtime);
    }

    protected MovieDetail(Parcel in) {
        // 1. MUST call the super constructor first to read the parent's fields
        super(in);
        // 2. Now, read the fields that are unique to this child class
        genres = in.createTypedArrayList(Genre.CREATOR);
        runtime = in.readInt();
    }

    public static final Creator<MovieDetail> CREATOR = new Creator<MovieDetail>() {
        @Override
        public MovieDetail createFromParcel(Parcel in) {
            return new MovieDetail(in);
        }

        @Override
        public MovieDetail[] newArray(int size) {
            return new MovieDetail[size];
        }
    };


    // --- The nested Genre class MUST also be Parcelable ---
    public static class Genre implements Parcelable {
        @SerializedName("name")
        private String name;

        public String getName() { return name; }

        protected Genre(Parcel in) {
            name = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Genre> CREATOR = new Creator<Genre>() {
            @Override
            public Genre createFromParcel(Parcel in) {
                return new Genre(in);
            }

            @Override
            public Genre[] newArray(int size) {
                return new Genre[size];
            }
        };
    }
}