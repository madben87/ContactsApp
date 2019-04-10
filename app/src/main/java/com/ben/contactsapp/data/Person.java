package com.ben.contactsapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Person implements Parcelable {

    @SerializedName("itemId")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String imageUrl;
    @SerializedName("description")
    private String description;
    @SerializedName("time")
    private long time;

    public Person() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = Long.parseLong(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Person(Parcel in) {
        id = in.readLong();
        name = in.readString();
        imageUrl = in.readString();
        description = in.readString();
        time = in.readLong();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(description);
        dest.writeLong(time);
    }
}
