package br.sergio.bakbata_mansion.sheet;

import lombok.Getter;

import java.io.*;
import java.util.List;

@Getter
public enum Race implements AttributeContributor {

    HUMAN(500, 250),
    WITCH(640, 920),
    MAINTARI(530, 520),
    URILO(880, 490),
    BYUND(780,820),
    PILO(1000, 320),
    TRION(800, 550),
    NOIN(720, 720),
    FORI(700, 500),
    VABA(950, 1000),
    JINTE(690, 600);

    private final AttrSet attributeSet;

    private final Ability firstAbility;
    private final Ability secondAbility;
    private final Ability thirdAbility;

    Race(int healthPoints, int manaPoints) {
        attributeSet = new AttrSet(healthPoints, manaPoints, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        InputStream stream = getClass().getResourceAsStream("/abilities.csv");
        if (stream == null) {
            throw new RuntimeException("Missing file \"abilities.csv\" in classpath");
        }

        long index = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            List<String> lines = reader.lines().toList();
            List<String> abilities = lines.stream().filter(line -> line.startsWith(name())).toList();
            if (abilities.size() != 3) {
                throw new RuntimeException("Abilities for " + name() + " count different of 3");
            }
            firstAbility = parseAbility(abilities.getFirst(), lines);
            secondAbility = parseAbility(abilities.get(1), lines);
            thirdAbility = parseAbility(abilities.get(2), lines);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Ability parseAbility(String line, List<String> lines) {
        long index = lines.indexOf(line) + 1;
        String[] args = line.split("#");
        if (args.length != 3) {
            throw new RuntimeException("Ability args count different of 3");
        }
        return new Ability(index, args[1], args[2]);
    }

}
