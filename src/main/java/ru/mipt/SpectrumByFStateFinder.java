package ru.mipt;

import ru.mipt.dao.DBFiller;
import ru.mipt.dao.DaoConfig;
import ru.mipt.dao.DecayRepository;
import ru.mipt.dao.ParticleRepository;
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
        DecayParser decayParser = new DecayParser();
        ParticleParser particleParser = new ParticleParser();
        DBFiller filler = new DBFiller(decayParser, particleParser);
        HashMap<String,Particle> particles = filler.getParticles();
        HashMap<String, Decay> decays = filler.getDecays();
        Particle fake_mother_particle = new Particle("FAKE_MOTHER_PARTICLE_ADD_ALIAS", 42069.0);
        particles.put("FAKE_MOTHER_PARTICLE_ADD_ALIAS", fake_mother_particle);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter final state");
        Cascade fstate = new Cascade();
        String inputParticle = "";
        while (!inputParticle.equals("exit")) {
            inputParticle = scanner.nextLine();
            for (Particle particle : particles.values()) {
                if (particle.getAliases().contains(inputParticle)) {
                    fstate.getParticleList().add(particle);
                }
            }
        }
        System.out.println("fstateMass = " + fstate.getMass() + " keV");
        ParticleCombinator combinator = new ParticleCombinator(particles);
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
