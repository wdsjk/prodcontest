package ru.prodcontest.repository.country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.prodcontest.domain.country.Country;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Query(value = "select * from countries order by alpha2", nativeQuery = true)
    Optional<List<Country>> findAllCountries();

    Optional<List<Country>> findAllByRegion(String region);

    Optional<Country> findByAlpha2(String alpha2);
}