package ru.mipt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Particle implements Serializable {
    @EqualsAndHashCode.Include
    private long id;
    private final String name;
    private final List<String> aliases;
    private final Double mass;
//   TODO: antiparticle, charge

    public Particle(@JsonProperty("id") long id,
                    @JsonProperty("name") String name,
                    @JsonProperty("aliases") List<String> aliases,
                    @JsonProperty("mass") Double mass) {
        this.id = id;
        this.name = name;
        this.mass = mass;
        this.aliases = aliases;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
