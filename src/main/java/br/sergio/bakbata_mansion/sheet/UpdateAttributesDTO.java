package br.sergio.bakbata_mansion.sheet;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UpdateAttributesDTO(
        @Min(0) @Max(1000) int healthPoints,
        @Min(0) @Max(1000) int manaPoints,
        @Min(0) @Max(100) int attack,
        @Min(0) @Max(100) int defense,
        @Min(0) @Max(200) int speed,
        @Min(0) @Max(20) int aim,
        @Min(0) @Max(20) int dexterity,
        @Min(0) @Max(20) int intelligence,
        @Min(0) @Max(20) int strength,
        @Min(0) @Max(20) int spirit,
        @Min(0) @Max(20) int precision
) implements AttributeContributor {

    @Override
    public AttrSet getAttributeSet() {
        return new AttrSet(healthPoints, manaPoints, attack, defense, speed, aim, dexterity,
                intelligence, strength, spirit, precision);
    }

}
