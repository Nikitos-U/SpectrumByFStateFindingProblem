package ru.mipt.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

@Data
public class Cascade {
    private final List<Particle> particleList;
    private final List<Decay> history;
    private final Double mass;
    private final List<Particle> initialFstateParticles;
    private final int id;


    public Cascade() {
        this.particleList = new ArrayList<>();
        this.history = new ArrayList<>();
        this.mass = 0.0;
        this.id = this.hashCode();
        this.initialFstateParticles = new ArrayList<>();
    }

    public Cascade(List<Particle> particleList, List<Decay> history) {
        this.particleList = particleList;
        sort(this.particleList);
        this.history = history;
        Double mass = 0.0;
        for (Particle particle : particleList) {
            mass += particle.getMass();
        }
        this.mass = mass;
        this.id = this.hashCode();
        this.initialFstateParticles = new ArrayList<>();
    }

    public Cascade(List<Particle> particleList, List<Decay> history, List<Particle> initialFstateParticles) {
        this.particleList = particleList;
        sort(this.particleList);
        this.history = history;
        Double mass = 0.0;
        for (Particle particle : particleList) {
            mass += particle.getMass();
        }
        this.mass = mass;
        this.id = this.hashCode();
        this.initialFstateParticles = initialFstateParticles;
    }




    public void addToParticleList(Particle particle){
        this.particleList.add(particle);
    }

    @Override
    public String toString() {
        return "Cascade with particle list: " + this.particleList
                + " and history: " + this.history;
    }
}
