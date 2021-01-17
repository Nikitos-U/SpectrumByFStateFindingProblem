import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import static ru.mipt.starters.SpectrumByFStateFinder.main;
import static ru.mipt.starters.SpectrumByFStateFinder.workingTime;


public class TimeMeasurementTest {
//
//    @BeforeAll
//    static void warmUp() {
//        for (int i = 0; i < 5000; i++) {
//            main(new String[]{"d", "u"});
//        }
//    }

    @SneakyThrows
    @Test
    public void measureWorkingTime() {
        File csvOutputFile = new File("");
        PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile, true));
        pw.println("num of particles, working time ns");
        for (int i = 0; i < 10; i++) {
            main(new String[]{"pi+", "pi-"});
            pw.println(2 + ", " + workingTime);
        }
        for (int i = 0; i < 10; i++) {
            main(new String[]{"pi+", "pi-", "pi0"});
            pw.println(3 + ", " + workingTime);
        }
//        for (int i = 0; i < 10; i++) {
//            main(new String[]{"pi+", "pi-", "K+", "K-"});
//            pw.println(4 + ", " + workingTime);
//        }
        pw.close();
    }
}
