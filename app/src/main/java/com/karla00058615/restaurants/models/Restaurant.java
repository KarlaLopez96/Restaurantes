package com.karla00058615.restaurants.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Karla on 13/5/2018.
 */

 public class Restaurant implements Parcelable {
        private int id;
        private String name;
        private float rating;
        private boolean favorite;

        public Restaurant(int id, String name, float rating, boolean favorite) {
            this.id = id;
            this.name = name;
            this.rating = rating;
            this.favorite = favorite;
        }

        protected Restaurant(Parcel in) {
            id = in.readInt();
            name = in.readString();
            rating = in.readFloat();
            favorite = in.readByte() != 0;
        }

        public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
            @Override
            public Restaurant createFromParcel(Parcel in) {
                return new Restaurant(in);
            }

            @Override
            public Restaurant[] newArray(int size) {
                return new Restaurant[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        public boolean isFavorite() {
            return favorite;
        }

        public void setFavorite(boolean favorite) {
            this.favorite = favorite;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(id);
            parcel.writeString(name);
            parcel.writeFloat(rating);
            parcel.writeByte((byte) (favorite ? 1 : 0));
        }
    }
