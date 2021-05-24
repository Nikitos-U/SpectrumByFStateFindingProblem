package ru.mipt;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.stereotype.Component;
import ru.mipt.domain.Decay;
import ru.mipt.domain.Particle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProbableParticlesMaker {

    public Map<Particle, Decay> combinationsToParticles(List<Particle> particles, MultiValuedMap<List<Particle>, Decay> parsedDecays) {
        Map<Particle, Decay> probableParticles = new HashMap<>();
        if (parsedDecays.containsKey(particles)) {
            parsedDecays.get(particles).forEach(decay -> probableParticles.put(decay.getMotherParticle(), decay));
        }
        return probableParticles;
    }
}