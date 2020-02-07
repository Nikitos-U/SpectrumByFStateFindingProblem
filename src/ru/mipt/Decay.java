package ru.mipt;

import java.util.ArrayList;

public class Decay {
    final ArrayList<Particle> particles;
    String name;
    Double probability = 0.0;
    Double mass = 0.0;

    public Decay(String name, ArrayList<Particle> particles, Double probability) {
        this.name = name;
        this.particles = particles;
        this.probability = probability;
        for (Particle particle : particles) {
            this.mass += particle.mass;
        }
    }

    @Override
    public String toString() {
        return "rasspad chasticy: " + this.name + " na chasticy: "
                + this.particles.toString() + " s veroyatnostiu: " + this.probability
                + " s massoy: " + this.mass;
    }
}
