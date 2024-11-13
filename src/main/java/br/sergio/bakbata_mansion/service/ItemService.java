package br.sergio.bakbata_mansion.service;

import br.sergio.bakbata_mansion.repository.specials.*;
import br.sergio.bakbata_mansion.sheet.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Service
@AllArgsConstructor
public class ItemService {

    private ItemKindRepository itemKindRepository;
    private RingRepository ringRepository;
    private BraceletRepository braceletRepository;
    private CollarRepository collarRepository;
    private AmuletRepository amuletRepository;

    @Transactional
    public void initializeItems() {
        InputStream stream = getClass().getResourceAsStream("/items.csv");
        if (stream == null) {
            throw new NullPointerException("items.csv not present in classpath");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            List<String> lines = reader.lines().filter(line -> !line.isEmpty()).toList();

            List<Ring> rings = new ArrayList<>();
            List<Bracelet> bracelets = new ArrayList<>();
            List<Collar> collars = new ArrayList<>();
            List<Amulet> amulets = new ArrayList<>();
            List<ItemKind> items = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split("#");
                switch (parts[0]) {
                    case "Ring" -> rings.add(new Ring(parts[1], parts[2]));
                    case "Bracelet" -> bracelets.add(new Bracelet(parts[1], parts[2]));
                    case "Collar" -> collars.add(new Collar(parts[1], parts[2]));
                    case "Amulet" -> amulets.add(new Amulet(parts[1], parts[2]));
                    case "Item" -> items.add(new ItemKind(parts[1], parts[2]));
                    default -> throw new RuntimeException("Unexpected SpecialItem type: " + parts[0]);
                }
            }

            save(ringRepository, rings);
            save(braceletRepository, bracelets);
            save(collarRepository, collars);
            save(amuletRepository, amulets);
            save(itemKindRepository, items);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private <T> void save(JpaRepository<T, ?> repository, List<T> entities) {
        long count = repository.count();
        if (count == 0) {
            repository.saveAll(entities);
        } else if (count != entities.size()) {
            repository.deleteAll();
            repository.saveAll(entities);
        }
    }

}
