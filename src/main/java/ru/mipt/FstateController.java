package ru.mipt;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mipt.domain.Cascade;
import ru.mipt.domain.Decay;
import ru.mipt.domain.Particle;
import ru.mipt.memory.MemoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class FstateController {
    private final FstateService service;
    private final MemoryService memoryService;
    private final Map<String, Particle> particles;
    private final MultiValuedMap<List<Particle>, Decay> decays;

    @RequestMapping("/compute")
    public String computeFstate(@RequestParam String fstate) {
        Map<List<Particle>, List<Cascade>> memo = new HashMap<>();
        return service.computeCascades(fstate, particles, decays, memo).toString();
    }

    @RequestMapping("/saveParticle")
    public void saveParticle(@RequestParam String name, @RequestParam Double mass, @RequestParam String aliases) {
        Particle particle = new Particle(name, mass, asList(aliases), (int) particles.keySet().stream().count() + 1);
        memoryService.saveNewParticle(particle);
        particles.put(name,particle);
    }

    @RequestMapping("/saveDecay")
    public void saveDecay(@RequestParam String motherParticle, @RequestParam String products, @RequestParam Double probability) {
        List<String> decayProducts = asList(products.split(","));
        List<Particle> particlesList = decayProducts.stream().map(particles::get).collect(toList());
        Decay decay = new Decay(particles.get(motherParticle.toLowerCase()), particlesList, probability);
        memoryService.saveNewDecay(decay);
        decays.put(decay.getParticles(), decay);
    }
}
