package ru.mipt;

import java.util.ArrayList;

public class newCombinationsFinder {

    public static ArrayList<ArrayList<Particle>> findCombinations(ArrayList<Particle> particles) {

        ArrayList<ArrayList<Particle>> combinationList = new ArrayList<>();
        // Start i at 1, so that we do not include the empty set in the results
        for (long i = 1; i < Math.pow(2, particles.size()); i++) {
            ArrayList<Particle> particleList = new ArrayList<>();
            for (int j = 0; j < particles.size(); j++) {
                if ((i & (long) Math.pow(2, j)) > 0) {
                    // Include j in set
                    particleList.add(particles.get(j));
                }
            }
            combinationList.add(particleList);
        }
        return combinationList;
    }
}
