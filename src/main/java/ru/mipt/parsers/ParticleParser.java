package ru.mipt.parsers;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.mipt.dao.FstateRepository;
import ru.mipt.domain.Particle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ParticleParser {
    private final FileReader inputFile = new FileReader("src/main/resources/particles.txt");
    private final FileReader decaysFile = new FileReader("src/main/resources/DECAY.DEC");
    private final BufferedReader reader = new BufferedReader(inputFile);
    private final BufferedReader decaysReader = new BufferedReader(decaysFile);
    private final AtomicInteger idsGenerator = new AtomicInteger();
    private final FstateRepository fstateRepository;

    public ParticleParser(FstateRepository fstateRepository) throws FileNotFoundException {
        this.fstateRepository = fstateRepository;
    }

    @SneakyThrows
    public HashMap<String, Particle> parse() {
        HashMap<String, Particle> parsedParticles = new HashMap<>();
        String line;
        while (!(line = reader.readLine()).startsWith(" -")) {
            double mass = parseMass(line);
            ArrayList<String> aliases = new ArrayList<>();
            aliases.add(line.split("\\|")[1].trim());
            Particle particle = new Particle(line.split("\\|")[1].trim(), mass, aliases, idsGenerator.incrementAndGet());
            if (!line.split("\\|")[7].trim().equals("unknown")) {
                particle.addAlias(line.split("\\|")[7].trim());
            }
            parsedParticles.put(particle.getName(), particle);
        }
        parseAliases(parsedParticles);
        List<String> fake_mother_particle_add_alias = new ArrayList<>();
        fake_mother_particle_add_alias.add("FAKE_MOTHER_PARTICLE_ADD_ALIAS");
        Particle fake_mother_particle = new Particle("FAKE_MOTHER_PARTICLE_ADD_ALIAS", 42069.0, fake_mother_particle_add_alias, 0);
        parsedParticles.put("FAKE_MOTHER_PARTICLE_ADD_ALIAS", fake_mother_particle);
        parsedParticles.values().forEach(fstateRepository::saveParticle);
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
                        parsedParticles.get(alias2).addAlias(alias1);
                    }
                } else {
                    parsedParticles.get(alias1).addAlias(alias2);
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
