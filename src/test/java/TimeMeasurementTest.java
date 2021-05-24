//import lombok.SneakyThrows;
//import org.apache.commons.collections4.MultiValuedMap;
//import org.junit.jupiter.api.Test;
//import ru.mipt.*;
//import ru.mipt.domain.Cascade;
//import ru.mipt.domain.Decay;
//import ru.mipt.domain.Particle;
//import ru.mipt.parsers.DecayParser;
//import ru.mipt.parsers.ParticleParser;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.List;
//
//import static java.lang.System.nanoTime;
//
//public class TimeMeasurementTest {
//    ParticleParser particleParser = new ParticleParser();
//    HashMap<String, Particle> particles = particleParser.parse();
//    DecayParser decayParser = new DecayParser(particles);
//    MultiValuedMap<List<Particle>, Decay> decays = decayParser.parse();
//    Particle fake_mother_particle = new Particle("FAKE_MOTHER_PARTICLE_ADD_ALIAS", 42069.0, 0);
//    ProbableParticlesMaker probableParticlesMaker = new ProbableParticlesMaker(decays);
//    ParticleCombinator combinator = new ParticleCombinator(particles);
//    DecaysFinder decaysFinder = new DecaysFinder(combinator, probableParticlesMaker);
//
//    public TimeMeasurementTest() throws IOException {
//    }
//
//    @SneakyThrows
//    public void warmUp() {
//        particles.put("FAKE_MOTHER_PARTICLE_ADD_ALIAS", fake_mother_particle);
//        Cascade fstate = new Cascade();
//        for (String arg : new String[]{"pi+", "pi-"}) {
//            for (Particle particle : particles.values()) {
//                if (particle.getAliases().contains(arg)) {
//                    fstate.getParticleList().add(particle);
//                }
//            }
//        }
//        System.out.println("started warm up");
//        for (int i = 0; i < 5000; i++) {
//            decaysFinder.findDecays(fstate);
//        }
//        System.out.println("ended warm up");
//    }
//
//    @SneakyThrows
//    @Test
//    public void measureWorkingTime() {
//        warmUp();
//        System.out.println("started test");
//        File csvOutputFile = new File("/Users/a18535673/Projects/SpectrumByFStateFindingProblem/src/test/resources/resultsWithApacheMultiMap.csv");
//        PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile, true));
//        pw.println("num of particles, working time ns");
//        particles.put("FAKE_MOTHER_PARTICLE_ADD_ALIAS", fake_mother_particle);
//        Cascade twoParticlesFstate = new Cascade();
//        for (String arg : new String[]{"pi+", "pi-"}) {
//            twoParticlesFstate.getParticleList().add(particles.get(arg));
//        }
//        for (int i = 0; i < 100; i++) {
//            long time = nanoTime();
//            decaysFinder.findDecays(twoParticlesFstate);
//            long timeOfRun = nanoTime() - time;
//            pw.println(2 + ", " + timeOfRun);
//        }
//        Cascade threeParticlesFstate = new Cascade();
//        for (String arg : new String[]{"pi+", "pi-", "pi0"}) {
//            threeParticlesFstate.getParticleList().add(particles.get(arg));
//        }
//        for (int i = 0; i < 100; i++) {
//            long time = nanoTime();
//            decaysFinder.findDecays(threeParticlesFstate);
//            long timeOfRun = nanoTime() - time;
//            pw.println(3 + ", " + timeOfRun);
//        }
//        Cascade fourParticlesFstate = new Cascade();
//        for (String arg : new String[]{"pi+", "pi-", "pi0", "K+"}) {
//            fourParticlesFstate.getParticleList().add(particles.get(arg));
//        }
//        for (int i = 0; i < 100; i++) {
//            long time = nanoTime();
//            decaysFinder.findDecays(fourParticlesFstate);
//            long timeOfRun = nanoTime() - time;
//            pw.println(4 + ", " + timeOfRun);
//        }
//        Cascade fiveParticlesFstate = new Cascade();
//        for (String arg : new String[]{"pi+", "pi-", "pi0", "K+", "K0"}) {
//            fiveParticlesFstate.getParticleList().add(particles.get(arg));
//        }
//        for (int i = 0; i < 100; i++) {
//            long time = nanoTime();
//            decaysFinder.findDecays(fiveParticlesFstate);
//            long timeOfRun = nanoTime() - time;
//            pw.println(5 + ", " + timeOfRun);
//        }
//        pw.close();
//    }
//}
