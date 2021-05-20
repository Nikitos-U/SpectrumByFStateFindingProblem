package ru.mipt.starters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mipt.dao.DBFiller;
import ru.mipt.dao.DbPatcher;

@Component
@RequiredArgsConstructor
public class DBStarter {
    private final DbPatcher patcher;

    public void parse() {
        DBFiller filler = new DBFiller(patcher);
        filler.parse();
    }
}