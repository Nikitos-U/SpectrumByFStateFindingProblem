package ru.mipt.parsers;

import ru.mipt.Particle;
import ru.mipt.dao.DaoConfig;
import ru.mipt.dao.ParticleEntry;
import ru.mipt.dao.ParticleRepository;

import java.io.*;
import java.util.HashMap;

public class ParticleParser {
    private final FileReader inputFile = new FileReader("src/main/resources/particles.txt");
    private final BufferedReader reader = new BufferedReader(inputFile);
    private final DaoConfig config = new DaoConfig();
    private final ParticleRepository repository = new ParticleRepository(config.getNamedParameterJdbcTemplate());

    public ParticleParser() throws FileNotFoundException {
    }

    public HashMap<String, Particle> parse() throws IOException {
        HashMap<String, Particle> parsedParticles = new HashMap<>();
        String line;
        while (!(line = reader.readLine()).startsWith(" -")) {
            double mass = Double.parseDouble(line.split("\\|")[4].trim().split(" ")[0]);
            String multiplier = line.split("\\|")[4].trim().split(" ")[1].trim();
            switch (multiplier.charAt(0)) {
                case ('e'):
                    mass *= 0.001;
                    break;
                case ('G'):
                    mass *= 1000000;
                    break;

                case ('M'):
                    mass *= 1000;
                    break;

                case ('T'):
                    mass *= 1000000000;
                    break;

            }
            Particle particle = new Particle(line.split("\\|")[1].trim(), mass);
            if(!line.split("\\|")[7].trim().equals("unknown")){
                particle.setAlias(line.split("\\|")[7].trim());
            } else {
                particle.setAlias(particle.getName());
            }
            parsedParticles.put(particle.getName(), particle);
            repository.save(new ParticleEntry(particle.getName(), particle.getAlias(), particle.getMass()));
        }
        return parsedParticles;
    }
}
