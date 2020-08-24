package ru.mipt;

import java.io.IOException;
import java.util.*;

public class AllCascadesByFstateFinder {
    //Основной класс из него вызываются парсеры и классы, исполняющие логику алгоритма
    public static void main(String[] args) throws IOException {
        ParticleParser particleParser = new ParticleParser();
        //распарс файла с частицами, в результате возвращается HashMap<String,Particle> - ключом является имя частицы,
        //значением сама частица (объект класса Particle)
        HashMap<String, Particle> particles = particleParser.parse();
        Scanner scanner = new Scanner(System.in);
        Cascade fstate = new Cascade();
        System.out.println("Enter final state");
        String inputParticle = "";
        //Создание изначального каскада (fstate) из пользовательского ввода
        while (!inputParticle.equals("exit")) {
            inputParticle = scanner.nextLine();
            for (Particle particle : particles.values()) {
                if (particle.alias.equals(inputParticle) || particle.name.equals(inputParticle)) {
                    fstate.particleList.add(particle);
                }
            }
        }
        ParticleCombinator combinator = new ParticleCombinator(particles);
        //Парсинг исходного файла с распадами
        DecayParser decayParser = new DecayParser();
        HashMap<String, Decay> decays = decayParser.parse(particles);
        ProbableParticlesMaker probableParticlesMaker = new ProbableParticlesMaker(decays);
        DecaysFinder decaysFinder = new DecaysFinder(combinator, probableParticlesMaker);
        long time = System.currentTimeMillis();
        ArrayList<Cascade> finalCascades = decaysFinder.findDecays(fstate);
        long sumTime = System.currentTimeMillis() - time;
        System.out.println("_______________FINAL RESULT_______________");
        for (Cascade cascade : finalCascades) {
            System.out.println(cascade);
        }
        System.out.print("Found " + finalCascades.size());
        if (finalCascades.size() > 1) {
            System.out.println(" cascades");
        } else {
            System.out.println(" cascade");
        }
        System.out.print("Cascades for " + fstate.particleList.size() + " particles found in: " + sumTime + " millis");
    }
}
