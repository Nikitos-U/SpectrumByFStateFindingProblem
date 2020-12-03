import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.mipt.*;
import ru.mipt.parsers.DecayParser;
import ru.mipt.parsers.ParticleParser;

import java.io.IOException;
import java.util.*;

public class TestProbableParticlesMaker {
    public HashMap<String, Particle> particles;
    public HashMap<String, Decay> decays;

//    @Test
//    void testForTwoParticles() throws IOException {
//        ParticleParser particleParser = new ParticleParser();
//        HashMap<String, Particle> particles;
//        particles = particleParser.parse();
//        DecayParser decayParser = new DecayParser();
//        HashMap<String, Decay> decays;
//        decays = decayParser.parse(particles);
//        ArrayList<Particle> testParticles = new ArrayList<>();
//        HashMap<Particle, Decay> resultParticles = new HashMap<>();
//        Set<Particle> result = new HashSet<>();
//        Particle particle1 = particles.get("pi+");
//        Particle particle2 = particles.get("pi-");
//        testParticles.addAll(Arrays.asList(particle1, particle2));
//        ProbableParticlesMaker probableParticlesMaker = new ProbableParticlesMaker(decays);
//        resultParticles = (HashMap<Particle, Decay>) probableParticlesMaker.convertCombinationsToParticles(testParticles);
//        result = resultParticles.keySet();
//        Particle particle3 = particles.get("KS0");
//        Set<Particle> particlesForAssertion = new HashSet<>();
//        particlesForAssertion.add(particle3);
//        Assertions.assertEquals(particlesForAssertion, result);
//    }

    @Test
    void testCaseParticlesCanNotBeConcated() throws IOException {
        ParticleParser particleParser = new ParticleParser();
        HashMap<String, Particle> particles;
        particles = particleParser.parse();
        DecayParser decayParser = new DecayParser();
        HashMap<String, Decay> decays;
        decays = decayParser.parse(particles);
        ArrayList<Particle> testParticles = new ArrayList<>();
        HashMap<Particle, Decay> resultParticles = new HashMap<>();
        Set<Particle> result = new HashSet<>();
        Particle particle1 = particles.get("Xu-");
        Particle particle2 = particles.get("pi-");
        testParticles.addAll(Arrays.asList(particle1, particle2));
        ProbableParticlesMaker probableParticlesMaker = new ProbableParticlesMaker(decays);
        resultParticles = (HashMap<Particle, Decay>) probableParticlesMaker.convertCombinationsToParticles(testParticles);
        result = resultParticles.keySet();
        Set<Particle> particlesForAssertion = new HashSet<>();
        Assertions.assertEquals(particlesForAssertion, result);
    }
}
