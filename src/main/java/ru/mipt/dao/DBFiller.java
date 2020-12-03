package ru.mipt.dao;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.mipt.Decay;
import ru.mipt.Particle;
import ru.mipt.parsers.DecayParser;
import ru.mipt.parsers.ParticleParser;

import java.io.IOException;
import java.util.HashMap;

@RequiredArgsConstructor
public class DBFiller {
    private final DecayParser decayParser;
    private final ParticleParser particleParser;
    private final JdbcTemplate template;

    public void fillDB() {
        initializeParsersAndParse();
    }

    private void initializeParsersAndParse() {
        HashMap<String, Particle> particles;
        HashMap<String, Decay> decays;
        try {
            particles = this.particleParser.parse();
            decays = this.decayParser.parse(particles);
            System.out.println("Decays parsed: " + decays.size());
            for (String s : particles.keySet()) {
                String name = particles.get(s).getName();
                String alias = particles.get(s).getAlias();
                Double mass = particles.get(s).getMass();
                String query = "INSERT INTO PARTICLES VALUES(" + "'" + name + "'," + "'" + alias + "'," + mass + ");";
                this.template.execute(query);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
