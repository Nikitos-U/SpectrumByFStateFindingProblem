package ru.mipt;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.stereotype.Component;
import ru.mipt.domain.Cascade;
import ru.mipt.domain.Decay;
import ru.mipt.domain.Particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Value
@Component
@RequiredArgsConstructor
public class DecaysFinder {
    ParticleCombinator particleCombinator;
    ProbableParticlesMaker probableParticlesMaker;

    public ArrayList<Cascade> findDecays(Cascade fstate, Map<String, Particle> parsedParticles, MultiValuedMap<List<Particle>, Decay> parsedDecays) {
        ArrayList<Cascade> finalCascades = new ArrayList<>();
        if (fstate.getParticleList().size() == 1) {
            finalCascades.add(fstate);
            return finalCascades;
        }
        for (Cascade cascade : particleCombinator.allCombinations(fstate, probableParticlesMaker, parsedParticles, parsedDecays)) {
            finalCascades.addAll(findDecays(cascade, parsedParticles, parsedDecays));
        }
        return finalCascades;
    }
}