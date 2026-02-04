package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    @NotEmpty
    @Size(min = 20, message = "Country name should be at-least 20 characters length")
    private String Country;

    @NotEmpty
    @Size(min = 2, message = "Country code should be at-least 2 characters length")
    private String CountryCode;

    @NotEmpty
    @Size(min = 10, message = "Street name should be at-least 10 characters length")
    private String street;

    @NotEmpty
    @Size(min = 10, message = "building name should be at-least 10 characters length")
    private String buildingName;

    @NotEmpty
    @Size(min = 10, message = "City name should be at-least 10 characters length")
    private String city;

    @NotEmpty
    @Size(min = 3 , max = 6, message = "Pin code should be max 6 digit length")
    private Integer pinCode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "address")
    private List<User> users = new ArrayList<>();

    public Address(String country, String countryCode, String street, String buildingName, String city, Integer pinCode) {
        Country = country;
        CountryCode = countryCode;
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.pinCode = pinCode;
    }
}
