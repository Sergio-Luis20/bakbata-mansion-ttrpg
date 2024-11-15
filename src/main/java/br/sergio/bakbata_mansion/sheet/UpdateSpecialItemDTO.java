package br.sergio.bakbata_mansion.sheet;

import br.sergio.bakbata_mansion.exception.NegativeIntegerException;
import br.sergio.bakbata_mansion.exception.NullJsonPropertyException;

public record UpdateSpecialItemDTO(Long id) {

    public UpdateSpecialItemDTO {
        if (id == null) {
            throw new NullJsonPropertyException("id cannot be null");
        }
        if (id <= 0) {
            throw new NegativeIntegerException("id must be positive");
        }
    }

}
