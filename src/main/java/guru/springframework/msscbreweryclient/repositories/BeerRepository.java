package guru.springframework.msscbreweryclient.repositories;

import guru.springframework.msscbreweryclient.domain.Beer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

}
