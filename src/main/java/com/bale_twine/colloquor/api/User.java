package com.bale_twine.colloquor.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User {

    @JsonProperty
    private String _id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String guid;

    // For Jackson JSON instantiation.
    public User() {}

    public User(String name, String guid) {
        this.name = name;
        this.guid = guid;
    }

    public User(String name) {
        this(name, UUID.randomUUID().toString());
    }

    protected User(String name, String guid, String _id) {
        this(name, guid);
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public String getGuid() {
        return guid;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (_id != null ? !_id.equals(user._id) : user._id != null) return false;
        if (!guid.equals(user.guid)) return false;
        if (!name.equals(user.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + guid.hashCode();
        return result;
    }
}
