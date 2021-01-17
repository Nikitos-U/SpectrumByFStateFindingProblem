package ru.mipt.dao;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class DecayEntry {
    private String particles;
    private String motherParticle;
    private Double probability;
    private Double mass;
}
