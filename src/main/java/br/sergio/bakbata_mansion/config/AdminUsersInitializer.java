package br.sergio.bakbata_mansion.config;

import br.sergio.bakbata_mansion.service.UserService;
import br.sergio.bakbata_mansion.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AdminUsersInitializer {

    private final UserService service;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Bean
    public CommandLineRunner initializeAdmins() {
        String username = adminUsername;
        String password = adminPassword;

        adminUsername = null;
        adminPassword = null;

        return args -> {
            if (!service.exists(username)) {
                service.addUser(new UserDTO(username, password), true);
            }
        };
    }

}
