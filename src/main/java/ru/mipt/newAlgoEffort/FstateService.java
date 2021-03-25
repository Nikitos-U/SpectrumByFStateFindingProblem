package ru.mipt.newAlgoEffort;

import ru.mipt.Cascade;

import java.util.List;

public interface FstateService {
    List<Cascade> computeCascades(List<String> fstate);
}
