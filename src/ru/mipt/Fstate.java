package ru.mipt;

import java.util.ArrayList;

public class Fstate {
    private ArrayList<Particle> fstate;

    public Fstate(ArrayList<Particle> fstate) {
        this.fstate = fstate;
    }

    public ArrayList<Particle> getFstate() {
        return fstate;
    }
}
