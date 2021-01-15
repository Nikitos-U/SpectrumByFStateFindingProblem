package ru.mipt.dao;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Array;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class DecayEntry {
    private Array particles;
    private Object motherParticle;
    private Double probability;
    private Double mass = 0.0;
}
