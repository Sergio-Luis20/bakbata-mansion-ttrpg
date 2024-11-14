package br.sergio.bakbata_mansion.controller;

import br.sergio.bakbata_mansion.Message;
import br.sergio.bakbata_mansion.service.CharacterSheetService;
import br.sergio.bakbata_mansion.service.UserService;
import br.sergio.bakbata_mansion.sheet.*;
import br.sergio.bakbata_mansion.user.GameUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

@Controller
@RequestMapping("/sheet")
@AllArgsConstructor
public class SheetController {

    private UserService userService;
    private CharacterSheetService service;

    @GetMapping("/all-sheets/{username}")
    public ResponseEntity<List<CharacterSheetDTO>> getAllSheets(@PathVariable String username) {
        try {
            return new ResponseEntity<>(userService.loadUserByUsername(username).getSheets().stream()
                    .map(CharacterSheetDTO::new).toList(), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all-sheets-abstracts/{username}")
    public ResponseEntity<List<SheetAbstract>> getAllSheetsAbstracts(@PathVariable String username) {
        try {
            return new ResponseEntity<>(userService.loadUserByUsername(username).getSheets().stream()
                    .map(SheetAbstract::new).toList(), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterSheetDTO> getSheet(@PathVariable UUID id) {
        return service.getSheet(id)
                .map(sheet -> new ResponseEntity<>(new CharacterSheetDTO(sheet), HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all-options/{type}")
    public ResponseEntity<?> allOptions(@PathVariable String type) {
        List<String> options = switch (type) {
            case "Profession" -> Arrays.stream(Profession.values()).map(Profession::toString).toList();
            case "Race" -> Arrays.stream(Race.values()).map(Race::toString).toList();
            case "Weapon" -> Arrays.stream(Weapon.values()).map(Weapon::toString).toList();
            default -> null;
        };
        if (options == null) {
            return ResponseEntity.badRequest().body(new Message(HttpStatus.BAD_REQUEST,
                    "Invalid type: " + type + ". Allowed types: Profession, Race and Weapon."));
        }
        return ResponseEntity.ok(options);
    }

    @PostMapping
    public ResponseEntity<?> createSheet(@RequestBody @Valid CreateSheetDTO sheetDTO, JwtAuthenticationToken token) {
        try {
            GameUser user = userService.loadUserByUsername(token.getName());
            CharacterSheet sheet = service.saveSheet(sheetDTO.toSheet(user));
            URI resourceURI = ServletUriComponentsBuilder.fromCurrentRequest()
                    .pathSegment(sheet.getId().toString())
                    .build()
                    .toUri();
            return ResponseEntity.created(resourceURI).body(new CharacterSheetDTO(sheet));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @PatchMapping("/update/{sheetId}")
    public ResponseEntity<?> updateSheet(@PathVariable UUID sheetId, @RequestBody @Valid UpdateSheetDTO sheetDTO, JwtAuthenticationToken token) {
        return operation(sheetId, token, (id, sheet) -> ResponseEntity.ok(new CharacterSheetDTO(service.updateSheet(sheet, sheetDTO))));
    }

    @DeleteMapping("/{sheetId}")
    public ResponseEntity<?> deleteSheet(@PathVariable UUID sheetId, JwtAuthenticationToken token) {
        return operation(sheetId, token, (id, sheet) -> {
            service.removeSheet(id);
            return ResponseEntity.noContent().build();
        });
    }

    private ResponseEntity<?> operation(UUID id, JwtAuthenticationToken token, BiFunction<UUID, CharacterSheet, ResponseEntity<?>> function) {
        try {
            Optional<CharacterSheet> optional = service.getSheet(id);
            if (optional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            CharacterSheet sheet = optional.get();
            String username = sheet.getUser().getUsername();
            if (!username.equals(token.getName()) && !userService.isAdmin(username)) {
                return new ResponseEntity<>(new Message(HttpStatus.FORBIDDEN, "Access denied"), HttpStatus.FORBIDDEN);
            }
            return function.apply(id, sheet);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

}
