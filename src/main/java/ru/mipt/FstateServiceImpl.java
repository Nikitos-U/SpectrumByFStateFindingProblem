package ru.mipt;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.stereotype.Component;
import ru.mipt.domain.Cascade;
import ru.mipt.domain.Decay;
import ru.mipt.domain.Particle;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@Component
@RequiredArgsConstructor
public class FstateServiceImpl implements FstateService {
    private final DecaysFinder finder;

    @Override
    public List<Cascade> computeCascades(String fstateString, Map<String, Particle> particles,
                                         MultiValuedMap<List<Particle>, Decay> decays) {
        Cascade fstate = parseParticles(fstateString, particles);
        return finder.findDecays(fstate, particles, decays);
    }

    private Cascade parseParticles(String fstateString, Map<String, Particle> particles) {
        List<String> fstateParticles = asList(fstateString.split(","));
        Cascade fstate = new Cascade();
        for (String fstateParticle : fstateParticles) {
            for (Particle particle : particles.values()) {
                if (particle.getAliases().contains(fstateParticle)) {
                    fstate.getParticleList().add(particle);
                }
            }
        }
        return fstate;
    }
}
