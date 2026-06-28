package com.example.otclients.services.command.configuration;

import com.example.otclients.dto.request.configuration.CountryRequest;
import com.example.otclients.dto.response.configuration.CountryData;
import com.example.otclients.dto.response.configuration.CountryResponse;
import com.example.otclients.exceptions.InvalidDataFormat;
import com.example.otclients.exceptions.ResourceExistsException;
import com.example.otclients.exceptions.ResourceNotFoundException;
import com.example.otclients.models.configurations.Country;
import com.example.otclients.repositories.configuration.CountryRepository;
import com.example.otclients.services.BaseService;
import com.example.otclients.services.query.configuration.CountryQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CountryCommandService extends BaseService<Country, Long> {

    private final CountryRepository repository;
    private final CountryQueryService countryQueryService;

    @Override
    protected JpaRepository<Country, Long> getRepository() {
        return repository;
    }

    public void bootstrap() {
        for (Country.CountryEnum value : Country.CountryEnum.values()) {
            Optional<Country> toSave =
                    repository.findByAnyIdentifier(true, value.getCountryCode());
            if (toSave.isEmpty()) {
                saveAndFlush(Country.builder()
                        .name(value.getName())
                        .countryCode(value.getCountryCode()).placeId(value.getPlaceId())
                        .enabled(true)
                        .build());
            }
        }
        log.info("Country bootstrap done");
    }

    public CountryResponse save(CountryRequest request) {

        Country saveObject = get(request.id()).orElse(null);

        if (StringUtils.isBlank(request.name())) {
            throw new InvalidDataFormat("The name can not be blank");
        }

        if (StringUtils.isBlank(request.code())) {
            throw new InvalidDataFormat("The code can not be blank");
        }

        if (StringUtils.isBlank(request.placeId())) {
            throw new InvalidDataFormat("The placeId can not be blank");
        }

        if(saveObject != null && !saveObject.getId().equals(request.id())) {

            if(countryQueryService.findByAnyIdentifier(true, request.name()).isPresent()) {
                throw new ResourceExistsException("A country with the value " + request.name() + " already exists.");
            }

            if(countryQueryService.findByAnyIdentifier(true, request.code()).isPresent()) {
                throw new ResourceExistsException("A country with the value " + request.code() + " already exists.");
            }

            if(countryQueryService.findByAnyIdentifier(true, request.placeId()).isPresent()) {
                throw new ResourceExistsException("A country with the value " + request.placeId() + " already exists.");
            }
        }

        saveObject = Country.builder()
                .name(request.name())
                .countryCode(request.code())
                .placeId(request.placeId())
                .build();
        saveObject = saveAndFlush(saveObject);

        return generateResponse(saveObject);
    }

    public CountryResponse delete(Long id) {
        Optional<Country> header = get(id);
        if (header.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Object by ID [" + id + "] does not exist");
        }

        disable(header.get());

        return generateResponse(header.get());
    }

    private CountryResponse generateResponse(Country country) {
        return CountryResponse.builder()
                .data(CountryData.builder()
                        .id(country.getId()).name(country.getName())
                        .placeId(country.getPlaceId())
                        .code(country.getCountryCode()).build())
                .build();
    }
}
