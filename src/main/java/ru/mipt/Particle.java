package ru.mipt;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Particle {
    private final String name;
    private ArrayList<String> aliases = new ArrayList<>();
    private final Double mass;
//   TODO: antiparticle, charge

    public Particle(String name, Double mass) {
        this.name = name;
        this.mass = mass;
        this.aliases.add(name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
