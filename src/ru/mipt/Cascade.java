package ru.mipt;

import java.util.List;

public class Cascade {
    private List<Particle> fstate;
    private List<Decay> history;

    public Cascade(List<Particle> fstate, List<Decay> history) {
        this.fstate = fstate;
        this.history = history;
    }
}
