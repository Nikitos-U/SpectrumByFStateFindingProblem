package ru.mipt;

import java.util.ArrayList;

public class Decay {
    final ArrayList<Particle> particles;
    Particle motherParticle; // Particle
    Double probability = 0.0;
    Double mass = 0.0;
// TODO charge conjugation method
    public Decay(Particle motherParticle, ArrayList<Particle> particles, Double probability) {
        this.motherParticle = motherParticle;
        this.particles = particles;
        this.probability = probability;
        for (Particle particle : particles) {
            this.mass += particle.mass;
        }
    }

    @Override
    public String toString() {
        return "decay of: " + this.motherParticle.name + " on "
                + this.particles.toString();
    }
}
