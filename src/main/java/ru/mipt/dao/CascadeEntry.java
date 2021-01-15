package ru.mipt.dao;

import lombok.*;

import java.sql.Array;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CascadeEntry {
    private Array particles;
    private Array history;
    private double mass;
}
