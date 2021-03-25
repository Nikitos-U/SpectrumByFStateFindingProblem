package ru.mipt.parsers;

import lombok.SneakyThrows;
import ru.mipt.Particle;
import ru.mipt.dao.DaoConfig;
import ru.mipt.dao.ParticleEntry;
import ru.mipt.dao.ParticleRepository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;


public class ParticleParser {
    private final FileReader inputFile = new FileReader("src/main/resources/particles.txt");
    private final FileReader decaysFile = new FileReader("src/main/resources/DECAY.DEC");
    private final BufferedReader reader = new BufferedReader(inputFile);
    private final BufferedReader decaysReader = new BufferedReader(decaysFile);
    private final DaoConfig config = new DaoConfig();
    private final ParticleRepository repository = new ParticleRepository(config.getJdbcTemplate());

    public ParticleParser() throws FileNotFoundException {
    }

    @SneakyThrows
    public HashMap<String, Particle> parse() {
        HashMap<String, Particle> parsedParticles = new HashMap<>();
        String line;
        long i = 0L;
        while (!(line = reader.readLine()).startsWith(" -")) {
            i++;
            double mass = parseMass(line);
            Particle particle = new Particle(i, line.split("\\|")[1].trim(), mass);
            if (!line.split("\\|")[7].trim().equals("unknown")) {
                particle.getAliases().add(line.split("\\|")[7].trim());
            }
            parsedParticles.put(particle.getName(), particle);
        }
        parseAliases(parsedParticles);
        for (Particle particle : parsedParticles.values()) {
            repository.saveParticle(new ParticleEntry(particle.getId(), particle.getName(), particle.getAliases().toString(), particle.getMass()));
        }
        Particle fake_mother_particle = new Particle( -1, "FAKE_MOTHER_PARTICLE_ADD_ALIAS", 42069.0);
        repository.saveParticle(new ParticleEntry(fake_mother_particle.getId(), fake_mother_particle.getName(), fake_mother_particle.getAliases().toString(), fake_mother_particle.getMass()));
        return parsedParticles;
    }

    @SneakyThrows
    private void parseAliases(HashMap<String, Particle> parsedParticles) {
        String line;
        while (!(line = decaysReader.readLine()).startsWith("Decay")) {
            if (line.startsWith("Alias")) {
                String alias1 = line.split("\\s+")[1].trim();
                String alias2 = line.split("\\s+")[2].trim();
                if (parsedParticles.get(alias1) == null) {
                    if (parsedParticles.get(alias2) != null) {
                        parsedParticles.get(alias2).getAliases().add(alias1);
                    }
                } else {
                    parsedParticles.get(alias1).getAliases().add(alias2);
                }
            }
        }
    }

    private double parseMass(String line) {
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
        return mass;
    }
}
