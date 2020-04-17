package ru.mipt.Tests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.mipt.Cascade;
import ru.mipt.Decay;
import ru.mipt.Particle;

import java.util.ArrayList;
import java.util.List;

public class TestCascade {

    @Test
    public void testCascadeByConstructorProduction() {
        String name = "name";
        String alias = "anotherName";
        Double mass = 13.0;
        Particle particle = new Particle(name, mass);
        ArrayList<Particle> particles = new ArrayList<>();
        particles.add(particle);
        Decay decay = new Decay(particle, particles, 0.1);
        ArrayList<Decay> decays = new ArrayList<>();
        decays.add(decay);
        Cascade cascade = new Cascade(particles, decays);
        Assertions.assertEquals(particles, cascade.getParticleList());
        Assertions.assertEquals(decays, cascade.getHistory());
    }
}
