package ru.mipt;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Decay {
    private final ArrayList<Particle> particles;
    private Particle motherParticle; // Particle
    private Double probability = 0.0;
    private Double mass = 0.0;
// TODO charge conjugation method
    public Decay(Particle motherParticle, ArrayList<Particle> particles, Double probability) {
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
