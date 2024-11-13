package br.sergio.bakbata_mansion.sheet;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class AbilitySet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "abilitySet")
    private CharacterSheet sheet;

    @ManyToOne
    @JoinColumn(name = "first_ability_id")
    private Ability first;

    @ManyToOne
    @JoinColumn(name = "second_ability_id")
    private Ability second;

    @ManyToOne
    @JoinColumn(name = "third_ability_id")
    private Ability third;

    public AbilitySet(Ability first, Ability second, Ability third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public void setId(UUID id) {
        this.id = Objects.requireNonNull(id, "id");
    }

}
