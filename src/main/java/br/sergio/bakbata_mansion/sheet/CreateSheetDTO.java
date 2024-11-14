package br.sergio.bakbata_mansion.sheet;

import br.sergio.bakbata_mansion.user.GameUser;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public record CreateSheetDTO(@NotNull @Pattern(regexp = "\\A[a-zA-ZáéíóúýÁÉÍÓÚÝâêîôûÂÊÎÔÛãõñÃÕÑçÇàèìòùÀÈÌÒÙäëïöüÄËÏÖÜ _'-]{3,36}\\z") String name, @NotNull Profession profession) {

    public CharacterSheet toSheet(GameUser user) {
        CharacterSheet sheet = new CharacterSheet(name, profession);
        sheet.setUser(Objects.requireNonNull(user, "user"));
        return sheet;
    }

}
