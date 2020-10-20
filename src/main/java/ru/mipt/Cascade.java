package ru.mipt;

import lombok.Value;

import java.util.ArrayList;

@Value
public class Cascade {
    ArrayList<Particle> particleList;
    ArrayList<Decay> history;

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
