package ru.mipt;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mipt.starters.DBStarter;

@RestController
@RequiredArgsConstructor
public class FstateController {
    private final FstateService service;
    private final DBStarter starter;

    @RequestMapping("/compute")
    public String computeFstate(@RequestParam String fstate) {
        return service.computeCascades(fstate).toString();
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
