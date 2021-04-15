package ru.mipt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DecaysFinder {
    private final ParticleCombinator particleCombinator;
    private final ProbableParticlesMaker probableParticlesMaker;

    public List<Cascade> findDecays(Cascade fstate) {
        List<Cascade> finalCascades = new ArrayList<>();
        if (fstate.getParticleList().size() == 1) {
            finalCascades.add(fstate);
            return finalCascades;
        }
        for (Cascade cascade : particleCombinator.allCombinations(fstate, probableParticlesMaker)) {
            finalCascades.addAll(findDecays(cascade));
        }
        return finalCascades;
    }
}