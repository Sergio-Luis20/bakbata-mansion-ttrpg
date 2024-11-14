package br.sergio.bakbata_mansion.controller;

import br.sergio.bakbata_mansion.sheet.AbilitySetDTO;
import br.sergio.bakbata_mansion.sheet.Race;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ability")
@AllArgsConstructor
public class AbilityController {

    @GetMapping("/{race}")
    public ResponseEntity<AbilitySetDTO> getAbilities(@PathVariable Race race) {
        AbilitySetDTO dto = new AbilitySetDTO(race.getFirstAbility(), race.getSecondAbility(), race.getThirdAbility());
        return ResponseEntity.ok(dto);
    }

}
