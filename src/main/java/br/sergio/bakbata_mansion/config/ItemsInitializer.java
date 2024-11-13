package br.sergio.bakbata_mansion.config;

import br.sergio.bakbata_mansion.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ItemsInitializer implements ApplicationRunner {

    private ItemService service;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        service.initializeItems();
    }

}
