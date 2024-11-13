package br.sergio.bakbata_mansion.sheet;

import lombok.Getter;

@Getter
public enum Profession implements AttributeContributor {

    POLICE(18, 12, 7, 14, 2, 9), // 60
    BUTCHER(11, 4, 6, 18, 10, 12), // 61
    DOCTOR(5, 5, 14, 6, 18, 20), // 68
    ENGINEER(8, 9, 16, 11, 4, 16), // 64
    MASON(12, 13, 4, 20, 3, 12), // 64
    PROFESSOR(7, 6, 20, 8, 12, 7), // 60
    MUSICIAN(0, 16, 13, 10, 12, 19), // 70
    FARMER(16, 15, 8, 19, 7, 1), // 66
    PHILOSOPHER(4, 11, 20, 15, 17, 3), // 70
    CARPENTER(6, 5, 11, 17, 10, 20), // 69
    LUMBERJACK(14, 12, 2, 20, 5, 14), // 67
    SOLDIER(20, 18, 10, 12, 1, 3), // 64
    ADMINISTRATOR(4, 9, 15, 3, 20, 12), // 63
    FLORIST(6, 10, 11, 2, 19, 17), // 65
    PRIEST(10, 4, 15, 5, 20, 7), // 61
    FISHERMAN(17, 13, 5, 18, 11, 6), // 70
    CHEF(8, 7, 16, 17, 7, 15), // 70
    ATHLETE(19, 20, 2, 18, 0, 11), // 70
    JUDGE(7, 7, 20, 11, 19, 6), //  70
    STUDENT(11, 11, 11, 11, 11, 11); // 66

    private final AttrSet attributeSet;

    Profession(int aim, int dexterity, int intelligence, int strength, int spirit, int precision) {
        attributeSet = new AttrSet(0, 0, 0, 0, 0, aim, dexterity, intelligence, strength, spirit, precision);
    }

}
