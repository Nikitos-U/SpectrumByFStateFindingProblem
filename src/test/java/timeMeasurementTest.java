import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import static ru.mipt.SpectrumByFStateFinder.*;

public class timeMeasurementTest {

    @SneakyThrows
    @Test
    public void measureWorkingTime(){
        File csvOutputFile = new File("/Users/a18535673/Projects/SpectrumByFStateFindingProblem/src/test/resources/timeMeasurementsResults.csv");
        PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile, true));
//        pw.println("num of particles, working time ns");
        String[] fstate = new String[]{"pi+", "pi-", "pi0", "K+", "K-"};
        for (int i = 0; i < 100; i++) {
            main(fstate);
            pw.println(fstate.length + ", " + workingTime );
        }
        pw.close();
    }
}
