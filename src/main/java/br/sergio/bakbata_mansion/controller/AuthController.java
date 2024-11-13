package br.sergio.bakbata_mansion.controller;

import br.sergio.bakbata_mansion.Message;
import br.sergio.bakbata_mansion.user.GameUser;
import br.sergio.bakbata_mansion.user.UserDTO;
import br.sergio.bakbata_mansion.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private UserService service;
    private JwtEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDTO register) {
        if (service.exists(register.username())) {
            return message(HttpStatus.CONFLICT, "User already exists");
        }
        GameUser user = service.addUser(register);
        String token = newToken(user.getUsername());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Content-Type", "text/plain")
                .body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserDTO login) {
        if (!service.exists(login.username())) {
            return message(HttpStatus.UNAUTHORIZED, "User doesn't exist");
        }
        if (!service.matches(login.username(), login.password())) {
            return message(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }
        String token = newToken(login.username());
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type", "text/plain")
                .body(token);
    }

    private String newToken(String username) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Bakbata's Mansion RPG")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(8 * 60 * 60)) // 8 hours
                .subject(username)
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    static ResponseEntity<Message> message(HttpStatusCode code, String message) {
        return new ResponseEntity<>(new Message(code, message), code);
    }

}
