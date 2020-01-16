package ru.mipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MassCounter {
    public static Double countMass (ArrayList<String> fstate, HashMap<String,Particle> particles){
        Double fstateMass = 0.0;
        for (String fstateParticle : fstate) {
            if (particles.containsKey(fstateParticle)){
                Particle particle = particles.get(fstateParticle);
                fstateMass += particle.mass;
            }
            else {
                System.out.println("No such particle found in db: " + fstateParticle);
            }
        }
        return fstateMass;
    }
}
