package ru.mipt.newAlgoEffort;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mipt.Cascade;
import ru.mipt.Decay;
import ru.mipt.Particle;
import ru.mipt.dao.DecayRepository;
import ru.mipt.dao.ParticleRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class CascadesComputer {
    private final List<Particle> motherParticles;

    public List<Decay> computeCascades(List<Particle> fstate, ParticleRepository particleRepository, DecayRepository decayRepository) {
        List<Decay> finalDecays;
        do {
            finalDecays = motherParticlesCreator(fstate, particleRepository, decayRepository, fstate);
            return finalDecays;
        } while (!finalDecays.isEmpty());
    }

    private List<Decay> motherParticlesCreator(List<Particle> fstate, ParticleRepository particleRepository, DecayRepository decayRepository, List<Particle> checkList) {
        try {
            motherParticles
                    .addAll(fstate.stream()
                            .map(particleRepository::getMothers)
                            .flatMap(Collection::stream)
                            .collect(toList()));
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
        List<Decay> decaysFromMothers = motherParticles
                .stream()
                .map(decayRepository::getMothersAsDecays)
                .flatMap(Collection::stream)
                .collect(toList());
        List<List<Particle>> childsOfmothers = decaysFromMothers
                .stream()
                .map(Decay::getParticles)
                .flatMap(Collection::stream)
                .map(particleRepository::getMothers).collect(toList());
        childsOfmothers = checkParticles(childsOfmothers, checkList);
        List<Decay> decays = childsOfmothers
                .stream()
                .map(decayRepository::getDecaysByParticles)
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
//        for (Decay decay : decays) {
//            for (Particle particle : decay.getParticles()) {
//                for (Decay decay1 : decays) {
//                    if (decay1.getMotherParticle().equals(particle)) {
//
//                    }
//                }
//            }
//        }
        return null;
    }
}
