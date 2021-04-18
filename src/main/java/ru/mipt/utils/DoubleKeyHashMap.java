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
    private final Map<Integer, Decay> key1Map;
    private final Map<List<Particle>, List<Particle>> key2Map;

    public DoubleKeyHashMap() {
        key1Map = new HashMap<>();
        key2Map = new HashMap<>();
    }

    public void addMotherParticle(Particle motherParticle, List<Particle> particles){
        if (!key2Map.containsKey(particles)) {
            key2Map.put(particles, new ArrayList<>());
        }
        key2Map.get(particles).add(motherParticle);
    }

    public Decay getByFirstKey(Integer id){
        return key1Map.get(id);
    }

    public List<Particle> getBySecondKey(List<Particle> key){
        return key2Map.get(key);
    }

    public void putByFirstKey(Integer id, Decay decay){
        key1Map.put(id, decay);
    }

    public Boolean containsFirstKey(Integer id){
        return key1Map.containsKey(id);
    }

    public Boolean containsSecondKey(List<Particle> key){
        return key2Map.containsKey(key);
    }
}
