package ru.mipt;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cascade {
    private ArrayList<Particle> particleList;
    private List<Decay> history;
    private Double mass = 0.0;

    public Cascade() {
        this.particleList = new ArrayList<>();
        this.history = new ArrayList<>();
        this.mass = 0.0;
    }

    public Cascade(ArrayList<Particle> particleList, List<Decay> history) {
        this.particleList = particleList;
        this.history = history;
        for (Particle particle : particleList) {
            this.mass += particle.getMass();
        }
    }

    public Double getMass(){
        for (Particle particle : particleList) {
            this.mass += particle.getMass();
        }
        return this.mass;
    }

    @Override
    public String toString() {
        return "Cascade with particle list: " + this.particleList
                + " and history: " + this.history;
    }
}
