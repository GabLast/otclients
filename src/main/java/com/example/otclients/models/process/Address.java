package com.example.otclients.models.process;

import com.example.otclients.models.BaseModel;
import com.example.otclients.models.configurations.Country;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class Address extends BaseModel {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Client client;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Country country;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String fullName;
    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String phoneNumber;
    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String street;
    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String building;
    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String city;
    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String stateProvince;
    @Column(columnDefinition = "varchar(25)", nullable = false)
    private String zipCode;
}
