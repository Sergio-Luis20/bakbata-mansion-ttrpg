package br.sergio.bakbata_mansion.sheet;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.util.Objects;
import java.util.UUID;

public record GameElementDTO(Object id, String name, String description) {

    public GameElementDTO {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(description, "description");
    }

    public GameElementDTO(GameElement<?> item) {
        this(item.getId(), item.getName(), item.getDescription());
    }

    public <T extends SpecialItem> T asSpecialItem(Class<T> specialItemClass) {
        try {
            if (specialItemClass == SpecialItem.class) {
                throw new IllegalArgumentException("Cannot pass abstract SpecialItem class");
            }
            Constructor<T> constructor = specialItemClass.getConstructor(Long.class, String.class, String.class);
            return constructor.newInstance(toLong(id), name, description);
        } catch (Exception e) {
            if (e instanceof RuntimeException ex) {
                throw ex;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    public Ability asAbility() {
        return new Ability(toLong(id), name, description);
    }

    private static Long toLong(Object id) {
        return id instanceof String str ? Long.parseLong(str) : (Long) id;
    }

}
