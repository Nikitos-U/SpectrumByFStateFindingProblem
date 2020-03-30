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
        ProbableParticlesMaker probableParticlesMaker = new ProbableParticlesMaker(allCombinations, decays, particles);
        Map<Integer, ArrayList<Particle>> probableParticles = new HashMap<>();
        probableParticles.putAll(probableParticlesMaker.convertCombinationsToParticles());
        System.out.println(probableParticles);
        Map<Integer, ArrayList<Particle>> fstateMap = new HashMap<>();
        ArrayList<Particle> fstateParticles = new ArrayList<>();
        for (int i = 0; i < fstate.size(); i++) {
            fstateParticles.add(particles.get(fstate.get(i)));
            System.out.println(particles.get(fstate.get(i)));
        }
        fstateMap.put(1,fstateParticles);
        probableParticles.putAll(fstateMap);
        System.out.println(probableParticles);

    }
        public ArrayList<Cascade> fromFinalCascadeChooser(ArrayList<Cascade> finalCascades) {
        ArrayList<Cascade> result = new ArrayList<>();
            for (Cascade finalCascade : finalCascades) {
                if (finalCascade.fstate.size() <= 1) {
                    result.add(finalCascade);
                }
            }
            return result;
        }
}
