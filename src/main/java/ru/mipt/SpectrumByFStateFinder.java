package ru.mipt;

import ru.mipt.dao.DaoClass;
import ru.mipt.dao.FstateRepository;
import ru.mipt.parsers.DecayParser;
import ru.mipt.parsers.ParticleParser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SpectrumByFStateFinder {
    //Основной класс из него вызываются парсеры и классы, исполняющие логику алгоритма
    public static void main(String[] args) throws IOException, SQLException {
        DaoClass DaoClass = new DaoClass();
        Connection connection = DaoClass.getConnectiontoDb();
        ParticleParser particleParser = new ParticleParser();
        HashMap<String, Particle> particles;
        particles = particleParser.parse();
        //распарс файла с частицами, в результате возвращается HashMap<String,Particle> - ключом является имя частицы,
        //значением сама частица (объект класса Particle)
        for (String s : particles.keySet()) {
            String query;
            String name = particles.get(s).getName();
            String alias = particles.get(s).getAlias();
            Double mass = particles.get(s).getMass();
            query = "INSERT INTO PARTICLES VALUES(" + "'" + name + "'," + "'"  + alias + "'," + mass + ");";
            FstateRepository.executeUpdate(query, connection);
        }
        connection.close();
        HashMap<String, Decay> decays;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter final state");
        Cascade fstate = new Cascade();
        String inputParticle = "";
        while (!inputParticle.equals("exit")) {
            inputParticle = scanner.nextLine();
            for (Particle particle : particles.values()) {
                if (particle.getAlias().equals(inputParticle) || particle.getName().equals(inputParticle)) {
                    fstate.getParticleList().add(particle);
                }
            }
        }
        System.out.println("fstateMass = " + fstate.getMass() + " keV");
        ParticleCombinator combinator = new ParticleCombinator(particles);
        DecayParser decayParser = new DecayParser();
        decays = decayParser.parse(particles);
        System.out.println("Decays parsed: " + decays.size());
        long time = System.currentTimeMillis();
        ProbableParticlesMaker probableParticlesMaker = new ProbableParticlesMaker(decays);
        DecaysFinder decaysFinder = new DecaysFinder(combinator, probableParticlesMaker);
        ArrayList<Cascade> finalCascades;
        finalCascades = decaysFinder.findDecays(fstate);
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
        System.out.print("Cascades for " + fstate.getParticleList().size() + " particles found in: ");
        System.out.print(System.currentTimeMillis() - time);
        System.out.println(" millis");
    }
}
