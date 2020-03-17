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

    // TODO HASMAP
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
                    //System.out.println(line);
                    Double probability = Double.parseDouble(line.split(" ")[0].trim());
                    ArrayList<Particle> particles = new ArrayList<>();
                    int i = 1;
                /*for (int j = 0;j < 4; j++){
                    System.out.println("Prost posmotrim kak tam liniya parsitsya: " + line.split("\\s+")[1].trim());
                }*/
                    while (!line.split("\\s+")[i].endsWith(";")) {
                        //System.out.println("Smotrim na vot etot kusok stroki: " + line.split("\\s+")[i]);
                        for (Particle particle : parsedParticles.values()) {
                            if (particle.alias.equals(line.split("\\s+")[i].trim()) || particle.name.equals(line.split("\\s+")[i].trim())) {
                                particles.add(parsedParticles.get(particle.name));
                                hashKeyParticles += particle.name + ",";
                            }
                        }
                        i++;
                    }
                    Decay someDecay = new Decay(decayName, particles, probability);
                    //System.out.println(someDecay.particles);
                    hashKeyParticles = hashKeyParticles.substring(0, hashKeyParticles.length() - 1);
                    parsedDecays.put(hashKeyParticles, someDecay);
                    hashKeyParticles = "";
                    line = reader.readLine();
                }
            }
        }
        return parsedDecays;
    }
}
