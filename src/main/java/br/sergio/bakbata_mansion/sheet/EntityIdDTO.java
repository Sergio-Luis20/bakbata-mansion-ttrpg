package br.sergio.bakbata_mansion.sheet;

import br.sergio.bakbata_mansion.exception.NegativeIntegerException;
import br.sergio.bakbata_mansion.exception.NullJsonPropertyException;

public record EntityIdDTO<T>(T id) {

    public EntityIdDTO {
        if (id == null) {
            throw new NullJsonPropertyException("Id cannot be null");
        }
        if (id instanceof Long l && l <= 0) {
            throw new NegativeIntegerException("Id cannot be negative");
        }
    }

    public EntityIdDTO(GameElement<T> element) {
        this(element.getId());
    }

}
