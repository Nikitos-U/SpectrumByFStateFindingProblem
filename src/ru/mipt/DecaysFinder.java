package ru.mipt;

import java.util.ArrayList;

public class DecaysFinder {
    ParticleCombinator particleCombinator;
    ProbableParticlesMaker probableParticlesMaker;

    public DecaysFinder(ParticleCombinator particleCombinator, ProbableParticlesMaker probableParticlesMaker) {
        this.particleCombinator = particleCombinator;
        this.probableParticlesMaker = probableParticlesMaker;
    }

    public ArrayList<Cascade> findDecays(Cascade fstate) {
        ArrayList<Cascade> finalCascades = new ArrayList<>();
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