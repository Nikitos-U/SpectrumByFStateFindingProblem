package ru.mipt;

import org.apache.commons.collections4.MultiValuedMap;
import ru.mipt.domain.Cascade;
import ru.mipt.domain.Decay;
import ru.mipt.domain.Particle;

import java.util.List;
import java.util.Map;

public interface FstateService {
    List<Cascade> computeCascades(String fstate, Map<String, Particle> particles,
                                  MultiValuedMap<List<Particle>, Decay> decays);
}
