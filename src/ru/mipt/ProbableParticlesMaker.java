package ru.mipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProbableParticlesMaker {
//    private HashMap<String, Decay> parsedDecays;
//    private HashMap<String,Particle> parsedParticles;
//
//
//    public ProbableParticlesMaker(HashMap<String, Decay> parsedDecays, HashMap<String,Particle> parsedParticles) {
//        this.parsedDecays = parsedDecays;
//        this.parsedParticles = parsedParticles;
//    }
//
//    public Map<Particle, Decay> convertCombinationsToParticles(ArrayList<Particle> particles) {
//        Map<Particle, Decay> probableParticles = new HashMap<>();
//        for (String s : parsedDecays.keySet()) {
//            String tmp = s.split(" ")[1];
//            Integer counter = 0;
//            if (Integer.parseInt(tmp) == particles.size()) {
//                for (Particle particle : particles) {
//                    if (s.contains(particle.name) || s.contains(particle.alias)) {
//                        counter++;
//                    }
//                }
//                if (counter == particles.size()) {
//                    probableParticles.put(Decay.motherParticle, Decay);
//                }
//            }
//        }
//        return probableParticles;
//    }
}
