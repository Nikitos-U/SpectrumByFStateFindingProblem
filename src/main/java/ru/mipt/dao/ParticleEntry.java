package ru.mipt.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ParticleEntry {
    private long id;
    private String name;
    private String alias;
    private double mass;
}
