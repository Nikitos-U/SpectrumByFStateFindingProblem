package ru.mipt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Decay {
    private final ArrayList<Particle> particles;
    private final Particle motherParticle;
    private final Double probability;
    private Double mass = 0.0;
// TODO charge conjugation method
    public Decay(@JsonProperty("motherParticle") Particle motherParticle,@JsonProperty("particles") ArrayList<Particle> particles, @JsonProperty("probability") Double probability) {
        this.motherParticle = motherParticle;
        this.particles = particles;
        this.probability = probability;
        for (Particle particle : particles) {
            this.mass += particle.getMass();
        }
    }

    @Override
    public String toString() {
        return "decay of: " + this.motherParticle.getName() + " on "
                + this.particles.toString();
    }
}
