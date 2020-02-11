package ru.mipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProbableParticlesMaker {
    private ArrayList<ArrayList<Particle>> allFstateCombinations;
    private ArrayList<Decay> parsedDecays;
    private HashMap<String,Particle> parsedParticles;


    public ProbableParticlesMaker(ArrayList<ArrayList<Particle>> allFstateCombinations, ArrayList<Decay> parsedDecays, HashMap<String,Particle> parsedParticles) {
        this.allFstateCombinations = allFstateCombinations;
        this.parsedDecays = parsedDecays;
        this.parsedParticles = parsedParticles;
    }

    public Map<Integer, ArrayList<Particle>> convertCombinationsToParticles() {
        Map<Integer, ArrayList<Particle>> probableParticles = new HashMap<>();
        for (ArrayList<Particle> fstateCombination : allFstateCombinations) {
            for (Decay parsedDecay : parsedDecays) {
                if (fstateCombination.equals(parsedDecay.particles)) {
                    ArrayList<Particle> sameKeyParticles = new ArrayList<>();
                    for (Particle particle : parsedParticles.values()) {
                        if (parsedDecay.name.equals(particle.name) || parsedDecay.name.equals(particle.alias)) {
                            sameKeyParticles.add(particle);
                        }
                    }
                    System.out.println(parsedDecay.name);
                    probableParticles.put(parsedDecay.particles.size(),sameKeyParticles);
                }
            }

        }
        return probableParticles;
    }
}
