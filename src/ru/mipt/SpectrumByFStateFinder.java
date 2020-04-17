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
        System.out.println("Enter final state");
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
        DecayParser decayParser = new DecayParser();
        decays = decayParser.parse(particles);
        System.out.println("Decays parsed: " + decays.size());
        ProbableParticlesMaker probableParticlesMaker = new ProbableParticlesMaker(decays);
        DecaysFinder decaysFinder = new DecaysFinder(combinator, probableParticlesMaker);
        ArrayList<Cascade> finalCascades;
        finalCascades = decaysFinder.findDecays(fstate);
        System.out.println("_______________FINAL RESULT_______________");
        for (Cascade cascade : finalCascades) {
            System.out.println(cascade);
        }
        System.out.print("Found " + finalCascades.size());
        if (finalCascades.size() > 1){
            System.out.print(" cascades");
        } else {
            System.out.print(" cascade");
        }
    }
}
