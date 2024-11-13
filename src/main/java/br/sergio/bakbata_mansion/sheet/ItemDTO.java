package br.sergio.bakbata_mansion.sheet;

import java.util.UUID;

public record ItemDTO(UUID id, String name, int amount, String description) {

    public ItemDTO(Item item) {
        this(item.getId(), item.getName(), item.getAmount(), item.getDescription());
    }

}
