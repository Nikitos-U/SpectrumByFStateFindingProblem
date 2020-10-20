import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.mipt.Particle;
import ru.mipt.parsers.ParticleParser;

import java.io.IOException;
import java.util.HashMap;

class TestParticleParser {

    @Test
    void TestParticlesNumber() throws IOException {
        ParticleParser particleParser = new ParticleParser();
        HashMap<String, Particle> particles;
        particles = particleParser.parse();
        Assertions.assertEquals(971, particles.size());
    }

    @Test
    void TestForSomeParticlePresenceInTheList() throws IOException {
        ParticleParser particleParser = new ParticleParser();
        HashMap<String, Particle> particles;
        particles = particleParser.parse();
        Particle particle2 = particles.get("pi-");
        Assertions.assertNotNull(particle2);
    }

}
