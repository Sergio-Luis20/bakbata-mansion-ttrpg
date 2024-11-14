package br.sergio.bakbata_mansion.sheet;

public record AbilitySetDTO(Ability first, Ability second, Ability third) {

    public AbilitySetDTO(AbilitySet set) {
        this(set.getFirst(), set.getSecond(), set.getThird());
    }

}
