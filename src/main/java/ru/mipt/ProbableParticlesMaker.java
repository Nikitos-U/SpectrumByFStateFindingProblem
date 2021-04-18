package ru.mipt;

import ru.mipt.utils.DoubleKeyHashMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class ProbableParticlesMaker {
    private final DoubleKeyHashMap parsedDecays;


    public ProbableParticlesMaker(DoubleKeyHashMap parsedDecays) {
        this.parsedDecays = parsedDecays;
    }

    public Map<Particle, Decay> combinationsToParticles(List<Particle> particles) {
        Map<Particle, Decay> probableParticles = new HashMap<>();
        if (parsedDecays.containsSecondKey(particles)) {
            parsedDecays.getBySecondKey(particles).stream()
                    .filter(Objects::nonNull)
                    .forEach(motherParticle -> probableParticles.put(motherParticle,
                            parsedDecays.getByFirstKey(new Decay(particles, motherParticle).hashCode())));
        }
        return probableParticles;
    }
}