package ru.mipt;

import lombok.Value;

import java.util.ArrayList;

@Value
public class DecaysFinder {
    ParticleCombinator particleCombinator;
    ProbableParticlesMaker probableParticlesMaker;

    ArrayList<Cascade> findDecays(Cascade fstate) {
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