package br.sergio.bakbata_mansion.controller;

import br.sergio.bakbata_mansion.Message;
import br.sergio.bakbata_mansion.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private ItemService service;

    @GetMapping("/{type}")
    public ResponseEntity<?> all(@PathVariable String type) {
        return switch (type) {
            case "Ring" -> ResponseEntity.ok(service.getRingRepository().findAll());
            case "Bracelet" -> ResponseEntity.ok(service.getBraceletRepository().findAll());
            case "Collar" -> ResponseEntity.ok(service.getCollarRepository().findAll());
            case "Amulet" -> ResponseEntity.ok(service.getAmuletRepository().findAll());
            case "Item" -> ResponseEntity.ok(service.getItemKindRepository().findAll());
            default -> ResponseEntity.badRequest().body(new Message(HttpStatus.BAD_REQUEST,
                    "Unrecognized item type: " + type + ". Available options: Ring, Bracelet, Collar, " +
                            "Amulet and Item."));
        };
    }

}
