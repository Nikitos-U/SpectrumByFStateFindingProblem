package ru.mipt.newAlgoEffort;

import lombok.AllArgsConstructor;
import ru.mipt.Cascade;
import ru.mipt.Decay;
import ru.mipt.Particle;
import ru.mipt.dao.FstateRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class CascadesComputer {
    private final List<Decay> motherParticles;

    public List<Cascade> computeCascades(List<Particle> fstate, FstateRepository repository, List<Cascade> cascades) {
        List<Decay> finalDecays;
        do {
            finalDecays = motherParticlesCreator(fstate, repository, fstate);
        } while (!finalDecays.isEmpty());
        return toCascades(finalDecays);
    }

    private List<Decay> motherParticlesCreator(List<Particle> fstate, FstateRepository repository, List<Particle> checkList) {
        try {
            motherParticles
                    .addAll(fstate.stream()
                            .map(repository::getChilds)
                            .flatMap(Collection::stream)
                            .collect(toList()));
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
        List<List<Particle>> childsOfmothers = motherParticles
                .stream()
                .map(Decay::getParticles)
                .flatMap(Collection::stream)
                .map(repository::getMothers).collect(toList());
        childsOfmothers = checkParticles(childsOfmothers, checkList);
        List<Decay> decays = childsOfmothers
                .stream()
                .map(repository::getDecaysByParticles)
                .flatMap(Collection::stream)
                .collect(toList());
        List<Particle> stepMotherParticles = decays.stream().map(Decay::getMotherParticle).collect(toList());
        checkList.addAll(stepMotherParticles);
        return decays;
    }

    private List<List<Particle>> checkParticles(List<List<Particle>> childsOfMothers, List<Particle> checkList) {
        childsOfMothers = childsOfMothers.stream().filter(checkList::contains).collect(toList());
        return childsOfMothers;
    }
    private List<Cascade> toCascades(List<Decay> decays) {
        return null;
    }
}
