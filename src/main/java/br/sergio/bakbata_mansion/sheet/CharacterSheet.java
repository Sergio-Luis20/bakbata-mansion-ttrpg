package br.sergio.bakbata_mansion.sheet;

import br.sergio.bakbata_mansion.user.GameUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "character_sheet")
public class CharacterSheet implements AttributeContributor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private GameUser user;

    @Column(length = 36, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Profession profession;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Race race;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Weapon weapon;

    @Embedded
    @Column(nullable = false)
    private AttrSet attributeSet;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Inventory inventory;

    @ManyToOne
    private Ring ring;

    @ManyToOne
    private Bracelet bracelet;

    @ManyToOne
    private Collar collar;

    @ManyToOne
    private Amulet amulet;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private AbilitySet abilitySet;

    public CharacterSheet(String name, Profession profession) {
        this.name = Objects.requireNonNull(name, "name");
        this.profession = Objects.requireNonNull(profession, "profession");

        calculateAttributes();
        abilitySet = new AbilitySet();
        inventory = new Inventory();
        inventory.setSheet(this);
    }

    public AttrSet getBasicAttributes() {
        return AttrSet.sum(profession, race, weapon);
    }

    public void calculateAttributes() {
        attributeSet = getBasicAttributes();
    }

    public void setAttributeSet(AttrSet attributeSet) {
        this.attributeSet = Objects.requireNonNull(attributeSet, "attributeSet");
    }

}
