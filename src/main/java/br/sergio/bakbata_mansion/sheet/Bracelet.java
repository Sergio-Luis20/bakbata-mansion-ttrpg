package br.sergio.bakbata_mansion.sheet;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@NoArgsConstructor
public final class Bracelet extends SpecialItem {

    public Bracelet(Long id, String name, String description) {
        super(id, name, description);
    }

    public Bracelet(String name, String description) {
        super(name, description);
    }

}
