package br.sergio.bakbata_mansion.sheet;

public record UpdateSheetDTO(
        Race race,
        Weapon weapon,
        AttrSet attributes,
        UpdateInventoryDTO inventory,
        UpdateSpecialItemDTO ring,
        UpdateSpecialItemDTO bracelet,
        UpdateSpecialItemDTO collar,
        UpdateSpecialItemDTO amulet,
        AbilitySetDTO abilities
) {
}
