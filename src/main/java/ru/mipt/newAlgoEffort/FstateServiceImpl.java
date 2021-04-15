package ru.mipt.newAlgoEffort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mipt.Cascade;
import ru.mipt.DecaysFinder;
import ru.mipt.dao.ParticleRepository;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Component
@RequiredArgsConstructor
public class FstateServiceImpl implements FstateService {
    private final ParticleRepository particlerepository;
    private final DecaysFinder finder;

    @Override
    public List<Cascade> computeCascades(String fstateString) {
        Cascade fstate = parseParticles(fstateString);
        return finder.findDecays(fstate);
    }

    private Cascade parseParticles(String fstateString) {
        List<String> particles = asList(fstateString.split(" "));
        Cascade fstate = new Cascade();
        fstate.setParticleList(particles.stream().map(particlerepository::getParticleByName).collect(Collectors.toList()));
        return fstate;
    }
}
