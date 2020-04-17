package ru.mipt.Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.mipt.Particle;
import ru.mipt.ParticleParser;

import java.io.IOException;
import java.util.HashMap;

public class TestParticleParser {

    @Test
    public void TestParticlesNumber() throws IOException {
        ParticleParser particleParser = new ParticleParser();
        HashMap<String, Particle> particles;
        particles = particleParser.parse();
        Assertions.assertEquals(971, particles.size());
    }

    @Test
    public void TestForSomeParticlePresenceInTheList() throws IOException {
        ParticleParser particleParser = new ParticleParser();
        HashMap<String, Particle> particles;
        particles = particleParser.parse();
        Particle particle2 = particles.get("pi-");
        Assertions.assertNotNull(particle2);
    }

}
