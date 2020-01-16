package ru.mipt;

import java.util.ArrayList;

public class Decay {
    final ArrayList<Particle> particles;
    Double mass = 0.0;

    public Decay(ArrayList<Particle> particles) {
        this.particles = particles;
        for (Particle particle : particles) {
            mass += particle.mass;
        }
    }
}
