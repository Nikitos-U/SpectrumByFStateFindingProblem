package ru.mipt;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;


@Data
@EqualsAndHashCode
public class Particle {
    private final String name;
    private ArrayList<String> aliases = new ArrayList<>();
    private final Double mass;
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
}
