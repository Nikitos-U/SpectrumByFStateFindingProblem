package ru.mipt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class SpectrumByFStateFinder {
    //Основной класс из него вызываются парсеры и классы, исполняющие логику алгоритма
    public static void main(String[] args) throws IOException {
        ParticleParser particleParser = new ParticleParser();
        HashMap<String, Particle> particles = new HashMap<>();
        particles = particleParser.parse();
        //распарс файла с частицами, в результате возвращается HashMap<String,Particle> - ключом является имя частицы,
        //значением сама частица (объект класса Particle)
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
        //выше происходит ввод fstate
        Double fstateMass = MassCounter.countMass(fstate, particles);
        System.out.println("fstateMass = " + fstateMass + " keV");
        ParticleCombinator combinator = new ParticleCombinator();
        ArrayList<ArrayList<Particle>> allCombinations = new ArrayList<>();
        allCombinations = combinator.allCombinations(fstate, 0, particles);
        //составление всевозможных комбинаций из частиц (пар, троек итд)
        //метод возвращает ArrayList<ArrayList<Particle>> - в каждом вложенном массиве перечисленны
        //частицы входящие в пару, тройку итд
        for (ArrayList<Particle> combination : allCombinations) {
            System.out.println(combination.toString());
        }
        DecayParser decayParser = new DecayParser();
        decays = decayParser.parse(particles);
        //Распарс модельного файла с распадами(содержит 67 распадов), возвращается ArrayList<Decay>
        System.out.println("Decays parsed: " + decays.size());
        System.out.println(decays.get("K+,pi0,"));
        for (String decayParticles : decays.keySet()) {
            System.out.println(decayParticles);
        }
        //ProbableParticlesMaker probableParticlesMaker = new ProbableParticlesMaker(allCombinations, decays, particles);
        System.out.println("Decays parsed: " + decays.size());
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
