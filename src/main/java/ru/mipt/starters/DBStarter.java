package ru.mipt.starters;

import ru.mipt.parsers.DecayParser;
import ru.mipt.parsers.ParticleParser;

import java.io.FileNotFoundException;

public class DBStarter {
    public static void main(String[] args) {
        DecayParser decayParser = null;
        try {
            decayParser = new DecayParser();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ParticleParser particleParser = null;
        try {
            particleParser = new ParticleParser();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ru.mipt.dao.DBFiller filler = new ru.mipt.dao.DBFiller(decayParser, particleParser);
        filler.parse();
    }
}
