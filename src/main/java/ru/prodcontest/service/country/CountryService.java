package ru.prodcontest.service.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.prodcontest.domain.country.Country;
import ru.prodcontest.exception.country.CountryNotFoundException;
import ru.prodcontest.exception.country.InvalidRegionException;
import ru.prodcontest.repository.country.CountryRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Collection<Country> getAllCountries() {
        return countryRepository.findAllCountries().orElseThrow();
    }

    public Collection<Country> getAllCountries(Collection<String> region) throws InvalidRegionException {
        if (region.isEmpty())
            return countryRepository.findAllCountries().orElseThrow();

        List<Country> countries = new ArrayList<>();

        for (String reg : region) {
            Collection<Country> countriesByRegion = countryRepository.findAllByRegion(reg).orElseThrow(
                    () -> new InvalidRegionException(reg)
            );

            if (countriesByRegion.isEmpty())
                throw new InvalidRegionException(reg);

            countries.addAll(countriesByRegion);
        }

        countries.sort(Comparator.comparing(Country::getAlpha2));

        return countries;
    }

    public Country getCountryByAlpha2(String alpha2) throws CountryNotFoundException {
      return countryRepository.findByAlpha2(alpha2).orElseThrow(
              () -> new CountryNotFoundException(alpha2)
      );
    }
}