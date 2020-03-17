package ru.mipt;

import java.util.*;

public class ParticleCombinator {
    ArrayList<String> result1 = new ArrayList<>();

    ArrayList<String> combinations2(ArrayList<String> fstate, int len, int startPosition, String[] result) {
        if (len == 0) {
            String s = "";
            for (int i = 0; i < result.length; i++) {
                s += " " + result[i];
            }
            result1.add(s);
            //System.out.println("result1: " + result1);
            return result1;
        }
        for (int i = startPosition; i <= fstate.size() - len; i++) {
            result[result.length - len] = fstate.get(i);
            combinations2(fstate, len - 1, i + 1, result);
        }
        return result1;
    }

    ArrayList allCombinations(ArrayList<String> fstate, int startPosition, HashMap<String, Particle> parsedParticles) {
        //ArrayList<String> allFstateCombinations = new ArrayList<>();
        ArrayList almostFinalResult = new ArrayList();
        ArrayList<ArrayList> finalResult = new ArrayList<>();
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
            //System.out.println("result posle dobavleniya tolko fstate: " + result);
        }
        for (int i = 2; i <= fstate.size(); i++) {
            //System.out.println(i);
            fstateCombination.clear();
            fstateCombination = combinations2(fstate, i, startPosition, new String[i]);
            //System.out.println("fstate combination: " + fstateCombination);
            for (String s : fstateCombination) {
                ArrayList<Particle> possibleDecayParticles = new ArrayList<>();
                for (int j = 0; j < s.split("\\s+").length; j++) {
                    System.out.println(s.split("\\s+")[j]);
                    for (Particle particle : parsedParticles.values()) {
                        if (particle.alias.equals(s.split("\\s+")[j].trim()) || particle.name.equals(s.split("\\s+")[j].trim())) {
                            //System.out.println("dobavlyaem chasticu s imenem: " + particle.name + " alias: " + particle.alias);
                            possibleDecayParticles.add(particle);
                        }
                    }
                }
                //System.out.println("Possible decay particles: " + possibleDecayParticles);
                result.add(possibleDecayParticles);
            }
            /*
            for (String combination : fstateCombination) {
                if (!allFstateCombinations.contains(combination))
                    allFstateCombinations.add(combination);
            }
             */
        }
//        System.out.println(finalResult);
//        System.out.println("result: " + result);
//        for (int i = fstate.size(); i > 0; i--) {
//            for (int j = i * 3; j < result.size(); j++) {
//                 if (almostFinalResult.size() >= i){
//                     break;
//                 } else {
//                     System.out.println("dobavlyaem v array: " + result.get(j));
//                     almostFinalResult.addAll(result.get(j));
//                     System.out.println("vot chto poluchilos': " + almostFinalResult);
//                 }
//            }
//            finalResult.add(almostFinalResult);
//            System.out.println("final result: " + finalResult);
//            almostFinalResult.clear();
//        }
        System.out.println(combinations(3, 1));
        finalResult.addAll(resultProcessor(result, fstate));
        System.out.println("final result: " + finalResult);
        System.out.println(resultProcessor(result, fstate));
        System.out.println();
        for (ArrayList list : finalResult) {
            System.out.println("opa che:  " + list);
            System.out.println("asdasdasjklasdkj");
        }
        return result;
    }

    public static int calculateFactorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result = result * i;
        }
        return result;
    }

    public static int combinations(int n, int m) {
        return (calculateFactorial((n)) / (calculateFactorial(n - m) * calculateFactorial(m)));
    }

    public ArrayList resultProcessor(ArrayList<ArrayList<Particle>> result, ArrayList<String> fstate) {
        ArrayList<ArrayList<Particle>> particleCombinations = new ArrayList<>();
        particleCombinations.addAll(result);
        ArrayList preParticles = new ArrayList();
        ArrayList<ArrayList> finalCombinations = new ArrayList<>();
        for (int i = 1; i <= fstate.size(); i++) {
            System.out.println("COMBINATIONS FOR: " + (combinations(fstate.size(), i)));
            System.out.println("COMBINATIONS FROM: " + (0));
            preParticles.addAll(particleCombinations.subList(0, combinations(fstate.size(), i)));
            particleCombinations.removeAll(particleCombinations.subList(0, combinations(fstate.size(), i)));
            System.out.println(preParticles);
            finalCombinations.retainAll(preParticles);
            System.out.println("final combinations: " + finalCombinations);
            preParticles.clear();
        }
        return finalCombinations;
    }
}


