package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient client;

    @Test
    void getBeerById() {
        BeerDto beerDto = client.getBeerById(UUID.randomUUID());
        assertNotNull(beerDto);
        assertEquals(beerDto.getBeerName(), "Lucky Jack");
    }

    @Test
    void testCreateBeer() {
        BeerDto beerDto = BeerDto.builder().beerName("Easy").beerStyle("New England IPA").build();
        URI uri = client.createBeer(beerDto);

        assertNotNull(uri);

        System.out.println("URI: "+uri.toString());
    }

    @Test
    void testUpdateBeer() {
        BeerDto beerDto = BeerDto.builder().beerName("Easy").beerStyle("New England IPA").build();

        client.updateBeer(UUID.randomUUID(), beerDto);
    }

}