package ru.mipt.starters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mipt.dao.DBFiller;
import ru.mipt.dao.DbPatcher;
import ru.mipt.parsers.DecayParser;
import ru.mipt.parsers.ParticleParser;

@Service
@RequiredArgsConstructor
public class DBStarter {
    private final DecayParser decayParser;
    private final ParticleParser particleParser;
    private final DbPatcher patcher;

    public void parse() {
        DBFiller filler = new DBFiller(decayParser, particleParser, patcher);
        filler.parse();
    }
}
