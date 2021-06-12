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

import static java.util.stream.Collectors.groupingBy;

@Value
@Component
@RequiredArgsConstructor
public class DecaysFinder {
    ParticleCombinator particleCombinator;
    ProbableParticlesMaker probableParticlesMaker;

    public ArrayList<Cascade> findDecays(Cascade fstate, Map<String, Particle> parsedParticles, MultiValuedMap<List<Particle>, Decay> parsedDecays, Map<List<Particle>, List<Cascade>> memo) {
        ArrayList<Cascade> finalCascades = new ArrayList<>();
        if (fstate.getParticleList().size() == 1) {
            finalCascades.add(fstate);
            return finalCascades;
        }
        ArrayList<Cascade> cascades = particleCombinator.allCombinations(fstate, probableParticlesMaker, parsedParticles, parsedDecays, memo);
        Map<List<Particle>, List<Cascade>> preMemo = cascades.stream().collect(groupingBy(Cascade::getInitialFstateParticles));
        preMemo.entrySet().forEach(entry -> {
            if (!memo.containsKey(entry.getKey())) {
                memo.put(entry.getKey(), entry.getValue());
            }
        });
        for (Cascade cascade : cascades) {

            finalCascades.addAll(findDecays(cascade, parsedParticles, parsedDecays, memo));
        }
        return finalCascades;
    }

    public void setFstateForParticleCombinator(Cascade fstate) {
        particleCombinator.setInitialFstate(fstate);
    }
}