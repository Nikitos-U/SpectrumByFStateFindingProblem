package ru.mipt.dao;


import lombok.Getter;
import lombok.SneakyThrows;
import ru.mipt.Decay;
import ru.mipt.Particle;
import ru.mipt.parsers.DecayParser;
import ru.mipt.parsers.ParticleParser;

import java.util.HashMap;

public class DBFiller {
    private final DecayParser decayParser;
    private final ParticleParser particleParser;
    private final DbPatcher patcher;
    @Getter
    private HashMap<String, Particle> particles = new HashMap<>();
    @Getter
    private HashMap<String, Decay> decays = new HashMap<>();

    public DBFiller(DecayParser decayParse, ParticleParser particleParser, DbPatcher patcher) {
        this.patcher = patcher;
        this.decayParser = decayParse;
        this.particleParser = particleParser;
    }

    @SneakyThrows
    public void parse() {
        this.patcher.createTables();
        this.particles = particleParser.parse();
        this.decays = decayParser.parse(this.particles);
    }
}
