package ru.mipt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mipt.dao.DecayRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProbableParticlesMaker {
    private final DecayRepository repository;

    public Map<Particle, Decay> convertCombinationsToParticles(ArrayList<Particle> particles) {
        Map<Particle, Decay> probableParticles = new HashMap<>();
        List<Decay> decays = repository.getDecaysByParticles(particles);
        for (Decay decay : decays) {
            probableParticles.put(decay.getMotherParticle(), decay);
        }
        return probableParticles;
    }
}