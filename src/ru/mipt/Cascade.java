package ru.mipt;

import java.util.ArrayList;

public class Cascade {
    ArrayList<Particle> particleList;
    ArrayList<Decay> history;

    public Cascade(ArrayList<Particle> particleList, ArrayList<Decay> history) {
        this.particleList = particleList;
        this.history = history;
    }

    public Cascade() {
        this.particleList = new ArrayList<>();
        this.history = new ArrayList<>();
    }

    @Override
    public String toString(){
        return "Cascade with particle list: " + this.particleList
                + " and history: " + this.history;
    }
}
