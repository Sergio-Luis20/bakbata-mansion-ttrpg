package br.sergio.bakbata_mansion.sheet;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public record AbilitySetDTO(GameElementDTO first, GameElementDTO second, GameElementDTO third) {

    public AbilitySetDTO(AbilitySet set) {
        this(
                convertIfPresent(set.getFirst(), GameElementDTO::new),
                convertIfPresent(set.getSecond(), GameElementDTO::new),
                convertIfPresent(set.getThird(), GameElementDTO::new)
        );
    }

    public Ability firstAsAbility() {
        return first == null ? null : first.asAbility();
    }

    public Ability secondAsAbility() {
        return second == null ? null : second.asAbility();
    }

    public Ability thirdAsAbility() {
        return third == null ? null : third.asAbility();
    }

    private static <T, U> U convertIfPresent(T value, Function<T, U> function) {
        return value == null ? null : function.apply(value);
    }

}
