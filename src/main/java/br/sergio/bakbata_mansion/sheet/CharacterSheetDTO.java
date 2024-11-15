package br.sergio.bakbata_mansion.sheet;

import java.util.UUID;
import java.util.function.Function;

public record CharacterSheetDTO(
        UUID id,
        String name,
        Profession profession,
        Race race,
        Weapon weapon,
        AttrSet attributes,
        InventoryDTO inventory,
        GameElementDTO ring,
        GameElementDTO bracelet,
        GameElementDTO collar,
        GameElementDTO amulet,
        AbilitySetDTO abilities
) {

    public CharacterSheetDTO(CharacterSheet sheet) {
        this(
                sheet.getId(),
                sheet.getName(),
                sheet.getProfession(),
                sheet.getRace(),
                sheet.getWeapon(),
                sheet.getAttributeSet(),
                new InventoryDTO(sheet.getInventory()),
                convertIfPresent(sheet.getRing(), GameElementDTO::new),
                convertIfPresent(sheet.getBracelet(), GameElementDTO::new),
                convertIfPresent(sheet.getCollar(), GameElementDTO::new),
                convertIfPresent(sheet.getAmulet(), GameElementDTO::new),
                new AbilitySetDTO(sheet.getAbilitySet())
        );
    }

    private static <T, U> U convertIfPresent(T value, Function<T, U> function) {
        return value == null ? null : function.apply(value);
    }

}
