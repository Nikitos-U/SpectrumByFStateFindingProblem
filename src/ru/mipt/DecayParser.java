package ru.mipt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class DecayParser {
    FileReader inputFile = new FileReader("src/ru/mipt/DECAY.DEC");
    BufferedReader reader = new BufferedReader(inputFile);
    List<String> models = new ArrayList<>(Arrays.asList("PHSP", "PHSP;", "PHSP; ", "HELAMP", "ISGW2;", "PHOTOS", "SVS", "SVS;", "SVV_HELAMP", "PYTHIA", "HQET2", "HQET2;", "ISGW2;","VVS_PWAVE","TAUSCALARNU","VSP_PWAVE;","VUB","VUB;","BTOXSGAMMA","SLN;","SLN","CB3PI-MPP","VSS","VSS;", "VSS; ","VSS_BMIX","VVPIPI;","VVPIPI;2","PARTWAVE","BTO3PI_CP","CB3PI-P00","STS;","SVP_HELAMP","BTOSLLALI;","TAUSCALARNU;","TAUHADNU","TAUVECTORNU;","D_DALITZ;","D_DALITZ;","PARTWAVE","PI0_DALITZ;","ETA_DALITZ;","OMEGA_DALITZ;","SVP_HELAMP","VVPIPI;","PARTWAVE","VVP","VLL;","BaryonPCR","TSS;","TVS_PWAVE"));

    public DecayParser() throws FileNotFoundException {
    }

    public HashMap<String, Decay> parse(HashMap<String, Particle> parsedParticles) throws IOException {
        HashMap<String, Decay> parsedDecays = new HashMap<>();
        String line = "";
        String decayName = "";
        String hashKeyParticles = "";
        while (!(line = reader.readLine().trim()).equals("End")) {
            if (line.startsWith("Decay")) {
                decayName = line.split("\\s+")[1].trim();
                //System.out.println(decayName);
                line = reader.readLine().trim();
                while (!(line.equals("Enddecay"))) {
                    if (line.startsWith("#") || line.isEmpty()){
                        line = reader.readLine().trim();
                        continue;
                    }
                    hashKeyParticles += decayName + ":";
                    Double probability = Double.parseDouble(line.split(" ")[0].trim());
                    ArrayList<Particle> particles = new ArrayList<>();
                    int i = 1;
                    while (!models.contains(line.split("\\s+")[i])) {
                        for (Particle particle : parsedParticles.values()) {
                            if (particle.alias.equals(line.split("\\s+")[i].trim()) || particle.name.equals(line.split("\\s+")[i].trim())) {
                                particles.add(parsedParticles.get(particle.name));
                                hashKeyParticles += particle.name + ",";
                            }
                        }
                        i++;
                    }
                    Particle motherParticle = new Particle("FAKE_MOTHER_PARTICLE_ADD_ALIAS", 120120.0);
                    for (Particle particle : parsedParticles.values()) {
                        if (particle.alias.equals(decayName) || particle.name.equals(decayName)) {
                            motherParticle = particle;
                        }
                    }
                    if (particles.size() == 0 ){
                        line = reader.readLine().trim();
                        continue;
                    }
                    Decay someDecay = new Decay(motherParticle, particles, probability);
                    hashKeyParticles = hashKeyParticles.substring(0, hashKeyParticles.length() - 1);
                    hashKeyParticles += " " + particles.size();
                    parsedDecays.put(hashKeyParticles, someDecay);
                    hashKeyParticles = "";
                    line = reader.readLine().trim();
                }
            }
        }
        return parsedDecays;
        //TODO parse aliases from decay.dec
    }
}
