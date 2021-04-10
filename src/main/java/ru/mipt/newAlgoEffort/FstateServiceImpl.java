package ru.mipt.newAlgoEffort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mipt.Decay;
import ru.mipt.Particle;
import ru.mipt.dao.DecayRepository;
import ru.mipt.dao.ParticleRepository;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Component
@RequiredArgsConstructor
public class FstateServiceImpl implements FstateService {
    private final ParticleRepository particlerepository;
    private final DecayRepository decayRepository;
    private final CascadesComputer computer;

    @Override
    public List<Decay> computeCascades(String fstate) {
        List<Particle> particles = parseParticles(fstate);
        return computer.computeCascades(particles, particlerepository, decayRepository);
    }

    private List<Particle> parseParticles(String fstate) {
        List<String> particles = asList(fstate.split(" "));
        return particles.stream().map(particlerepository::getParticleByName).collect(Collectors.toList());
    }
}
