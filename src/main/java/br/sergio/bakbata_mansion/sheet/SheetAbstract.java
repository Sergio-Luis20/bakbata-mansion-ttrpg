package br.sergio.bakbata_mansion.sheet;

import java.util.UUID;

public record SheetAbstract(UUID id, String name, Profession profession, Race race, Weapon weapon) {

    public SheetAbstract(CharacterSheet sheet) {
        this(sheet.getId(), sheet.getName(), sheet.getProfession(), sheet.getRace(), sheet.getWeapon());
    }

}
