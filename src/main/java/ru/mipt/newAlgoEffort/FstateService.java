package ru.mipt.newAlgoEffort;

import ru.mipt.Decay;

import java.util.List;

public interface FstateService {
    List<Decay> computeCascades(String fstate);
}
