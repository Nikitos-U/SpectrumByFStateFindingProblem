package ru.mipt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

import static java.util.Collections.sort;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Decay {
    @EqualsAndHashCode.Include
    private final List<Particle> particles;
    @EqualsAndHashCode.Include
    private final Particle motherParticle;
    private final Double probability;
    private final int id;
    private Double mass = 0.0;
// TODO charge conjugation method
    public Decay(@JsonProperty("motherParticle") Particle motherParticle,@JsonProperty("particles") ArrayList<Particle> particles, @JsonProperty("probability") Double probability) {
        this.motherParticle = motherParticle;
        this.particles = particles;
        sort(this.particles);
        this.probability = probability;
        for (Particle particle : particles) {
            this.mass += particle.getMass();
        }
        id = this.hashCode();
    }

    @Override
    public String toString() {
        return "decay of: " + this.motherParticle.getName() + " on "
                + this.particles.toString();
    }
}
