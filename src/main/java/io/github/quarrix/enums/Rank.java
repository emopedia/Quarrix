package io.github.quarrix.enums;

public enum Rank {
    E4("E4"),
    E3("E3"),
    E2("E2"),
    E1("E1"),
    D4("D4"),
    D3("D3"),
    D2("D2"),
    D1("D1"),
    C4("C4"),
    C3("C3"),
    C2("C2"),
    C1("C1"),
    B4("B4"),
    B3("B3"),
    B2("B2"),
    B1("B1"),
    A4("A4"),
    A3("A3"),
    A2("A2"),
    A1("A1");

    private String prefix;

    Rank(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
