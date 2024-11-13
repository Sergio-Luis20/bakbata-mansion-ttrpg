package br.sergio.bakbata_mansion.sheet;

import java.util.List;
import java.util.UUID;

public record InventoryDTO(List<ItemDTO> items, int size, int capacity) {

    public InventoryDTO(Inventory inventory) {
        this(inventory.getItems().stream().map(ItemDTO::new).toList(), inventory.size(), inventory.capacity());
    }

}
