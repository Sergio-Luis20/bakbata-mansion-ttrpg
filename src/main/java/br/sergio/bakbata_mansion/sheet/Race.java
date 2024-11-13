package br.sergio.bakbata_mansion.sheet;

import lombok.Getter;

@Getter
public enum Race implements AttributeContributor {

    HUMAN(500, 250),
    WITCH(640, 920),
    MAINTARI(530, 420),
    URILO(880, 490),
    BYUND(780,820),
    PILO(1000, 320),
    TRION(800, 550),
    NOIN(720, 720),
    FORI(700, 500),
    VABA(950, 1000),
    JINTE(690, 600);

    private final AttrSet attributeSet;

    Race(int healthPoints, int manaPoints) {
        attributeSet = new AttrSet(healthPoints, manaPoints, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

}
