package ru.mipt;

import java.util.ArrayList;

public class DecaysFinder {
    public DecaysFinder() {
    }
    public ArrayList<Cascade> findDecays(Cascade fstate) {
        ArrayList<Cascade> finalCascades = new ArrayList<>();
        ArrayList<Cascade> tmp;
        ParticleCombinator particleCombinator = new ParticleCombinator();
       // tmp = particleCombinator.allCombinations(fstate);
        //finalCascades.addAll(tmp);
        //for (Cascade cascade : tmp) {
          //  finalCascades.addAll(findDecays(cascade));
       // }
        return finalCascades;
    }
}
