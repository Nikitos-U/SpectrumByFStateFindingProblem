package ru.mipt.utils;

import lombok.Data;
import ru.mipt.Decay;
import ru.mipt.Particle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class DoubleKeyHashMap {
    private final Map<String, Decay> key1Map;
    private final Map<String, List<Particle>> key2Map;

    public DoubleKeyHashMap() {
        key1Map = new HashMap<>();
        key2Map = new HashMap<>();
    }

    public void addMotherParticle(Particle motherParticle, String particles){
        if (!key2Map.containsKey(particles)) {
            key2Map.put(particles, new ArrayList<>());
        }
        key2Map.get(particles).add(motherParticle);
    }

    public Decay getByFirstKey(String key){
        return key1Map.get(key);
    }

    public List<Particle> getBySecondKey(String key){
        return key2Map.get(key);
    }

    public void putByFirstKey(String key, Decay decay){
        key1Map.put(key, decay);
    }

    public Boolean containsFirstKey(String key){
        return key1Map.containsKey(key);
    }

    public Boolean containsSecondKey(String key){
        return key2Map.containsKey(key);
    }
}
