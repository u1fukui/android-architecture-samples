package com.u1fukui.droidkaigi2017_mvvm.model;

import com.google.gson.annotations.SerializedName;

public class Contributor {

    @SerializedName(value = "login", alternate = "name")
    public String name;

    @SerializedName("avatar_url")
    public String avatarUrl;
}
