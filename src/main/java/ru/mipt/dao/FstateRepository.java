package ru.mipt.dao;

import ru.mipt.domain.Cascade;
import ru.mipt.domain.Decay;
import ru.mipt.domain.Particle;

import java.util.List;

public interface FstateRepository {
     void saveParticle(Particle particle);

     void saveDecay(Decay decay);

     void saveCascade(Cascade cascade);

     List<Particle> getParticles();

     List<Decay> getDecays();

     List<Cascade> getCascades();
}
