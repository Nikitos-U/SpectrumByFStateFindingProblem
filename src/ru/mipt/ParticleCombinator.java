package ru.mipt;

import java.util.ArrayList;
import java.util.HashMap;

import static ru.mipt.newCombinationsFinder.findCombinations;

public class ParticleCombinator {
    HashMap<String, Particle> parsedParticles;
    Integer fstateSize = 0;

    public ParticleCombinator(HashMap<String, Particle> parsedParticles) {
        this.parsedParticles = parsedParticles;
    }

    ArrayList<Cascade> allCombinations(Cascade cascade, ProbableParticlesMaker probableParticlesMaker) {
        ArrayList<Cascade> cascades = new ArrayList<>();
        fstateSize = cascade.particleList.size();
        System.out.println("Processing this state: " + cascade.particleList);
        long timeSum = 0L;
        for (int i = 1; i <= cascade.particleList.size(); i++) {
            combinationProcessor(cascade, probableParticlesMaker, cascades);
        }
        System.out.println("recursive combination finding time: " + timeSum);
        System.out.println("++++++++++++++++++++++++++++++++Result combinations:++++++++++++++++++++++++++++++++");
        for (Cascade cascade1 : cascades) {
            System.out.println(cascade1);
        }
        System.out.println("++++++++++++++++++++++++++++++++END++++++++++++++++++++++++++++++++");
        return cascades;
    }

    private void combinationProcessor(Cascade cascade, ProbableParticlesMaker probableParticlesMaker, ArrayList<Cascade> cascades) {
        ArrayList<ArrayList<Particle>> fstateCombination = findCombinations(cascade.particleList);
        System.out.println(fstateCombination);
        for (ArrayList<Particle> combination : fstateCombination) {
            ArrayList<Particle> possibleDecayParticles = new ArrayList<>(combination);
            if (!possibleDecayParticles.containsAll(cascade.particleList)) {
                int counter = possibleDecayParticles.size();
                while (counter < fstateSize) {
                    if (!probableParticlesMaker.convertCombinationsToParticles(possibleDecayParticles).isEmpty()) {
                        for (Decay decay : probableParticlesMaker.convertCombinationsToParticles(possibleDecayParticles).values()) {
                            Cascade formedCascade = new Cascade();
                            formedCascade.particleList.add(decay.motherParticle);
                            formedCascade.history.add(decay);
                            formedCascade.history.addAll(cascade.history);
                            for (Particle particle : cascade.particleList) {
                                if (!possibleDecayParticles.contains(particle)) {
                                    formedCascade.particleList.add(particle);
                                    counter++;
                                }
                            }
                            if (!cascades.contains(formedCascade)) {
                                cascades.add(formedCascade);
                            }
                        }
                    } else {
                        break;
                    }
                }
            } else {
                for (Decay decay : probableParticlesMaker.convertCombinationsToParticles(possibleDecayParticles).values()) {
                    Cascade formedCascade = new Cascade();
                    formedCascade.particleList.add(decay.motherParticle);
                    formedCascade.history.add(decay);
                    formedCascade.history.addAll(cascade.history);
                    if (!cascades.contains(formedCascade)) {
                        cascades.add(formedCascade);
                    }
                }
            }
            possibleDecayParticles.clear();
        }
    }
}