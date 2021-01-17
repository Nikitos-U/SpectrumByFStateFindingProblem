package ru.mipt;

import ru.mipt.dao.DaoClass;
import ru.mipt.dao.FstateRepository;
import ru.mipt.parsers.DecayParser;
import ru.mipt.parsers.ParticleParser;
import ru.mipt.utils.DoubleKeyHashMap;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.System.nanoTime;

public class SpectrumByFStateFinder {
    public static long workingTime;

    //Основной класс из него вызываются парсеры и классы, исполняющие логику алгоритма
    public static void main(String[] args) throws IOException, SQLException {
        ParticleParser particleParser = new ParticleParser();
        HashMap<String, Particle> particles;
        particles = particleParser.parse();
        DoubleKeyHashMap decays;
        DecayParser decayParser = new DecayParser(particles);
        ParticleCombinator combinator = new ParticleCombinator(particles);
        Particle fake_mother_particle = new Particle("FAKE_MOTHER_PARTICLE_ADD_ALIAS", 42069.0);
        particles.put("FAKE_MOTHER_PARTICLE_ADD_ALIAS", fake_mother_particle);
        decays = decayParser.parse();
//        decays.getKey1Map().keySet().forEach(System.out::println);
//        System.out.println("second index contains: " + decays.getKey2Map().keySet().size() + " keys");
//        System.out.println("parsed: " + decays.getKey1Map().keySet().size() + " decays");
//        DaoClass DaoClass = new DaoClass();
//        Connection connection = DaoClass.getConnectiontoDb();
//        //распарс файла с частицами, в результате возвращается HashMap<String,Particle> - ключом является имя частицы,
//        //значением сама частица (объект класса Particle)
//        for (String s : particles.keySet()) {
//            String query;
//            String name = particles.get(s).getName();
//            String alias = particles.get(s).getAliases().toString();
//            Double mass = particles.get(s).getMass();
//            query = "INSERT INTO PARTICLES VALUES(" + "'" + name + "'," + "'"  + alias + "'," + mass + ");";
//            FstateRepository.executeUpdate(query, connection);
//        }
//        connection.close();
        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter final state");
        Cascade fstate = new Cascade();
        String inputParticle = "";
        if (args.length == 0) {
            while (!inputParticle.equals("exit")) {
                inputParticle = scanner.nextLine();
                for (Particle particle : particles.values()) {
                    if (particle.getAliases().contains(inputParticle)) {
                        fstate.getParticleList().add(particle);
                    }
                }
            }
        } else {
            for (String arg : args) {
                for (Particle particle : particles.values()) {
                    if (particle.getAliases().contains(arg)) {
                        fstate.getParticleList().add(particle);
                    }
                }
            }
        }
//        System.out.println("fstateMass = " + fstate.getMass() + " keV");
        ProbableParticlesMaker probableParticlesMaker = new ProbableParticlesMaker(decays);
        DecaysFinder decaysFinder = new DecaysFinder(combinator, probableParticlesMaker);
        ArrayList<Cascade> finalCascades;
        long time = nanoTime();
        finalCascades = decaysFinder.findDecays(fstate);
        workingTime = nanoTime() - time;
//        System.out.println("_______________FINAL RESULT_______________");
//        for (Cascade cascade : finalCascades) {
//            System.out.println(cascade);
//        }
//        System.out.print("Found " + finalCascades.size());
//        if (finalCascades.size() > 1) {
//            System.out.println(" cascades");
//        } else {
//            System.out.println(" cascade");
//        }
//        System.out.print("Cascades for " + fstate.getParticleList().size() + " particles found in: ");
//        System.out.print(workingTime);
//        System.out.println(" ns");
    }
}
