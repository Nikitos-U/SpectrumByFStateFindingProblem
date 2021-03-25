package ru.mipt.newAlgoEffort;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FstateController {
    private final FstateService service;

    @RequestMapping("/")
    public String computeFstate() {
        return null;
    }
}
