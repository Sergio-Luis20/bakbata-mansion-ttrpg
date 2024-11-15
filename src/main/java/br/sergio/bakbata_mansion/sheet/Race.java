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

    private Ability firstAbility;
    private Ability secondAbility;
    private Ability thirdAbility;

    private String description;

    Race(int healthPoints, int manaPoints) {
        attributeSet = new AttrSet(healthPoints, manaPoints, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        setAbilities();
    }

    private void setAbilities() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream("/abilities.csv")))) {
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

    private void setDescriptions() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream("/races.csv")))) {
            description = reader.lines()
                    .filter(line -> line.startsWith(name()))
                    .findFirst()
                    .get()
                    .split("#")[1];
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

    private static InputStream getInputStream(String file) {
        InputStream stream = Race.class.getResourceAsStream(file);
        if (stream == null) {
            throw new RuntimeException("Missing file \"" + file +"\" in classpath");
        }
        return stream;
    }

}
