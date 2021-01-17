import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import static ru.mipt.starters.SpectrumByFStateFinder.main;
import static ru.mipt.starters.SpectrumByFStateFinder.workingTime;


public class TimeMeasurementTest {

//    @BeforeAll
//    static void warmUp() {
//        for (int i = 0; i < 5000; i++) {
//            main(new String[]{"d", "u"});
//        }
//    }

    @SneakyThrows
    @Test
    public void measureWorkingTime() {
        File csvOutputFile = new File("C:/Users/auhov/IdeaProjects/SpectrumByFStateFindingProblem/src/test/resources/timeMeasurementsResults.csv");
        PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile, true));
        pw.println("num of particles, working time ns");
        for (int i = 0; i < 10; i++) {
            main(new String[]{"pi+", "pi-"});
            pw.println(2 + ", " + workingTime);
        }
        pw.close();
    }

}
