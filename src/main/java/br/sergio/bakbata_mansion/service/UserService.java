package br.sergio.bakbata_mansion.service;

import br.sergio.bakbata_mansion.user.GameUser;
import br.sergio.bakbata_mansion.user.UserDTO;
import br.sergio.bakbata_mansion.exception.UserAlreadyExistsException;
import br.sergio.bakbata_mansion.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserRepository repository;
    private PasswordEncoder encoder;

    @Value("${admin.username}")
    private String adminUsername;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public GameUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public GameUser addUser(@Valid UserDTO dto) {
        return addUser(dto, false);
    }

    public GameUser addUser(@Valid UserDTO dto, boolean admin) {
        String username = dto.username();

        if (exists(username)) {
            throw new UserAlreadyExistsException("User already exists: " + username);
        }

        String encodedPassword = encoder.encode(dto.password());
        GameUser user = new GameUser(username, encodedPassword);
        if (admin) {
            user.getEffectiveAuthorities().add("ADMIN");
        }
        return repository.save(user);
    }

    public boolean matches(String username, String rawPassword) {
        GameUser user = loadUserByUsername(username);
        return encoder.matches(rawPassword, user.getPassword());
    }

    public void removeUser(String username) {
        repository.deleteById(username);
    }

    public boolean exists(String username) {
        return repository.existsById(username);
    }

    public boolean isAdmin(String username) {
        return adminUsername.equals(username);
    }

}
