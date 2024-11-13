package br.sergio.bakbata_mansion.sheet;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public final class Amulet extends SpecialItem {

    public Amulet(Long id, String name, String description) {
        super(id, name, description);
    }

    public Amulet(String name, String description) {
        super(name, description);
    }

}
