package ru.mipt;

import java.util.List;

class MassCounter {
    static Double countMass(List<Particle> fstateParticles){
        Double fstateMass = 0.0;
        for (Particle fstateParticle : fstateParticles) {
            fstateMass += fstateParticle.getMass();
        }
        return fstateMass;
    }
}
