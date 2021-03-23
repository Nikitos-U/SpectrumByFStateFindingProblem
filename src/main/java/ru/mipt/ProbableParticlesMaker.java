//package ru.mipt;
//
//import lombok.NoArgsConstructor;
//import ru.mipt.dao.DaoConfig;
//import ru.mipt.dao.DecayEntry;
//import ru.mipt.dao.DecayRepository;
//import ru.mipt.dao.ParticleRepository;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@NoArgsConstructor
//public class ProbableParticlesMaker {
//    private final DaoConfig config = new DaoConfig();
//    private final DecayRepository repository = new DecayRepository(config.getNamedParameterJdbcTemplate());
//    private final ParticleRepository particleRepository = new ParticleRepository(config.getNamedParameterJdbcTemplate());
//
//    public Map<Particle, Decay> convertCombinationsToParticles(ArrayList<Particle> particles) {
//        Map<Particle, Decay> probableParticles = new HashMap<>();
//        List<DecayEntry> decayEntries = repository.findByParticles(particles);
//        for (DecayEntry decayEntry : decayEntries) {
//            Particle motherParticle = particleRepository.findByName(decayEntry.getMotherParticle());
//            ArrayList<Particle> decayParticles = new ArrayList<>();
//            for (String s : decayEntry.getParticles().substring(1, decayEntry.getParticles().length() - 1).split(", ")) {
//                decayParticles.add(particleRepository.findByName(s));
//            }
//            probableParticles.put(motherParticle, new Decay(motherParticle, decayParticles, decayEntry.getProbability()));
//        }
//        return probableParticles;
//    }
//}