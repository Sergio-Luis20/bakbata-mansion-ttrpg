package br.sergio.bakbata_mansion.service;

import br.sergio.bakbata_mansion.exception.*;
import br.sergio.bakbata_mansion.repository.CharacterSheetRepository;
import br.sergio.bakbata_mansion.sheet.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CharacterSheetService {

    private CharacterSheetRepository repository;
    private ItemService itemService;

    public Optional<CharacterSheet> getSheet(UUID id) {
        return repository.findById(id);
    }

    public CharacterSheet saveSheet(CharacterSheet sheet) {
        if (sheet.getUser() == null) {
            throw new OrphanSheetException("Character sheet must have an associated user");
        }
        return repository.save(sheet);
    }

    public CharacterSheet updateSheet(UUID id, UpdateSheetDTO sheetDTO) {
        return repository.findById(id).map(sheet -> updateSheet(sheet, sheetDTO)).orElseThrow(() -> new NotFoundException("Sheet not found for id: " + id));
    }

    @Transactional
    public CharacterSheet updateSheet(CharacterSheet sheet, UpdateSheetDTO sheetDTO) {
        boolean modified = false;

        if (sheetDTO.weapon() != null) {
            sheet.setWeapon(sheetDTO.weapon());
            modified = true;
        }
        if (sheetDTO.race() != null) {
            sheet.setRace(sheetDTO.race());
            modified = true;
        }

        AttrSet set = sheetDTO.attributes();
        if (set != null) {
            sheet.setAttributeSet(set);
        } else if (modified) {
            sheet.calculateAttributes();
        }

        UpdateInventoryDTO inv = sheetDTO.inventory();
        if (inv != null) {
            Map<UUID, Integer> itemsToUpdate = inv.items().stream()
                    .filter(item -> item != null && !item.isNewItem())
                    .collect(Collectors.toMap(UpdateItemDTO::idAsUUID, UpdateItemDTO::amount));

            Inventory inventory = sheet.getInventory();
            List<Item> items = inventory.getItems();
            for (Item item : items) {
                Integer amount = itemsToUpdate.get(item.getId());
                if (amount == null) {
                    continue;
                }
                if (amount < 0) {
                    throw new NegativeIntegerException("Item amount can't be negative");
                }
                item.setAmount(amount);
            }
            List<Item> itemsToDelete = items.stream().filter(Item::isDead).toList();
            items.removeAll(itemsToDelete);

            Map<Long, UpdateItemDTO> newItems = inv.items().stream().filter(UpdateItemDTO::isNewItem)
                    .collect(Collectors.toMap(UpdateItemDTO::idAsLong, item -> item));
            List<ItemKind> itemsToAdd = itemService.getItemKindRepository().findAllById(newItems.keySet());
            List<Item> createdItems = itemsToAdd.stream().map(item -> new Item(item.getName(),
                    newItems.get(item.getId()).amount(), item.getDescription())).toList();

            if (createdItems.size() + inventory.size() > inventory.capacity()) {
                throw new FullInventoryException("Full inventory");
            }

            createdItems.forEach(inventory::add);
        }

        updateSpecialItem(sheet, "Ring", sheetDTO.ring());
        updateSpecialItem(sheet, "Bracelet", sheetDTO.bracelet());
        updateSpecialItem(sheet, "Collar", sheetDTO.collar());
        updateSpecialItem(sheet, "Amulet", sheetDTO.amulet());

        UpdateAbilitiesDTO abilities = sheetDTO.abilities();
        if (abilities != null) {
            AbilitySet abilitySet = sheet.getAbilitySet();
            Race race = sheet.getRace();
            if (abilities.first() != null) {
                abilitySet.setFirst(abilities.first() ? race.getFirstAbility() : null);
            }
            if (abilities.second() != null) {
                abilitySet.setSecond(abilities.second() ? race.getSecondAbility() : null);
            }
            if (abilities.third() != null) {
                abilitySet.setThird(abilities.third() ? race.getThirdAbility() : null);
            }
        }

        return repository.save(sheet);
    }

    private void updateSpecialItem(CharacterSheet sheet, String type, UpdateSpecialItemDTO dto) {
        switch (type) {
            case "Ring" -> setSpecialItem(itemService.getRingRepository(), type, dto, sheet::setRing);
            case "Bracelet" -> setSpecialItem(itemService.getBraceletRepository(), type, dto, sheet::setBracelet);
            case "Collar" -> setSpecialItem(itemService.getCollarRepository(), type, dto, sheet::setCollar);
            case "Amulet" -> setSpecialItem(itemService.getAmuletRepository(), type, dto, sheet::setAmulet);
            default -> throw new IllegalArgumentException("Unexpected SpecialItem type: " + type);
        }
    }

    private <T> void setSpecialItem(JpaRepository<T, Long> repository, String type,
                                    UpdateSpecialItemDTO dto, Consumer<T> consumer) {
        Long id;
        if (dto == null || (id = dto.id()) == null) {
            consumer.accept(null);
            return;
        }
        repository.findById(id)
                .ifPresentOrElse(consumer, () -> {
                    throw new ItemNotFoundException(type + " not found for id: " + id);
                });
    }

    public void removeSheet(UUID id) {
        repository.deleteById(id);
    }

    public boolean exists(UUID id) {
        return repository.existsById(id);
    }

}
