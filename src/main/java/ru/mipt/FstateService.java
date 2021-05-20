package ru.mipt;

import java.util.List;

public interface FstateService {
    List<Cascade> computeCascades(String fstate);
}
