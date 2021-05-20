package ru.mipt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;

@Component
@RequiredArgsConstructor
public class FstateServiceImpl implements FstateService {
    private final DecaysFinder finder;
    private final HashMap<String, Particle> particles;

    @Override
    public List<Cascade> computeCascades(String fstateString) {
        Cascade fstate = parseParticles(fstateString);
        return finder.findDecays(fstate);
    }

    private Cascade parseParticles(String fstateString) {
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
