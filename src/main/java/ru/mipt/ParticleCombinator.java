package ru.mipt;

import java.util.*;

import static java.util.Arrays.asList;

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
        cascade.getParticleList().forEach(particle -> fstate.add(particle.getId().toString()));
        System.out.println("Processing this fstate: " + fstate);
        for (int i = 2; i <= fstate.size(); i++) {
            ArrayList<String> fstateCombination = combinations2(fstate, i, 0, new String[i]);
            for (String s : fstateCombination) {
                List<Particle> possibleDecayParticles = stringToParticles(s);
                Map<Particle, Decay> mpsFromCombinations = probableParticlesMaker.combinationsToParticles(asList(s.trim().split("\\s+")));
                if (possibleDecayParticles.equals(cascade.getParticleList())) {
                    mpsFromCombinations.values().stream()
                            .map(decay -> createAndFillNewCascade(cascade, decay))
                            .filter(formedCascade -> !cascades.contains(formedCascade))
                            .forEach(cascades::add);
                } else {
                    List<Cascade> probableCascades = new ArrayList<>();
                    int counter = possibleDecayParticles.size();
                    if (mpsFromCombinations.isEmpty()) {
                        break;
                    }
                    for (Decay decay : mpsFromCombinations.values()) {
                        Cascade formedCascade = createAndFillNewCascade(cascade, decay);
                        counter += cascade.getParticleList().stream()
                                .filter(particle -> !possibleDecayParticles.contains(particle))
                                .map(particle -> formedCascade.getParticleList().add(particle))
                                .count();
                        probableCascades.add(formedCascade);
                    }
                    if (counter < fstate.size()) {
                        break;
                    } else {
                        probableCascades.stream()
                                .filter(cascade1 -> !cascades.contains(cascade1))
                                .forEach(cascades::add);
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

    private List<Particle> stringToParticles(String s) {
        List<Particle> possibleDecayParticles = new ArrayList<>();
        Arrays.stream(s.split("\\s+"))
                .map(key -> parsedParticles.values().stream()
                        .filter(particle -> key.equals(particle.getId().toString()))
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .forEach(possibleDecayParticles::add);
        return possibleDecayParticles;
    }

    private Cascade createAndFillNewCascade(Cascade cascade, Decay decay) {
        ArrayList<Decay> history = new ArrayList<>();
        history.add(decay);
        history.addAll(cascade.getHistory());
        return new Cascade(new ArrayList<>(asList(decay.getMotherParticle())), history);
    }
}