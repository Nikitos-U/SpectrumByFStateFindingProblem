package ru.mipt.Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.mipt.Decay;
import ru.mipt.DecayParser;
import ru.mipt.Particle;
import ru.mipt.ParticleParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class TestDecayParser {

    @Test
    public void TestDecaysNumber() throws IOException {
        ParticleParser particleParser = new ParticleParser();
        HashMap<String, Particle> particles;
        particles = particleParser.parse();
        DecayParser decayParser = new DecayParser();
        HashMap<String, Decay> decays;
        decays = decayParser.parse(particles);
        Assertions.assertEquals(75, decays.size());
    }

    @Test
    public void TestForSomeDecayPresenceInTheList() throws IOException {
        ParticleParser particleParser = new ParticleParser();
        HashMap<String, Particle> particles;
        particles = particleParser.parse();
        DecayParser decayParser = new DecayParser();
        HashMap<String, Decay> decays;
        decays = decayParser.parse(particles);
        Decay decay = decays.get("D_1+:D*(2007)0,pi+ 2");
        Assertions.assertNotNull(decay);
    }
}
