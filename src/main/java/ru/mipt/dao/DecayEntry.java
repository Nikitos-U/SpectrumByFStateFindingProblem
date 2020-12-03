package ru.mipt.dao;


import lombok.Value;
import ru.mipt.Particle;

import java.util.ArrayList;

@Value
public class DecayEntry {
    ArrayList<Particle> particles;
    Particle motherParticle;
    Double probability;
    Double mass = 0.0;
}
