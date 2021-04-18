import org.junit.jupiter.api.Test;
import ru.mipt.ParticleCombinator;

import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParticleCombinatorTests {

    @Test
    void testCombinations() {

    }

    @Test
    void combinations2DifferentParticlesTest() {
        ParticleCombinator particleCombinator = new ParticleCombinator(new HashMap<>());
        ArrayList<String> particles = new ArrayList<>(asList("pi+", "pi-", "pi0", "K+"));
        assertEquals(particleCombinator.combinations2(particles, 2, 0, new String[2]).size(), 6);
        assertEquals(particleCombinator.combinations2(particles, 3, 0, new String[3]).size(), 4);
        assertEquals(particleCombinator.combinations2(particles, 4, 0, new String[4]).size(), 1);
    }


    @Test
    void combinations2SameParticlesTest() {
        ParticleCombinator particleCombinator = new ParticleCombinator(new HashMap<>());
        ArrayList<String> particles = new ArrayList<>(asList("pi+", "pi+", "pi-", "pi-"));
        assertEquals(particleCombinator.combinations2(particles, 2, 0, new String[2]).size(), 6);
        assertEquals(particleCombinator.combinations2(particles, 3, 0, new String[3]).size(), 4);
        assertEquals(particleCombinator.combinations2(particles, 4, 0, new String[4]).size(), 1);
    }
}
