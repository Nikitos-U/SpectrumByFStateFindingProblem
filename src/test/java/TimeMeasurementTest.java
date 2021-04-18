//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import ru.mipt.*;
//import ru.mipt.parsers.DecayParser;
//import ru.mipt.parsers.ParticleParser;
//import ru.mipt.utils.DoubleKeyHashMap;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//
//import static java.lang.System.nanoTime;
//
//public class TimeMeasurementTest {
//    ParticleParser particleParser = new ParticleParser();
//    HashMap<String, Particle> particles = particleParser.parse();
//    DecayParser decayParser = new DecayParser(particles);
//    DoubleKeyHashMap decays = decayParser.parse();
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
//        File csvOutputFile = new File("");
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
//        pw.close();
//    }
//}
