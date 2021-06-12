package ru.mipt;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.stereotype.Component;
import ru.mipt.domain.Cascade;
import ru.mipt.domain.Decay;
import ru.mipt.domain.Particle;

import java.util.*;

import static java.util.Collections.sort;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.collections4.CollectionUtils.subtract;

@Component
@RequiredArgsConstructor
public class ParticleCombinator {
    @Setter
    private Map<List<Particle>, List<Cascade>> memo;
    @Setter
    private Map<String, Particle> parsedParticles;
    @Setter
    private MultiValuedMap<List<Particle>, Decay> parsedDecays;
    @Setter
    private Cascade initialFstate;

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

    ArrayList<Cascade> allCombinations(Cascade cascade, ProbableParticlesMaker probableParticlesMaker, Map<String, Particle> parsedParticles, MultiValuedMap<List<Particle>, Decay> parsedDecays, Map<List<Particle>, List<Cascade>> memo) {
        setParsedDecays(parsedDecays);
        setParsedParticles(parsedParticles);
        setMemo(memo);
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
//        if (memo.containsKey(possibleDecayParticles)) {
//            cascades.addAll(memo.get(possibleDecayParticles));
//            return;
//        }
        Map<Particle, Decay> mpsFromCombinations = probableParticlesMaker.combinationsToParticles(possibleDecayParticles, parsedDecays);
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
                .collect(toList());
    }

    private List<Cascade> getMissedParticlesCascades(List<Particle> possibleDecayParticles, Map<Particle, Decay> mpsFromCombinations, Cascade cascade, List<Cascade> cascades, ArrayList<String> fstate) {
        List<Cascade> probableCascades = new ArrayList<>();
        int counter = possibleDecayParticles.size();
        Set<Particle> foundParticles = mpsFromCombinations.values().stream().flatMap(decay -> decay.getParticles().stream()).collect(toSet());
        if (memo.containsKey(foundParticles)) {
            List<Cascade> formedCascades = memo.get(foundParticles);
            for (Cascade formedCascade : formedCascades) {
                Collection<Particle> missedParticles = subtract(cascade.getParticleList(), possibleDecayParticles);
                missedParticles.forEach(formedCascade::addToParticleList);
                counter += missedParticles.size();
                probableCascades.add(formedCascade);
            }
        } else {
            for (Decay decay : mpsFromCombinations.values()) {
                Cascade formedCascade = createAndFillNewCascade(cascade, decay);
                Collection<Particle> missedParticles = subtract(cascade.getParticleList(), possibleDecayParticles);
                missedParticles.forEach(formedCascade::addToParticleList);
                counter += missedParticles.size();
                probableCascades.add(formedCascade);
            }
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
        List<Particle> initialParticles = history.stream().map(Decay::getParticles).flatMap(Collection::stream).filter(initialFstate.getParticleList()::contains).collect(toList());
        ArrayList<Particle> particleList = new ArrayList<>();
        particleList.add(decay.getMotherParticle());
        sort(initialParticles);
        return new Cascade(particleList, history, initialParticles);
    }
}