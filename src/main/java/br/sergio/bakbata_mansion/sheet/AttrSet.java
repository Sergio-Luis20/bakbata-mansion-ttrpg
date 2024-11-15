package br.sergio.bakbata_mansion.sheet;

import br.sergio.bakbata_mansion.exception.NegativeIntegerException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;

import java.util.Arrays;
import java.util.Objects;

@Embeddable
public record AttrSet(
        int healthPoints,
        int manaPoints,
        int attack,
        int defense,
        int speed,
        int aim,
        int dexterity,
        int intelligence,
        int strength,
        int spirit,
        int precision
) implements AttributeContributor {

    public static final AttrSet ZERO = new AttrSet(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

    public AttrSet {
        if (healthPoints < 0 || manaPoints < 0 || attack < 0 || defense < 0 || speed < 0 || aim < 0
                || dexterity < 0 || intelligence < 0 || strength < 0 || spirit < 0 || precision < 0) {
            throw new NegativeIntegerException("All args must be non-negative");
        }
    }

    @Override
    @JsonIgnore
    public AttrSet getAttributeSet() {
        return this;
    }

    public static AttrSet sum(AttributeContributor... attributeContributors) {
        return sum(Arrays.stream(attributeContributors)
                .filter(Objects::nonNull)
                .map(AttributeContributor::getAttributeSet)
                .toArray(AttrSet[]::new));
    }

    public static AttrSet sum(AttrSet... sets) {
        AttrSet result = ZERO;
        for (AttrSet set : sets) {
            result = new AttrSet(
                    result.healthPoints + set.healthPoints,
                    result.manaPoints + set.manaPoints,
                    result.attack + set.attack,
                    result.defense + set.defense,
                    result.speed + set.speed,
                    result.aim + set.aim,
                    result.dexterity + set.dexterity,
                    result.intelligence + set.intelligence,
                    result.strength + set.strength,
                    result.spirit + set.spirit,
                    result.precision + set.precision
            );
        }
        return result;
    }

}
