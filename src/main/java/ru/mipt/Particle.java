package ru.mipt;

import lombok.Data;

@Data
public class Particle {
    private final String name;
    private String alias;
    private final Double mass;
//   TODO: antiparticle, charge

    public Particle(String name, Double mass) {
        this.name = name;
        this.mass = mass;
        this.alias = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
