package br.sergio.bakbata_mansion.repository.specials;

import br.sergio.bakbata_mansion.sheet.Bracelet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BraceletRepository extends JpaRepository<Bracelet, Long> {
}
