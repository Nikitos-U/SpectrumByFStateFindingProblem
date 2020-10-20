package ru.mipt;

import java.util.ArrayList;
import java.util.HashMap;

public class ParticleCombinator {
    private ArrayList<String> result1 = new ArrayList<>();
    private HashMap<String, Particle> parsedParticles = new HashMap<>();
    private Integer fstateSize = 0;

    public ParticleCombinator(HashMap<String, Particle> parsedParticles) {
        this.parsedParticles = parsedParticles;
    }

    private ArrayList<String> combinations2(ArrayList<String> fstate, int len, int startPosition, String[] result) {
        if (len == 0) {
            String s = "";
            for (int i = 0; i < result.length; i++) {
                s += " " + result[i];
            }
            result1.add(s);
            s = "";
            return result1;
        }
        for (int i = startPosition; i <= fstate.size() - len; i++) {
            result[result.length - len] = fstate.get(i);
            combinations2(fstate, len - 1, i + 1, result);
        }
        return result1;
    }

    ArrayList<Cascade> allCombinations(Cascade cascade, ProbableParticlesMaker probableParticlesMaker) {
        ArrayList<String> fstateCombination = new ArrayList<>();
        ArrayList<String> fstate = new ArrayList<>();
        ArrayList<ArrayList<Particle>> result = new ArrayList<>();
        ArrayList<Cascade> cascades = new ArrayList<>();
        fstateSize = cascade.getParticleList().size();
        result1 = new ArrayList<>();
        for (Particle particle : cascade.getParticleList()) {
            ArrayList<Particle> preParticles = new ArrayList<>();
            fstate.add(particle.getName());
            preParticles.add(particle);
            result.add(preParticles);
        }
        System.out.println("Processing this fstate: " + fstate);
        for (int i = 2; i <= fstate.size(); i++) {
            fstateCombination = new ArrayList<>();
            fstateCombination = combinations2(fstate, i, 0, new String[i]);
            for (String s : fstateCombination) {
                ArrayList<Particle> possibleDecayParticles = new ArrayList<>();
                for (int j = 0; j < s.split("\\s+").length; j++) {
                    for (Particle particle : parsedParticles.values()) {
                        if (particle.getAlias().equals(s.split("\\s+")[j].trim()) || particle.getName().equals(s.split("\\s+")[j].trim())) {
                            possibleDecayParticles.add(particle);
                        }
                    }
                }
                if (!possibleDecayParticles.containsAll(cascade.getParticleList())) {
                    Integer counter = possibleDecayParticles.size();
                    while (counter < fstateSize) {
                        if (!probableParticlesMaker.convertCombinationsToParticles(possibleDecayParticles).isEmpty()) {
                            for (Decay decay : probableParticlesMaker.convertCombinationsToParticles(possibleDecayParticles).values()) {
                                Cascade formedCascade = new Cascade();
                                formedCascade.getParticleList().add(decay.getMotherParticle());
                                formedCascade.getHistory().add(decay);
                                formedCascade.getHistory().addAll(cascade.getHistory());
                                for (Particle particle : cascade.getParticleList()) {
                                    if (!possibleDecayParticles.contains(particle)) {
                                        formedCascade.getParticleList().add(particle);
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
                        formedCascade.getParticleList().add(decay.getMotherParticle());
                        formedCascade.getHistory().add(decay);
                        formedCascade.getHistory().addAll(cascade.getHistory());
                        if (!cascades.contains(formedCascade)) {
                            cascades.add(formedCascade);
                        }
                    }
                }
                result.add(possibleDecayParticles);
                possibleDecayParticles.clear();
            }
        }
        System.out.println("++++++++++++++++++++++++++++++++Result combinations:++++++++++++++++++++++++++++++++");
        for (Cascade cascade1 : cascades) {
            System.out.println(cascade1);
        }
        System.out.println("++++++++++++++++++++++++++++++++END++++++++++++++++++++++++++++++++");
        return cascades;
    }
}