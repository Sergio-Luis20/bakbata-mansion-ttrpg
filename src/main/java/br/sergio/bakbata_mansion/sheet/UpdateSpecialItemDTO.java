package br.sergio.bakbata_mansion.sheet;

import java.util.Objects;

public record UpdateSpecialItemDTO(Long id) {

    public UpdateSpecialItemDTO {
        Objects.requireNonNull(id, "id");
        if (id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }
    }

}
