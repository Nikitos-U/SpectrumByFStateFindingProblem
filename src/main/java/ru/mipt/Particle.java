package ru.mipt;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Particle implements Comparable<Particle> {
    private final String name;
    private ArrayList<String> aliases = new ArrayList<>();
    private final Double mass;
    @EqualsAndHashCode.Include
    private final Integer id;
//   TODO: antiparticle, charge

    public Particle(String name, Double mass, Integer id) {
        this.name = name;
        this.mass = mass;
        this.aliases.add(name);
        this.id = id;
    }

    public void addAlias(String alias){
        if (!aliases.contains(alias)){
            aliases.add(alias);
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Particle particle) {
        return this.getId() < particle.getId() ? 0 : -1;
    }
}
