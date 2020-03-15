package ru.mipt;

import java.util.ArrayList;

public class Cascade {
    private Fstate fstate;
    private ArrayList<Decay> history;

    public Cascade(Fstate fstate, ArrayList<Decay> history) {
        this.fstate = fstate;
        this.history = history;
    }
}
