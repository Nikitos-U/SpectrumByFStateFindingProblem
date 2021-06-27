import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.mipt.DecaysFinder;
import ru.mipt.ParticleCombinator;
import ru.mipt.ProbableParticlesMaker;
import ru.mipt.dao.FstateRepository;
import ru.mipt.dao.FstateRepositoryImpl;
import ru.mipt.domain.Cascade;
import ru.mipt.domain.Decay;
import ru.mipt.domain.Particle;
import ru.mipt.parsers.DecayParser;
import ru.mipt.parsers.ParticleParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.System.nanoTime;

public class TimeMeasurementTest {
    JdbcDataSource dataSource = new JdbcDataSource();
    JdbcTemplate jdbcTemplate;
    ObjectMapper mapper;
    ParticleParser particleParser;
    Map<String, Particle> particles;
    ProbableParticlesMaker maker = new ProbableParticlesMaker();
    ParticleCombinator combinator = new ParticleCombinator();
    DecaysFinder decaysFinder = new DecaysFinder(combinator, maker);
    MultiValuedMap<List<Particle>, Decay> parsedDecays;
    Map<List<Particle>, List<Cascade>> memo = new HashMap<>();

    public TimeMeasurementTest() throws IOException {
    }

    private void setUp() throws IOException {
        dataSource.setURL("jdbc:h2:~/test");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        jdbcTemplate = new JdbcTemplate(dataSource);
        mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        FstateRepository repository = new FstateRepositoryImpl(jdbcTemplate, mapper);
        particleParser = new ParticleParser(repository);
        List<Particle> parsedParticles = repository.getParticles();
        particles = parsedParticles.stream().collect(Collectors.toMap(Particle::getName, Function.identity()));
        DecayParser decayParser = new DecayParser(particles, repository);
        List<Decay> parsedDecays1 = repository.getDecays();
        MultiValuedMap<List<Particle>, Decay> result = new ArrayListValuedHashMap<>();
        parsedDecays1.forEach(decay -> result.put(decay.getParticles(), decay));
        parsedDecays = result;
    }

    @SneakyThrows
    public void warmUp() {
        setUp();
        Cascade fstate = new Cascade();
        for (String arg : new String[]{"pi+", "pi-"}) {
            for (Particle particle : particles.values()) {
                if (particle.getAliases().contains(arg)) {
                    fstate.getParticleList().add(particle);
                }
            }
        }
        System.out.println("started warm up");
        for (int i = 0; i < 5000; i++) {
            decaysFinder.setFstateForParticleCombinator(fstate);
            decaysFinder.findDecays(fstate, particles, parsedDecays, memo);
        }
        System.out.println("ended warm up");
    }

    @SneakyThrows
    @Test
    public void measureWorkingTime() {
        warmUp();
        System.out.println("started test");
        File csvOutputFile = new File("/Users/a18535673/Projects/SpectrumByFStateFindingProblem/src/test/resources/resultsForMemo.csv");
        PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile, true));
        pw.println("num of particles, working time ns");
        Cascade twoParticlesFstate = new Cascade();
        for (String arg : new String[]{"pi+", "pi-"}) {
            twoParticlesFstate.getParticleList().add(particles.get(arg));
        }
        for (int i = 0; i < 100; i++) {
            decaysFinder.setFstateForParticleCombinator(twoParticlesFstate);
            long time = nanoTime();
            decaysFinder.findDecays(twoParticlesFstate, particles, parsedDecays, memo);
            long timeOfRun = nanoTime() - time;
            pw.println(2 + ", " + timeOfRun);
        }
        Cascade threeParticlesFstate = new Cascade();
        for (String arg : new String[]{"pi+", "pi-", "pi0"}) {
            threeParticlesFstate.getParticleList().add(particles.get(arg));
        }
        for (int i = 0; i < 100; i++) {
            long time = nanoTime();
            decaysFinder.findDecays(threeParticlesFstate, particles, parsedDecays, memo);
            long timeOfRun = nanoTime() - time;
            pw.println(3 + ", " + timeOfRun);
        }
        Cascade fourParticlesFstate = new Cascade();
        for (String arg : new String[]{"pi+", "pi-", "pi0", "K+"}) {
            fourParticlesFstate.getParticleList().add(particles.get(arg));
        }
        for (int i = 0; i < 100; i++) {
            long time = nanoTime();
            decaysFinder.findDecays(fourParticlesFstate, particles, parsedDecays, memo);
            long timeOfRun = nanoTime() - time;
            pw.println(4 + ", " + timeOfRun);
        }
        Cascade fiveParticlesFstate = new Cascade();
        for (String arg : new String[]{"pi+", "pi-", "pi0", "K+", "K0"}) {
            fiveParticlesFstate.getParticleList().add(particles.get(arg));
        }
        for (int i = 0; i < 100; i++) {
            decaysFinder.setFstateForParticleCombinator(fiveParticlesFstate);
            long time = nanoTime();
            decaysFinder.findDecays(fiveParticlesFstate, particles, parsedDecays, memo);
            long timeOfRun = nanoTime() - time;
            pw.println(5 + ", " + timeOfRun);
        }
        Cascade sixParticlesFstate = new Cascade();
        for (String arg : new String[]{"pi+", "pi-", "pi0", "K+", "K0", "pi-"}) {
            sixParticlesFstate.getParticleList().add(particles.get(arg));
        }
        for (int i = 0; i < 100; i++) {
            decaysFinder.setFstateForParticleCombinator(sixParticlesFstate);
            long time = nanoTime();
            decaysFinder.findDecays(sixParticlesFstate, particles, parsedDecays, memo);
            long timeOfRun = nanoTime() - time;
            pw.println(6 + ", " + timeOfRun);
        }
//        Cascade sevenParticlesFstate = new Cascade();
//        for (String arg : new String[]{"pi+", "pi-", "pi0", "K+", "K0", "K-", "pi-"}) {
//            sevenParticlesFstate.getParticleList().add(particles.get(arg));
//        }
//        for (int i = 0; i < 100; i++) {
//            decaysFinder.setFstateForParticleCombinator(sevenParticlesFstate);
//            long time = nanoTime();
//            decaysFinder.findDecays(fiveParticlesFstate, particles, parsedDecays, memo);
//            long timeOfRun = nanoTime() - time;
//            pw.println(7 + ", " + timeOfRun);
//        }
        pw.close();
    }
}
