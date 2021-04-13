package ru.mipt.dao;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class DecayEntry {
    @JsonProperty("particles")
    String particles;
    @JsonProperty("motherParticle")
    String motherParticle;
    @JsonProperty("probability")
    Double probability;
    @JsonProperty("mass")
    Double mass;

    public DecayEntry(@JsonProperty("particles") String particles,
                      @JsonProperty("motherParticle") String motherParticle,
                      @JsonProperty("probability") Double probability,
                      @JsonProperty("mass") Double mass) {

        this.particles = particles;
        this.motherParticle = motherParticle;
        this.probability = probability;
        this.mass = mass;
    }
}
