package ru.mipt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ParticleCombinator {
    ArrayList<String> result1 = new ArrayList<>();

    ArrayList<String> combinations2(ArrayList<String> fstate, int len, int startPosition, String[] result) {
        if (len == 0) {
            String s = "";
            for (int i = 0; i < result.length; i++) {
                s += result[i];
            }
            result1.add(s);
            //System.out.println(result1);
            return result1;
        }
        for (int i = startPosition; i <= fstate.size() - len; i++) {
            result[result.length - len] = fstate.get(i);
            combinations2(fstate, len - 1, i + 1, result);
        }
        return result1;
    }

    ArrayList<ArrayList<Particle>> allCombinations(ArrayList<String> fstate, int startPosition, HashMap<String, Particle> parsedParticles) {
        //ArrayList<String> allFstateCombinations = new ArrayList<>();
        ArrayList<String> fstateCombination = new ArrayList<>();
        //allFstateCombinations.addAll(fstate);
        ArrayList<ArrayList<Particle>> result = new ArrayList<>();
        for (String s : fstate) {
            ArrayList<Particle> possibleDecayParticles = new ArrayList<>();
            for (Particle particle : parsedParticles.values()) {
                if (particle.alias.equals(s) || particle.name.equals(s)) {
                    possibleDecayParticles.add(particle);
                }
            }
            result.add(possibleDecayParticles);
            System.out.println("result posle dobavleniya tolko fstate: " + result);
        }
        for (int i = 2; i <= fstate.size(); i++) {
            //System.out.println(i);
            fstateCombination = combinations2(fstate, i, startPosition, new String[i]);
            System.out.println(fstateCombination);
            for (String s : fstateCombination) {
                ArrayList<Particle> possibleDecayParticles = new ArrayList<>();
                for (int j = 0; j < s.length(); j++) {
                    for (Particle particle : parsedParticles.values()) {
                        if (particle.alias.equals(s.substring(j, j + 1)) || particle.name.equals(s.substring(j, j + 1))) {
                            possibleDecayParticles.add(particle);
                        }
                    }
                }
                result.add(possibleDecayParticles);
            }
            /*
            for (String combination : fstateCombination) {
                if (!allFstateCombinations.contains(combination))
                    allFstateCombinations.add(combination);
            }
             */
        }
        System.out.println("result: " + result);
        return result;
    }
}


