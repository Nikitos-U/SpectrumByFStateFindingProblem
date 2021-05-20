package ru.mipt;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Value
@Component
@RequiredArgsConstructor
public class DecaysFinder {
    ParticleCombinator particleCombinator;
    ProbableParticlesMaker probableParticlesMaker;

    public ArrayList<Cascade> findDecays(Cascade fstate) {
        ArrayList<Cascade> finalCascades = new ArrayList<>();
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