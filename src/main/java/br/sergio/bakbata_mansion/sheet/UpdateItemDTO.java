package br.sergio.bakbata_mansion.sheet;

import java.util.Objects;
import java.util.UUID;

public record UpdateItemDTO(Object id, int amount) {

    public UpdateItemDTO(Object id, int amount) {
        this.id = Objects.requireNonNull(id, "id");
        this.amount = Math.max(amount, 0);

        if (isNewItem() && amount == 0) {
            throw new IllegalArgumentException("Cannot add a dead item to inventory");
        }
    }

    public UUID idAsUUID() {
        return id instanceof String str ? UUID.fromString(str) : (UUID) id;
    }

    public Long idAsLong() {
        return id instanceof String str ? Long.parseLong(str) : (id instanceof Integer i ? i.longValue() : (Long) id);
    }

    public boolean isNewItem() {
        try {
            long idLong = idAsLong();
            if (idLong <= 0) {
                throw new IllegalArgumentException("Not a valid long id, must be positive: " + idLong);
            }
            return true;
        } catch (NumberFormatException | ClassCastException e) {
            return false;
        }
    }

}
