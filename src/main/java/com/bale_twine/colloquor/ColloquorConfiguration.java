package com.bale_twine.colloquor;

import com.yammer.dropwizard.config.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class ColloquorConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    private String template;

    @NotEmpty
    @JsonProperty
    private String defaultName = "Stranger";

    @NotEmpty
    @JsonProperty
    private String mongoUri;

    @NotEmpty
    private String mongoUser = System.getenv("MONGO_USER");

    @NotEmpty
    private String mongoPass = System.getenv("MONGO_PASS");

    public String getTemplate() {
        return template;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public String getMongoUri() {
        return String.format(mongoUri, mongoUser, mongoPass);
    }

    public String getMongoUser() {
        return mongoUser;
    }

    public String getMongoPass() {
        return mongoPass;
    }
}