package ru.mipt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

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
        ArrayList<String> allCombinations = new ArrayList<>();
        allCombinations = combinator.allCombinations(fstate, 0);
        for (String combination : allCombinations) {
            System.out.println(combination);
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
        System.out.println(probableParticlesMaker.convertCombinationsToParticles());
    }
}
