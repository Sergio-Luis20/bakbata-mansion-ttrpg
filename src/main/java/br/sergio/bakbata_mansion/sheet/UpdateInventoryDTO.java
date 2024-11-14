package br.sergio.bakbata_mansion.sheet;

import br.sergio.bakbata_mansion.exception.NullJsonPropertyException;

import java.util.List;

public record UpdateInventoryDTO(List<UpdateItemDTO> items) {

    public UpdateInventoryDTO {
        if (items == null) {
            throw new NullJsonPropertyException("Items cannot be null");
        }
        for (UpdateItemDTO item : items) {
            if (item == null) {
                throw new NullJsonPropertyException("Inventory item cannot be null");
            }
        }
    }

}
