//package ru.mipt.starters;
//
//import ru.mipt.*;
//
//import java.util.ArrayList;
//
//import static java.lang.System.nanoTime;
//
//public class SpectrumByFStateFinder {
//    //Для измерения времени работы работы программы
//    public static Long workingTime;
//    //Основной класс из него вызываются парсеры и классы, исполняющие логику алгоритма
//    public static void main(String[] args) {
//        FstateMatcher fstateMatcher = new FstateMatcher();
//        Cascade fstate = fstateMatcher.prepareFstateByInput(args);
//        System.out.println("fstateMass = " + fstate.getMass() + " keV");
//        ParticleCombinator combinator = new ParticleCombinator();
//        ProbableParticlesMaker probableParticlesMaker = new ProbableParticlesMaker();
//        DecaysFinder decaysFinder = new DecaysFinder(combinator, probableParticlesMaker);
//        ArrayList<Cascade> finalCascades;
//        long time = nanoTime();
//        finalCascades = decaysFinder.findDecays(fstate);
//        workingTime = nanoTime() - time;
//        System.out.println("_______________FINAL RESULT_______________");
//        for (Cascade cascade : finalCascades) {
//            System.out.println(cascade);
//        }
//    }
//}
