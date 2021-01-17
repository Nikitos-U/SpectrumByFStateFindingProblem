package ru.mipt.starters;

import ru.mipt.*;

import java.util.ArrayList;

public class SpectrumByFStateFinder {
    //Основной класс из него вызываются парсеры и классы, исполняющие логику алгоритма
    public static void main(String[] args) {
        FstateMatcher fstateMatcher = new FstateMatcher();
        Cascade fstate = fstateMatcher.prepareFstateByInput();
        System.out.println("fstateMass = " + fstate.getMass() + " keV");
        ParticleCombinator combinator = new ParticleCombinator();
        ProbableParticlesMaker probableParticlesMaker = new ProbableParticlesMaker();
        DecaysFinder decaysFinder = new DecaysFinder(combinator, probableParticlesMaker);
        ArrayList<Cascade> finalCascades;
        finalCascades = decaysFinder.findDecays(fstate);
        System.out.println("_______________FINAL RESULT_______________");
        for (Cascade cascade : finalCascades) {
            System.out.println(cascade);
        }
    }
}
