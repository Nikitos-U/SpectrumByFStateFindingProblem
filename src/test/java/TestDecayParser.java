import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.mipt.Decay;
import ru.mipt.Particle;
import ru.mipt.parsers.DecayParser;
import ru.mipt.parsers.ParticleParser;

import java.io.IOException;
import java.util.HashMap;

class TestDecayParser {
    private final ParticleParser particleParser = new ParticleParser();
    private final HashMap<String, Particle> particles = particleParser.parse();
    private final DecayParser decayParser = new DecayParser();
    private final HashMap<String, Decay> decays = decayParser.parse(particles);

    TestDecayParser() throws IOException {
    }

    @Test
    void TestDecaysNumber()  {
        Assertions.assertEquals(75, decays.size());
    }

    @Test
    void TestForSomeDecayPresenceInTheList() {
        Decay decay = decays.get("D_1+:D*(2007)0,pi+ 2");
        Assertions.assertNotNull(decay);
    }
}
