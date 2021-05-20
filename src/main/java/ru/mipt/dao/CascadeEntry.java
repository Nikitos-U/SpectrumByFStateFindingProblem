package ru.mipt.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
