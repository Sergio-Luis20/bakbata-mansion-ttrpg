package br.sergio.bakbata_mansion.sheet;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public final class ItemKind extends SpecialItem {

    public ItemKind(Long id, String name, String description) {
        super(id, name, description);
    }

    public ItemKind(String name, String description) {
        super(name, description);
    }

}
