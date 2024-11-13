package br.sergio.bakbata_mansion.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDTO(@NotNull @Pattern(regexp = "\\A(?!\\d)[\\u0020-\\uFFFD]{3,36}\\z") String username, @NotNull @Size(min = 8, max = 128) String password) {
}

