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
@Table(name = "items")
public class Item implements GameElement<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    private String name;
    private String description;

    private int amount;

    public Item(String name, String description) {
        this(name, 1, description);
    }

    public Item(String name, int amount, String description) {
        this.name = Objects.requireNonNull(name, "name");
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.amount = amount;
        this.description = description;
    }

    public void setId(UUID id) {
        this.id = Objects.requireNonNull(id, "id");
    }

    public void setAmount(int amount) {
        this.amount = Math.max(amount, 0);
    }

    public void addAmount(int amount) {
        setAmount(this.amount + amount);
    }

    public void subtractAmount(int amount) {
        setAmount(this.amount - amount);
    }

    public void incrementAmount() {
        addAmount(1);
    }

    public void decrementAmount() {
        subtractAmount(1);
    }

    public boolean isDead() {
        return amount == 0;
    }

}
