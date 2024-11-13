package br.sergio.bakbata_mansion.user;

import br.sergio.bakbata_mansion.sheet.CharacterSheet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CollectionType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class GameUser implements UserDetails {

    @Id
    @Column(length = 36, nullable = false, updatable = false, unique = true)
    private String username;

    @Setter
    @Column(length = 128, nullable = false)
    private String password;

    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CharacterSheet> sheets;

    @ElementCollection
    private Set<String> authorities;

    public GameUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.sheets = new ArrayList<>();
        this.authorities = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(SimpleGrantedAuthority::new).toList();
    }

    public Set<String> getEffectiveAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
