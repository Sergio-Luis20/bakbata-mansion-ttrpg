package br.sergio.bakbata_mansion.sheet;

import br.sergio.bakbata_mansion.exception.NegativeIntegerException;
import br.sergio.bakbata_mansion.exception.NullJsonPropertyException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class Ability implements GameElement<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    public Ability(Long id, String name, String description) {
        this(name, description);
        setId(id);
    }

    public Ability(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setId(Long id) {
        if (id == null) {
            throw new NullJsonPropertyException("Ability id cannot be null");
        }
        if (id <= 0) {
            throw new NegativeIntegerException("Ability id must be positive");
        }
        this.id = id;
    }

}
