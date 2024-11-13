package br.sergio.bakbata_mansion.repository;

import br.sergio.bakbata_mansion.user.GameUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<GameUser, String> {
}
