package com.bale_twine.colloquor.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty
    private String name;

    // For Jackson JSON instantiation.
    public User() {}

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof User))return false;
        User otherUser = (User)other;
        if (!(otherUser.getName().equals(this.getName()))) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
