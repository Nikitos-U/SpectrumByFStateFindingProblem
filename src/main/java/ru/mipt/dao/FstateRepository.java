package ru.mipt.dao;

import ru.mipt.Decay;
import ru.mipt.Particle;

import java.util.List;

public interface FstateRepository {
    void saveParticle(ParticleEntry entry);
    Particle getParticleByName(String name);
    List<Decay> getChilds(Particle particle);
    List<Particle> getMothers(Particle particle);
    List<Decay> getDecaysByParticles(List<Particle> particles);
}
