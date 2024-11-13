package br.sergio.bakbata_mansion.sheet;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public final class Collar extends SpecialItem {

    public Collar(Long id, String name, String description) {
        super(id, name, description);
    }

    public Collar(String name, String description) {
        super(name, description);
    }

}
