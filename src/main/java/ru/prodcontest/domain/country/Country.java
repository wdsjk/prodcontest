package ru.prodcontest.domain.country;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "countries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Country {
    @Id
    @JsonIgnore
    private Integer id;

    private String name;

    private String alpha2;

    private String alpha3;

    private String region;

    public Country(String name, String alpha2, String alpha3, String region) {
        this.name = name;
        this.alpha2 = alpha2;
        this.alpha3 = alpha3;
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        return region.equals(country.region);
    }

    @Override
    public int hashCode() {
        return region.hashCode();
    }
}
