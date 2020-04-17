package ru.mipt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ParticleCombinator {
    ArrayList<String> result1 = new ArrayList<>();
    HashMap<String, Particle> parsedParticles = new HashMap<>();
    Integer fstateSize = 0;

    public ParticleCombinator(HashMap<String, Particle> parsedParticles) {
        this.parsedParticles = parsedParticles;
    }

    ArrayList<String> combinations2(ArrayList<String> fstate, int len, int startPosition, String[] result) {
        if (len == 0) {
            String s = "";
            for (int i = 0; i < result.length; i++) {
                s += " " + result[i];
            }
            result1.add(s);
            //System.out.println("result1: " + result1);
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
        fstateSize = cascade.particleList.size();
        result1 = new ArrayList<>();
        for (Particle particle : cascade.particleList) {
            ArrayList<Particle> preParticles = new ArrayList<>();
            fstate.add(particle.name);
            preParticles.add(particle);
            result.add(preParticles);
        }
        System.out.println("fstate: " + fstate);
        for (int i = 2; i <= fstate.size(); i++) {
            //System.out.println(i);
            fstateCombination = new ArrayList<>();
            fstateCombination = combinations2(fstate, i, 0, new String[i]);
            //System.out.println("fstate combination: " + fstateCombination);
            for (String s : fstateCombination) {
                ArrayList<Particle> possibleDecayParticles = new ArrayList<>();
                for (int j = 0; j < s.split("\\s+").length; j++) {
                    //System.out.println(s.split("\\s+")[j]);
                    for (Particle particle : parsedParticles.values()) {
                        if (particle.alias.equals(s.split("\\s+")[j].trim()) || particle.name.equals(s.split("\\s+")[j].trim())) {
                            //System.out.println("dobavlyaem chasticu s imenem: " + particle.name + " alias: " + particle.alias);
                            possibleDecayParticles.add(particle);
                        }
                    }
                }
                if (!possibleDecayParticles.containsAll(cascade.particleList)) {
                    Integer counter = possibleDecayParticles.size();
                    while (counter < fstateSize) {
                        System.out.println(possibleDecayParticles);
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
                result.add(possibleDecayParticles);
                possibleDecayParticles.clear();
            }
        }
//        System.out.println("result: " + result);
        System.out.println("++++++++++++++++++++++++++++++++VOT ETO USHLO V OBRABOTKU+++++++++++++++++++++++++++++++++");
        for (Cascade cascade1 : cascades) {
            System.out.println(cascade1);
        }
        System.out.println("++++++++++++++++++++++++++++++++END+++++++++++++++++++++++++++++++++");
        return cascades;
    }
}