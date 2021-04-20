package ru.mipt;

import com.google.common.collect.Table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProbableParticlesMaker {
    private final Table<Particle, List<Particle>, Decay> parsedDecays;


    public ProbableParticlesMaker(Table<Particle, List<Particle>, Decay> parsedDecays) {
        this.parsedDecays = parsedDecays;
    }

    public Map<Particle, Decay> combinationsToParticles(List<Particle> particles) {
        return parsedDecays.containsColumn(particles) ? parsedDecays.column(particles) : new HashMap<>();
    }
}