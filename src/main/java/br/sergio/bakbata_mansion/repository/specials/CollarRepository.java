package br.sergio.bakbata_mansion.repository.specials;

import br.sergio.bakbata_mansion.sheet.Collar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollarRepository extends JpaRepository<Collar, Long> {
}
