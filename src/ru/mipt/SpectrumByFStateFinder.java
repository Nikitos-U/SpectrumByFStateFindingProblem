package ru.mipt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class SpectrumByFStateFinder {
    public static void main(String[] args) throws IOException {
        ParticleParser particleParser = new ParticleParser();
        HashMap<String, Particle> particles = new HashMap<>();
        particles = particleParser.parse();
        ArrayList<Decay> decays;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Vvedite fstate");
        ArrayList<String> fstate = new ArrayList<>();
        String inputParticle = "";
        while (!inputParticle.equals("exit")) {
            inputParticle = scanner.nextLine();
            fstate.add(inputParticle);
        }
        fstate.remove("exit");
        Double fstateMass = MassCounter.countMass(fstate, particles);
        System.out.println("fstateMass = " + fstateMass + " keV");
        ParticleCombinator combinator = new ParticleCombinator();
        ArrayList<ArrayList<Particle>> allCombinations = new ArrayList<>();
        allCombinations = combinator.allCombinations(fstate, 0,particles);
        /*
        for (String combination : allCombinations) {
            System.out.println(combination);
        }*/
        for (ArrayList<Particle> combination : allCombinations) {
            System.out.println(combination.toString());
        }
        DecayParser decayParser = new DecayParser();
        decays = decayParser.parse(particles);
        /*
        System.out.println("Rasparsily vot takie vot raspady: ");
        for (Decay decay : decays) {
            System.out.println(decay);
        }*/
        System.out.println("Decays parsed: " + decays.size());
        ProbableParticlesMaker probableParticlesMaker = new ProbableParticlesMaker(allCombinations, decays, particles);
        Map<Integer, Particle> probableParticles = new HashMap<>();
        probableParticles.putAll(probableParticlesMaker.convertCombinationsToParticles());
        System.out.println(probableParticles);
        Map<Integer, Particle> fstateMap = new HashMap<>();
        for (int i = 0; i < fstate.size(); i++) {
            fstateMap.put(1, particles.get(fstate.get(i)));
            System.out.println(particles.get(fstate.get(i)));
        }
        probableParticles.putAll(fstateMap);
        System.out.println(probableParticles);
    }
}
