package ru.mipt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class DecayParser {
    FileReader inputFile = new FileReader("src/ru/mipt/DECAYS.txt");
    BufferedReader reader = new BufferedReader(inputFile);

    public DecayParser() throws FileNotFoundException {
    }

    public HashMap<String, Decay> parse(HashMap<String, Particle> parsedParticles) throws IOException {
        HashMap<String, Decay> parsedDecays = new HashMap<>();
        String line = "";
        String decayName = "";
        String hashKeyParticles = "";
        while (!(line = reader.readLine()).startsWith("-")) {
            if (line.startsWith("Decay")) {
                decayName = line.split(" ")[1].trim();
                line = reader.readLine();
                while (!(line.equals("Enddecay"))) {
                    hashKeyParticles += decayName + ":";
                    Double probability = Double.parseDouble(line.split(" ")[0].trim());
                    ArrayList<Particle> particles = new ArrayList<>();
                    int i = 1;
                    while (!line.split("\\s+")[i].endsWith(";")) {
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
                    Decay someDecay = new Decay(motherParticle, particles, probability);
                    hashKeyParticles = hashKeyParticles.substring(0, hashKeyParticles.length() - 1);
                    hashKeyParticles += " " + particles.size();
                    parsedDecays.put(hashKeyParticles, someDecay);
                    hashKeyParticles = "";
                    line = reader.readLine();
                }
            }
        }
        return parsedDecays;
    }
}
