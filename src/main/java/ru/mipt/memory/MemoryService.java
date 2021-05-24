package ru.mipt.memory;

import ru.mipt.domain.Decay;
import ru.mipt.domain.Particle;

public interface MemoryService {

    void saveNewParticle(Particle particle);

    void saveNewDecay(Decay decay);
}
