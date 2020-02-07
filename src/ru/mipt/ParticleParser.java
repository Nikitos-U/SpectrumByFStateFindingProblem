package ru.mipt;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParticleParser {
    FileReader inputFile = new FileReader("src/ru/mipt/particles.txt");
    BufferedReader reader = new BufferedReader(inputFile);

    public ParticleParser() throws FileNotFoundException {
    }

    public HashMap<String, Particle> parse() throws IOException {
        HashMap<String, Particle> parsedParticles = new HashMap<>();
        List particles = new ArrayList();
        String line;
        while (!(line = reader.readLine()).startsWith(" -")) {
            //System.out.println(line);
            Double mass = Double.parseDouble(line.split("\\|")[4].trim().split(" ")[0]);
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
                //System.out.println("Particle name: " + particle.name + " alias: " + particle.alias + " Particle mass: " + particle.mass);
                //System.out.println("");
            }
            parsedParticles.put(particle.name, particle);
            //System.out.println(line.split("\\|")[4].trim());
        }
        //System.out.println(parsedParticles);
        return parsedParticles;
    }
}
