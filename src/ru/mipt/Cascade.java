package ru.mipt;

import java.util.ArrayList;

public class Cascade {
    ArrayList<Particle> particleList;
    ArrayList<Decay> history;

    public ArrayList<Particle> getParticleList() {
        return particleList;
    }

    public ArrayList<Decay> getHistory() {
        return history;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cascade){
            return ((Cascade) obj).particleList.equals(this.particleList) && ((Cascade) obj).history.equals(this.history);
        } else {
            return super.equals(obj);
        }
    }

}
