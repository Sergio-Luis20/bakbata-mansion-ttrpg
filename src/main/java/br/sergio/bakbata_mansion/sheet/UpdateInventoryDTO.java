package br.sergio.bakbata_mansion.sheet;

import java.util.List;
import java.util.Objects;

public record UpdateInventoryDTO(List<UpdateItemDTO> items) {

    public UpdateInventoryDTO {
        for (UpdateItemDTO item : Objects.requireNonNull(items, "items")) {
            Objects.requireNonNull(item, "item");
        }
    }

}
