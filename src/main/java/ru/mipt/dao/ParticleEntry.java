package ru.mipt.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParticleEntry {
    @JsonProperty("id")
    long id;
    @JsonProperty("name")
    String name;
    @JsonProperty("alias")
    String alias;
    @JsonProperty("mass")
    double mass;

    public ParticleEntry(@JsonProperty("id") long id, @JsonProperty("name") String name,
                         @JsonProperty("alias") String alias, @JsonProperty("mass") double mass) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.mass = mass;
    }
}
