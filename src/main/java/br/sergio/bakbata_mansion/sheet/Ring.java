package br.sergio.bakbata_mansion.sheet;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public final class Ring extends SpecialItem {

    public Ring(Long id, String name, String description) {
        super(id, name, description);
    }

    public Ring(String name, String description) {
        super(name, description);
    }

}
