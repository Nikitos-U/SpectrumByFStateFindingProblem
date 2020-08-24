package ru.mipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProbableParticlesMaker {
    private final HashMap<String, Decay> parsedDecays;


    public ProbableParticlesMaker(HashMap<String, Decay> parsedDecays) {
        this.parsedDecays = parsedDecays;
    }

    public Map<Particle, Decay> convertCombinationsToParticles(ArrayList<Particle> particles) {
        Map<Particle, Decay> probableParticles = new HashMap<>();
        for (String s : parsedDecays.keySet()) {
            String s1 = s.split(":")[1];
            String tmp = s.split(" ")[1];
            int counter = 0;
            if (Integer.parseInt(tmp) == particles.size()) {
                for (Particle particle : particles) {
                    if (s1.contains(particle.name) || s1.contains(particle.alias)) {
                        counter++;
                    }
                }
                if (counter == particles.size()) {
                    probableParticles.put(parsedDecays.get(s).motherParticle, parsedDecays.get(s));
                }
            }
        }
        return probableParticles;
    }
}