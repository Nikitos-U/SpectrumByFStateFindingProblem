package ru.mipt.parsers;

import ru.mipt.Particle;

import java.io.*;
import java.util.HashMap;

public class ParticleParser {
    private final FileReader inputFile = new FileReader("src/main/resources/particles.txt");
    private final BufferedReader reader = new BufferedReader(inputFile);

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
            }
            parsedParticles.put(particle.getName(), particle);
        }
        return parsedParticles;
    }
}
