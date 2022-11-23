package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
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
    void createBeer() {
        BeerDto beerDto = BeerDto.builder().beerName("Easy").beerStyle("New England IPA").build();
        URI uri = client.createBeer(beerDto);

        assertNotNull(uri);

        System.out.println("URI: "+uri.toString());
    }

    @Test
    void updateBeer() {
        BeerDto beerDto = BeerDto.builder().beerName("Easy").beerStyle("New England IPA").build();

        client.updateBeer(UUID.randomUUID(), beerDto);
    }

    @Test
    void deleteBeer() {
        client.deleteBeer(UUID.randomUUID());
    }


    @Test
    void getCustomerById() {
        CustomerDto customerDto = client.getCustomerById(UUID.randomUUID());
        assertNotNull(customerDto);
        assertEquals(customerDto.getName(), "Bart Simpson");
        System.out.println("Customer: "+customerDto);
    }

    @Test
    void createCustomer() {
        CustomerDto customerDto = CustomerDto.builder().name("Lisa Simpson").build();
        URI uri = client.createCustomer(customerDto);

        assertNotNull(uri);

        System.out.println("URI: "+uri.toString());
    }

    @Test
    void updateCustomer() {
        CustomerDto customerDto = CustomerDto.builder().name("Lisa Simpson").build();

        client.updateCustomer(UUID.randomUUID(), customerDto);
    }

    @Test
    void deleteCustomer() {
        client.deleteCustomer(UUID.randomUUID());
    }

}