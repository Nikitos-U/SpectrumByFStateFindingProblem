package ru.mipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.quote;

public class ProbableParticlesMaker {
    private final HashMap<String, Decay> parsedDecays;


    public ProbableParticlesMaker(HashMap<String, Decay> parsedDecays) {
        this.parsedDecays = parsedDecays;
    }

    public Map<Particle, Decay> combinationsToParticles(ArrayList<Particle> particles) {
        Map<Particle, Decay> probableParticles = new HashMap<>();
        for (String s : parsedDecays.keySet()) {
            String s1 = s.split(":")[1];
            String tmp = s.split(" ")[1];
            int counter = 0;
            if (parseInt(tmp) == particles.size()) {
                for (Particle particle : particles) {
                    if (particle.getAliases().stream().anyMatch(s1::contains)) {
                        s1 = s1.replaceFirst(quote(particle.getAliases().stream()
                                .filter(s1::contains)
                                .findFirst()
                                .orElse("")), "");
                        System.out.println("Result s1: " + s1);
                        System.out.println();
                        counter++;
                    }
                }
                if (counter == particles.size()) {
                    probableParticles.put(parsedDecays.get(s).getMotherParticle(), parsedDecays.get(s));
                }
            }
        }
        return probableParticles;
    }
}