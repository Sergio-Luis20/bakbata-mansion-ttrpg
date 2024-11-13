package br.sergio.bakbata_mansion.service;

import br.sergio.bakbata_mansion.exception.ItemNotFoundException;
import br.sergio.bakbata_mansion.exception.NotFoundException;
import br.sergio.bakbata_mansion.exception.OrphanSheetException;
import br.sergio.bakbata_mansion.repository.CharacterSheetRepository;
import br.sergio.bakbata_mansion.repository.ItemRepository;
import br.sergio.bakbata_mansion.repository.specials.ItemKindRepository;
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




    /*
     * Adicionar nível na ficha. Vai de 0 a 30.
     * Criar habilidades. Apenas 3 habilidades por raça.
     * O jogador ganha a primeira habilidade no nível 10, a segunda no nível 20
     * e a terceira no nível 30.
     * Lembrar de adicionar isso ao sistema de atualizar a ficha.
     */




    private CharacterSheetRepository repository;
    private ItemRepository itemRepository;
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
            Map<Long, UpdateItemDTO> newItems = inv.items().stream().filter(UpdateItemDTO::isNewItem)
                    .collect(Collectors.toMap(UpdateItemDTO::idAsLong, item -> item));
            List<ItemKind> itemsToAdd = itemService.getItemKindRepository().findAllById(newItems.keySet());
            itemRepository.saveAll(itemsToAdd.stream().map(item -> {
                Item newItem = new Item(item.getName(), newItems.get(item.getId()).amount(),
                        item.getDescription());
                newItem.setInventory(sheet.getInventory());
                return newItem;
            }).toList());
            Map<UUID, Integer> itemsToUpdate = inv.items().stream()
                    .filter(item -> !item.isNewItem())
                    .collect(Collectors.toMap(UpdateItemDTO::idAsUUID, UpdateItemDTO::amount));
            List<Item> items = itemRepository.findAllById(itemsToUpdate.keySet());
            items.forEach(item -> item.setAmount(itemsToUpdate.get(item.getId())));
            List<Item> itemsToDelete = items.stream().filter(Item::isDead).toList();
            items.removeAll(itemsToDelete);
            itemRepository.saveAll(items);
            itemRepository.deleteAll(itemsToDelete);
        }

        updateSpecialItem(sheet, "Ring", sheetDTO.ring());
        updateSpecialItem(sheet, "Bracelet", sheetDTO.bracelet());
        updateSpecialItem(sheet, "Collar", sheetDTO.collar());
        updateSpecialItem(sheet, "Amulet", sheetDTO.amulet());

        AbilitySetDTO abilities = sheetDTO.abilities();
        if (abilities != null) {
            AbilitySet abilitySet = sheet.getAbilitySet();
            abilitySet.setFirst(abilities.firstAsAbility());
            abilitySet.setSecond(abilities.secondAsAbility());
            abilitySet.setThird(abilities.thirdAsAbility());
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
        if (dto == null) {
            consumer.accept(null);
            return;
        }
        long id = dto.id();;
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
