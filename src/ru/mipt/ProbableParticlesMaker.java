package ru.mipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProbableParticlesMaker {
    private ArrayList<ArrayList<Particle>> allFstateCombinations;
    private ArrayList<Decay> parsedDecays;
    private HashMap parsedParticles;


    public ProbableParticlesMaker(ArrayList<ArrayList<Particle>> allFstateCombinations, ArrayList<Decay> parsedDecays, HashMap parsedParticles) {
        this.allFstateCombinations = allFstateCombinations;
        this.parsedDecays = parsedDecays;
        this.parsedParticles = parsedParticles;
    }

    public Map<Integer, Particle> convertCombinationsToParticles() {
        Map<Integer, Particle> probableParticles = new HashMap<>();
        for (ArrayList<Particle> fstateCombination : allFstateCombinations) {
            for (Decay parsedDecay : parsedDecays) {
                if (fstateCombination.equals(parsedDecay.particles)) {
                    for (Object value : parsedParticles.values()) {
                        if (parsedDecay.name.equals(((Particle) value).name) || parsedDecay.name.equals(((Particle) value).alias)) {
                            probableParticles.put(parsedDecay.particles.size(), (Particle) value);
                        }
                    }
                    System.out.println(parsedDecay.name);
                }
            }
        }
        return probableParticles;
    }
}
