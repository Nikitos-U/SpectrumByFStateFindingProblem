package ru.mipt.dao;

public interface SqlColumn {
    String name();

    default String column() {
        return this.name().toUpperCase();
    }
    default String param() {
        return this.name().toLowerCase();
    }
}
