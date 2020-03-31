package ru.mipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MassCounter {
    public static Double countMass (List<Particle> fstateParticles){
        Double fstateMass = 0.0;
        for (Particle fstateParticle : fstateParticles) {
            fstateMass += fstateParticle.mass;
        }
        return fstateMass;
    }
}
