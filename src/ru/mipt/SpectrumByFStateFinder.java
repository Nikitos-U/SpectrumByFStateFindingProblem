package ru.mipt;

import java.io.IOException;
import java.util.*;

public class SpectrumByFStateFinder {
    //Основной класс из него вызываются парсеры и классы, исполняющие логику алгоритма
    public static void main(String[] args) throws IOException {
        ParticleParser particleParser = new ParticleParser();
        HashMap<String, Particle> particles;
        particles = particleParser.parse();
        //распарс файла с частицами, в результате возвращается HashMap<String,Particle> - ключом является имя частицы,
        //значением сама частица (объект класса Particle)
        ArrayList<Decay> decays;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Vvedite fstate");
        Cascade fstate = new Cascade();
        String inputParticle = "";
        while (!inputParticle.equals("exit")) {
            inputParticle = scanner.nextLine();
            for (Particle particle : particles.values()) {
                if (particle.alias.equals(inputParticle) || particle.name.equals(inputParticle)) {
                    fstate.particleList.add(particle);
                }
            }
        }
        Double fstateMass = MassCounter.countMass(fstate.particleList);
        System.out.println("fstateMass = " + fstateMass + " keV");
        ParticleCombinator combinator = new ParticleCombinator(particles);
        //ArrayList<Cascade> allCombinations;
        DecayParser decayParser = new DecayParser();
        decays = decayParser.parse(particles);
        ProbableParticlesMaker probableParticlesMaker = new ProbableParticlesMaker(decays);
        DecaysFinder decaysFinder = new DecaysFinder(combinator, probableParticlesMaker);
        ArrayList<Cascade> finalCascades;
        finalCascades = decaysFinder.findDecays(fstate);
        for (int i = 0; i < finalCascades.size(); i++) {
            for (int j = 0; j < finalCascades.size(); j++) {
                if (i != j) {
//                    System.out.println("Cascade" + i + " " + finalCascades.get(i));
//                    System.out.println("Cascade" + j + " " + finalCascades.get(j));
                    if (finalCascades.get(i).particleList.equals(finalCascades.get(j).particleList) && finalCascades.get(i).history.equals(finalCascades.get(j).history)) {
                        finalCascades.remove(i);
                    }
                }
            }
        }
        System.out.println("");
        for (Cascade cascade : finalCascades) {
            System.out.println(cascade);
        }

//        System.out.println("Rasparsily vot takie vot raspady: ");
//        for (Decay decay : decays.values()) {
//            System.out.println(decay);
//        }
        System.out.println("Decays parsed: " + decays.size());
//        System.out.println(decays.get("K+,pi0,"));
//        for (String decayParticles : decays.keySet()) {
//            System.out.println(decayParticles);
//        }
//        System.out.println("Decays parsed: " + decays.size());
        //ProbableParticlesMaker probableParticlesMaker = new ProbableParticlesMaker(allCombinations, decays, particles);
//        Map<Integer, Particle> probableParticles = new HashMap<>();
//        probableParticles.putAll(probableParticlesMaker.convertCombinationsToParticles());
//        System.out.println(probableParticles);
//        Map<Integer, Particle> fstateMap = new HashMap<>();
//        for (int i = 0; i < fstate.size(); i++) {
//            fstateMap.put(1, particles.get(fstate.get(i)));
//            System.out.println(particles.get(fstate.get(i)));
//        }
//        probableParticles.putAll(fstateMap);
//        System.out.println(probableParticles);
    }
}
