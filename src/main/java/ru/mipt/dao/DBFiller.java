package ru.mipt.dao;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class DBFiller {

    private final DbPatcher patcher;


    @SneakyThrows
    public void parse() {
        this.patcher.createTables();
    }
}
