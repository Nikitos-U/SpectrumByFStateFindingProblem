package ru.mipt;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.sort;
import static org.apache.commons.collections4.CollectionUtils.subtract;

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
        for (int i = 2; i <= fstate.size(); i++) {
            ArrayList<String> fstateCombination = combinations2(fstate, i, 0, new String[i]);
            fstateCombination.stream()
                    .map(this::stringToParticles)
                    .forEach(possibleDecayParticles -> getAndStoreAllCascades(cascade, possibleDecayParticles, fstate, probableParticlesMaker, cascades));
        }
        return cascades;
    }

    private void getAndStoreAllCascades(Cascade cascade, List<Particle> possibleDecayParticles, ArrayList<String> fstate, ProbableParticlesMaker probableParticlesMaker, List<Cascade> cascades) {
        Map<Particle, Decay> mpsFromCombinations = probableParticlesMaker.combinationsToParticles(possibleDecayParticles);
        List<Cascade> probableCascades;
        if (!mpsFromCombinations.isEmpty()) {
            probableCascades = possibleDecayParticles.equals(cascade.getParticleList()) ?
                    getAllParticlesCascades(mpsFromCombinations, cascade, cascades) :
                    getMissedParticlesCascades(possibleDecayParticles, mpsFromCombinations, cascade, cascades, fstate);
            probableCascades.stream()
                    .filter(Objects::nonNull)
                    .filter(probableCascade -> !cascades.contains(probableCascade))
                    .forEach(cascades::add);
        }
    }

    private List<Cascade> getAllParticlesCascades(Map<Particle, Decay> mpsFromCombinations, Cascade cascade, List<Cascade> cascades) {
        return mpsFromCombinations.values().stream()
                .map(decay -> createAndFillNewCascade(cascade, decay))
                .collect(Collectors.toList());
    }

    private List<Cascade> getMissedParticlesCascades(List<Particle> possibleDecayParticles, Map<Particle, Decay> mpsFromCombinations, Cascade cascade, List<Cascade> cascades, ArrayList<String> fstate) {
        List<Cascade> probableCascades = new ArrayList<>();
        int counter = possibleDecayParticles.size();
        for (Decay decay : mpsFromCombinations.values()) {
            Cascade formedCascade = createAndFillNewCascade(cascade, decay);
            Collection<Particle> missedParticles = subtract(cascade.getParticleList(), possibleDecayParticles);
            missedParticles.forEach(formedCascade::addToParticleList);
            counter += missedParticles.size();
            probableCascades.add(formedCascade);
        }
        if (counter >= fstate.size()) {
            return probableCascades;
        }
        return new ArrayList<>();
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
        sort(possibleDecayParticles);
        return possibleDecayParticles;
    }

    private Cascade createAndFillNewCascade(Cascade cascade, Decay decay) {
        ArrayList<Decay> history = new ArrayList<>();
        history.add(decay);
        history.addAll(cascade.getHistory());
        ArrayList<Particle> particleList = new ArrayList<>();
        particleList.add(decay.getMotherParticle());
        return new Cascade(particleList, history);
    }
}