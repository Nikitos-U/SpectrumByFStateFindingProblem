package ru.mipt.newAlgoEffort;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mipt.Cascade;
import ru.mipt.starters.DBStarter;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FstateController {
    private final FstateService service;
    private final DBStarter starter;

    @RequestMapping("/compute")
    public List<Cascade> computeFstate(@RequestParam String fstate) {
        return service.computeCascades(fstate);
    }

    @RequestMapping("/parse")
    public void parseFiles() {
        try {
            starter.parse();
        } catch (Exception e) {
        e.printStackTrace();
    }
    }
}
