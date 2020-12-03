package ru.mipt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Particle {
    private final String name;
    private String alias;
    private final Double mass;
//   TODO: antiparticle, charge

    @Override
    public String toString(){
        return this.name;
    }
}
