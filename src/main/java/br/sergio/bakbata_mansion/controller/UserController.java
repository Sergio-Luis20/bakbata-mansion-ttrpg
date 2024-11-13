package br.sergio.bakbata_mansion.controller;

import br.sergio.bakbata_mansion.Message;
import br.sergio.bakbata_mansion.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService service;

    @DeleteMapping
    public ResponseEntity<?> deleteSelfUser(JwtAuthenticationToken token) {
        String username = token.getName();
        if (service.isAdmin(username)) {
            Message responseBody = new Message(HttpStatus.FORBIDDEN, "Admins can't delete admin accounts, even if it is your account");
            return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);
        }
        service.removeUser(token.getName());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        if (service.isAdmin(username)) {
            Message responseBody = new Message(HttpStatus.FORBIDDEN, "Admins can't delete admin accounts, even if it is your account");
            return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);
        }
        service.removeUser(username);
        return ResponseEntity.noContent().build();
    }

}
