package ru.mipt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DecaysFinder {
    ParticleCombinator particleCombinator;
    ProbableParticlesMaker probableParticlesMaker;

    public DecaysFinder(ParticleCombinator particleCombinator, ProbableParticlesMaker probableParticlesMaker) {
        this.particleCombinator = particleCombinator;
        this.probableParticlesMaker = probableParticlesMaker;
    }

    public DecaysFinder() {
    }

    public ArrayList<Cascade> findDecays(Cascade fstate) {
        ArrayList<Cascade> finalCascades = new ArrayList<>();
        ArrayList<Cascade> tmp = new ArrayList<>();
        if (fstate.particleList.size() == 1) {
            finalCascades.add(fstate);
            return finalCascades;
        }

        for (Cascade cascade : particleCombinator.allCombinations(fstate, probableParticlesMaker)) {
            finalCascades.addAll(findDecays(cascade));
        }
        return finalCascades;
    }
}