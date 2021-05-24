package ru.mipt.parsers;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import ru.mipt.dao.FstateRepository;
import ru.mipt.domain.Decay;
import ru.mipt.domain.Particle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static java.util.Arrays.asList;

public class DecayParser {
    private final FileReader inputFile = new FileReader("src/main/resources/DECAY.DEC");
    private final BufferedReader reader = new BufferedReader(inputFile);
    private final List<String> models = new ArrayList<>(asList("PHSP", "PHSP;", "PHSP; ", "HELAMP", "ISGW2;", "PHOTOS", "SVS", "SVS;", "SVV_HELAMP", "PYTHIA", "HQET2", "HQET2;", "ISGW2;", "VVS_PWAVE", "TAUSCALARNU", "VSP_PWAVE;", "VUB", "VUB;", "BTOXSGAMMA", "SLN;", "SLN", "CB3PI-MPP", "VSS", "VSS;", "VSS; ", "VSS_BMIX", "VVPIPI;", "VVPIPI;2", "PARTWAVE", "BTO3PI_CP", "CB3PI-P00", "STS;", "SVP_HELAMP", "BTOSLLALI;", "TAUSCALARNU;", "TAUHADNU", "TAUVECTORNU;", "D_DALITZ;", "D_DALITZ;", "PARTWAVE", "PI0_DALITZ;", "ETA_DALITZ;", "OMEGA_DALITZ;", "SVP_HELAMP", "VVPIPI;", "PARTWAVE", "VVP", "VLL;", "BaryonPCR", "TSS;", "TVS_PWAVE"));
    private final Map<String, Particle> parsedParticles;
    private final FstateRepository fstateRepository;

    public DecayParser(Map<String, Particle> parsedParticles, FstateRepository fstateRepository) throws FileNotFoundException {
        this.parsedParticles = parsedParticles;
        this.fstateRepository = fstateRepository;
    }

    public MultiValuedMap<List<Particle>, Decay> parse() throws IOException {
        MultiValuedMap<List<Particle>, Decay> parsedDecays = new ArrayListValuedHashMap<>();
        String line;
        String decayName;
        while (!(line = reader.readLine().trim()).equals("End")) {
            if (line.startsWith("Decay")) {
                decayName = line.split("\\s+")[1].trim();
                line = reader.readLine().trim();
                while (!(line.equals("Enddecay"))) {
                    if (line.startsWith("#") || line.isEmpty()) {
                        line = reader.readLine().trim();
                        continue;
                    }
                    String[] splittedLine = line.split("\\s+");
                    Double probability = parseDouble(line.split(" ")[0].trim());
                    List<Particle> particles = parseDecayParticles(parsedParticles, splittedLine);
                    Particle motherParticle = new Particle("FAKE_MOTHER_PARTICLE_ADD_ALIAS", 120120.0, asList("FAKE_MOTHER_PARTICLE_ADD_ALIAS"),0);
                    for (Particle particle : parsedParticles.values()) {
                        String finalDecayName = decayName;
                        if (particle.getAliases().stream().anyMatch(alias -> alias.equals(finalDecayName))) {
                            motherParticle = particle;
                        }
                    }
                    if (particles.size() == 0) {
                        line = reader.readLine().trim();
                        continue;
                    }
                    Decay someDecay = new Decay(motherParticle, particles, probability);
                    parsedDecays.put(particles, someDecay);
                    line = reader.readLine().trim();
                }
            }
        }
        parsedDecays.values().forEach(fstateRepository::saveDecay);
        return parsedDecays;
        //TODO parse aliases from decay.dec
    }

    private ArrayList<Particle> parseDecayParticles(Map<String, Particle> parsedParticles, String[] splittedLine) {
        ArrayList<Particle> particles = new ArrayList<>();
        int i = 1;
        while (!models.contains(splittedLine[i])) {
            String possibleName = splittedLine[i].trim();
            for (Particle particle : parsedParticles.values()) {
                if (particle.getAliases()
                        .stream().anyMatch(alias -> alias.equals(possibleName))) {
                    particles.add(parsedParticles.get(particle.getName()));
                }
            }
            i++;
        }
        return particles;
    }
}
