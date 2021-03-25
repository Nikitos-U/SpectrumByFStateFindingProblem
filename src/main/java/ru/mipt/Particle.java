package ru.mipt;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

@Getter
@Setter
@AllArgsConstructor
public class Particle implements Serializable {
    @EqualsAndHashCode.Include
    private long id;
    private final String name;
    private ArrayList<String> aliases = new ArrayList<>();
    private final Double mass;
//   TODO: antiparticle, charge

    public Particle(long id, String name, Double mass) {
        this.id = id;
        this.name = name;
        this.mass = mass;
        this.aliases.add(name);
    }

    @Override
    public String toString(){
        return this.name;
    }
}
