package br.sergio.bakbata_mansion.sheet;

import br.sergio.bakbata_mansion.exception.DeadItemException;
import br.sergio.bakbata_mansion.exception.InvalidIdException;

import java.util.UUID;

public record UpdateItemDTO(Object id, int amount) {

    public UpdateItemDTO(Object id, int amount) {
        if (id == null) {
            throw new InvalidIdException("Id cannot be null");
        }
        this.id = id;
        this.amount = Math.max(amount, 0);

        if (isNewItem() && amount == 0) {
            throw new DeadItemException("Cannot add a dead item to inventory");
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
                throw new InvalidIdException("Not a valid long id, must be positive: " + idLong);
            }
            return true;
        } catch (NumberFormatException | ClassCastException e) {
            return false;
        }
    }

}
