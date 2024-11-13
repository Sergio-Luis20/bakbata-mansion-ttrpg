package br.sergio.bakbata_mansion.repository;

import br.sergio.bakbata_mansion.sheet.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
}
