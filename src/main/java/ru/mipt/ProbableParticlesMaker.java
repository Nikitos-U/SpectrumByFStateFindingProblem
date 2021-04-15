package ru.mipt;

import ru.mipt.utils.DoubleKeyHashMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProbableParticlesMaker {
    private final DoubleKeyHashMap parsedDecays;


    public ProbableParticlesMaker(DoubleKeyHashMap parsedDecays) {
        this.parsedDecays = parsedDecays;
    }

    public Map<Particle, Decay> combinationsToParticles(List<String> ids) {
        Map<Particle, Decay> probableParticles = new HashMap<>();
        String contactedIds = ids.stream().reduce("", (x, y) -> x + " " + y, String::concat).trim();
        if (parsedDecays.containsSecondKey(contactedIds)){
            parsedDecays.getBySecondKey(contactedIds).stream()
                    .filter(Objects::nonNull)
                    .forEach(motherParticle -> probableParticles.put(motherParticle,
                            parsedDecays.getByFirstKey(motherParticle.getId().toString() + ":" + contactedIds)));
        }
        return probableParticles;
    }
}