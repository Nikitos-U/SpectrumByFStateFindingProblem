package ru.mipt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Particle implements Comparable<Particle> {
    private final String name;
    private final List<String> aliases;
    private final Double mass;
    @EqualsAndHashCode.Include
    private final Integer id;
//   TODO: antiparticle, charge

    public Particle(@JsonProperty("name") String name,
                    @JsonProperty("mass") Double mass,
                    @JsonProperty("aliases") List<String> aliases,
                    @JsonProperty("id") Integer id) {
        this.id = id;
        this.name = name;
        this.mass = mass;
        this.aliases = aliases;
    }

    public void addAlias(String alias) {
        if (!aliases.contains(alias)) {
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
