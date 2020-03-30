package ru.mipt;

import java.util.ArrayList;
import java.util.List;

public class Cascade {
    List fstate;
    ArrayList<Decay> history;

    public Cascade(List fstate, ArrayList<Decay> history) {
        this.fstate = fstate;
        this.history = history;
    }
}
