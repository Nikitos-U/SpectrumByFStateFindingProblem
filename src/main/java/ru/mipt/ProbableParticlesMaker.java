package ru.mipt;

import ru.mipt.utils.DoubleKeyHashMap;
import java.util.*;
import com.google.common.collect.Lists;

public class ProbableParticlesMaker {
    private final DoubleKeyHashMap parsedDecays;


    public ProbableParticlesMaker(DoubleKeyHashMap parsedDecays) {
        this.parsedDecays = parsedDecays;
    }

    public Map<Particle, Decay> combinationsToParticles(ArrayList<Particle> particles) {
        Map<Particle, Decay> probableParticles = new HashMap<>();
//        for (String s : parsedDecays.getKey1Map().keySet()) {
//            String s1 = s.split(":")[1];
//            String tmp = s.split(" ")[1];
//            int counter = 0;
//            if (Integer.parseInt(tmp) == particles.size()) {
//                for (Particle particle : particles) {
//                    if (particle.getAliases().stream().anyMatch(s1::contains)) {
//                        counter++;
//                    }
//                }
//                if (counter == particles.size()) {
//                    probableParticles.put(parsedDecays.getKey1Map().get(s).getMotherParticle(), parsedDecays.getKey1Map().get(s));
//                }
//            }
//        }

        ArrayList<Particle> motherParticles = new ArrayList<>();
        ArrayList<ArrayList<String>> allAliases = new ArrayList<>();
        for (Particle particle : particles) {
            allAliases.add(particle.getAliases());
        }
        Lists.cartesianProduct(allAliases).stream()
                .map(particlesArray -> parsedDecays.getBySecondKey(particlesArray.toString()))
                .filter(Objects::nonNull)
                .forEach(motherParticles::addAll);
        for (Particle motherParticle : motherParticles) {
            for (String motherParticleAlias : motherParticle.getAliases()) {
                String key = motherParticleAlias + ":" + particles.toString()
                        .substring(1, particles.toString().length() - 1)
                        .replace(" ", "");
                if (parsedDecays.containsFirstKey(key)) {
                    probableParticles.put(motherParticle, parsedDecays.getByFirstKey(key));
                }
            }
        }
        return probableParticles;
    }
}