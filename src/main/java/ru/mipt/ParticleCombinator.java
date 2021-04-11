package ru.mipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParticleCombinator {
    private final HashMap<String, Particle> parsedParticles;

    public ParticleCombinator(HashMap<String, Particle> parsedParticles) {
        this.parsedParticles = parsedParticles;
    }

    public ArrayList<String> combinations2(ArrayList<String> fstate, int len, int startPosition, String[] result) {
        ArrayList<String> result1 = new ArrayList<>();
        if (len == 0) {
            StringBuilder s = new StringBuilder();
            for (String value : result) {
                s.append(" ").append(value);
            }
            result1.add(s.toString());
            return result1;
        }
        for (int i = startPosition; i <= fstate.size() - len; i++) {
            result[result.length - len] = fstate.get(i);
            result1.addAll(combinations2(fstate, len - 1, i + 1, result));
        }
        return result1;
    }

    ArrayList<Cascade> allCombinations(Cascade cascade, ProbableParticlesMaker probableParticlesMaker) {
        ArrayList<String> fstate = new ArrayList<>();
        ArrayList<Cascade> cascades = new ArrayList<>();
        cascade.getParticleList().forEach(particle -> fstate.add(particle.getName()));
        System.out.println("Processing this fstate: " + fstate);
        for (int i = 2; i <= fstate.size(); i++) {
            ArrayList<String> fstateCombination = combinations2(fstate, i, 0, new String[i]);
            for (String s : fstateCombination) {
                ArrayList<Particle> possibleDecayParticles = stringToParticles(s);
                Map<Particle, Decay> mpsFromCombinations = probableParticlesMaker.combinationsToParticles(possibleDecayParticles);
                if (possibleDecayParticles.containsAll(cascade.getParticleList())) {
                    mpsFromCombinations.values().stream()
                            .map(decay -> createAndFillNewCascade(cascade, decay))
                            .filter(formedCascade -> !cascades.contains(formedCascade))
                            .forEach(cascades::add);
                } else {
                    int counter = possibleDecayParticles.size();
                    while (counter < fstate.size()) {
                        if (mpsFromCombinations.isEmpty()) {
                            break;
                        }
                        for (Decay decay : mpsFromCombinations.values()) {
                            Cascade formedCascade = createAndFillNewCascade(cascade, decay);
                            counter += cascade.getParticleList().stream()
                                    .filter(particle -> !possibleDecayParticles.contains(particle))
                                    .map(particle -> formedCascade.getParticleList().add(particle))
                                    .count();
                            if (!cascades.contains(formedCascade)) {
                                cascades.add(formedCascade);
                            }
                        }
                    }
                }
                possibleDecayParticles.clear();
            }
        }
        System.out.println("++++++++++++++++++++++++++++++++Result combinations:++++++++++++++++++++++++++++++++");
        cascades.forEach(System.out::println);
        System.out.println("++++++++++++++++++++++++++++++++END++++++++++++++++++++++++++++++++");
        return cascades;
    }

    private ArrayList<Particle> stringToParticles(String s) {
        ArrayList<Particle> possibleDecayParticles = new ArrayList<>();
        for (int j = 0; j < s.split("\\s+").length; j++) {
            int finalJ = j;
            parsedParticles.values().stream()
                    .filter(particle -> particle.getAliases().contains(s.split("\\s+")[finalJ].trim()))
                    .forEach(possibleDecayParticles::add);
        }
        return possibleDecayParticles;
    }

    private Cascade createAndFillNewCascade(Cascade cascade, Decay decay) {
        Cascade formedCascade = new Cascade();
        formedCascade.getParticleList().add(decay.getMotherParticle());
        formedCascade.getHistory().add(decay);
        formedCascade.getHistory().addAll(cascade.getHistory());
        return formedCascade;
    }
}