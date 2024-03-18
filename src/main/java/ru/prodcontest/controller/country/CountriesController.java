package ru.prodcontest.controller.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.prodcontest.domain.country.Country;
import ru.prodcontest.exception.country.CountryNotFoundException;
import ru.prodcontest.exception.country.InvalidRegionException;
import ru.prodcontest.service.country.CountryService;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/countries")
public class CountriesController {
    private final CountryService countryService;

    @Autowired
    public CountriesController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<Collection<Country>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @GetMapping(params = "region")
    public ResponseEntity<Collection<Country>> getAllCountries(@RequestParam ArrayList<String> region) throws InvalidRegionException {
        return ResponseEntity.ok(countryService.getAllCountries(region));
    }

    @GetMapping("/{alpha2}")
    public ResponseEntity<Country> getCountry(@PathVariable String alpha2) throws CountryNotFoundException {
        Country country = countryService.getCountryByAlpha2(alpha2.toUpperCase());

        return ResponseEntity.ok(country);
    }
}