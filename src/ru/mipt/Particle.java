package ru.mipt;

public class Particle {
    final String name;
    String alias;
    final Double mass;
//   TODO: antiparticle, charge

    public Particle(String name, Double mass) {
        this.name = name;
        this.mass = mass;
        this.alias = name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
