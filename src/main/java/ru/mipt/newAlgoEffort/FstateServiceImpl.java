package ru.mipt.newAlgoEffort;

import lombok.RequiredArgsConstructor;
import ru.mipt.Cascade;
import ru.mipt.Decay;
import ru.mipt.Particle;
import ru.mipt.dao.FstateRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FstateServiceImpl implements FstateService{
    private final FstateRepository Particlerepository;
    private final FstateRepository DecayRepository;

    @Override
    public List<Cascade> computeCascades(List<String> fstate) {
        List<Particle> particles = parseParticles(fstate);
        return null;
    }

    private List<Particle> parseParticles(List<String> fstate) {
        return fstate.stream().map(Particlerepository::getParticleByName).collect(Collectors.toList());
    }
}
