package ru.mipt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Particle implements Serializable {
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
    public String toString(){
        return this.name;
    }
}
