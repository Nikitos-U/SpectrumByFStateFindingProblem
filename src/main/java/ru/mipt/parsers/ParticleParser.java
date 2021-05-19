package ru.mipt.parsers;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.mipt.Particle;
import ru.mipt.dao.ParticleRepository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class ParticleParser {
    private final FileReader inputFile = new FileReader("src/main/resources/particles.txt");
    private final FileReader decaysFile = new FileReader("src/main/resources/DECAY.DEC");
    private final BufferedReader reader = new BufferedReader(inputFile);
    private final BufferedReader decaysReader = new BufferedReader(decaysFile);
    private final ParticleRepository repository;

    public ParticleParser(ParticleRepository repository) throws FileNotFoundException {
        this.repository = repository;
    }

    @SneakyThrows
    public HashMap<String, Particle> parse() {
        HashMap<String, Particle> parsedParticles = new HashMap<>();
        String line;
        Integer i = 0;
        while (!(line = reader.readLine()).startsWith(" -")) {
            i++;
            double mass = parseMass(line);
            ArrayList<String> aliases = new ArrayList<>();
            aliases.add(line.split("\\|")[1].trim());
            Particle particle = new Particle(i, line.split("\\|")[1].trim(), aliases, mass);
            if (!line.split("\\|")[7].trim().equals("unknown")) {
                particle.getAliases().add(line.split("\\|")[7].trim());
            }
            parsedParticles.put(particle.getName(), particle);
        }
        parseAliases(parsedParticles);
        for (Particle particle : parsedParticles.values()) {
            repository.saveParticle(particle);
        }
        ArrayList<String> fakeMotherAlias = new ArrayList<>();
        fakeMotherAlias.add("FAKE_MOTHER_PARTICLE_ADD_ALIAS");
        Particle fake_mother_particle = new Particle( -1, "FAKE_MOTHER_PARTICLE_ADD_ALIAS", fakeMotherAlias, 42069.0);
        repository.saveParticle(fake_mother_particle);
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
