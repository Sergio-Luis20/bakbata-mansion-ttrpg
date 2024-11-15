package br.sergio.bakbata_mansion.controller;

import br.sergio.bakbata_mansion.Message;
import br.sergio.bakbata_mansion.sheet.Race;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/story")
public class StoryController {

    @GetMapping("/race/{race}")
    public ResponseEntity<String> getStory(@PathVariable Race race) {
        return ResponseEntity.ok()
                .header("Content-Type", "text/plain")
                .body(race.getDescription());
    }

    @GetMapping("/all/{type}")
    public ResponseEntity<?> getAllStories(@PathVariable String type) {
        Map<String, String> response = new HashMap<>();
        switch (type) {
            case "Race" -> Arrays.asList(Race.values()).forEach(race -> response.put(race.toString(), race.getDescription()));
            default -> {
                return ResponseEntity.badRequest().body(new Message(HttpStatus.BAD_REQUEST, "Type not found: " + type));
            }
        }
        return ResponseEntity.ok(response);
    }

}
