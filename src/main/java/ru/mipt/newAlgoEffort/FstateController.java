package ru.mipt.newAlgoEffort;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mipt.Cascade;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FstateController {
    private final FstateService service;

    @RequestMapping("/")
    public List<Cascade> computeFstate(@RequestParam String fstate) {
        return service.computeCascades(fstate);
    }
}
