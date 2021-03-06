package ru.mipt.parsers;

import ru.mipt.Decay;
import ru.mipt.Particle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DecayParser {
    private final FileReader inputFile = new FileReader("src/main/resources/DECAY.DEC");
    private final BufferedReader reader = new BufferedReader(inputFile);
    private final List<String> models = new ArrayList<>(Arrays.asList("PHSP", "PHSP;", "PHSP; ", "HELAMP", "ISGW2;", "PHOTOS", "SVS", "SVS;", "SVV_HELAMP", "PYTHIA", "HQET2", "HQET2;", "ISGW2;", "VVS_PWAVE", "TAUSCALARNU", "VSP_PWAVE;", "VUB", "VUB;", "BTOXSGAMMA", "SLN;", "SLN", "CB3PI-MPP", "VSS", "VSS;", "VSS; ", "VSS_BMIX", "VVPIPI;", "VVPIPI;2", "PARTWAVE", "BTO3PI_CP", "CB3PI-P00", "STS;", "SVP_HELAMP", "BTOSLLALI;", "TAUSCALARNU;", "TAUHADNU", "TAUVECTORNU;", "D_DALITZ;", "D_DALITZ;", "PARTWAVE", "PI0_DALITZ;", "ETA_DALITZ;", "OMEGA_DALITZ;", "SVP_HELAMP", "VVPIPI;", "PARTWAVE", "VVP", "VLL;", "BaryonPCR", "TSS;", "TVS_PWAVE"));

    public DecayParser() throws FileNotFoundException {
    }

    public HashMap<String, Decay> parse(HashMap<String, Particle> parsedParticles) throws IOException {
        HashMap<String, Decay> parsedDecays = new HashMap<>();
        String line;
        String decayName;
        StringBuilder hashKeyParticles = new StringBuilder();
        while (!(line = reader.readLine().trim()).equals("End")) {
            if (line.startsWith("Decay")) {
                decayName = line.split("\\s+")[1].trim();
                line = reader.readLine().trim();
                while (!(line.equals("Enddecay"))) {
                    if (line.startsWith("#") || line.isEmpty()) {
                        line = reader.readLine().trim();
                        continue;
                    }
                    hashKeyParticles.append(decayName).append(":");
                    Double probability = Double.parseDouble(line.split(" ")[0].trim());
                    ArrayList<Particle> particles = new ArrayList<>();
                    int i = 1;
                    while (!models.contains(line.split("\\s+")[i])) {
                        for (Particle particle : parsedParticles.values()) {
                            if (particle.getAlias().equals(line.split("\\s+")[i].trim()) || particle.getName().equals(line.split("\\s+")[i].trim())) {
                                particles.add(parsedParticles.get(particle.getName()));
                                hashKeyParticles.append(particle.getName()).append(",");
                            }
                        }
                        i++;
                    }
                    Particle motherParticle = new Particle("FAKE_MOTHER_PARTICLE_ADD_ALIAS", 120120.0);
                    for (Particle particle : parsedParticles.values()) {
                        if (particle.getAlias().equals(decayName) || particle.getName().equals(decayName)) {
                            motherParticle = particle;
                        }
                    }
                    if (particles.size() == 0) {
                        line = reader.readLine().trim();
                        continue;
                    }
                    Decay someDecay = new Decay(motherParticle, particles, probability);
                    hashKeyParticles = new StringBuilder(hashKeyParticles.substring(0, hashKeyParticles.length() - 1));
                    hashKeyParticles.append(" ").append(particles.size());
                    parsedDecays.put(hashKeyParticles.toString(), someDecay);
                    hashKeyParticles = new StringBuilder();
                    line = reader.readLine().trim();
                }
            }
        }
        return parsedDecays;
        //TODO parse aliases from decay.dec
    }
}
