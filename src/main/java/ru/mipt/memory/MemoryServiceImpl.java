package ru.mipt.memory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mipt.dao.FstateRepository;
import ru.mipt.domain.Decay;
import ru.mipt.domain.Particle;

@Component
@RequiredArgsConstructor
public class MemoryServiceImpl implements MemoryService {
    private final FstateRepository repository;

    @Override
    public void saveNewParticle(Particle particle) {
        repository.saveParticle(particle);
    }

    @Override
    public void saveNewDecay(Decay decay) {
        repository.saveDecay(decay);
    }

}