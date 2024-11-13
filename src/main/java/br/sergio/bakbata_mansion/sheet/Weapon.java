package br.sergio.bakbata_mansion.sheet;

import lombok.Getter;

@Getter
public enum Weapon implements AttributeContributor {

    SWORD(80, 30, 117),
    BOW(70, 5, 137),
    STAFF(60, 70, 125),
    SPEAR(75, 20, 134),
    SLINGSHOT(45, 5, 156),
    BAZOOKA(100, 40, 107),
    SHIELD(10, 100, 121),
    CARDS(90, 50, 90),
    WHIP(80, 20, 130),
    NUNCHAKU(95, 10, 100),
    DAGGER(65, 30, 143),
    SPRAY(90, 15, 140),
    HAMMER(95, 60, 95);

    private final AttrSet attributeSet;

    Weapon(int attack, int defense, int speed) {
        attributeSet = new AttrSet(0, 0, attack, defense, speed, 0, 0, 0, 0, 0, 0);
    }

}
