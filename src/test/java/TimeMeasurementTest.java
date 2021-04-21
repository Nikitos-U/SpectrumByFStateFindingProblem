import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.mipt.DecaysFinder;
import ru.mipt.ParticleCombinator;
import ru.mipt.ProbableParticlesMaker;
import ru.mipt.dao.DecayRepository;
import ru.mipt.dao.ParticleRepository;
import ru.mipt.newAlgoEffort.FstateServiceImpl;
import ru.mipt.newAlgoEffort.NewDaoConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import static java.lang.System.nanoTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = NewDaoConfig.class)
public class TimeMeasurementTest {

    JdbcDataSource dataSource = new JdbcDataSource();

    ParticleRepository particleRepository;

    DecayRepository decayRepository;

    JdbcTemplate jdbcTemplate;

    ObjectMapper mapper;

    DecaysFinder finder;

    ProbableParticlesMaker maker;

    ParticleCombinator combinator;

    FstateServiceImpl service;

    private final String fstateTwoParticles = "K0,pi-";
    private final String fstateThreeParticles = "K0,pi0,pi0";
    private final String fstateFourParticles = "pi+,pi-,K0,K-";
    private final String fstateFiveParticles = "pi+,pi+,pi-,K0,K-";


    private void setUp() {
        dataSource.setURL("jdbc:h2:~/test");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        jdbcTemplate = new JdbcTemplate(dataSource);

        mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        decayRepository = new DecayRepository(mapper, jdbcTemplate);
        particleRepository = new ParticleRepository(mapper, jdbcTemplate);

        maker = new ProbableParticlesMaker(decayRepository);
        combinator = new ParticleCombinator(particleRepository);

        finder = new DecaysFinder(combinator, maker);

        service = new FstateServiceImpl(particleRepository, finder);
    }

    @SneakyThrows
    public void warmUp() {
        setUp();
        System.out.println("started warm up");
        for (int i = 0; i < 1000; i++) {
            service.computeCascades(fstateTwoParticles);
        }
        System.out.println("ended warm up");
    }

    @SneakyThrows
    @Test
    public void measureWorkingTime() {
        warmUp();
        System.out.println("started test");
        File csvOutputFile = new File("H2_measurements_Logs_all.csv");
        PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile, true));

        for (int i = 0; i < 100; i++) {
            long time = nanoTime();
            service.computeCascades(fstateTwoParticles);
            long timeOfRun = nanoTime() - time;
            pw.println(2 + ", " + timeOfRun);
        }

        for (int i = 0; i < 50; i++) {
            long time = nanoTime();
            service.computeCascades(fstateThreeParticles);
            long timeOfRun = nanoTime() - time;
            pw.println(3 + ", " + timeOfRun);
        }

        for (int i = 0; i < 50; i++) {
            long time = nanoTime();
            service.computeCascades(fstateFourParticles);
            long timeOfRun = nanoTime() - time;
            pw.println(4 + ", " + timeOfRun);
        }

//        for (int i = 0; i < 10; i++) {
//            long time = nanoTime();
//            service.computeCascades(fstateFiveParticles);
//            long timeOfRun = nanoTime() - time;
//            pw.println(3 + ", " + timeOfRun);
//        }

        pw.close();
    }
}
