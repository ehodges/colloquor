package com.bale_twine.colloquor.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty
    private String name;

    private User() {}

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
