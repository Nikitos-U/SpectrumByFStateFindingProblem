package ru.mipt;

import java.util.ArrayList;
import java.util.HashMap;

public class ProbableParticlesMaker {
    private ArrayList<String> allFstateCombinations;
    private ArrayList<Decay> parsedDecays;
    private HashMap parsedParticles;


    public ProbableParticlesMaker(ArrayList<String> allFstateCombinations, ArrayList<Decay> parsedDecays, HashMap parsedParticles) {
        this.allFstateCombinations = allFstateCombinations;
        this.parsedDecays = parsedDecays;
        this.parsedParticles = parsedParticles;
    }

    public ArrayList<Particle> convertCombinationsToParticles() {
        ArrayList<String> probableParticlesNames = new ArrayList<>();
        ArrayList<Particle> probableParticles = new ArrayList<>();
        for (String fstateCombination : allFstateCombinations) {
            for (Decay parsedDecay : parsedDecays) {
                if (fstateCombination.equals(parsedDecay.particles)) {
                    probableParticlesNames.add(parsedDecay.name);

                    System.out.println(parsedDecay.name);
                }
            }
        }
        for (String probableParticlesName : probableParticlesNames) {
            if (parsedParticles.containsKey(probableParticlesName)) {
                probableParticles.add((Particle) parsedParticles.get(probableParticlesName));
                System.out.println(probableParticles);
            }
        }
        return probableParticles;
    }
}
