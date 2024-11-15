package br.sergio.bakbata_mansion.repository;

import br.sergio.bakbata_mansion.sheet.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbilityRepository extends JpaRepository<Ability, Long> {
}
