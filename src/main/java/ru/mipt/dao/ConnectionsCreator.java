package ru.mipt.dao;

import lombok.RequiredArgsConstructor;
import ru.mipt.Decay;
import ru.mipt.Particle;

import java.util.Map;

@RequiredArgsConstructor
public class ConnectionsCreator {
    private final Map<String, Particle> particles;
    private final Map<String, Decay> Decays;

    public void createConnectionsInDb() {

    }
}
