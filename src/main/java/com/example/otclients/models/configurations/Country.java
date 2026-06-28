package com.example.otclients.models.configurations;

import com.example.otclients.models.BaseModel;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public class Country extends BaseModel {

    @Getter
    @AllArgsConstructor
    public enum CountryEnum {
        USA("United States of America", "US", "ChIJCzYy5IS16lQRQrfeQ5K5Oxw"),
        CANADA("Canada", "CA", "ChIJ2WrMN9MDDUsRpY9Doiq3aJk");

        private final String name;
        private final String countryCode;
        private final String placeId;
    }

    private String name;
    private String countryCode;
    private String placeId;
}
