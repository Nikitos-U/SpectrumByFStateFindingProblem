//import org.junit.jupiter.api.Test;
//import ru.mipt.domain.Decay;
//import ru.mipt.domain.Particle;
//import ru.mipt.utils.DoubleKeyHashMap;
//
//import java.util.List;
//
//import static java.util.Arrays.asList;
//
//public class TestProbableParticlesMaker {
//
//    @Test
//    void testExactMatch() {
//        Particle particle1 = new Particle("pi+", 10.0, 1);
//        Particle particle2 = new Particle("pi-", 10.0, 2);
//        Particle particle3 = new Particle("pi0", 10.0, 3);
//        Particle particle4 = new Particle("K+", 10.0, 4);
//        Decay decay123 = new Decay(particle1, asList(particle2, particle3), 0.0001);
//        Decay decay124 = new Decay(particle1, asList(particle2, particle4), 0.0001);
//        Decay decay134 = new Decay(particle1, asList(particle3, particle4), 0.0001);
//        Decay decay213 = new Decay(particle2, asList(particle1, particle3), 0.0001);
//        Decay decay214 = new Decay(particle2, asList(particle1, particle4), 0.0001);
//        Decay decay234 = new Decay(particle2, asList(particle3, particle4), 0.0001);
//        DoubleKeyHashMap parsedDecays = new DoubleKeyHashMap();
////        parsedDecays.putByFirstKey("1:2 3", decay123);
////        parsedDecays.addMotherParticle(particle1, getSecondKey(decay123.getParticles()));
////        parsedDecays.putByFirstKey("1:2 4", decay124);
////        parsedDecays.addMotherParticle(particle1, getSecondKey(decay124.getParticles()));
////        parsedDecays.putByFirstKey("1:3 4", decay134);
////        parsedDecays.addMotherParticle(particle1, getSecondKey(decay134.getParticles()));
////        parsedDecays.putByFirstKey("2:1 3", decay213);
////        parsedDecays.addMotherParticle(particle2, getSecondKey(decay213.getParticles()));
////        parsedDecays.putByFirstKey("2:1 4", decay214);
////        parsedDecays.addMotherParticle(particle2, getSecondKey(decay214.getParticles()));
////        parsedDecays.putByFirstKey("2:3 4", decay234);
////        parsedDecays.addMotherParticle(particle2, getSecondKey(decay234.getParticles()));
////        ProbableParticlesMaker particlesMaker = new ProbableParticlesMaker(parsedDecays);
////        assertEquals("{pi-=decay of: pi- on [pi0, K+], pi+=decay of: pi+ on [pi0, K+]}",
////                particlesMaker.combinationsToParticles(asList("3", "4")).toString());
////        assertEquals("{}",
////                particlesMaker.combinationsToParticles(asList("1", "2")).toString());
////        assertEquals("{pi-=decay of: pi- on [pi+, pi0]}",
////                particlesMaker.combinationsToParticles(asList("1", "3")).toString());
//    }
//
//    private String getSecondKey(List<Particle> particleList){
//        return particleList.stream()
//                .map(Particle::getId)
//                .reduce("", (x,y) -> x + " " + y.toString(), String::concat).trim();
//    }
//}
