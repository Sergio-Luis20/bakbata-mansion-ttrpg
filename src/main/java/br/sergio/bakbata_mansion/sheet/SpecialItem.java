package br.sergio.bakbata_mansion.sheet;

import br.sergio.bakbata_mansion.exception.NegativeIntegerException;
import br.sergio.bakbata_mansion.exception.NullJsonPropertyException;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode
public abstract sealed class SpecialItem implements GameElement<Long> permits Ring, Bracelet, Collar, Amulet, ItemKind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    public SpecialItem(Long id, String name, String description) {
        this(name, description);
        setId(id);
    }

    public SpecialItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setId(Long id) {
        if (id == null) {
            throw new NullJsonPropertyException("SpecialItem id cannot be null");
        }
        if (id <= 0) {
            throw new NegativeIntegerException("SpecialItem id must be positive");
        }
        this.id = id;
    }

}
