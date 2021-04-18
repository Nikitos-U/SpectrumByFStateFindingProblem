package ru.mipt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mipt.dao.DecayRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class ProbableParticlesMaker {
    private final DecayRepository decayRepository;

    public Map<Particle, Decay> combinationsToParticles(List<Particle> particles) {
        Map<Particle, Decay> probableParticles = new HashMap<>();
        List<Decay> decaysByParticles = decayRepository.getDecaysByParticles(particles);
        decaysByParticles.forEach(decay -> probableParticles.put(decay.getMotherParticle(), decay));
        return probableParticles;
    }
}