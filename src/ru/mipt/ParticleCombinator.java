package ru.mipt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ParticleCombinator {
    ArrayList<String> result1 = new ArrayList<>();

    ArrayList<String> combinations2(ArrayList<String> fstate, int len, int startPosition, String[] result) {
        if (len == 0) {
            //System.out.println(Arrays.toString(result));
            String s = "";
            for (int i = 0; i < result.length; i++) {
                s += result[i];
            }
            result1.add(s);
            System.out.println(result1);
            return result1;
        }
        for (int i = startPosition; i <= fstate.size() - len; i++) {
            result[result.length - len] = fstate.get(i);
            combinations2(fstate, len - 1, i + 1, result);
        }
        return result1;
    }

    ArrayList<String> allCombinations(ArrayList<String> fstate, int startPosition) {
        ArrayList<String> allFstateCombinations = new ArrayList<>();
        ArrayList<String> fstateCombination = new ArrayList<>();
        allFstateCombinations.addAll(fstate);
        for (int i = 2; i <= fstate.size(); i++) {
            //System.out.println(i);
            fstateCombination = combinations2(fstate, i, startPosition, new String[i]);
            for (String combination : fstateCombination) {
                if (!allFstateCombinations.contains(combination))
                    allFstateCombinations.add(combination);
            }
        }
        System.out.println(allFstateCombinations);
        return allFstateCombinations;
    }
}


